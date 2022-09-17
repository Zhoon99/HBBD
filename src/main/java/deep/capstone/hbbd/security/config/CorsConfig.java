package deep.capstone.hbbd.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer { //CORS 설정

   private final long MAX_AGE_SECS = 3600;

   //쿠키는 동일 출처 정책에따라 같은 도메인에서만 작동
   //헤더에 직접 쿠키를 담아 줘도 서버에서 거부함 (http only : http 가 아닌 다른 곳(ajax 등)에서 쿠키 사용 금지)
   //따라서 아래 설정을 통해 모든 요청 허용 (CrossOrigin 정책 비활성화)

   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry
              //CORS 적용할 URL 패턴
              .addMapping("/**")
              //자원을 공유할 오리진(ip) 지정
              .allowedOriginPatterns("*")
              //요청 허용 메서드
              .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
              .allowedHeaders("*")
              .allowCredentials(true) //http 가 아닌 ajax 등에서 요청 가능
              .maxAge(MAX_AGE_SECS);
   }
}