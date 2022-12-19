package bigcircle.travel.repository;

import bigcircle.travel.domain.Category;

public interface CategoryRepository {
    public Long findIdByKorean(String korean);
    public Category findById(Long id);
}
