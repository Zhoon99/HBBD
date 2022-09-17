package deep.capstone.hbbd.controller.login;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping(value="/users")
	public String createUser() throws Exception {

		return "user/login/register";
	}

	@PostMapping(value="/users")
	public String createUser(AccountDto accountDto) throws Exception {

		userService.createUser(accountDto);
		return "redirect:/";
	}

	@GetMapping(value="/mypage")
	public String myPage() throws Exception {
		return "user/mypage";
	}
}
