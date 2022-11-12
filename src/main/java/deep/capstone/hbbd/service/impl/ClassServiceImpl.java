package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.*;
import deep.capstone.hbbd.entity.*;
import deep.capstone.hbbd.repository.*;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.security.util.FileUtil;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassesRepository classesRepository;
    private final CurriculumRepository curriculumRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final ActivityImgRepository activityImgRepository;

    private final CategoryRepository categoryRepository;

    @Value("${image.upload.path}")
    private String updatePath;

    /**
     * 클래스 등록
     */
    @Transactional
    @Override
    public void registerClass(ClassesDto.Request classesDto, ClassScheduleDto.RequestList classScheduleDto, MultipartFile repImg, MultipartFile[] activityImg, Authentication authentication) {

        if (repImg != null) {
            ImageDto repImgDto = FileUtil.saveFileAndThumbnail(updatePath, repImg, 300, 100);

            List<ImageDto> activityImgDtoList = null;
            if(activityImg != null && activityImg.length > 0) {
                activityImgDtoList = new ArrayList<>();
                for (MultipartFile file : activityImg) {
                    ImageDto activityImgDto = FileUtil.saveFileAndThumbnail(updatePath, file, 300, 300);
                    activityImgDtoList.add(activityImgDto);
                }
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            Classes classes = Classes.builder()
                    .classification("원데이")
                    .account(userPrincipal.getAccount())
                    .category(categoryRepository.findById(classesDto.getCategoryId()).get())
                    .className(classesDto.getClassName())
                    .imgPath(repImgDto.getPath())
                    .imgUuid(repImgDto.getUuid())
                    .imgName(repImgDto.getImgName())
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
            Classes savedClass = classesRepository.save(classes);

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

            if (activityImgDtoList != null) {
                List<ActivityImg> activityImgList = new ArrayList<>();
                activityImgDtoList.forEach(element -> {
                    ActivityImg newActivityImg = ActivityImg.builder()
                            .path(element.getPath())
                            .uuid(element.getUuid())
                            .imgName(element.getImgName())
                            .activityImgClasses(savedClass)
                            .build();
                    activityImgList.add(newActivityImg);
                });
                activityImgRepository.saveAll(activityImgList);
            }
        }
    }

    /**
     * 현재위치 기준 일정 범위 내 클래스 반환
     */
//    public ClassesDto.Preview getCloseClass() {
//
//    }

}
