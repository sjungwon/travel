package bigcircle.travel.service.dto;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.UploadFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class ItemUpdateDto {
    private Long id;
    private Category category;
    private String title;
    private Address address;
    private String addressDetail;
    private String description;
    private List<UploadFile> itemImages;

    public ItemUpdateDto() {
    }

    public ItemUpdateDto(Long id, Category category, String title, Address address, String addressDetail, String description, List<UploadFile> itemImages) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.itemImages = itemImages;
    }
}
