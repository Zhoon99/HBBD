package deep.capstone.hbbd.controller.login;


import deep.capstone.hbbd.dto.ProfileDto;
import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final AccountService accountService;

	@RequestMapping(value="/login")
	public String login(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "exception", required = false) String exception, Model model){
		model.addAttribute("error",error);
		model.addAttribute("exception",exception);
		return "login/login";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/login";
	}

	@GetMapping(value="/denied")
	public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Principal principal, Model model) throws Exception {

		Account account = null;

		if (principal instanceof UsernamePasswordAuthenticationToken) {
			account = (Account) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

		}

//		else if(principal instanceof AjaxAuthenticationToken){
//			account = (Account) ((AjaxAuthenticationToken) principal).getPrincipal();
//		}

		model.addAttribute("email", account.getEmail());
		model.addAttribute("exception", exception);

		return "user/login/denied";
	}

	@PostMapping(value = "loadHeader")
	@ResponseBody
	public ProfileDto loadHeader(Authentication authentication) {
		return accountService.loadHeader(authentication);
	}
}
