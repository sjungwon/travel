package bigcircle.travel.repository.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    public ItemFormDto() {
    }

    public ItemFormDto(String title, int zonecode, String address, String addressDetail,String description) {
        this.title = title;
        this.zonecode = zonecode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.description = description;
    }

    @Override
    public String toString() {
        return "HotelCreateDto{" +
                "title='" + title + '\'' +
                ", zonecode=" + zonecode +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
