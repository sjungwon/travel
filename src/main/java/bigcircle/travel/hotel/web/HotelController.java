package bigcircle.travel.hotel.web;

import bigcircle.travel.hotel.domain.Hotel;
import bigcircle.travel.hotel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService service;

    public HotelController(HotelService service) {
        this.service = service;
    }

    @GetMapping
    public String getHotels(Model model){

        List<Hotel> hotels = service.getHotels();

        model.addAttribute("hotels", hotels);

        return "hotel";
    }
}
