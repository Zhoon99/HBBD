package deep.capstone.hbbd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Category;
import deep.capstone.hbbd.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long profileId;

    private String profileImg;

    private String nickname;

    private String phone;

    private String birth;

    private String gender;

    private String address;

    private String addressDetail;

    private List<String> interest;
}
