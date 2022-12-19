package bigcircle.travel.repository;

import bigcircle.travel.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findIdByKorean(){
        Long id = this.categoryRepository.findIdByKorean("호텔");
        Assertions.assertThat(id).isEqualTo(Category.HOTEL.getId());
    }

    @Test
    void findById(){
        Category byId = this.categoryRepository.findById(Category.HOTEL.getId());
        Assertions.assertThat(byId).isEqualTo(Category.HOTEL);
    }

}