package bigcircle.travel.service;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.repository.*;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.service.dto.ItemFormDto;
import bigcircle.travel.service.dto.ItemUpdateFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    private final ItemImageRepository itemImageRepository;
    private final FileStore fileStore;

    public ItemService(ItemRepository itemRepository, AddressRepository addressRepository, CategoryRepository categoryRepository, FileRepository fileRepository, ItemImageRepository itemImageRepository, FileStore fileStore) {
        this.itemRepository = itemRepository;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        this.fileRepository = fileRepository;
        this.itemImageRepository = itemImageRepository;
        this.fileStore = fileStore;
    }

    public ItemDto getItem(Long id){return this.itemRepository.findById(id);}

    public List<ItemDto> getItems(){
        return this.itemRepository.findAll();
    }

    public Long save(ItemFormDto itemFormDto) throws IOException {
        //주소 저장
        Address address = new Address(itemFormDto.getZonecode(), itemFormDto.getAddress());
        addressRepository.save(address);


        //카테고리 가져옴
        Long categoryId = categoryRepository.findIdByKorean(itemFormDto.getCategoryTitle());

        //사진 저장
        List<MultipartFile> multipartFiles = itemFormDto.getItemImages();
        List<UploadFile> uploadFiles = null;
        if(multipartFiles != null && multipartFiles.size() > 0) {
            uploadFiles = fileStore.storeFiles(multipartFiles);
            fileRepository.saveFiles(uploadFiles);
        }

        //item 저장 DTO 생성 및 저장
        String currentDateTime = LocalDateTime.now().toString();
        ItemSaveDto itemSaveDto = new ItemSaveDto(itemFormDto.getTitle(), (uploadFiles != null && uploadFiles.size() > 0) ? uploadFiles.get(0).getStoreFileName() : null,itemFormDto.getZonecode(), itemFormDto.getAddressDetail(), itemFormDto.getDescription(), categoryId, currentDateTime, currentDateTime);
        Long itemId = itemRepository.save(itemSaveDto);

        //사진 연관 관계 저장
        if(uploadFiles != null && uploadFiles.size() > 0){
            //이미지 정보 저장
            List <ItemImage> itemImages = new ArrayList<>(uploadFiles.size());
            for (UploadFile uploadFile : uploadFiles) {
                itemImages.add(new ItemImage(itemId, uploadFile.getStoreFileName()));
            }
            itemImageRepository.saveItemImages(itemImages);
        }

        return itemId;
    }

    public void update(ItemUpdateFormDto itemUpdateFormDto){
        //주소 변경 가능성이 있으므로 저장
        Address address = new Address(itemUpdateFormDto.getZonecode(), itemUpdateFormDto.getAddress());
        addressRepository.save(address);

        //카테고리 id 가져옴
        Long categoryId = categoryRepository.findIdByKorean(itemUpdateFormDto.getCategoryTitle());

//        ItemSaveDto itemSaveDto = new ItemSaveDto(itemUpdateFormDto.getTitle(), itemUpdateFormDto.getZonecode(), itemUpdateFormDto.getAddressDetail(), itemUpdateFormDto.getDescription(), categoryId, new ArrayList<>());

        //사진 변경 처리 어떻게 할지 생각하기
        //일단 이건 차후에 구현
//        itemRepository.update(itemUpdateFormDto.getId(), itemSaveDto);
    }

    public void delete(Long id){
        itemRepository.delete(id);
    }
}
