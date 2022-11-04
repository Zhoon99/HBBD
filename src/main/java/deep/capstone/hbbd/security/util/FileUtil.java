package deep.capstone.hbbd.security.util;

import deep.capstone.hbbd.dto.ImageDto;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtil {

    @Value("${image.upload.path}")
    private String updatePath;

    public ImageDto saveFileAndThumbnail(MultipartFile file, int thumbWidth, int thumbHeight) {
        //실제 파일 이름 IE나 Edge 는 전체 경로가 들어오므로 마지막 단어를 가져옴
        String originalName = file.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

        //날짜 폴더 생성
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("//", File.separator);

        File uploadPathFolder = new File(updatePath, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }

        //UUID
        String uuid = UUID.randomUUID().toString();

        //저장할 파일 이름 중간에 "_"를 이용해서 구분
        String saveName = updatePath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);

        try {
            //원본 파일 저장
            file.transferTo(savePath);

            //썸네일 생성
            String thumbnailSaveName = updatePath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
            File thumbnailFile = new File(thumbnailSaveName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, thumbWidth, thumbHeight);

            ImageDto imageDto = ImageDto.builder()
                    .path(folderPath)
                    .uuid(uuid)
                    .imgName(fileName)
                    .build();

            return imageDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
