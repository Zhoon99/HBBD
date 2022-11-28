package deep.capstone.hbbd.dto;

import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.entity.Comment;
import deep.capstone.hbbd.entity.CommentImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

public class CommentImgDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String path;
        private String uuid;
        private String imgName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String path;
        private String uuid;
        private String imgName;
    }

}
