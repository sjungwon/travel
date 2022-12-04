package bigcircle.travel.hotel.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelCreateDto {
    private String title;
    private String description;
    private String location;
    private int price;

    public HotelCreateDto() {
    }

    public HotelCreateDto(String title, String description, String location, int price) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
    }
}
