package bigcircle.travel.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * PK - id
 * FK - zonecode
 * FK - categoryId
 */
@Getter
@EqualsAndHashCode
@ToString
public class Item {

    private final Long id;
    private final String title;

    private final String thumbnail;

    private final Integer zonecode;
    private final String addressDetail;
    private final String description;

    private final Long categoryId;

    private final String createdAt;

    private final String updatedAt;


    public Item(Long id, String title, String thumbnail, Integer zonecode, String addressDetail, String description, Long categoryId, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.zonecode = zonecode;
        this.addressDetail = addressDetail;
        this.description = description;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
