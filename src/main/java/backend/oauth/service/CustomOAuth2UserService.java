package backend.oauth.service;

import backend.api.entity.User;
import backend.api.repository.user.UserRepository;
import backend.oauth.entity.UserPrincipal;
import backend.oauth.entity.ProviderType;
import backend.oauth.entity.RoleType;
import backend.oauth.exception.OAuthProviderMissMatchException;
import backend.oauth.userinfo.OAuth2UserInfo;
import backend.oauth.userinfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAuth2UserService.loadUser()");
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());

        String username = providerType.name() + "_" + oAuth2UserInfo.getUsername();
        User findUser = userRepository.findByUsername(username);

        if (findUser != null) {
            if (providerType != findUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + findUser.getProviderType() + " account to login."
                );
            }
            updateUser(findUser, oAuth2UserInfo);
        } else {
            findUser = createUser(oAuth2UserInfo, providerType);
        }

        return UserPrincipal.create(findUser, oAuth2User.getAttributes());
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

        return userRepository.saveAndFlush(user);
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