package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.AccountDto;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    void createUser(AccountDto accountDto, MultipartFile file);
}
