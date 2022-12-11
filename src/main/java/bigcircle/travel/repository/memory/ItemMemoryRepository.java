package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.CategoryType;
import bigcircle.travel.domain.Item;
import bigcircle.travel.lib.LongIdGenerator;
import bigcircle.travel.repository.AddressRepository;
import bigcircle.travel.repository.CategoryRepository;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.service.dto.ItemFormDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemMemoryRepository implements ItemRepository {
    private final Map<Long, Item> db;


    //JOIN 구현을 위해 다른 repository 의존
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;

    private final LongIdGenerator idGenerator;

    public ItemMemoryRepository(AddressRepository addressRepository, CategoryRepository categoryRepository) {
        this.db = new ConcurrentHashMap<>();
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        idGenerator = new LongIdGenerator();
    }

    @Override
    public Long save(ItemSaveDto itemSaveDto){
        Long id = idGenerator.getId();

        Item item = itemSaveDtoToItemConverter(id, itemSaveDto, null);

        this.db.put(id, item);

        return id;
    }

    @Override
    public ItemDto findById(Long id){
        Item item = this.db.get(id);

        return join(item);
    }

    @Override
    public List<ItemDto> findAll(){
        Set<Long> keySet = this.db.keySet();

        List<ItemDto> items = new ArrayList<>(this.db.size());

        for (Long s : keySet) {
            items.add(join(this.db.get(s)));
        }

        return items;
    }

    @Override
    public void update(Long id, ItemSaveDto itemSaveDto){
        Item prev = this.db.get(id);

        String createdAt = prev.getCreatedAt();

        this.db.remove(id);

        Item item = itemSaveDtoToItemConverter(id, itemSaveDto, createdAt);

        this.db.put(id, item);
    }

    @Override
    public void delete(Long id) {
        this.db.remove(id);
    }

    public void clear(){
        this.db.clear();
    }

    public ItemDto join(Item item){
        int zonecode = item.getZonecode();
        Address address = addressRepository.findByZonecode(zonecode);

        Long categoryId = item.getCategoryId();
        CategoryType category = categoryRepository.findCategoryById(categoryId);

        return new ItemDto(item.getId(), item.getTitle(), category, address, item.getAddressDetail(), item.getDescription(), LocalDateTime.parse(item.getCreatedAt()), LocalDateTime.parse(item.getLastUpdate()));
    }

    private Item itemSaveDtoToItemConverter(Long id, ItemSaveDto itemSaveDto, String createdAt){
        if(createdAt == null){
            return new Item(id, itemSaveDto.getTitle(),itemSaveDto.getZonecode(), itemSaveDto.getAddressDetail(),itemSaveDto.getDescription(), itemSaveDto.getCategoryId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        }
        return new Item(id, itemSaveDto.getTitle(),itemSaveDto.getZonecode(), itemSaveDto.getAddressDetail(),itemSaveDto.getDescription(), itemSaveDto.getCategoryId(), createdAt, LocalDateTime.now().toString());
    }
}
