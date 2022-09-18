package deep.capstone.hbbd.controller;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class TestController {

    // 이미지 저장 위치
    String rootPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\upload";

    @RequestMapping(value = "/url", method = RequestMethod.POST)
    public String getData(@RequestPart(value = "account") AccountDto accountDto,
                          @RequestPart(value = "profile") ProfileDto profileDto,
                          @RequestPart(value = "image") MultipartFile file, Model model) {

        System.out.println(accountDto.toString() + " " + profileDto.toString());

        if (!file.isEmpty()) {

            //실제 파일 이름 IE나 Edge 는 전체 경로가 들어오므로
            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            //날짜 폴더 생성
            String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            String folderPath = str.replace("//", File.separator);

            File uploadPathFolder = new File(rootPath, folderPath);

            if (uploadPathFolder.exists() == false) {
                uploadPathFolder.mkdirs();
            }

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = rootPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try {
                //원본 파일 저장
                file.transferTo(savePath);



            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //기본 이미지 저장
        }

        model.addAttribute("log", "사진 전송완료!");
        return "login/sign_up :: #resultDiv";
    }

//    @RequestMapping(value = "/url", method = RequestMethod.POST)
//    public String getData(Model model, @RequestPart(value = "account") AccountDto accountDto, ProfileDto profileDto, MultipartHttpServletRequest req) {
//
//        System.out.println(accountDto.toString() + " " + profileDto.toString());
//
//        //get image file.
//        List<MultipartFile> multipartFileList = new ArrayList<>();
//        try {
//            MultiValueMap<String, MultipartFile> files = req.getMultiFileMap();
//            for (Map.Entry<String, List<MultipartFile>> entry : files.entrySet()) {
//                List<MultipartFile> fileList = entry.getValue();
//                for (MultipartFile file : fileList) {
//                    if (file.isEmpty()) continue;
//                    multipartFileList.add(file);
//                }
//            }
//            if (multipartFileList.size() > 0) {
//                for (MultipartFile file : multipartFileList) {
//
//                    //실제 파일 이름 IE나 Edge 는 전체 경로가 들어오므로
//                    String originalName = file.getOriginalFilename();
//                    String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//
//                    //날짜 폴더 생성
//                    String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//
//                    String folderPath =  str.replace("//", File.separator);
//
//                    File uploadPathFolder = new File(rootPath, folderPath);
//
//                    if (uploadPathFolder.exists() == false) {
//                        uploadPathFolder.mkdirs();
//                    }
//
//                    //UUID
//                    String uuid = UUID.randomUUID().toString();
//
//                    //저장할 파일 이름 중간에 "_"를 이용해서 구분
//                    String saveName = rootPath + File.separator + folderPath + File.separator + uuid +"_" + fileName;
//                    Path savePath = Paths.get(saveName);
//
//                    try {
//                        //원본 파일 저장
//                        file.transferTo(savePath);
//
//                        //resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("has no multipartFile!");
//        }
//
//        model.addAttribute("log", "사진 " + multipartFileList.size() + "장 전송완료!");
//        return "login/sign_up :: #resultDiv";
//    }
}
