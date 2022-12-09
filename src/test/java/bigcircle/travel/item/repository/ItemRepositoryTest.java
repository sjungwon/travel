package bigcircle.travel.item.repository;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.AddressRepository;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemFormDto;
import bigcircle.travel.repository.memory.AddressMemoryRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class ItemRepositoryTest {

    private final ItemRepository repository;

    public ItemRepositoryTest() {
        this.repository = new ItemMemoryRepository(new AddressMemoryRepository());
    }

    @AfterEach
    void afterEach(){
        if(this.repository instanceof ItemMemoryRepository){
            ((ItemMemoryRepository)this.repository).clear();
        }
    }

    @Test
    @DisplayName("save & findById")
    void saveAndFindById() {
        //given
        ItemFormDto item = new ItemFormDto("가나다 호텔", 1234, "서울 어딘가", "상세주소", "5성급 최고급 호텔");
        String id = this.repository.save(item);

        //then
        ItemDto byId = this.repository.findById(id);
        log.info("itemdto = {}",byId);
        assertThat(byId.getId()).isEqualTo(id);
        assertThat(byId.getDescription()).isEqualTo(item.getDescription());
        //이건 addressRepository 책임
//        assertThat(byId.getAddress().getZonecode()).isEqualTo(item.getZonecode());
//        assertThat(byId.getAddress().getAddress()).isEqualTo(item.getAddress());
        assertThat(byId.getAddress()).isNull();
        assertThat(byId.getAddressDetail()).isEqualTo(item.getAddressDetail());
        assertThat(byId.getTitle()).isEqualTo(item.getTitle());
    }

    @Test
    void findAll() {
        ItemFormDto item = new ItemFormDto("가나다 호텔", 1234, "서울 어딘가", "상세주소",  "5성급 최고급 호텔");
        ItemFormDto item2 = new ItemFormDto("가나다 호텔2", 1235, "경기 어딘가", "상세주소", "5성급 최고급 호텔");

        this.repository.save(item);
        this.repository.save(item2);

        List<ItemDto> all = this.repository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

}