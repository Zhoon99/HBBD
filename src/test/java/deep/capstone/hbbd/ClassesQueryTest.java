package deep.capstone.hbbd;

import deep.capstone.hbbd.repository.custom.CustomClassesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
@Slf4j
public class ClassesQueryTest {

    private final CustomClassesRepository customClassesRepository;

    @Test
    @DisplayName("preview 전체 조회")
    public void queryDslTest() {
        log.info(customClassesRepository.getPreviewInfo().toString());
    }
}
