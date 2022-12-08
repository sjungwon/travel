package bigcircle.travel.hotel.repository;

import bigcircle.travel.hotel.domain.Hotel;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HotelMemoryRepository implements HotelRepository{
    private Map<String, Hotel> db;

    public HotelMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
    }

    public String save(HotelCreateDto item){
        String id = UUID.randomUUID().toString();
        Hotel hotel = new Hotel(id, item.getTitle(),item.getZonecode(), item.getAddress(), item.getAddressDetail(),item.getPrice(),item.getDescription());
        this.db.put(id, hotel);
        return id;
    }

    public Hotel findById(String id){
        Hotel hotel = this.db.get(id);

        return hotel;
    }

    public List<Hotel> findAll(){
        Set<String> keySet = this.db.keySet();

        List<Hotel> hotels = new ArrayList<>(this.db.size());

        for (String s : keySet) {
            hotels.add(this.db.get(s));
        }

        return hotels;
    }

    public void clear(){
        this.db.clear();
    }
}
