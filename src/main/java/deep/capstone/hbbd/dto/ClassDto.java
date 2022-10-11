package deep.capstone.hbbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ClassDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String className;
        private Long categoryId;
        private String classification;
        private String timeRequired;
        private String personnel;
        private Integer price;
        private String classIntro;
        private String material;
        private List<CurriculumDto> curriculumList;
    }
}
