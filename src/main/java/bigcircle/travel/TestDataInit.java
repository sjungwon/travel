package bigcircle.travel;


import bigcircle.travel.service.dto.ItemFormDto;
import bigcircle.travel.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemService service;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        service.save(new ItemFormDto("가나다 호텔 강남점",12345, "가나다","", "5성급", "호텔"));
        service.save(new ItemFormDto("가나다 호텔 강서점",12344,"라마바","","5성급", "호텔"));
    }
}
