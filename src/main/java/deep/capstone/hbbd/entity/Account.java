package deep.capstone.hbbd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"userRoles"})
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    // OAuth를 위해 구성한 추가 필드 2개
    @Column(length = 30)
    private String provider;

    private String providerId;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> userRoles = new HashSet<>();

    @OneToOne(mappedBy = "account")
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Category> interests = new ArrayList<>();
}


