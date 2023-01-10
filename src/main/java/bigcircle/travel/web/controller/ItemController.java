package bigcircle.travel.web.controller;

import bigcircle.travel.domain.*;
import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.service.dto.ItemDto;
import bigcircle.travel.service.dto.ItemUpdateDto;
import bigcircle.travel.web.dto.ItemFormDto;
import bigcircle.travel.service.ItemService;
import bigcircle.travel.web.dto.ItemUpdateFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    //service 쪽에서 저장하면 서비스가 multipartfile를 사용해서 의존하게됨 -> 테스트가 어려워짐 -> 컨트롤러로 밀어냄
    private final FileStore fileStore;

    private final String prefixViewPath = "/item/";

    public ItemController(ItemService service, FileStore fileStore) {
        this.service = service;
        this.fileStore = fileStore;
    }

    @GetMapping("{id}")
    public String getItem(@PathVariable Long id, Model model){
        Item item = service.getItem(id);

        model.addAttribute("item", item);

        return prefixViewPath + "item";
    }

    @GetMapping
    public String getItems(Model model){

        List<Item> items = service.getItems();

        model.addAttribute("items", items);

        return prefixViewPath + "item-list";
    }

    @GetMapping("/save")
    public String getSaveForm(Model model){
        ItemFormDto itemFormDto = new ItemFormDto();
        model.addAttribute("itemFormDto", itemFormDto);
        model.addAttribute("categories", Category.values());
        return prefixViewPath + "item-form";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute @Validated ItemFormDto itemFormDto, BindingResult bindingResult, Model model) throws IOException {
        log.info("itemCreateDto={}", itemFormDto);

        if(bindingResult.hasErrors()){
            log.info("bindingResult Error={}",bindingResult);
            model.addAttribute("categories",Category.values());
            return prefixViewPath + "item-form";
        }

        List<MultipartFile> multipartFiles = itemFormDto.getItemImages();
        List<UploadFile> uploadFiles = null;
        if(multipartFiles.size() > 0 && StringUtils.hasText(multipartFiles.get(0).getOriginalFilename())) {
            uploadFiles = fileStore.storeFiles(multipartFiles);
        }

        String now = LocalDateTime.now().toString();
        ItemDto itemDto = new ItemDto(itemFormDto.getCategory(), itemFormDto.getTitle(), new Address(itemFormDto.getZonecode(), itemFormDto.getAddress()), itemFormDto.getAddressDetail(), itemFormDto.getDescription(), uploadFiles);

        Long id = service.save(itemDto);

        return "redirect:/items/" + id.toString();
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model){
        Item item = service.getItem(id);

        ItemUpdateFormDto itemUpdateFormDto = new ItemUpdateFormDto(item.getId(), item.getTitle(), item.getAddress().getZonecode(), item.getAddress().getAddress(), item.getAddressDetail(), item.getDescription(), item.getCategory(), null);

        model.addAttribute("itemUpdateFormDto", itemUpdateFormDto);
        model.addAttribute("imageStoreFileNames",item.getItemImages().stream().map(ItemImage::getStoreFileName).collect(Collectors.toList()));
        model.addAttribute("categories", Category.values());

        return prefixViewPath + "item-update-form";
    }

    @PostMapping("/update")
    public String updateItem(@ModelAttribute @Validated ItemUpdateFormDto itemUpdateFormDto, BindingResult bindingResult, Model model) throws IOException {
        log.info("itemUpdateFormDto = {}", itemUpdateFormDto);

        if(bindingResult.hasErrors()){
            model.addAttribute("categories", Category.values());
            return prefixViewPath + "item-update-form";
        }

        List<MultipartFile> multipartFiles = itemUpdateFormDto.getItemImages();
        List<UploadFile> uploadFiles = null;

        if(multipartFiles.size() > 0 && StringUtils.hasText(multipartFiles.get(0).getOriginalFilename())){
            //새로 업로드된 사진 저장
            uploadFiles = fileStore.storeFiles(multipartFiles);
        }

        ItemUpdateDto itemUpdateDto = new ItemUpdateDto(itemUpdateFormDto.getId(), itemUpdateFormDto.getCategory(), itemUpdateFormDto.getTitle(), new Address(itemUpdateFormDto.getZonecode(), itemUpdateFormDto.getAddress()), itemUpdateFormDto.getAddressDetail(), itemUpdateFormDto.getDescription(), uploadFiles);

        service.update(itemUpdateDto);

        return "redirect:/items/" + itemUpdateFormDto.getId().toString();
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id){
        service.delete(id);

        return "redirect:/items";
    }

}
