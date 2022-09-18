package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {""})
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    private String profileImg;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(length = 10, nullable = false)
    private String birth;

    @Column(length = 2, nullable = false)
    private String gender;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 20, nullable = false)
    private String addressDetail;

    @OneToMany(mappedBy = "profile")
    private List<CategoryProfile> categoryProfiles = new ArrayList<>();

    @OneToOne
    @JoinColumn(nullable = false, name = "id")
    private Account account;
}
