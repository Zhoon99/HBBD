package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.CategoryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryAccountRepository extends JpaRepository<CategoryAccount, Long> {
}
