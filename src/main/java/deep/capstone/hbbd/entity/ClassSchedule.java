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
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_schedule_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String classDate;

    @Column(length = 1, nullable = false)
    private String week;

    @Column(length = 7, nullable = false)
    private String startTime;

    @Column(nullable = false)
    private Integer personnel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classes_id")
    private Classes scheduleClasses;
}
