package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = {""})
@NoArgsConstructor
@AllArgsConstructor
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curriculum_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String content;

    @Column(nullable = false)
    private Short sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classes_id")
    private Classes classes;
}
