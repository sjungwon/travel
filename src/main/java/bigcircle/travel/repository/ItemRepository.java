package bigcircle.travel.repository;

import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.dto.ItemSaveDto;

import java.util.*;

public interface ItemRepository {
    Long save(ItemSaveDto itemSaveDto);

    Item findById(Long id);

    List<Item> findAll();

    void update(Long id, ItemSaveDto itemSaveDto);

    void delete(Long id);
}
