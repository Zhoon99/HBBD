package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.*;
import deep.capstone.hbbd.entity.*;
import deep.capstone.hbbd.repository.*;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.security.util.FileUtil;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
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
                            .classes(savedClass)
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
                            .classes(savedClass)
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
                            .classes(savedClass)
                            .build();
                    activityImgList.add(newActivityImg);
                });
                activityImgRepository.saveAll(activityImgList);
            }
        }
    }

    /**
     * 클래스 리스트로 미리보기를 생성해 반환
     */
    @Override
    @Transactional
    public List<PreviewDto> getPreviewList(List<Classes> classesList) {
        List<PreviewDto> previewDtoList = new ArrayList<>();
        PreviewDto previewDto;
        for (Classes classes : classesList) {
            List<Double> cmtScopeList = classes.getCommentList().stream().map(Comment::getScope).collect(Collectors.toList());
            previewDto = PreviewDto.builder()
                    .classesId(classes.getId())
                    .categoryName(classes.getClassName())
                    .className(classes.getClassName())
                    .imgPath(classes.getImgPath())
                    .imgUuid(classes.getImgUuid())
                    .imgName(classes.getImgName())
                    .thumbnailSrc(classes.getImgPath() + "/s_" + classes.getImgUuid() + "_" + classes.getImgName())
                    .classification(classes.getClassification())
                    .price(classes.getPrice())
                    .latitude(classes.getLatitude())
                    .longitude(classes.getLongitude())
                    .categoryName(classes.getCategory().getCategoryName())
                    .addressSummary("(" + classes.getAddress().split(" ")[1] + "/" + classes.getAddress().split(" ")[0] + ")")
                    .cmtCount(classes.getCommentList().size())
                    .cmtAvg(Math.round(getCommentScopeAverage(cmtScopeList) * 10) / 10.0)
                    .build();
            previewDtoList.add(previewDto);
        }
        return previewDtoList;
    }

    /**
     * 댓글 평점 평균 구하기
     */
    private static double getCommentScopeAverage(List<Double> list) {
        return list.stream()
                .mapToDouble(a -> a)
                .average().orElse(0.0);
    }

    @Override
    public ClassesDto.detail getClassesDetail(Long cId) {
        Optional<Classes> classDetail = classesRepository.findById(cId);

        ModelMapper modelMapper = new ModelMapper();
        ClassesDto.detail classesDto = modelMapper.map(classDetail.get(), ClassesDto.detail.class);

        List<Double> cmtScopeList = classDetail.get().getCommentList().stream().map(Comment::getScope).collect(Collectors.toList());
        classesDto.setCmtCountAndAvg(classDetail.get().getCommentList().size(), Math.round(getCommentScopeAverage(cmtScopeList) * 10) / 10.0);

        return classesDto;
    }
}
