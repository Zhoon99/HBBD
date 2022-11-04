package deep.capstone.hbbd.controller.user;

import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.repository.CategoryRepository;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;

    private final CategoryRepository categoryRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "user/class_register";
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public void register(@RequestPart(value = "class") ClassesDto.Request classDto,
                         @RequestPart(value = "schedule") ClassScheduleDto.RequestList classScheduleDto,
                         @RequestPart(value = "repImg", required = false) MultipartFile repImg,
                         @RequestPart(value = "activityImg", required = false) MultipartFile[] activityImg,
                         Authentication authentication) {

        classService.registerClass(classDto, classScheduleDto, repImg, activityImg, authentication);
    }
}
