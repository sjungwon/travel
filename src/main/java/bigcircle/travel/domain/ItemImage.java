package bigcircle.travel.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
public class ItemImage {
    private final Long itemId;
    private final String storeFileName;

    public ItemImage(Long itemId, String storeFileName) {
        this.itemId = itemId;
        this.storeFileName = storeFileName;
    }
}
