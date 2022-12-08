package bigcircle.travel.item.domain;

public class Item {

    private String id;
    private String title;

    private int zonecode;
    private String address;
    private String addressDetail;
    private int price;

    private String description;

    public Item() {
    }

    public Item(String id, String title, int zonecode, String address, String addressDetail, int price, String description) {
        this.id = id;
        this.title = title;
        this.zonecode = zonecode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.price = price;
        this.description = description;
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

    public int getZonecode() {
        return zonecode;
    }

    public String getAddress() {
        return address;
    }

    public String getAddressDetail() {
        return addressDetail;
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

    public void setZonecode(int zonecode) {
        this.zonecode = zonecode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
