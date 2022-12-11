package bigcircle.travel.web;

import bigcircle.travel.lib.PrefixViewPath;
import bigcircle.travel.lib.PrefixViewPathGenerator;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.service.dto.ItemFormDto;
import bigcircle.travel.service.ItemService;
import bigcircle.travel.service.dto.ItemUpdateFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;
    private final PrefixViewPath prefixViewPath;

    public ItemController(ItemService service, PrefixViewPathGenerator prefixViewPathGenerator) {
        this.service = service;
        this.prefixViewPath = prefixViewPathGenerator.prefixView("item");
    }

    @GetMapping("{id}")
    public String getItem(@PathVariable Long id, Model model){
        ItemDto item = service.getItem(id);

        model.addAttribute("item", item);

        return prefixViewPath.call("item");
    }

    @GetMapping
    public String getItems(Model model){

        List<ItemDto> items = service.getItems();

        model.addAttribute("items", items);

        return prefixViewPath.call("item-list");
    }

    @GetMapping("/save")
    public String getSaveForm(Model model){
        ItemFormDto itemFormDto = new ItemFormDto();
        model.addAttribute("itemFormDto", itemFormDto);
        return prefixViewPath.call("item-form");
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute @Validated ItemFormDto itemFormDto, BindingResult bindingResult){
        log.info("itemCreateDto={}", itemFormDto);

        if(bindingResult.hasErrors()){
            return prefixViewPath.call("/item-form");
        }

        Long id = service.save(itemFormDto);

        return "redirect:/items/" + id.toString();
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model){
        ItemDto itemDto = service.getItem(id);

        ItemUpdateFormDto itemUpdateFormDto = new ItemUpdateFormDto(itemDto.getId(), itemDto.getTitle(), itemDto.getAddress().getZonecode(), itemDto.getAddress().getAddress(), itemDto.getAddressDetail(), itemDto.getDescription(), itemDto.getCategory().getKorean());

        model.addAttribute("id",id);
        model.addAttribute("itemUpdateFormDto", itemUpdateFormDto);

        return prefixViewPath.call("item-update-form");
    }

    @PostMapping("/update")
    public String updateItem(@ModelAttribute @Validated ItemUpdateFormDto itemUpdateFormDto, BindingResult bindingResult){
        log.info("itemUpdateFormDto = {}", itemUpdateFormDto);

        if(bindingResult.hasErrors()){
            return prefixViewPath.call("item-update-form");
        }

        service.update(itemUpdateFormDto);

        return "redirect:/items/" + itemUpdateFormDto.getId().toString();
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id){
        service.delete(id);

        return "redirect:/items";
    }
}
