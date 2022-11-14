package deep.capstone.hbbd.repository;

import deep.capstone.hbbd.dto.PreviewDto;
import deep.capstone.hbbd.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassesRepository extends JpaRepository<Classes, Long> {

    @Query(value = "SELECT c FROM Classes c WHERE c.latitude BETWEEN :swLat AND :neLat AND c.longitude BETWEEN :swLng AND :neLng")
    List<Classes> getClassesInBounds(String swLat, String neLat, String swLng, String neLng);

}
