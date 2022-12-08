package bigcircle.travel.item.service;

import bigcircle.travel.item.domain.Item;
import bigcircle.travel.item.repository.ItemCreateDto;
import bigcircle.travel.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getItems(){
        List<Item> all = this.repository.findAll();
        return all;
    }

    public void save(ItemCreateDto itemCreateDto){
        String save = repository.save(itemCreateDto);
    }

}
