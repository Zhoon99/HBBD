package deep.capstone.hbbd.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@ToString(exclude = {"categoryAccounts"})
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20, name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<CategoryAccount> categoryAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Classes> classesList = new ArrayList<>();
}
