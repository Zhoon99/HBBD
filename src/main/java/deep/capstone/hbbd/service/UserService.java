package deep.capstone.hbbd.service;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.entity.Account;

import java.util.List;


public interface UserService {

    void createUser(AccountDto accountDto);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);
}
