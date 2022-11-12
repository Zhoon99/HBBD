package deep.capstone.hbbd.controller.user.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class ProfileController {

    @GetMapping("/info")
    public String profileInfo() {
        return "user/profile/user_profile";
    }
}
