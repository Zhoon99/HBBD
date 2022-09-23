package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.CategoryAccountDto;
import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Category;
import deep.capstone.hbbd.entity.CategoryAccount;
import deep.capstone.hbbd.entity.Role;
import deep.capstone.hbbd.repository.AccountRepository;
import deep.capstone.hbbd.repository.CategoryProfileRepository;
import deep.capstone.hbbd.repository.CategoryRepository;
import deep.capstone.hbbd.repository.RoleRepository;
import deep.capstone.hbbd.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CategoryProfileRepository categoryProfileRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미지 저장 위치
    private final String rootPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\upload";

    @Override
    @Transactional
    public void createUser(AccountDto accountDto, MultipartFile file) {

        String imagePath = null;

        if (file != null) {

            //실제 파일 이름 IE나 Edge 는 전체 경로가 들어오므로 마지막 단어를 가져옴
            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            //날짜 폴더 생성
            String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            String folderPath = str.replace("//", File.separator);

            File uploadPathFolder = new File(rootPath, folderPath);

            if (uploadPathFolder.exists() == false) {
                uploadPathFolder.mkdirs();
            }

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = rootPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            imagePath = "images\\upload" + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            try {
                //원본 파일 저장
                file.transferTo(savePath);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else { //기본 이미지 저장
            imagePath = "images\\common\\noimage.jpg";
        }

        Account newAccount;

        if(accountDto.getId() == null) { //폼 회원가입

            //비밀번호 인코딩
            accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));

            //프로필 이미지 저장
            accountDto.setProfileImg(imagePath);

            //유저 권한 저장
            Role role = roleRepository.findByRoleName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            accountDto.setRoles(roles);

            newAccount = accountRepository.save(accountDto.toEntity());

        } else { //oauth2 회원가입

            Account oauth2Account = accountRepository.findById(accountDto.getId()).get();

            //프로필 추가
            oauth2Account.setSocialProfile(accountDto.getIntroduce(), accountDto.getNickname(), imagePath);

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
            categoryProfileRepository.saveAll(categoryAccountList);
        }
    }
}
