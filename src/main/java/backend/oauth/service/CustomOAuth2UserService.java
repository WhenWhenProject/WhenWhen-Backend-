package backend.oauth.service;

import backend.api.entity.User;
import backend.api.repository.user.UserRepository;
import backend.oauth.entity.ProviderType;
import backend.oauth.entity.RoleType;
import backend.oauth.entity.UserPrincipal;
import backend.oauth.userinfo.OAuth2UserInfo;
import backend.oauth.userinfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());

        String username = providerType.name() + "_" + oAuth2UserInfo.getUsername();

        User user = null;
        if (userRepository.findByUsername(username).isPresent()) {
            user = userRepository.findByUsername(username).get();
            updateUser(user, oAuth2UserInfo);
        } else {
            user = createUser(oAuth2UserInfo, providerType);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User createUser(OAuth2UserInfo oAuth2UserInfo, ProviderType providerType) {
        User user = User.builder()
                .username(oAuth2UserInfo.getUsername())
                .nickName(oAuth2UserInfo.getNickName())
                .email(oAuth2UserInfo.getEmail())
                .profileImageUrl(oAuth2UserInfo.getImageUrl())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .build();

        return userRepository.save(user);
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        if (oAuth2UserInfo.getNickName() != null && !user.getUsername().equals(oAuth2UserInfo.getNickName())) {
            user.changeNickName(oAuth2UserInfo.getNickName());
        }

        if (oAuth2UserInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(oAuth2UserInfo.getImageUrl())) {
            user.changeProfileImageUrl(oAuth2UserInfo.getImageUrl());
        }

        return user;
    }

}