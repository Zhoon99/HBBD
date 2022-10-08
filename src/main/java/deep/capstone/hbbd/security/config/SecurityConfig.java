package deep.capstone.hbbd.security.config;

import deep.capstone.hbbd.security.oauth.handler.Oauth2LoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception { //webIgnore - 필터를 거치지 않음(permitAll 차이점)
        web
                .ignoring()
                .antMatchers("/vendor/**", "/favicon.ico", "/resources/**", "/error");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); //js,css,img 파일 같이 필터를 적용할 필요가 없는 리소스 적용
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors() //CORS on
                .and()
                .csrf().disable(); //CSRF off
        http
                .authorizeRequests()
                .antMatchers("/", "/login*", "/signUp", "/createUser", "/socialSignUp", "/class/register").permitAll()
                .antMatchers("/mypage").hasRole("USER") //hasRole : ROLE_권한명(prefix 로 ROLE_이 붙음)
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/login_proc")
                .permitAll()

                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(oauth2LoginSuccessHandler())
                .permitAll()

                .and()
                .logout()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public Oauth2LoginSuccessHandler oauth2LoginSuccessHandler() {
        return new Oauth2LoginSuccessHandler();
    }

}
