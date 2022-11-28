package deep.capstone.hbbd.entity;

import deep.capstone.hbbd.dto.ImageDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = {"userRoles", "categoryAccounts", "classesList"})
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    // OAuth를 위해 구성한 추가 필드 2개
    @Column(length = 30)
    private String provider;

    private String providerId;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> userRoles = new HashSet<>();

    //프로필
    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 100, nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private String imgUuid;

    @Column(nullable = false)
    private String imgName;

    @Column(length = 150, nullable = false)
    private String introduce;

    @OneToMany(mappedBy = "account")
    private List<CategoryAccount> categoryAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Classes> classesList = new ArrayList<>();

    public void setSocialProfile(String introduce, String nickname, ImageDto imageDto) {
        this.introduce = introduce;
        this.nickname = nickname;
        this.imgPath = imageDto.getPath();
        this.imgUuid = imageDto.getUuid();
        this.imgName = imageDto.getImgName();
    }
}


