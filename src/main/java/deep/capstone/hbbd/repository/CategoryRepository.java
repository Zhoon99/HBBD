package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
