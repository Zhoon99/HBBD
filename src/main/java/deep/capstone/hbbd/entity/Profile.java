package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Long id;

    private String profileImg;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 13, nullable = false)
    private String phone;

    private LocalDateTime birth;

    @Column(length = 2)
    private String gender;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 20, nullable = false)
    private String address_detail;

    @OneToOne
    @JoinColumn(name = "id")
    private Account account;
}
