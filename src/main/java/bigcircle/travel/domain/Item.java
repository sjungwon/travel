package bigcircle.travel.domain;

import java.time.LocalDateTime;


/**
 * PK - id
 * FK - address(zonecode)
 */
public class Item {

    private String id;
    private String title;
    private int zonecode;
    private String addressDetail;
    private String description;

    private String createdAt;

    private String lastUpdate;

    public Item() {
    }

    public Item(String id, String title, int zonecode, String addressDetail, String description, String createdAt, String lastUpdate) {
        this.id = id;
        this.title = title;
        this.zonecode = zonecode;
        this.addressDetail = addressDetail;
        this.description = description;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
    }

    public String getId() {
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
