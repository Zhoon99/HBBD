package deep.capstone.hbbd.entity;

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
@ToString(exclude = {"userRoles", "categoryAccounts"})
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

    @Column(nullable = false)
    private String profileImg;

    @Column(length = 150, nullable = false)
    private String introduce;

    @OneToMany(mappedBy = "account")
    private List<CategoryAccount> categoryAccounts = new ArrayList<>();


}


