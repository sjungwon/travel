package bigcircle.travel.address.domain;

import java.time.LocalDateTime;


/**
 * PK - zoncode
 */
public class Address {
    private Integer zonecode;
    private String address;
    private LocalDateTime created_at;
    private LocalDateTime last_update;

    public Address() {
    }

    public Address(Integer zonecode, String address, LocalDateTime created_at, LocalDateTime last_update) {
        this.zonecode = zonecode;
        this.address = address;
        this.created_at = created_at;
        this.last_update = last_update;
    }

    public Integer getZonecode() {
        return zonecode;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }


}
