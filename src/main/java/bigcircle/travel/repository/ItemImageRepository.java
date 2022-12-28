package bigcircle.travel.repository;

import bigcircle.travel.domain.ItemImage;

import java.util.List;

public interface ItemImageRepository {
    public void saveItemImages(List<ItemImage> itemImages);
    public void saveItemImage(ItemImage itemImage);

    public List<ItemImage> findByItemId(Long itemId);

    public void deleteByStoreFileName(String storeFileName);
}
