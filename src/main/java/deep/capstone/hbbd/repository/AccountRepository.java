package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByUsername(String username);

  int countByUsername(String username);

  Optional<Account> findByProviderAndProviderId(String provider, String providerId);
}