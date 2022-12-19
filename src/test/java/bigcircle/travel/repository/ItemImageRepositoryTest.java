package bigcircle.travel.repository;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.memory.AddressMemoryRepository;
import bigcircle.travel.repository.memory.FileMemoryRepository;
import bigcircle.travel.repository.memory.ItemImageMemoryRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemImageRepositoryTest {

    @Autowired
    private ItemImageRepository repository;

    //연관관계가 묶여 있어 다른 저장소도 필요
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    void afterEach(){
        if(this.repository instanceof ItemImageMemoryRepository){
            ((ItemImageMemoryRepository)this.repository).clear();
        }
        if(this.fileRepository instanceof FileMemoryRepository){
            ((FileMemoryRepository)this.fileRepository).clear();
        }
        if(this.itemRepository instanceof ItemMemoryRepository){
            ((ItemMemoryRepository)this.itemRepository).clear();
        }
        if(this.addressRepository instanceof AddressMemoryRepository){
            ((AddressMemoryRepository)this.addressRepository).clear();
        }
    }

    @Test
    @DisplayName("저장")
    public void save(){
        //given
        String storeFileName = UUID.randomUUID().toString().substring(0,30);
        UploadFile uploadFile = new UploadFile("이미지.png", storeFileName);
        this.fileRepository.saveFile(uploadFile);

        Address address = new Address(1234, "주소 어딘가");
        this.addressRepository.save(address);

        ItemSaveDto itemSaveDto = new ItemSaveDto("내집", storeFileName,1234, "내집", "설명", Category.ETC.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        Long itemId = this.itemRepository.save(itemSaveDto);

        //when & then
        ItemImage itemImage = new ItemImage(itemId, storeFileName);
        this.repository.saveItemImage(itemImage);
    }

    @Test
    @DisplayName("save & findByItemId")
    public void saveAndFind(){
        //given
        String storeFileName = UUID.randomUUID().toString().substring(0,30);
        UploadFile uploadFile = new UploadFile("이미지.png", storeFileName);
        this.fileRepository.saveFile(uploadFile);

        Address address = new Address(1234, "주소 어딘가");
        this.addressRepository.save(address);

        ItemSaveDto itemSaveDto = new ItemSaveDto("내집", storeFileName,1234, "내집", "설명", Category.ETC.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        Long itemId = this.itemRepository.save(itemSaveDto);

        //when
        ItemImage itemImage = new ItemImage(itemId, storeFileName);
        this.repository.saveItemImage(itemImage);

        //then
        List<ItemImage> byItemId = this.repository.findByItemId(itemId);
        Assertions.assertThat(byItemId.size()).isEqualTo(1);
        ItemImage itemImage1 = byItemId.get(0);
        Assertions.assertThat(itemImage1).isEqualTo(itemImage);

    }
}