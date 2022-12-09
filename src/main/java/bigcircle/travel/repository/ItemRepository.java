package bigcircle.travel.repository;

import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemFormDto;

import java.util.*;

public interface ItemRepository {
    String save(ItemFormDto itemFormDto);

    ItemDto findById(String id);

    List<ItemDto> findAll();

    void clear();
}
