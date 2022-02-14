package backend.api.service;

import backend.api.entity.User;
import backend.api.service.dto.UserDto;
import backend.api.exception.UserNotFountException;
import backend.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findByUsername(String username) {
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFountException());

        return UserDto.of(findUser);
    }

}
