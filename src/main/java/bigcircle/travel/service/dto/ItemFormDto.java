package bigcircle.travel.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {
    @NotBlank
    private String title;

    @NotNull
    private Integer zonecode;

    @NotBlank
    private String address;

    @NotBlank
    private String addressDetail;
    @NotBlank
    private String description;

    @NotBlank
    private String categoryTitle;

    private List<MultipartFile> itemImages;

    public ItemFormDto() {
    }

    public ItemFormDto(String title, Integer zonecode, String address, String addressDetail, String description, String categoryTitle, List<MultipartFile> itemImages) {
        this.title = title;
        this.zonecode = zonecode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.categoryTitle = categoryTitle;
        this.itemImages = itemImages;
    }

    @Override
    public String toString() {
        return "ItemFormDto{" +
                "title='" + title + '\'' +
                ", zonecode=" + zonecode +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", description='" + description + '\'' +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", itemImages=" + itemImages +
                '}';
    }
}
