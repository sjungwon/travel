package bigcircle.travel.repository;

import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.service.dto.ItemFormDto;

import java.util.*;

public interface ItemRepository {
    Long save(ItemSaveDto itemSaveDto);

    ItemDto findById(Long id);

    List<ItemDto> findAll();

    void update(Long id, ItemSaveDto itemSaveDto);

    void delete(Long id);
}
