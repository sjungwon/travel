package bigcircle.travel.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemUpdateFormDto {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Integer zonecode;

    @NotBlank
    private String address;

    private String addressDetail;

    @NotBlank
    private String description;

    @NotBlank
    private String categoryTitle;

    public ItemUpdateFormDto() {
    }

    public ItemUpdateFormDto(Long id, String title, Integer zonecode, String address, String addressDetail, String description, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.zonecode = zonecode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
        this.categoryTitle = categoryTitle;
    }

    @Override
    public String toString() {
        return "ItemUpdateFormDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", zonecode=" + zonecode +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", description='" + description + '\'' +
                ", categoryTitle='" + categoryTitle + '\'' +
                '}';
    }
}
