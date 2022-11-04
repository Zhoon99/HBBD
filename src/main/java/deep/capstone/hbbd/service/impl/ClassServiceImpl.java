package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.CategoryAccountDto;
import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.entity.*;
import deep.capstone.hbbd.repository.ClassRepository;
import deep.capstone.hbbd.repository.ClassScheduleRepository;
import deep.capstone.hbbd.repository.CurriculumRepository;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
public class ClassServiceImpl implements ClassService {

    @Value("${image.upload.path}")
    private String updatePath;

    private final ClassRepository classRepository;

    private final CurriculumRepository curriculumRepository;

    private final ClassScheduleRepository classScheduleRepository;

    @Override
    @Transactional
    public void registerClass(ClassesDto.Request classesDto, ClassScheduleDto.RequestList classScheduleDto, MultipartFile repImg, MultipartFile[] activityImg, Authentication authentication) {

        if (repImg != null || activityImg.length > 0) {

            //실제 파일 이름 IE나 Edge 는 전체 경로가 들어오므로 마지막 단어를 가져옴
            String originalName = repImg.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            //날짜 폴더 생성
            String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            String folderPath = str.replace("//", File.separator);

            File uploadPathFolder = new File(updatePath, folderPath);

            if (uploadPathFolder.exists() == false) {
                uploadPathFolder.mkdirs();
            }

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = updatePath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try {
                //원본 파일 저장
                repImg.transferTo(savePath);

                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                Classes classes = Classes.builder()
                        .account(userPrincipal.getAccount())
                        .categoryId(classesDto.getCategoryId())
                        .className(classesDto.getClassName())
                        .imgPath(folderPath)
                        .imgUuid(uuid)
                        .imgName(fileName)
                        .classification("원데이")
                        .timeRequired(classesDto.getTimeRequired())
                        .personnel(classesDto.getPersonnel())
                        .price(classesDto.getPrice())
                        .classIntro(classesDto.getClassIntro())
                        .material(classesDto.getMaterial())
                        .precautions(classesDto.getPrecautions())
                        .address(classesDto.getAddress())
                        .addressDetail(classesDto.getAddressDetail())
                        .latitude(classesDto.getLat())
                        .longitude(classesDto.getLng())
                        .views(0)
                        .build();
                Classes savedClass = classRepository.save(classes);

                List<Curriculum> curriculumList = new ArrayList<>();
                if (classesDto.getCurriculumList() != null) {
                    classesDto.getCurriculumList().forEach(element -> {
                        Curriculum curriculum = Curriculum.builder()
                                .title(element.getTitle())
                                .content(element.getContent())
                                .sequence(element.getSequence())
                                .curriculumClasses(savedClass)
                                .build();
                        curriculumList.add(curriculum);
                    });
                    curriculumRepository.saveAll(curriculumList);
                }

                List<ClassSchedule> classScheduleList = new ArrayList<>();
                if (classScheduleDto.getScheduleList() != null) {
                    classScheduleDto.getScheduleList().forEach(element -> {
                        ClassSchedule classSchedule = ClassSchedule.builder()
                                .classDate(element.getDate())
                                .week(element.getWeek())
                                .startTime(element.getTime().split("~")[0])
                                .personnel(Integer.valueOf(element.getPersonnel().split("~")[1]))
                                .scheduleClasses(savedClass)
                                .build();
                        classScheduleList.add(classSchedule);
                    });
                    classScheduleRepository.saveAll(classScheduleList);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//
//        Account newAccount;
//
//        if(accountDto.getId() == null) { //폼 회원가입
//
//            //비밀번호 인코딩
//            accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//
//            //프로필 이미지 저장
//            accountDto.setProfileImg(imagePath);
//
//            //유저 권한 저장
//            Role role = roleRepository.findByRoleName("ROLE_USER");
//            Set<Role> roles = new HashSet<>();
//            roles.add(role);
//            accountDto.setRoles(roles);
//
//            newAccount = accountRepository.save(accountDto.toEntity());
//
//        } else { //oauth2 회원가입
//
//            Account oauth2Account = accountRepository.findById(accountDto.getId()).get();
//
//            //프로필 추가
//            oauth2Account.setSocialProfile(accountDto.getIntroduce(), accountDto.getNickname(), imagePath);
//
//            newAccount = accountRepository.save(oauth2Account);
//        }
//
//        //관심도 저장 (카테고리_프로필)
//        List<CategoryAccount> categoryAccountList = new ArrayList<>();
//
//        if (accountDto.getInterest() != null) {
//            accountDto.getInterest().forEach(interest -> {
//                Category category = categoryRepository.findByCategoryName(interest);
//                CategoryAccountDto categoryAccountDto = CategoryAccountDto.builder()
//                        .account(newAccount)
//                        .category(category)
//                        .build();
//                categoryAccountList.add(categoryAccountDto.toEntity());
//            });
//            categoryProfileRepository.saveAll(categoryAccountList);
//        }
    }
}
