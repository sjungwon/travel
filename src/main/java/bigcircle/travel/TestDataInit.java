package bigcircle.travel;


import bigcircle.travel.hotel.repository.HotelCreateDto;
import bigcircle.travel.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final HotelRepository repository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        repository.save(new HotelCreateDto("가나다 호텔 강남점",12345, "가나다","", 10, "5성급"));
        repository.save(new HotelCreateDto("가나다 호텔 강서점",12344,"라마바","", 20,"5성급"));
    }
}
