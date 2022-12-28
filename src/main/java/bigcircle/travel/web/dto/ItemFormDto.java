package bigcircle.travel.web.dto;

import bigcircle.travel.domain.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
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

    @NotNull
    private Category category;

    private List<MultipartFile> itemImages;

    public ItemFormDto() {
    }

    public ItemFormDto(String title, Integer zonecode, String address, String addressDetail, String description, Category category, List<MultipartFile> itemImages) {
        this.title = title;
        this.zonecode = zonecode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.category = category;
        this.itemImages = itemImages;
    }
}
