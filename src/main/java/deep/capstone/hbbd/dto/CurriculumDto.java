package deep.capstone.hbbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDto {

    private String title;
    private String content;
    private Integer order;

}
