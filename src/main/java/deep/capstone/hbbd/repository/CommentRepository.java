package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.entity.ClassSchedule;
import deep.capstone.hbbd.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>  {
}
