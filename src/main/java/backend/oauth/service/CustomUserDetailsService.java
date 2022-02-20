package backend.oauth.service;

import backend.api.entity.User;
import backend.api.repository.user.UserRepository;
import backend.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService.loadUserByUsername()");
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Can not find username.");
        }

        return UserPrincipal.create(user);
    }

}
