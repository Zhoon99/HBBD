package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.dto.ImageDto;
import deep.capstone.hbbd.entity.ActivityImg;
import deep.capstone.hbbd.entity.ClassSchedule;
import deep.capstone.hbbd.entity.Classes;
import deep.capstone.hbbd.entity.Curriculum;
import deep.capstone.hbbd.repository.ActivityImgRepository;
import deep.capstone.hbbd.repository.ClassRepository;
import deep.capstone.hbbd.repository.ClassScheduleRepository;
import deep.capstone.hbbd.repository.CurriculumRepository;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.security.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final ClassRepository classRepository;
    private final CurriculumRepository curriculumRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final ActivityImgRepository activityImgRepository;

    @Transactional
    public void registerClass(ClassesDto.Request classesDto, ClassScheduleDto.RequestList classScheduleDto, MultipartFile repImg, MultipartFile[] activityImg, Authentication authentication) {

        if (repImg != null) {
            FileUtil fileUtil = new FileUtil();

            ImageDto repImgDto = fileUtil.saveFileAndThumbnail(repImg, 300, 100);

            List<ImageDto> activityImgDtoList = new ArrayList<>();
            for (MultipartFile file : activityImg) {
                ImageDto activityImgDto = fileUtil.saveFileAndThumbnail(file, 300, 300);
                activityImgDtoList.add(activityImgDto);
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            Classes classes = Classes.builder()
                    .account(userPrincipal.getAccount())
                    .categoryId(classesDto.getCategoryId())
                    .className(classesDto.getClassName())
                    .imgPath(repImgDto.getPath())
                    .imgUuid(repImgDto.getUuid())
                    .imgName(repImgDto.getImgName())

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

            List<ActivityImg> activityImgList = new ArrayList<>();
            if (activityImgDtoList != null) {
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
}
