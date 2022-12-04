package bigcircle.travel.hotel.repository;

import bigcircle.travel.hotel.domain.Hotel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        HotelCreateDto item = new HotelCreateDto("메리어트 호텔", "5성급 최고급 호텔", "서울 강남", 30000);
        String id = this.repository.save(item);
        Hotel byId = this.repository.findById(id);
        assertThat(byId.getId()).isEqualTo(id);
        assertThat(byId.getDescription()).isEqualTo(item.getDescription());
        assertThat(byId.getLocation()).isEqualTo(item.getLocation());
        assertThat(byId.getPrice()).isEqualTo(item.getPrice());
        assertThat(byId.getTitle()).isEqualTo(item.getTitle());
    }

    @Test
    void findAll() {
        HotelCreateDto item = new HotelCreateDto("메리어트 호텔", "5성급 최고급 호텔", "서울 강남", 30000);
        HotelCreateDto item2 = new HotelCreateDto("메리어트 호텔2", "5성급 최고급 호텔", "서울 강남", 30000);
        this.repository.save(item);
        this.repository.save(item2);

        List<Hotel> all = this.repository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

}