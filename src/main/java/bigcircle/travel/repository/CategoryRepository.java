package bigcircle.travel.repository;

import bigcircle.travel.domain.CategoryType;

public interface CategoryRepository {
    public Long findIdByKoreanTitle(String title);
    public CategoryType findCategoryById(Long id);
}
