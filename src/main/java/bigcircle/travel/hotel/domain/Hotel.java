package bigcircle.travel.hotel.domain;

public class Hotel {

    private String id;
    private String title;
    private String description;

    private String location;
    private int price;

    public Hotel() {
    }

    public Hotel(String id, String title, String description, String location,int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation(){
        return location;
    }

    public int getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
