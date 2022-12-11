package bigcircle.travel.repository.dto;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.CategoryType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ItemDto {
    private Long id;
    private String title;

    private CategoryType category;
    private Address address;
    private String addressDetail;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;

    public ItemDto() {
    }

    public ItemDto(Long id, String title, CategoryType category, Address address, String addressDetail, String description, LocalDateTime createdAt, LocalDateTime lastUpdate) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setId(Long id) {
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

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
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
