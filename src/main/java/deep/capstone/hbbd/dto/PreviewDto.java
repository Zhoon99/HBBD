package deep.capstone.hbbd.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@NoArgsConstructor
public class PreviewDto {
    private Long classesId;
    private String className;
    private String imgPath;
    private String imgUuid;
    private String imgName;
    private String thumbnailSrc;
    private String classification;
    private Integer price;
    private String latitude;
    private String longitude;
    private String categoryName;
    private String province;
    private String city;
    private Integer cmtCount;
    private Double cmfAvg;

    @QueryProjection
    public PreviewDto(Long classesId, String className, String imgPath, String imgUuid, String imgName, String thumbnailSrc, String classification, Integer price, String latitude, String longitude, String categoryName, String province, String city, Integer cmtCount, Double cmfAvg) {
        this.classesId = classesId;
        this.className = className;
        this.imgPath = imgPath;
        this.imgUuid = imgUuid;
        this.imgName = imgName;
        this.thumbnailSrc = thumbnailSrc;
        this.classification = classification;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoryName = categoryName;
        this.province = province;
        this.city = city;
        this.cmtCount = cmtCount;
        this.cmfAvg = cmfAvg;
    }
}
