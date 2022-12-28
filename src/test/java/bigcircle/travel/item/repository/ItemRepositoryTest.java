package bigcircle.travel.item.repository;

import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.dto.ItemCreateDto;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    private final ItemRepository repository;

    public ItemRepositoryTest() {
        this.repository = new ItemMemoryRepository();
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
        ItemCreateDto item = new ItemCreateDto("가나다 호텔", 1234, "서울 어딘가", "상세주소", 30000, "5성급 최고급 호텔");
        String id = this.repository.save(item);
        Item byId = this.repository.findById(id);
        assertThat(byId.getId()).isEqualTo(id);
        assertThat(byId.getDescription()).isEqualTo(item.getDescription());
        assertThat(byId.getAddress()).isEqualTo(item.getAddress());
        assertThat(byId.getPrice()).isEqualTo(item.getPrice());
        assertThat(byId.getTitle()).isEqualTo(item.getTitle());
    }

    @Test
    void findAll() {
        ItemCreateDto item = new ItemCreateDto("가나다 호텔", 1234, "서울 어딘가", "상세주소", 30000, "5성급 최고급 호텔");
        ItemCreateDto item2 = new ItemCreateDto("가나다 호텔2", 1235, "경기 어딘가", "상세주소", 30000, "5성급 최고급 호텔");

        this.repository.save(item);
        this.repository.save(item2);

        List<Item> all = this.repository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

}