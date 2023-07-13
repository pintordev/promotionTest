package com.sbs.promotionTest.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        Optional<_User> _user = this.userRepository.findByNickname(nickname);

        if(!_user.isPresent()) {
            throw new UsernameNotFoundException("입력한 닉네임을 가진 사용자를 찾을 수 없습니다.");
        }

        _User user = _user.get();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (user.getNickname().equals("admin")) {
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(user.getNickname(), user.getPassword(), authorityList);
    }
}
