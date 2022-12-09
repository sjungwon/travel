package bigcircle.travel.service;

import bigcircle.travel.domain.Address;
import bigcircle.travel.repository.AddressRepository;
import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemFormDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;

    public ItemService(ItemRepository itemRepository, AddressRepository addressRepository) {
        this.itemRepository = itemRepository;
        this.addressRepository = addressRepository;
    }

    public List<ItemDto> getItems(){
        return this.itemRepository.findAll();
    }

    public void save(ItemFormDto itemCreateDto){
        Address address = new Address(itemCreateDto.getZonecode(), itemCreateDto.getAddress());
        addressRepository.save(address);
        String save = itemRepository.save(itemCreateDto);
    }

}
