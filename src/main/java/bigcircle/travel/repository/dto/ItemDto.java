package bigcircle.travel.repository.dto;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.domain.UploadFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
public class ItemDto {
    private Long id;
    private String title;

    private String thumbnail;
    private Category category;
    private Address address;
    private String addressDetail;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private List<ItemImage> images;

    public ItemDto() {
    }

    public ItemDto(Long id, String title, String thumbnail, Category category, Address address, String addressDetail, String description, LocalDateTime createdAt, LocalDateTime updatedAt, List<ItemImage> images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.images = images;
    }

}
