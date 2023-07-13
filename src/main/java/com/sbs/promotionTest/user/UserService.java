package com.sbs.promotionTest.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String nickname, String password, String email) {
        _User user = new _User();
        user.setNickname(nickname);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setEmail(email);
        user.setCreateDate(LocalDateTime.now());
        this.userRepository.save(user);
    }

    public boolean hasNickName(String nickname) {
        Optional<_User> _user = this.userRepository.findByNickname(nickname);
        return _user.isPresent();
    }

    public boolean hasEmail(String email) {
        Optional<_User> _user = this.userRepository.findByEmail(email);
        return _user.isPresent();
    }
}
