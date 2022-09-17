package deep.capstone.hbbd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import deep.capstone.hbbd.entity.Category;
import deep.capstone.hbbd.entity.Profile;
import deep.capstone.hbbd.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String username;
    private String password;
    private String email;

    private String provider;
    private String providerId;

    private List<String> roles;
    private List<String> interests;

    private LocalDateTime regDate, modDate;
}


