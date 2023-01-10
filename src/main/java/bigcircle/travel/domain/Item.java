package bigcircle.travel.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Item {

    private final Long id;
    private final String title;
    private final String thumbnail;

    private final Category category;

    private final Address address;
    private final String addressDetail;
    private final String description;

    private final List<ItemImage> itemImages;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;
}
