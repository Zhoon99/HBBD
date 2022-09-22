package deep.capstone.hbbd.dto;

import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Category;
import deep.capstone.hbbd.entity.CategoryAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAccountDto {

    private Long categoryProfileId;

    private Account account;

    private Category category;

    public CategoryAccount toEntity() {
        CategoryAccount categoryAccount = CategoryAccount.builder()
                .categoryProfileId(categoryProfileId)
                .category(category)
                .account(account)
                .build();
        return categoryAccount;
    }
}
