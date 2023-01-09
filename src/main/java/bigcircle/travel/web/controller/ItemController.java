package bigcircle.travel.web.controller;

import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.dto.ItemCreateDto;
import bigcircle.travel.service.ItemService;
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
    public String getItems(Model model){

        List<Item> items = service.getItems();

        model.addAttribute("items", items);

        return "item";
    }

    @GetMapping("/add")
    public String getItemForm(Model model){
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        model.addAttribute("itemCreateDto", itemCreateDto);
        return "item-form";
    }

    @PostMapping("/add")
    public String saveItem(@ModelAttribute @Validated ItemCreateDto itemCreateDto, BindingResult bindingResult){
        log.info("itemCreateDto={}", itemCreateDto);

        if(bindingResult.hasErrors()){
            return "/item-form";
        }

        service.save(itemCreateDto);

        return "redirect:/items";
    }
}
