package deep.capstone.hbbd.security.oauth.handler;

import deep.capstone.hbbd.security.form.service.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("----------------------인증 성공----------------------");

        setDefaultTargetUrl("/");

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (userPrincipal.getAccount().getNickname().equals("")) {
            redirectStrategy.sendRedirect(request, response, "/socialSignUp");
        } else {
            if(savedRequest != null) {
                String targetUrl = savedRequest.getRedirectUrl();
                redirectStrategy.sendRedirect(request, response, targetUrl);
            } else {
                redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
            }
        }
    }
}
