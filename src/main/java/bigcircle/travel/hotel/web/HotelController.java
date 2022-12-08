package bigcircle.travel.hotel.web;

import bigcircle.travel.hotel.domain.Hotel;
import bigcircle.travel.hotel.repository.HotelCreateDto;
import bigcircle.travel.hotel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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

    @GetMapping("/add")
    public String getHotelForm(Model model){
        HotelCreateDto hotelCreateDto = new HotelCreateDto();
        model.addAttribute("hotelCreateDto", hotelCreateDto);
        return "hotel-form";
    }

    @PostMapping("/add")
    public String saveHotel(@ModelAttribute @Validated HotelCreateDto hotelCreateDto, BindingResult bindingResult){
        log.info("hotelCreateDto={}",hotelCreateDto);

        if(bindingResult.hasErrors()){
            return "/hotel-form";
        }

        service.save(hotelCreateDto);

        return "redirect:/hotel";
    }
}
