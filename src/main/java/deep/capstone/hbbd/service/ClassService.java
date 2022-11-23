package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.dto.PreviewDto;
import deep.capstone.hbbd.entity.Classes;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClassService {

    void registerClass(ClassesDto.Request classesDto, ClassScheduleDto.RequestList classScheduleDto, MultipartFile repImg, MultipartFile[] activityImg, Authentication authentication);

    List<PreviewDto> getPreviewList(List<Classes> classesList);

    ClassesDto.detail getClassesDetail(Long cId);
}
