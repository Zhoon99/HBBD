package deep.capstone.hbbd.controller;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ProfileDto;
import deep.capstone.hbbd.entity.*;
import deep.capstone.hbbd.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;
    private final CategoryProfileRepository categoryProfileRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미지 저장 위치
    private final String rootPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\upload";

    @RequestMapping(value = "/url", method = RequestMethod.POST)
    @Transactional
    public String getData(@RequestPart(value = "account") AccountDto accountDto,
                          @RequestPart(value = "profile") ProfileDto profileDto,
                          @RequestPart(value = "image") MultipartFile file, Model model) {

        String imagePath = null;

        if (!file.isEmpty()) {

            //실제 파일 이름 IE나 Edge 는 전체 경로가 들어오므로
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

        } else {
            //기본 이미지 저장
            imagePath = "images\\common\\noimage.jpg";
        }


        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);
        Profile profile = modelMapper.map(profileDto, Profile.class);

        //비밀번호 인코딩
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        //유저 권한 저장
        Role role = roleRepository.findByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        account.setUserRoles(roles);

        Account newAccount = accountRepository.save(account);

        //프로필 이미지 저장
        profile.setProfileImg(imagePath);
        profile.setAccount(newAccount);
        Profile newProfile = profileRepository.save(profile);

        //관심도 저장 (카테고리_프로필)
        List<CategoryProfile> categoryProfileList = new ArrayList<>();

        if (profileDto.getInterest() != null) {
            profileDto.getInterest().forEach(interest -> {
                Category category = categoryRepository.findByCategoryName(interest);
                CategoryProfile categoryProfile = CategoryProfile.builder()
                        .profile(newProfile)
                        .category(category)
                        .build();
                categoryProfileList.add(categoryProfile);
            });
            categoryProfileRepository.saveAll(categoryProfileList);
        }

        model.addAttribute("log", "사진 전송완료!");
        return "login/sign_up :: #resultDiv";
    }
}
