package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.Category;
import bigcircle.travel.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryEnumRepository implements CategoryRepository {

    private Category categoryType;

    @Override
    public Long findIdByKorean(String korean){
        Category[] values = Category.values();
        Category finded = null;

        for (Category value : values) {
            if( value.getKr().equals(korean)){
                finded = value;
            }
        }
        if(finded == null){
            throw new NullPointerException();
        }

        return finded.getId();
    }

    @Override
    public Category findById(Long id){
        Category[] values = Category.values();
        Category finded = null;

        for (Category value : values) {
            if(Objects.equals(value.getId(), id)){
                finded = value;
            }
        }

        if(finded == null){
            throw new NullPointerException();
        }

        return finded;
    }
}
