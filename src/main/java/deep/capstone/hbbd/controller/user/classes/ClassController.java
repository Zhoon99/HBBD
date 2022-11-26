package deep.capstone.hbbd.controller.user.classes;

import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.dto.CommentDto;
import deep.capstone.hbbd.repository.CategoryRepository;
import deep.capstone.hbbd.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/class")
public class ClassController {

    private final ClassService classService;

    private final CategoryRepository categoryRepository;

    @GetMapping("/register")
    public String register() {
        return "user/class/class_register";
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

    @GetMapping("/comment/{cId}")
    public String comment(@PathVariable Long cId, Model model) {
        model.addAttribute("cId", cId);
        return "user/class/class_comment";
    }

    @PostMapping(value = "/comment/register")
    @ResponseBody
    public void commentRegister(@RequestPart(value = "comment") CommentDto.Response commentDto,
                                @RequestPart(value = "commentImg", required = false) MultipartFile[] commentImg,
                                Authentication authentication) {

        log.info("-------------------------" + commentDto.toString());
        log.info("-------------------------" + commentImg.length);

    }
}
