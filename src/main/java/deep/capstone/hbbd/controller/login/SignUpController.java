package deep.capstone.hbbd.controller.login;

import deep.capstone.hbbd.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final CategoryRepository categoryRepository;

    @GetMapping(value="/signUp")
    public String createUser(Model model) throws Exception {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "/login/sign_up";
    }

    @GetMapping(value="/socialSignUp")
    public String createSocialUser(Model model) throws Exception {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "login/social_sign_up";
    }
}
