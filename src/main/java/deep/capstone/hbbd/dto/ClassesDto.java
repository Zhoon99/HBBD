package deep.capstone.hbbd.dto;

import deep.capstone.hbbd.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ClassesDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class detail {

        private Long id;
        private String className;
        private String imgPath;
        private String imgUuid;
        private String imgName;
        private String classification;
        private String timeRequired;
        private String personnel;
        private Integer price;
        private String classIntro;
        private String material;
        private String precautions;
        private String latitude;
        private String longitude;
        private String address;
        private String addressDetail;
        private Integer views;

        private ProfileDto account;
        private CategoryDto.Request category;

        private Integer cmtCount;
        private Double cmtAvg;

        private List<ClassScheduleDto.Response> classScheduleList;
        private List<ActivityImgDto.Response> activityImgList;
        private List<CurriculumDto> curriculumList;
        private List<CommentDto.Request> commentList;

        public void setCmtCountAndAvg(Integer count, Double avg) {
            this.cmtCount = count;
            this.cmtAvg = avg;
        }

        public String getImageURL(){
            try {
                return URLEncoder.encode(imgPath+"/"+imgUuid+"_"+imgName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        public String getThumbnailURL(){
            try {
                return URLEncoder.encode(imgPath+"/s_"+imgUuid+"_"+imgName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String className;
        private Long categoryId;
        private String classIntro;
        private String address;
        private String addressDetail;
        private String lat;
        private String lng;
        private String timeRequired;
        private String personnel;
        private Integer price;
        private String material;
        private String precautions;
        private List<CurriculumDto> curriculumList;
    }
}
