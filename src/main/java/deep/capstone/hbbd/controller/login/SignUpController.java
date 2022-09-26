package deep.capstone.hbbd.controller.login;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ProfileDto;
import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.repository.CategoryRepository;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final CategoryRepository categoryRepository;
    private final AccountService accountService;

    @GetMapping(value="/signUp")
    public String signUp(Model model) throws Exception {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "/login/sign_up_form";
    }

    @GetMapping(value="/socialSignUp")
    public String socialSignUp(Model model, Authentication authentication) throws Exception {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("accountId", userPrincipal.getAccount().getId());
        return "login/social_sign_up";
    }

    @PostMapping(value = "/createUser")
    @ResponseBody
    public void createUser(@RequestPart(value = "account") AccountDto accountDto,
                        @RequestPart(value = "image", required = false) MultipartFile file) {

        accountService.createUser(accountDto, file);
    }

    @PostMapping(value = "loadHeader")
    @ResponseBody
    public ProfileDto loadHeader(Authentication authentication) {
        return accountService.loadHeader(authentication);
    }

}
