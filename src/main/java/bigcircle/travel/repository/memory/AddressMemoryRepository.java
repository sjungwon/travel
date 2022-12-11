package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.Address;
import bigcircle.travel.repository.AddressRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AddressMemoryRepository implements AddressRepository {

    private Map<Integer, Address> db;

    public AddressMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Address address){
        Address dup = db.get(address.getZonecode());
        if(dup != null){
            return;
        }
        db.put(address.getZonecode(), address);
    }

    @Override
    public Address findByZonecode(int zonecode) {
        Address address = db.get(zonecode);

        return address;
    }

    @Override
    public void update(Address address) {

    }
}
