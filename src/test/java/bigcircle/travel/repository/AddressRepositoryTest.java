package bigcircle.travel.repository;

import bigcircle.travel.domain.Address;
import bigcircle.travel.repository.memory.AddressMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    void afterEach(){
        if(this.addressRepository instanceof AddressMemoryRepository){
            ((AddressMemoryRepository)this.addressRepository).clear();
        }
    }

    @Test
    @DisplayName("중복 저장시 터지는 에러 확인")
    public void saveTest(){
        Address address = new Address(12345, "서울");
        this.addressRepository.save(address);
//        Assertions.assertThatThrownBy(()->{
//            this.addressRepository.save(address);}).isInstanceOf(DuplicateKeyException.class);
        //중복인 경우 무시하도록 중복 저장 예외 잡아놨음
        this.addressRepository.save(address);
    }

    @Test
    @DisplayName("저장 후 찾기")
    public void saveAndFindByZonecode(){
        //given
        Address address = new Address(23456, "경기");
        this.addressRepository.save(address);

        //when
        Address byZonecode = this.addressRepository.findByZonecode(address.getZonecode());

        //then
        Assertions.assertThat(byZonecode).isEqualTo(address);
    }

    @Test
    @DisplayName("없는 주소 찾기")
    public void notFound(){
        //given
        Address address = new Address(23456, "경기");

        //when & then
//        Assertions.assertThatThrownBy(()->{this.addressRepository.findByZonecode(23456);}).isInstanceOf(EmptyResultDataAccessException.class);
        //오류 잡아놨음
        Address byZonecode = this.addressRepository.findByZonecode(23456);
        Assertions.assertThat(byZonecode).isNull();
    }
}
