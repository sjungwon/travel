package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.Item;
import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.repository.ItemImageRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemImageMemoryRepository implements ItemImageRepository {
    private final Map<String, ItemImage> db;

    public ItemImageMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
    }

    @Override
    public void saveItemImages(List<ItemImage> itemImages) {
        for (ItemImage itemImage : itemImages) {
            this.saveItemImage(itemImage);
        }
    }

    @Override
    public void saveItemImage(ItemImage itemImage) {
        this.db.put(itemImage.getStoreFileName(), itemImage);
    }

    @Override
    public List<ItemImage> findByItemId(Long itemId) {
        Set<String> keySet = this.db.keySet();
        List<ItemImage> itemImages = new LinkedList<>();
        for (String s : keySet) {
            ItemImage itemImage = this.db.get(s);
            if(itemImage.getItemId().equals(itemId)){
                itemImages.add(itemImage);
            }
        }

        return itemImages;
    }

    @Override
    public void deleteByStoreFileName(String storeFileName) {
        this.db.remove(storeFileName);
    }

    public void clear(){
        this.db.clear();
    }
}
