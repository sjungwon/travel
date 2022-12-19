package bigcircle.travel.repository;

import bigcircle.travel.domain.Address;

public interface AddressRepository {
    public void save(Address address);

    public Address findByZonecode(Integer zonecode);
}
