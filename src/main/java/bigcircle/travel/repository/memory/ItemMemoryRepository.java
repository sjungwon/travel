package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.*;
import bigcircle.travel.lib.LongIdGenerator;
import bigcircle.travel.repository.AddressRepository;
import bigcircle.travel.repository.ItemImageRepository;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Repository
public class ItemMemoryRepository implements ItemRepository {
    private final Map<Long, Item> db;

    private final LongIdGenerator idGenerator;

    //JOIN 구현을 위해 다른 repository 의존
    private final AddressRepository addressRepository;
    private final ItemImageRepository itemImageRepository;


    public ItemMemoryRepository(AddressRepository addressRepository,ItemImageRepository itemImageRepository) {
        this.db = new ConcurrentHashMap<>();
        this.idGenerator = new LongIdGenerator();
        this.addressRepository = addressRepository;
        this.itemImageRepository = itemImageRepository;
    }

    @Override
    public Long save(ItemSaveDto itemSaveDto){
        Long id = idGenerator.getId();


        Item item = itemSaveDtoToItemConverter(id, itemSaveDto);

        log.info("item={}",item);

        this.db.put(id, item);

        return id;
    }

    @Override
    public Item findById(Long id){
        Item item = this.db.get(id);

        log.info("test={}",item);

        return item;
    }

    @Override
    public List<Item> findAll(){
        Set<Long> keySet = this.db.keySet();

        List<Item> items = new ArrayList<>(this.db.size());

        for (Long s : keySet) {
            items.add(this.findById(s));
        }

        return items;
    }

    @Override
    public void update(Long id, ItemSaveDto itemSaveDto){
        Item prev = this.db.get(id);

        this.db.remove(id);

        Item item = itemSaveDtoToItemConverter(id, itemSaveDto);

        this.db.put(id, item);
    }

    @Override
    public void delete(Long id) {
        this.db.remove(id);
    }

    public void clear(){
        this.db.clear();
    }

    private Item itemSaveDtoToItemConverter(Long id, ItemSaveDto itemSaveDto){
        Long categoryId = itemSaveDto.getCategoryId();

        Category[] values = Category.values();
        Category category = null;
        for (Category value : values) {
            if(categoryId.equals(value.getId())){
                category = value;
            }
        }

        Address address = this.addressRepository.findByZonecode(itemSaveDto.getZonecode());

        List<ItemImage> itemImages = itemImageRepository.findByItemId(id);

        List<String> itemStoreFileNames = new ArrayList<>(itemImages.size());
        for (ItemImage itemImage : itemImages) {
            itemStoreFileNames.add(itemImage.getStoreFileName());
        }

        log.info("itemSaveDto = {}", itemSaveDto);
        return new Item(id, itemSaveDto.getTitle(),itemSaveDto.getThumbnail(),category ,address, itemSaveDto.getAddressDetail(),itemSaveDto.getDescription(), itemSaveDto.getCreatedAt(), itemSaveDto.getUpdatedAt(), itemStoreFileNames);
    }
}
