package bigcircle.travel.service;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Item;
import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.exception.NotFoundException;
import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.repository.*;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.service.dto.ItemDto;
import bigcircle.travel.service.dto.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;
    private final ItemImageRepository itemImageRepository;

    private final FileStore fileStore;

    public ItemService(ItemRepository itemRepository, AddressRepository addressRepository, FileRepository fileRepository, ItemImageRepository itemImageRepository, FileStore fileStore) {
        this.itemRepository = itemRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
        this.itemImageRepository = itemImageRepository;
        this.fileStore = fileStore;
    }

    public Item getItem(Long id){
        Optional<Item> byId = this.itemRepository.findById(id);
        if(byId.isEmpty()){
            throw new NotFoundException(id + " item not found");
        }

        return byId.get();
    }

    public List<Item> getItems(){
        return this.itemRepository.findAll();
    }

    @Transactional
    public Long save(ItemDto itemFormPureDto){
        //주소 저장
        Address address = itemFormPureDto.getAddress();
        addressRepository.save(address);

        //사진 저장
        List<UploadFile> uploadFiles = itemFormPureDto.getItemImages();
        if(uploadFiles != null) {
            for (UploadFile uploadFile : uploadFiles) {
                fileRepository.saveFile(uploadFile);
            }
        }

        //item 저장 DTO 생성 및 저장
        String now = LocalDateTime.now().toString();
        ItemSaveDto itemSaveDto = new ItemSaveDto(itemFormPureDto.getTitle(), uploadFiles != null ? uploadFiles.get(0).getStoreFileName() : null, address.getZonecode(), itemFormPureDto.getAddressDetail(), itemFormPureDto.getDescription(), itemFormPureDto.getCategory().getId(), now, now);
        Long itemId = itemRepository.save(itemSaveDto);

        //사진 연관 관계 저장
        if(uploadFiles != null && uploadFiles.size() > 0){
            //이미지 정보 저장
            for (UploadFile uploadFile : uploadFiles) {
                itemImageRepository.saveItemImage(new ItemImage(itemId, uploadFile.getStoreFileName()));
            }
        }

        return itemId;
    }

    @Transactional
    public void update(ItemUpdateDto itemUpdateDto) {
        Long id = itemUpdateDto.getId();

        //없으면 repository에서 예외 던져짐
        Optional<Item> byId = this.itemRepository.findById(id);

        if(byId.isEmpty()){
            throw new NotFoundException();
        }

        Item item = byId.get();

        //주소 변경 가능성이 있으므로 저장
        //동일 주소 저장은 무시함
        Address address = itemUpdateDto.getAddress();
        addressRepository.save(address);

        //사진 변경 처리 어떻게 할지 생각하기
        //일단 사진은 일괄로만 변경 가능하게 구현
        List<UploadFile> uploadFiles = itemUpdateDto.getItemImages();

        if(uploadFiles != null && uploadFiles.size() > 0){
            //이전 사진 제거
            List<ItemImage> itemImages = item.getItemImages();
            for (ItemImage itemImage : itemImages) {
                String storeFileName = itemImage.getStoreFileName();
                this.fileStore.deleteFile(storeFileName);
                this.fileRepository.deleteFile(storeFileName);
                this.itemImageRepository.deleteByStoreFileName(storeFileName);
            }

            //새로 업로드된 사진 정보 저장 - 컨트롤러에서 파일은 저장했음
            for (UploadFile uploadFile : uploadFiles) {
                fileRepository.saveFile(uploadFile);
                //사진 연관 관계 저장
                itemImageRepository.saveItemImage(new ItemImage(id, uploadFile.getStoreFileName()));
            }
        }

        //item 저장 DTO 생성 및 저장
        String currentDateTime = LocalDateTime.now().toString();
        ItemSaveDto itemSaveDto = new ItemSaveDto(itemUpdateDto.getTitle(), (uploadFiles != null && uploadFiles.size() > 0) ? uploadFiles.get(0).getStoreFileName() : item.getThumbnail(), address.getZonecode(), itemUpdateDto.getAddressDetail(), itemUpdateDto.getDescription(), itemUpdateDto.getCategory().getId(), currentDateTime, currentDateTime);
        itemRepository.update(id, itemSaveDto);
    }

    public void delete(Long id){
        itemRepository.delete(id);
    }
}
