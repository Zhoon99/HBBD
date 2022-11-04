package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}
