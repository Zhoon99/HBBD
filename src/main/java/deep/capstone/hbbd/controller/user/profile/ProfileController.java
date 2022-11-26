package deep.capstone.hbbd.controller.user.profile;

import deep.capstone.hbbd.dto.ProfileDto;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class ProfileController {

    private final AccountService accountService;

    @GetMapping("/info")
    public String profileInfo(Model model, Authentication authentication)
    {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        ProfileDto userprofile = accountService.getUserprofile(userPrincipal.getAccount().getId());
        model.addAttribute("profile", userprofile);
        return "user/profile/user_profile";
    }
}
