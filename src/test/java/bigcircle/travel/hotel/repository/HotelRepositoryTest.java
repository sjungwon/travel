package bigcircle.travel.hotel.repository;

import bigcircle.travel.hotel.domain.Hotel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HotelRepositoryTest {

    private final HotelRepository repository;

    public HotelRepositoryTest() {
        this.repository = new HotelMemoryRepository();
    }

    @AfterEach
    void afterEach(){
        if(this.repository instanceof HotelMemoryRepository){
            ((HotelMemoryRepository)this.repository).clear();
        }
    }

    @Test
    @DisplayName("save & findById")
    void saveAndFindById() {
        HotelCreateDto item = new HotelCreateDto("가나다 호텔", 1234, "서울 어딘가", "상세주소", 30000, "5성급 최고급 호텔");
        String id = this.repository.save(item);
        Hotel byId = this.repository.findById(id);
        assertThat(byId.getId()).isEqualTo(id);
        assertThat(byId.getDescription()).isEqualTo(item.getDescription());
        assertThat(byId.getAddress()).isEqualTo(item.getAddress());
        assertThat(byId.getPrice()).isEqualTo(item.getPrice());
        assertThat(byId.getTitle()).isEqualTo(item.getTitle());
    }

    @Test
    void findAll() {
        HotelCreateDto item = new HotelCreateDto("가나다 호텔", 1234, "서울 어딘가", "상세주소", 30000, "5성급 최고급 호텔");
        HotelCreateDto item2 = new HotelCreateDto("가나다 호텔2", 1235, "경기 어딘가", "상세주소", 30000, "5성급 최고급 호텔");

        this.repository.save(item);
        this.repository.save(item2);

        List<Hotel> all = this.repository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

}