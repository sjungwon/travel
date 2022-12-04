package bigcircle.travel.hotel.repository;

import bigcircle.travel.hotel.domain.Hotel;

import java.util.*;

public interface HotelRepository {
    String save(HotelCreateDto item);

    Hotel findById(String id);

    List<Hotel> findAll();

    void clear();
}
