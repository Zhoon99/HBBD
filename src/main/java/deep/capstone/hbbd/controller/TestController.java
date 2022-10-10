package deep.capstone.hbbd.controller;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.CurListDto;
import deep.capstone.hbbd.dto.CurTestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestController {

    @GetMapping(value="/test")
    public String test() throws Exception {
        return "test";
    }

    @PostMapping(value = "/curTest")
    @ResponseBody
    public void curTest(HttpServletRequest req) {
        System.out.println(req.getParameter("curList"));
    }

}
