package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ClassService {

    void registerClass(ClassesDto.Request classesDto, ClassScheduleDto.RequestList classScheduleDto, MultipartFile repImg, MultipartFile[] activityImg, Authentication authentication);
}
