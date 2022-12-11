package bigcircle.travel.domain;

import java.time.LocalDateTime;


/**
 * PK - zonecode
 */
public class Address {

    private Integer zonecode;
    private String address;

    public Address(Integer zonecode, String address) {
        this.zonecode = zonecode;
        this.address = address;
    }

    public Integer getZonecode() {
        return zonecode;
    }

    public String getAddress() {
        return address;
    }

}
