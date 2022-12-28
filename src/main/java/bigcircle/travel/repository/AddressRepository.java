package bigcircle.travel.repository;

import bigcircle.travel.repository.dto.AddressCreateDto;

public interface AddressRepository {
    public void save(AddressCreateDto addressCreateDto);
}
