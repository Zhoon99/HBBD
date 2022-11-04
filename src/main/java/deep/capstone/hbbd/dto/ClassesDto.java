package deep.capstone.hbbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ClassesDto {

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
