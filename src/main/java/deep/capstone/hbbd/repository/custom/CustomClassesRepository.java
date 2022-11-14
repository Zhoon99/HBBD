package deep.capstone.hbbd.repository.custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import deep.capstone.hbbd.dto.PreviewDto;
import deep.capstone.hbbd.dto.QPreviewDto;
import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.entity.QClasses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static deep.capstone.hbbd.entity.QCategory.category;
import static deep.capstone.hbbd.entity.QClasses.classes;
import static deep.capstone.hbbd.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CustomClassesRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * SELECT cls.classes_id, cls.class_name, cls.img_path, cls.img_uuid, cls.img_name, cls.classification, cls.price, cls.latitude, cls.longitude, ctg.category_name,
     * SUBSTRING_INDEX(cls.address, ' ', 1) province, SUBSTRING_INDEX(SUBSTRING_INDEX(cls.address, ' ', 2), ' ', -1) city,
     * COUNT(cmt.comment_id) cmtCount, ROUND(AVG(COALESCE(cmt.scope, 0)), 1) cmtAvg
     * FROM classes cls
     * INNER JOIN category ctg ON cls.category_id = ctg.category_id
     * LEFT JOIN comment cmt ON cls.classes_id = cmt.classes_id
     * GROUP BY cls.classes_id
     */
    public List<PreviewDto> getPreviewInfo() {
        return jpaQueryFactory.select(Projections.constructor(PreviewDto.class, classes.id, classes.className, classes.imgPath, classes.imgUuid, classes.imgName, classes.imgName,
                classes.classification, classes.price, classes.latitude, classes.longitude, category.categoryName,
                classes.address.substring(0), classes.address.substring(1),
                        comment.id.count().intValue(), comment.scope.coalesce(0.0).avg().round()))
                .from(classes)
                .leftJoin(classes.category, category).fetchJoin()
                .innerJoin(classes.commentList, comment).fetchJoin()
                .groupBy(classes.id)
                .distinct()
                .fetch();
    }


    public List<Classes> getClassesInBounds(String swLat, String swLng, String neLat, String neLng) {
        return (List<Classes>) jpaQueryFactory.select(Projections.constructor(Classes.class, classes))
                .from(classes)
                .where(classes.latitude.between(swLat, neLat)
                        .and(classes.longitude.between(swLng, neLng)));

    }
}
