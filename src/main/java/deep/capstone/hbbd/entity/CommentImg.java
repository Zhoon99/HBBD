package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = {})
@NoArgsConstructor
@AllArgsConstructor
public class CommentImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_img_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String path;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classes_id")
    private Classes classes;
}
