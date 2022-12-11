package bigcircle.travel.repository;

import bigcircle.travel.domain.Address;

public interface AddressRepository {
    public void save(Address address);

    public Address findByZonecode(int zonecode);

    public void update(Address address);
}
