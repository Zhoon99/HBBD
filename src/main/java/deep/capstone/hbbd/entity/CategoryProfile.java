package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@ToString
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryProfileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
