package bigcircle.travel.item.repository;

import bigcircle.travel.item.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemMemoryRepository implements ItemRepository {
    private Map<String, Item> db;

    public ItemMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
    }

    public String save(ItemCreateDto item){
        String id = UUID.randomUUID().toString();
        Item hotel = new Item(id, item.getTitle(),item.getZonecode(), item.getAddress(), item.getAddressDetail(),item.getPrice(),item.getDescription());
        this.db.put(id, hotel);
        return id;
    }

    public Item findById(String id){
        Item item = this.db.get(id);

        return item;
    }

    public List<Item> findAll(){
        Set<String> keySet = this.db.keySet();

        List<Item> items = new ArrayList<>(this.db.size());

        for (String s : keySet) {
            items.add(this.db.get(s));
        }

        return items;
    }

    public void clear(){
        this.db.clear();
    }
}
