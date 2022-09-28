package deep.capstone.hbbd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping(value="/login2")
    public String index() throws Exception {
        return "login/login";
    }

    @GetMapping(value="/test")
    public String signuptest() throws Exception {
        return "social_sign_up";
    }

}
