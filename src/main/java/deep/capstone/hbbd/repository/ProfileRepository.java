package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
