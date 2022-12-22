package bigcircle.travel.repository;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.memory.AddressMemoryRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    //연관관계 때문에 의존함
    @Autowired
    private AddressRepository addressRepository;


    @AfterEach
    void afterEach(){
        if(this.repository instanceof ItemMemoryRepository){
            ((ItemMemoryRepository)this.repository).clear();
        }
        if(this.repository instanceof AddressMemoryRepository){
            ((AddressMemoryRepository)this.repository).clear();
        }
    }

    @Test
    @DisplayName("save 정상 작동")
    void save(){
        Address address = new Address(1234, "내집");
        this.addressRepository.save(address);
        this.repository.save(
                new ItemSaveDto("내집", null,address.getZonecode(), "상세주소", "내집", Category.HOTEL.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString()));
    }

    @Test
    @DisplayName("save & findById - 저장 데이터 확인")
    void saveAndFindById() {
        //given
        Address address = new Address(1234, "내집");
        this.addressRepository.save(address);

        ItemSaveDto itemSaveDto = new ItemSaveDto("내집", null, address.getZonecode(), "상세주소", "내집", Category.HOTEL.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        Long id = this.repository.save(itemSaveDto);

        //when
        Item item = this.repository.findById(id);

        //then
        assertThat(item.getId()).isEqualTo(id);
        assertThat(item.getTitle()).isEqualTo(itemSaveDto.getTitle());
        assertThat(item.getThumbnail()).isNull();
        assertThat(item.getAddress().getZonecode()).isEqualTo(address.getZonecode());
        assertThat(item.getAddress().getAddress()).isEqualTo(address.getAddress());
        assertThat(item.getAddressDetail()).isEqualTo(itemSaveDto.getAddressDetail());
        assertThat(item.getDescription()).isEqualTo(itemSaveDto.getDescription());
        assertThat(item.getImageStoreFileNames().size()).isEqualTo(0);
        log.info("itemdto = {}",item);
    }
    @Test
    void findAll() {
        //given
        Address address = new Address(1234, "내집");
        this.addressRepository.save(address);

        ItemSaveDto itemSaveDto1 = new ItemSaveDto("내집", null, address.getZonecode(), "상세주소", "내집", Category.HOTEL.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        ItemSaveDto itemSaveDto2 = new ItemSaveDto("내집2", null, address.getZonecode(), "상세주소", "내집2", Category.HOTEL.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        this.repository.save(itemSaveDto1);
        this.repository.save(itemSaveDto2);

        //when
        List<Item> all = this.repository.findAll();

        //then
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void update(){
        //given
        Address address = new Address(1234, "내집");
        this.addressRepository.save(address);

        ItemSaveDto itemSaveDto = new ItemSaveDto("내집", null, address.getZonecode(), "상세주소", "내집", Category.HOTEL.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        Long id = this.repository.save(itemSaveDto);

        //when
        Address address2 = new Address(2345, "내집1");
        this.addressRepository.save(address2);

        ItemSaveDto itemSaveDto2 = new ItemSaveDto("내집1", null, address2.getZonecode(), "상세주소1", "내집", Category.ETC.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        this.repository.update(id, itemSaveDto2);

        Item item = this.repository.findById(id);

        //then
        assertThat(item.getId()).isEqualTo(id);
        assertThat(item.getTitle()).isEqualTo(itemSaveDto2.getTitle());
        assertThat(item.getThumbnail()).isNull();
        assertThat(item.getAddress().getZonecode()).isEqualTo(address2.getZonecode());
        assertThat(item.getAddress().getAddress()).isEqualTo(address2.getAddress());
        assertThat(item.getAddressDetail()).isEqualTo(itemSaveDto2.getAddressDetail());
        assertThat(item.getDescription()).isEqualTo(itemSaveDto2.getDescription());
        assertThat(item.getImageStoreFileNames().size()).isEqualTo(0);
    }

    @Test
    void delete(){
        //given
        Address address = new Address(1234, "내집");
        this.addressRepository.save(address);

        ItemSaveDto itemSaveDto = new ItemSaveDto("내집", null, address.getZonecode(), "상세주소", "내집", Category.HOTEL.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        Long id = this.repository.save(itemSaveDto);

        //when
        this.repository.delete(id);

        //then
        Item byId = this.repository.findById(id);
        assertThat(byId).isNull();

    }

}