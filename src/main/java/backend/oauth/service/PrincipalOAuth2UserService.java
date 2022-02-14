package backend.oauth.service;

import backend.api.entity.User;
import backend.api.repository.user.UserRepository;
import backend.oauth.entity.PrincipalDetails;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("PrincipalOAuth2UserService.loadUser");
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

            OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oauth2User.getAttributes());

            String username = providerType.name() + "_" + oAuth2UserInfo.getUsername();

            Optional<User> findUser = userRepository.findByUsername(username);

            User savedUser = null;
            if (findUser.isEmpty()) {
                savedUser = createUser(oAuth2UserInfo, providerType, username);
            }
            else {
                if (providerType != findUser.get().getProviderType()) {
                    throw new OAuthProviderMissMatchException("Looks like you're signed up with " + providerType + " account. Please use your " + findUser.get().getProviderType() + " account to login.");
                }

                savedUser = updateUser(findUser.get(), oAuth2UserInfo);
            }

            return PrincipalDetails.create(savedUser, oauth2User.getAttributes());
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private User createUser(OAuth2UserInfo oAuth2UserInfo, ProviderType providerType, String username) {
        User user = User.builder()
                .username(username)
                .nickName(oAuth2UserInfo.getNickName())
                .email(oAuth2UserInfo.getEmail())
                .profileImageUrl(oAuth2UserInfo.getImageUrl() == null ? "" : oAuth2UserInfo.getImageUrl())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .build();

        return userRepository.saveAndFlush(user);
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        if (oAuth2UserInfo.getNickName() != null && !user.getNickName().equals(oAuth2UserInfo.getNickName())) {
            user.changeNickName(oAuth2UserInfo.getNickName());
        }

        if (oAuth2UserInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(oAuth2UserInfo.getImageUrl())) {
            user.changeProfileImageUrl(oAuth2UserInfo.getImageUrl());
        }

        return user;
    }

}
