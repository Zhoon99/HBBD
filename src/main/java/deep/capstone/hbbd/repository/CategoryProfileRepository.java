package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.Category;
import deep.capstone.hbbd.entity.CategoryProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryProfileRepository extends JpaRepository<CategoryProfile, Long> {
}
