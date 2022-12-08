package bigcircle.travel.address.respository;

public class AddressCreateDto {
    private Integer zonecode;

    private String address;

    public AddressCreateDto() {
    }

    public AddressCreateDto(Integer zonecode, String address) {
        this.zonecode = zonecode;
        this.address = address;
    }

    public Integer getZonecode() {
        return zonecode;
    }

    public String getAddress() {
        return address;
    }

    public void setZonecode(Integer zonecode) {
        this.zonecode = zonecode;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
