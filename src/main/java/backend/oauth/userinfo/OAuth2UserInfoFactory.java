package backend.oauth.userinfo;

import backend.oauth.entity.ProviderType;
import backend.oauth.userinfo.impl.GoogleOAuth2UserInfo;
import backend.oauth.userinfo.impl.KakaoOAuth2UserInfo;
import backend.oauth.userinfo.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("유효하지 않은 Provider 타입입니다..");
        }
    }

}
