package bigcircle.travel.item.repository;

import bigcircle.travel.item.domain.Item;

import java.util.*;

public interface ItemRepository {
    String save(ItemCreateDto item);

    Item findById(String id);

    List<Item> findAll();

    void clear();
}
