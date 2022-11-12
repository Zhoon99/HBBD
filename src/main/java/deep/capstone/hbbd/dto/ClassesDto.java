package deep.capstone.hbbd.dto;

import deep.capstone.hbbd.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ClassesDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto {

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
        private String address;
        private String addressDetail;
        private String latitude;
        private String longitude;
        private Integer views;

        private Long account_id;
        private Long category_id;

        private List<ActivityImg> activityImgList;
        private List<Curriculum> curriculumList;
        private List<ClassSchedule> classScheduleList;

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Preview {
        private Long classesId;
        private String className;
        private String imgPath;
        private String imgUuid;
        private String imgName;
        private String classification;
        private Integer price;
        private String latitude;
        private String longitude;
        private String categoryName;
        private String province;
        private String city;
        private Integer cmtCount;
        private Float cmfAvg;
    }

}
