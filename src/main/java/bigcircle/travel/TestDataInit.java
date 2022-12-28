package bigcircle.travel;


import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.service.dto.ItemDto;
import bigcircle.travel.web.dto.ItemFormDto;
import bigcircle.travel.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemService service;
    private final ItemRepository itemRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() throws IOException {
        log.info("test data init");
        if(itemRepository instanceof ItemMemoryRepository){
            service.save(new ItemDto(Category.HOTEL, "가나다 호텔 강남점",new Address(12345, "가나다"),"상세 주소", "5성급", null));
            service.save(new ItemDto(Category.HOTEL, "가나다 호텔 강남점",new Address(12344, "라마바"),"상세 주소 2", "5성급", null));
        }
    }
}
