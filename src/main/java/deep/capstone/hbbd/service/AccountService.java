package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.dto.ProfileDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    void createUser(AccountDto accountDto, MultipartFile file);

    ProfileDto loadHeader(Authentication authentication);

    ProfileDto getUserprofile(Long accountId);
}
