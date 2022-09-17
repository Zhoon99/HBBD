package deep.capstone.hbbd.controller.login;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    @GetMapping(value="/signUp")
    public String createUser() throws Exception {
        return "login/sign_up";
    }

    @PostMapping(value="/signUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String createUser(AccountDto accountDto, ProfileDto profileDto) throws Exception {
        System.out.println(accountDto.toString() + "\n" + profileDto.toString());
        return "redirect:/";
    }
}
