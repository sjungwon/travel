package bigcircle.travel.item.web;

import bigcircle.travel.item.domain.Item;
import bigcircle.travel.item.repository.ItemCreateDto;
import bigcircle.travel.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public String getHotels(Model model){

        List<Item> items = service.getHotels();

        model.addAttribute("hotels", items);

        return "item";
    }

    @GetMapping("/add")
    public String getHotelForm(Model model){
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        model.addAttribute("hotelCreateDto", itemCreateDto);
        return "item-form";
    }

    @PostMapping("/add")
    public String saveHotel(@ModelAttribute @Validated ItemCreateDto itemCreateDto, BindingResult bindingResult){
        log.info("hotelCreateDto={}", itemCreateDto);

        if(bindingResult.hasErrors()){
            return "/hotel-form";
        }

        service.save(itemCreateDto);

        return "redirect:/items";
    }
}
