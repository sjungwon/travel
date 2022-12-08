package bigcircle.travel.address.respository;

import bigcircle.travel.address.domain.Address;
import bigcircle.travel.item.domain.Item;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AddressMemoryRepository implements AddressRepository{

    private static Map<Integer, Address> db = new ConcurrentHashMap<>();

    @Override
    public void save(AddressCreateDto addressCreateDto){
        Address dup = db.get(addressCreateDto.getZonecode());
        if(dup != null){
            throw new DuplicateKeyException(addressCreateDto.getZonecode() + "exist");
        }
        Address address = new Address(addressCreateDto.getZonecode(), addressCreateDto.getAddress(), LocalDateTime.now(), null);
        db.put(address.getZonecode(), address);
    }

}
