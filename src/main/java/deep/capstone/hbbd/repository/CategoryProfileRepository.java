package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.CategoryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryProfileRepository extends JpaRepository<CategoryAccount, Long> {
}
