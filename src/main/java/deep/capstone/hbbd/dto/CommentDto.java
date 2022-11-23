package deep.capstone.hbbd.dto;

import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.entity.CommentImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Double scope;
        private String content;
        private ProfileDto account;
        private LocalDateTime regDate;
        private List<CommentImgDto.Request> commentImgList;
    }
}
