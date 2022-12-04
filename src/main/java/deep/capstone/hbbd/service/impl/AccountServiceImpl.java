package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.*;
import deep.capstone.hbbd.entity.*;
import deep.capstone.hbbd.repository.AccountRepository;
import deep.capstone.hbbd.repository.CategoryAccountRepository;
import deep.capstone.hbbd.repository.CategoryRepository;
import deep.capstone.hbbd.repository.RoleRepository;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.security.util.FileUtil;
import deep.capstone.hbbd.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryAccountRepository categoryAccountRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미지 저장 위치
    @Value("${image.upload.path}")
    private String updatePath;

    @Override
    @Transactional
    public void createUser(AccountDto accountDto, MultipartFile file) {

        if (file != null) {
            ImageDto profileImgDto = FileUtil.saveFileAndThumbnail(updatePath, file, 300, 100);

            Account newAccount;

            if (accountDto.getId() == null) { //폼 회원가입

                //비밀번호 인코딩
                accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));

                //프로필 이미지 저장
                accountDto.setProfileImage(profileImgDto);

                //유저 권한 저장
                Role role = roleRepository.findByRoleName("ROLE_USER");
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                accountDto.setRoles(roles);

                newAccount = accountRepository.save(accountDto.toEntity());

            } else { //oauth2 회원가입

                Account oauth2Account = accountRepository.findById(accountDto.getId()).get();

                //프로필 추가
                oauth2Account.setSocialProfile(accountDto.getIntroduce(), accountDto.getNickname(), profileImgDto);

                newAccount = accountRepository.save(oauth2Account);
            }

            //관심도 저장 (카테고리_프로필)
            List<CategoryAccount> categoryAccountList = new ArrayList<>();

            if (accountDto.getInterest() != null) {
                accountDto.getInterest().forEach(interest -> {
                    Category category = categoryRepository.findByCategoryName(interest);
                    CategoryAccountDto categoryAccountDto = CategoryAccountDto.builder()
                            .account(newAccount)
                            .category(category)
                            .build();
                    categoryAccountList.add(categoryAccountDto.toEntity());
                });
                categoryAccountRepository.saveAll(categoryAccountList);
            }
        }

    }

    @Override
    @Transactional
    public ProfileDto loadHeader(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Account loginAccount = accountRepository.findById(userPrincipal.getAccount().getId()).get();

        ProfileDto profileDto = ProfileDto.builder()
                .nickname(loginAccount.getNickname())
                .imgPath(loginAccount.getImgPath())
                .imgUuid(loginAccount.getImgUuid())
                .imgName(loginAccount.getImgName())
                .build();
        return profileDto;
    }

    @Override
    @Transactional
    public ProfileDto getUserprofile(Long accountId) {
        Account loginAccount = accountRepository.findById(accountId).get();

        List<CategoryDto.Request> categoryDtoList = new ArrayList<>();
        Category category;
        for (CategoryAccount categoryAccount : loginAccount.getCategoryAccounts()) {
            category = categoryAccount.getCategory();
            CategoryDto.Request categoryDto = CategoryDto.Request.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .build();
            categoryDtoList.add(categoryDto);
        }

        ProfileDto profileDto = ProfileDto.builder()
                .id(loginAccount.getId())
                .imgPath(loginAccount.getImgPath())
                .imgUuid(loginAccount.getImgUuid())
                .imgName(loginAccount.getImgName())
                .nickname(loginAccount.getNickname())
                .introduce(loginAccount.getIntroduce())
                .interest(categoryDtoList)
                .build();

        return profileDto;
    }
}
