package bigcircle.travel.item.repository;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemCreateDto {
    @NotBlank
    private String title;

    @NotNull
    private Integer zonecode;

    @NotBlank
    private String address;

    @NotBlank
    private String addressDetail;
    @NotNull
    @Range(min = 100)
    private Integer price;

    @NotBlank
    private String description;

    public ItemCreateDto() {
    }

    public ItemCreateDto(String title, int zonecode, String address, String addressDetail, int price, String description) {
        this.title = title;
        this.zonecode = zonecode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "HotelCreateDto{" +
                "title='" + title + '\'' +
                ", zonecode=" + zonecode +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
