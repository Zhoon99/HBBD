package deep.capstone.hbbd.dto;

import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Category;
import deep.capstone.hbbd.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String email;
    private String password;
    private String provider;
    private String providerId;

    private Set<Role> roles;

    private String introduce;
    private String profileImg;
    private String nickname;

    private List<String> interest;

    private LocalDateTime regDate, modDate;

    public Account toEntity() {
        Account account = Account.builder()
                .id(id)
                .email(email)
                .password(password)
                .provider(provider)
                .providerId(providerId)
                .userRoles(roles)
                .nickname(nickname)
                .profileImg(profileImg)
                .introduce(introduce)
                .build();
        return account;
    }
}


