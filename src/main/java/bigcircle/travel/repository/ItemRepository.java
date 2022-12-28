package bigcircle.travel.repository;

import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.dto.ItemCreateDto;

import java.util.*;

public interface ItemRepository {
    String save(ItemCreateDto item);

    Item findById(String id);

    List<Item> findAll();

    void clear();
}
