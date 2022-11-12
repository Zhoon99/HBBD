package deep.capstone.hbbd.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import deep.capstone.hbbd.dto.ClassesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomClassesRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * SELECT cls.classes_id, cls.class_name, cls.img_path, cls.img_uuid, cls.img_name, cls.classification, cls.price, cls.latitude, cls.longitude, ctg.category_name,
     * 		 SUBSTRING_INDEX(cls.address, ' ', 1) province, SUBSTRING_INDEX(SUBSTRING_INDEX(cls.address, ' ', 2), ' ', -1) city,
     * 		 COUNT(cmt.comment_id) cmtCount, ROUND(AVG(COALESCE(cmt.scope, 0)), 1) cmtAvg
     * FROM classes cls
     *  INNER JOIN category ctg ON cls.category_id = ctg.category_id
     *  LEFT JOIN comment cmt ON cls.classes_id = cmt.classes_id
     * GROUP BY cls.classes_id
     */

}
