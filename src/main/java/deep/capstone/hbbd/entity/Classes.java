package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = {"activityImgList", "curriculumList", "classScheduleList"})
@NoArgsConstructor
@AllArgsConstructor
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classes_id")
    private Long id;

    @Column(nullable = false)
    private Long categoryId;

    @Column(length = 30, nullable = false)
    private String className;

    @Column(length = 100)
    private String imgPath;

    private String imgUuid;

    @Column(nullable = false)
    private String imgName;

    @Column(length = 3, nullable = false)
    private String classification;

    @Column(length = 20, nullable = false)
    private String timeRequired;

    @Column(length = 10, nullable = false)
    private String personnel;

    @Column(nullable = false)
    private Integer price;

    @Lob
    private String classIntro;

    private String material;

    private String precautions;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 20, nullable = false)
    private String addressDetail;

    @Column(length = 20, nullable = false)
    private String latitude;

    @Column(length = 20, nullable = false)
    private String longitude;

    private Integer views = 0;

    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "activityImgClasses")
    private List<ActivityImg> activityImgList = new ArrayList<>();

    @OneToMany(mappedBy = "curriculumClasses")
    private List<Curriculum> curriculumList = new ArrayList<>();

    @OneToMany(mappedBy = "scheduleClasses")
    private List<ClassSchedule> classScheduleList = new ArrayList<>();
}
