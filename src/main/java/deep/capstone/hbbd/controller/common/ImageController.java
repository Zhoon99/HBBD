package deep.capstone.hbbd.controller.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    @Value("${image.upload.path}")
    private String updatePath;

    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(String img) {

        ResponseEntity<byte[]> result = null;

        try {
            log.info(img);
            String imgSrc = URLDecoder.decode(img, "UTF-8");
            log.info(imgSrc);
            File file = new File(updatePath + File.separator + imgSrc);
            HttpHeaders header = new HttpHeaders();

            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
