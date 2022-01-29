package backend.service;

import backend.domain.User;
import backend.dto.UserDto;
import backend.exception.UserNotFountException;
import backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findById(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFountException());

        return UserDto.of(findUser);
    }


}
