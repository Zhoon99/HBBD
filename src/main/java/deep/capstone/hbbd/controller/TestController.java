package deep.capstone.hbbd.controller;

import deep.capstone.hbbd.dto.ClassScheduleDto;
import deep.capstone.hbbd.dto.ClassesDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestController {

    @GetMapping(value="/test")
    public String test() throws Exception {
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
