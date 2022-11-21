package deep.capstone.hbbd;

import deep.capstone.hbbd.repository.custom.CustomClassesRepository;
import deep.capstone.hbbd.security.util.AES128;
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
    public void queryDslTest() throws Exception {
        AES128 aes128 = new AES128("assdfghjk123456788");
        String enc = aes128.encrypt("띠용띠용");
        String dec = aes128.decrypt(enc);
        log.info(enc);
        log.info(dec);
    }
}
