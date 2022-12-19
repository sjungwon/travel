package bigcircle.travel.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


/**
 * PK - zonecode
 */
@EqualsAndHashCode
@Getter
@ToString
public class Address {

    private final Integer zonecode;
    private final String address;

    public Address(Integer zonecode, String address) {
        this.zonecode = zonecode;
        this.address = address;
    }
}
