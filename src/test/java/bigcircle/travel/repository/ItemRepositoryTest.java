package bigcircle.travel.repository;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.memory.CategoryEnumRepository;
import bigcircle.travel.repository.memory.AddressMemoryRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        ItemDto item = this.repository.findById(id);

        //then
        assertThat(item.getId()).isEqualTo(id);
        assertThat(item.getTitle()).isEqualTo(itemSaveDto.getTitle());
        assertThat(item.getThumbnail()).isNull();
        assertThat(item.getAddress().getZonecode()).isEqualTo(address.getZonecode());
        assertThat(item.getAddress().getAddress()).isEqualTo(address.getAddress());
        assertThat(item.getAddressDetail()).isEqualTo(itemSaveDto.getAddressDetail());
        assertThat(item.getDescription()).isEqualTo(itemSaveDto.getDescription());
        assertThat(item.getImages().size()).isEqualTo(0);
        log.info("itemdto = {}",item);
    }
//
//    @Test
//    void findAll() {
//        ItemCreateDto item = new ItemCreateDto("가나다 호텔", 1234, "상세주소",  "5성급 최고급 호텔", UUID.randomUUID().toString());
//        ItemCreateDto item2 = new ItemCreateDto("가나다 호텔2", 1235, "상세주소", "5성급 최고급 호텔", UUID.randomUUID().toString());
//
//        this.repository.save(item);
//        this.repository.save(item2);
//
//        List<ItemDto> all = this.repository.findAll();
//        assertThat(all.size()).isEqualTo(2);
//    }

}