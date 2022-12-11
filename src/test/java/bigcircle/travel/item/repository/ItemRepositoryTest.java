package bigcircle.travel.item.repository;

import bigcircle.travel.repository.memory.CategoryEnumRepository;
import bigcircle.travel.repository.memory.AddressMemoryRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;

@Slf4j
class ItemRepositoryTest {

    private final ItemRepository repository;

    public ItemRepositoryTest() {
        this.repository = new ItemMemoryRepository(new AddressMemoryRepository(),new CategoryEnumRepository());
    }

    @AfterEach
    void afterEach(){
        if(this.repository instanceof ItemMemoryRepository){
            ((ItemMemoryRepository)this.repository).clear();
        }
    }

//    @Test
//    @DisplayName("save & findById")
//    void saveAndFindById() {
//        //given
//        ItemCreateDto item = new ItemCreateDto("가나다 호텔", 1234, "상세주소", "5성급 최고급 호텔", UUID.randomUUID().toString());
//        String id = this.repository.save(item);
//
//        //then
//        ItemDto byId = this.repository.findById(id);
//        log.info("itemdto = {}",byId);
//        assertThat(byId.getId()).isEqualTo(id);
//        assertThat(byId.getDescription()).isEqualTo(item.getDescription());
//        assertThat(byId.getAddress()).isNull();
//        assertThat(byId.getAddressDetail()).isEqualTo(item.getAddressDetail());
//        assertThat(byId.getTitle()).isEqualTo(item.getTitle());
//    }
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