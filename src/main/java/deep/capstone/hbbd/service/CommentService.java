package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.CommentDto;
import deep.capstone.hbbd.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {

    void commentRegister(CommentDto.Response commentDto, MultipartFile[] commentImgFiles, Account account);
}
