package deep.capstone.hbbd.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/class")
public class ClassController {

    @GetMapping("register")
    public String register() {
        return "user/class_register";
    }
}
