package deep.capstone.hbbd.security.form.service;

import deep.capstone.hbbd.entity.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

  private static final long serialVersionUID = 1L; //직렬화 사용에 필요
  private Account account;

  private List<GrantedAuthority> roles;

  private Map<String, Object> attributes;

  // 일반 시큐리티 로그인시 사용
  public UserPrincipal(Account account, List<GrantedAuthority> roles) {
    this.account = account;
    this.roles = roles;
  }

  // OAuth2.0 로그인시 사용
  public UserPrincipal(Account account, List<GrantedAuthority> roles, Map<String, Object> attributes) {
    this.account = account;
    this.roles = roles;
    this.attributes = attributes;
  }

  public Account getAccount() {
    return account;
  }

  @Override
  public String getPassword() {
    return account.getPassword();
  }

  @Override
  public String getUsername() {
    return account.getEmail(); //username = email
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> collect = account.getUserRoles()
            .stream()
            .map(userRole -> userRole.getRoleName())
            .collect(Collectors.toSet())
            .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    return collect;
  }

  // 리소스 서버로 부터 받는 회원정보
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  // User의 PrimaryKey
  @Override
  public String getName() {
    return account.getId()+"";
  }

}
