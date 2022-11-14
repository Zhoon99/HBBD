package deep.capstone.hbbd.controller;

import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import deep.capstone.hbbd.dto.PreviewDto;
import deep.capstone.hbbd.repository.ClassesRepository;
import deep.capstone.hbbd.repository.custom.CustomClassesRepository;
import deep.capstone.hbbd.service.ClassService;
import deep.capstone.hbbd.service.impl.ClassServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final CustomClassesRepository customClassesRepository;

    private final ClassesRepository classesRepository;

    private final ClassServiceImpl classService;

    @GetMapping(value="/test")
    public String test(Model model) throws Exception {
//        List<PreviewDto> previewInfo = classService.getPreviewList();
//        model.addAttribute("test", previewInfo);
        return "test";
    }

    @PostMapping(value = "/curTest")
    @ResponseBody
    public void curTest(@RequestPart(value = "class") ClassesDto.Request classDto,
                        @RequestPart(value = "schedule") ClassScheduleDto.RequestList classScheduleDto,
                        @RequestPart(value = "repImg", required = false) MultipartFile repImg,
                        @RequestPart(value = "activityImg[]", required = false) MultipartFile a[]) {
        System.out.println("aaaaaaaaaa" + classDto.toString());
        System.out.println("2222222222" + classScheduleDto.toString());
        System.out.println("bbbbbbbbbb" + repImg.getOriginalFilename());
        System.out.println("cccccccccc" + a.length);
    }
}
