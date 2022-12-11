package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.CategoryType;
import bigcircle.travel.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryEnumRepository implements CategoryRepository {

    private CategoryType categoryType;

    @Override
    public Long findIdByKoreanTitle(String title){
        CategoryType[] values = CategoryType.values();
        CategoryType finded = null;

        for (CategoryType value : values) {
            if( value.getKorean().equals(title)){
                finded = value;
            }
        }
        if(finded == null){
            throw new NullPointerException();
        }

        return finded.getId();
    }

    @Override
    public CategoryType findCategoryById(Long id){
        CategoryType[] values = CategoryType.values();
        CategoryType finded = null;

        for (CategoryType value : values) {
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
