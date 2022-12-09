package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.AddressRepository;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemFormDto;
import bigcircle.travel.repository.ItemRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemMemoryRepository implements ItemRepository {
    private final Map<String, Item> db;
    private final AddressRepository addressRepository;

    public ItemMemoryRepository(AddressRepository addressRepository) {
        this.db = new ConcurrentHashMap<>();
        this.addressRepository = addressRepository;
    }

    public String save(ItemFormDto itemFormDto){
        String id = UUID.randomUUID().toString();
        Item item = new Item(id, itemFormDto.getTitle(),itemFormDto.getZonecode(), itemFormDto.getAddressDetail(),itemFormDto.getDescription(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        this.db.put(id, item);
        return id;
    }

    public ItemDto findById(String id){
        Item item = this.db.get(id);

        return join(item);
    }

    public List<ItemDto> findAll(){
        Set<String> keySet = this.db.keySet();

        List<ItemDto> items = new ArrayList<>(this.db.size());

        for (String s : keySet) {
            items.add(join(this.db.get(s)));
        }

        return items;
    }

    public void clear(){
        this.db.clear();
    }

    public ItemDto join(Item item){
        int zonecode = item.getZonecode();
        Address address = addressRepository.findByZonecode(zonecode);

        return new ItemDto(item.getId(), item.getTitle(), address, item.getAddressDetail(), item.getDescription(), item.getCreatedAt(), item.getLastUpdate());
    }
}
