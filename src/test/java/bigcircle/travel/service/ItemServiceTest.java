package bigcircle.travel.service;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.Item;
import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.service.dto.ItemDto;
import bigcircle.travel.service.dto.ItemUpdateDto;
import bigcircle.travel.web.dto.ItemFormDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    void getItem(){
        //given
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("업로드 이미지1.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles.add(new UploadFile("업로드 이미지2.png", UUID.randomUUID().toString().substring(0,30)));
        ItemDto itemDto = new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles);
        Long save = this.itemService.save(itemDto);

        //when
        Item item = this.itemService.getItem(save);

        //then
        assertThat(item.getId()).isEqualTo(save);
        assertThat(item.getCategory()).isEqualTo(itemDto.getCategory());
        assertThat(item.getTitle()).isEqualTo(itemDto.getTitle());
        assertThat(item.getAddress()).isEqualTo(itemDto.getAddress());
        assertThat(item.getAddressDetail()).isEqualTo(itemDto.getAddressDetail());
        assertThat(item.getDescription()).isEqualTo(itemDto.getDescription());
        List<String> imageStoreFileNames = item.getImageStoreFileNames();
        Map<String, UploadFile> collect = uploadFiles.stream().collect(Collectors.toMap(UploadFile::getStoreFileName, e -> e));
        for (String imageStoreFileName : imageStoreFileNames) {
            UploadFile uploadFile = collect.get(imageStoreFileName);
            assertThat(uploadFile).isNotNull();
            log.info("uploadFile={}",uploadFile);
        }
    }

    @Test
    void getItems() {
        //given
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("업로드 이미지1.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles.add(new UploadFile("업로드 이미지2.png", UUID.randomUUID().toString().substring(0,30)));
        ItemDto itemDto = new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles);
        Long save = this.itemService.save(itemDto);

        List<UploadFile> uploadFiles2 = new ArrayList<>();
        uploadFiles2.add(new UploadFile("업로드 이미지3.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles2.add(new UploadFile("업로드 이미지4.png", UUID.randomUUID().toString().substring(0,30)));
        ItemDto itemDto2 = new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles2);
        Long save2 = this.itemService.save(itemDto2);

        //when
        List<Item> items = this.itemService.getItems();

        //then
        assertThat(items.size()).isEqualTo(2);
    }

    @Test
    void save() {
        //given
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("업로드 이미지1.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles.add(new UploadFile("업로드 이미지2.png", UUID.randomUUID().toString().substring(0,30)));

        //when
        Long save = this.itemService.save(new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles));

        //then
        assertThat(save).isNotNull();
    }

    @Test
    void update() {
        //given
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("업로드 이미지1.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles.add(new UploadFile("업로드 이미지2.png", UUID.randomUUID().toString().substring(0,30)));
        Long save = this.itemService.save(new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles));

        //when
        List<UploadFile> uploadFiles2 = new ArrayList<>();
        uploadFiles2.add(new UploadFile("업로드 이미지3.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles2.add(new UploadFile("업로드 이미지4.png", UUID.randomUUID().toString().substring(0,30)));
        Address address = new Address(2345, "한국 어딘가 2");
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto(save, Category.ETC, "뉴 테스트 123", address, "상세 주소 2", "설명 234", uploadFiles2);
        this.itemService.update(itemUpdateDto);
        Item item = this.itemService.getItem(save);

        //then
        assertThat(item.getId()).isEqualTo(save);
        assertThat(item.getCategory()).isEqualTo(Category.ETC);
        assertThat(item.getTitle()).isEqualTo(itemUpdateDto.getTitle());
        assertThat(item.getAddress()).isEqualTo(address);
        assertThat(item.getAddressDetail()).isEqualTo(itemUpdateDto.getAddressDetail());
        assertThat(item.getDescription()).isEqualTo(itemUpdateDto.getDescription());
        Map<String, UploadFile> collect = uploadFiles2.stream().collect(Collectors.toMap(UploadFile::getStoreFileName, e -> e));
        List<String> imageStoreFileNames = item.getImageStoreFileNames();
        for (String imageStoreFileName : imageStoreFileNames) {
            UploadFile uploadFile = collect.get(imageStoreFileName);
            assertThat(uploadFile).isNotNull();
        }
    }

    @Test
    void delete() {
        //given
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new UploadFile("업로드 이미지1.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles.add(new UploadFile("업로드 이미지2.png", UUID.randomUUID().toString().substring(0,30)));
        ItemDto itemDto = new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles);
        Long save = this.itemService.save(itemDto);

        List<UploadFile> uploadFiles2 = new ArrayList<>();
        uploadFiles2.add(new UploadFile("업로드 이미지3.png", UUID.randomUUID().toString().substring(0,30)));
        uploadFiles2.add(new UploadFile("업로드 이미지4.png", UUID.randomUUID().toString().substring(0,30)));
        ItemDto itemDto2 = new ItemDto(Category.HOTEL, "테스트 123", new Address(1234, "한국 어딘가"), "상세 주소", "설명 123", uploadFiles2);
        Long save2 = this.itemService.save(itemDto2);

        List<Item> items = this.itemService.getItems();
        assertThat(items.size()).isEqualTo(2);

        //when
        this.itemService.delete(save);

        //then
        List<Item> items1 = this.itemService.getItems();
        assertThat(items1.size()).isEqualTo(1);
    }
}