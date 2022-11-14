package deep.capstone.hbbd.controller;

import deep.capstone.hbbd.repository.custom.CustomClassesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QueryTestController {

    private final CustomClassesRepository customClassesRepository;

    @GetMapping(value = "/query")
    public void queryDslTest() {
        log.info(customClassesRepository.getPreviewInfo().toString());
    }
}
