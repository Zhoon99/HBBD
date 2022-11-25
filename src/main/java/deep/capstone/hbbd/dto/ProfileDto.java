package deep.capstone.hbbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long id;
    private String nickname;
    private String introduce;
    private String profileImg;
    private List<CategoryDto.Request> interest;

}
