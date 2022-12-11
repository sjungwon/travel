package bigcircle.travel.service;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.CategoryType;
import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.AddressRepository;
import bigcircle.travel.repository.CategoryRepository;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.service.dto.ItemFormDto;
import bigcircle.travel.service.dto.ItemUpdateFormDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;

    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, AddressRepository addressRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
    }

    public ItemDto getItem(Long id){return this.itemRepository.findById(id);}

    public List<ItemDto> getItems(){
        return this.itemRepository.findAll();
    }

    public Long save(ItemFormDto itemFormDto){
        //주소 저장
        Address address = new Address(itemFormDto.getZonecode(), itemFormDto.getAddress());
        addressRepository.save(address);

        //카테고리 가져옴
        Long categoryId = categoryRepository.findIdByKoreanTitle(itemFormDto.getCategoryTitle());

        //저장 DTO 생성 및 저장
        ItemSaveDto itemSaveDto = new ItemSaveDto(itemFormDto.getTitle(), itemFormDto.getZonecode(), itemFormDto.getAddressDetail(), itemFormDto.getDescription(), categoryId);
        return itemRepository.save(itemSaveDto);
    }

    public void update(ItemUpdateFormDto itemUpdateFormDto){
        //주소 변경 가능성이 있으므로 저장
        Address address = new Address(itemUpdateFormDto.getZonecode(), itemUpdateFormDto.getAddress());
        addressRepository.save(address);

        //카테고리 id 가져옴
        Long categoryId = categoryRepository.findIdByKoreanTitle(itemUpdateFormDto.getCategoryTitle());

        ItemSaveDto itemSaveDto = new ItemSaveDto(itemUpdateFormDto.getTitle(), itemUpdateFormDto.getZonecode(), itemUpdateFormDto.getAddressDetail(), itemUpdateFormDto.getDescription(), categoryId);

        itemRepository.update(itemUpdateFormDto.getId(), itemSaveDto);
    }

    public void delete(Long id){
        itemRepository.delete(id);
    }
}
