package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.AccountDto;
import deep.capstone.hbbd.entity.Account;
import deep.capstone.hbbd.entity.Role;
import deep.capstone.hbbd.repository.RoleRepository;
import deep.capstone.hbbd.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl {

//    private final AccountRepository accountRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Transactional
//    @Override
//    public void createUser(AccountDto accountDto){
//        ModelMapper modelMapper = new ModelMapper();
//        Account account = modelMapper.map(accountDto, Account.class);
//
//        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//
//        Role role = roleRepository.findByRoleName("ROLE_USER");
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        account.setUserRoles(roles);
//
//        accountRepository.save(account);
//    }
//
//    @Transactional
//    @Override
//    public void modifyUser(AccountDto accountDto){
//
//        ModelMapper modelMapper = new ModelMapper();
//        Account account = modelMapper.map(accountDto, Account.class);
//
//        if(accountDto.getRoles() != null){
//            Set<Role> roles = new HashSet<>();
//            accountDto.getRoles().forEach(role -> {
//                Role r = roleRepository.findByRoleName(role);
//                roles.add(r);
//            });
//            account.setUserRoles(roles);
//        }
//        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//        accountRepository.save(account);
//
//    }
//
//    @Transactional
//    public AccountDto getUser(Long id) {
//
//        Account account = accountRepository.findById(id).orElse(new Account());
//        ModelMapper modelMapper = new ModelMapper();
//        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
//
//        List<String> roles = account.getUserRoles()
//                .stream()
//                .map(role -> role.getRoleName())
//                .collect(Collectors.toList());
//
//        accountDto.setRoles(roles);
//        return accountDto;
//    }
//
//    @Transactional
//    public List<Account> getUsers() {
//        return accountRepository.findAll();
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        accountRepository.deleteById(id);
//    }
}