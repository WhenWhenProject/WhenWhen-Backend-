package backend.restdocs.auth;

import backend.api.controller.dto.request.LoginRequest;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginDocumentationTest extends ApiDocumentationTest {

    @Test
    public void login() throws Exception {
        // given
        String username = "abc123";
        String password = "1234";

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

        given(userService.login(any(), any(), any(), any()))
                .willReturn(accessToken);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andReturn().getResponse().addCookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()));

        // then
        result
                .andDo(print())
                .andDo(document("api-auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("계정 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.SET_COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰")
                        )
                ))
                .andExpect(status().isOk());
    }

}
