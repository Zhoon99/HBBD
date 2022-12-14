package deep.capstone.hbbd.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
//    private String province;
//    private String city;
    private String addressSummary;
    private Integer cmtCount;
    private Double cmtAvg;

    @QueryProjection
    public PreviewDto(Long classesId, String className, String imgPath, String imgUuid, String imgName, String thumbnailSrc, String classification, Integer price, String latitude, String longitude, String categoryName, String addressSummary, Integer cmtCount, Double cmtAvg) {
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
        this.addressSummary = addressSummary;
//        this.province = province;
//        this.city = city;
        this.cmtCount = cmtCount;
        this.cmtAvg = cmtAvg;
    }
}
