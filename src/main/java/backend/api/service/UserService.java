package backend.api.service;

import backend.api.entity.User;
import backend.api.repository.user.UserRepository;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto signUp(String username, String password, String nickName, String email) {
        User user = User.builder()
                .username(username)
                .password(password)
                .nickName(nickName)
                .email(email)
                .roleType(RoleType.USER)
                .build();

        userRepository.save(user);

        return UserDto.of(user);
    }

}
