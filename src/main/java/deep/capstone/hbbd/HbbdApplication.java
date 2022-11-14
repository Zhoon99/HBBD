package deep.capstone.hbbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing //@CreatedDate 사용
public class HbbdApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbbdApplication.class, args);
    }

}
