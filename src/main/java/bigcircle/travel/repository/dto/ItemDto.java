package bigcircle.travel.repository.dto;

import bigcircle.travel.domain.Address;

public class ItemDto {
    private String id;
    private String title;
    private Address address;
    private String addressDetail;
    private String description;
    private String createdAt;
    private String lastUpdate;

    public ItemDto() {
    }

    public ItemDto(String id, String title, Address address, String addressDetail, String description, String createdAt, String lastUpdate) {
        this.id = id;
        this.title = title;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", address=" + address +
                ", addressDetail='" + addressDetail + '\'' +
                ", description='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
