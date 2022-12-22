package bigcircle.travel.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Item {

    private final Long id;
    private final String title;
    private final String thumbnail;

    private Category category;

    private Address address;
    private final String addressDetail;
    private final String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final String createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final String updatedAt;

    private final List<String> imageStoreFileNames;

    public Item(Long id, String title, String thumbnail, Category category, Address address, String addressDetail, String description, String createdAt, String updatedAt, List<String> imageStoreFileNames) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.category = category;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageStoreFileNames = imageStoreFileNames;
    }
}
