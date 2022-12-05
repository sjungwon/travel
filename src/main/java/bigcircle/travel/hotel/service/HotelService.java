package bigcircle.travel.hotel.service;

import bigcircle.travel.hotel.domain.Hotel;
import bigcircle.travel.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public List<Hotel> getHotels(){
        List<Hotel> all = this.repository.findAll();
        return all;
    }

}
