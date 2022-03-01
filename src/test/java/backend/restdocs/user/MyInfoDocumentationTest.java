package backend.restdocs.user;

import backend.api.controller.dto.request.ChangeUserRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.restdocs.ApiDocumentationTest;
import backend.token.AuthToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.Date;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MyInfoDocumentationTest extends ApiDocumentationTest {

    @Test
    void getMyInfo() throws Exception {
        // given
        String username = "abc123";
        String nickName = "baby";
        String email = "aaa@naver.com";

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                username,
                RoleType.USER.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        given(userService.findByUsername(any()))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .username(username)
                        .nickName(nickName)
                        .email(email)
                        .profileImageUrl(null)
                        .providerType(null)
                        .roleType(RoleType.USER)
                        .build());

        // when
        ResultActions result = mockMvc.perform(
                get("/api/user/my-info")
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(print())
                .andDo(document("api-user-get-my-info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
//                                headerWithName("cookie").description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName("Cookie").description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName(HttpHeaders.SET_COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName(HttpHeaders.COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
                                headerWithName(AUTHORIZATION_HEADER).description("엑세스 토큰")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.user.nickName").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("body.user.email").type(JsonFieldType.STRING).description("이메일").optional(),
                                fieldWithPath("body.user.profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 주소").optional(),
                                fieldWithPath("body.user.providerType").type(JsonFieldType.STRING).description("OAuth 제공자 종류").optional(),
                                fieldWithPath("body.user.roleType").type(JsonFieldType.STRING).description("유저 권한")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void updateMyInfo() throws Exception {
        // given
        String username = "abc123";

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                username,
                RoleType.USER.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        String changeNickName = "baby";
        String changeEmail = "bbb@naver.com";

        given(userService.changeUserInfo(any(), any()))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .username(username)
                        .nickName(changeNickName)
                        .email(changeEmail)
                        .profileImageUrl(null)
                        .providerType(null)
                        .roleType(RoleType.USER)
                        .build());

        ChangeUserRequest request = new ChangeUserRequest();
        request.setNickName(changeNickName);
        request.setEmail(changeEmail);

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/user/my-info")
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(print())
                .andDo(document("api-user-patch-my-info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
//                                headerWithName("cookie").description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName("Cookie").description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName(HttpHeaders.SET_COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName(HttpHeaders.COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
                                headerWithName(AUTHORIZATION_HEADER).description("엑세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("변경할 닉네임"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("변경할 이메일 주소").optional()
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.user.nickName").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("body.user.email").type(JsonFieldType.STRING).description("이메일").optional(),
                                fieldWithPath("body.user.profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 주소").optional(),
                                fieldWithPath("body.user.providerType").type(JsonFieldType.STRING).description("OAuth 제공자 종류").optional(),
                                fieldWithPath("body.user.roleType").type(JsonFieldType.STRING).description("유저 권한")
                        )
                ))
                .andExpect(status().isOk());
    }

}
