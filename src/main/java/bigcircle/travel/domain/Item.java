package bigcircle.travel.domain;


/**
 * PK - id
 * FK - address(zonecode)
 */
public class Item {

    private Long id;
    private String title;
    private int zonecode;
    private String addressDetail;
    private String description;

    private Long categoryId;

    private String createdAt;

    private String lastUpdate;


    public Item(Long id, String title, int zonecode, String addressDetail, String description, Long categoryId, String createdAt, String lastUpdate) {
        this.id = id;
        this.title = title;
        this.zonecode = zonecode;
        this.addressDetail = addressDetail;
        this.description = description;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getZonecode() {
        return zonecode;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }
}
