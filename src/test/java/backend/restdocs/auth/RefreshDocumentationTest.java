package backend.restdocs.auth;

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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RefreshDocumentationTest extends ApiDocumentationTest {

    @Test
    public void refresh() throws Exception {
        // given
        String username = "abc123";

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                username,
                RoleType.USER.getCode(),
                new Date(now.getTime() - appProperties.getAuth().getTokenExpiry()) // - 를 줌으로써 억지로 만료시키기
        );

        AuthToken newAccessToken = tokenProvider.createAuthToken(
                username,
                RoleType.USER.getCode(),
                new Date(now.getTime() + 12345 + appProperties.getAuth().getTokenExpiry())
        );

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        given(userService.updateRefreshToken(any(), any(), any(), any(), any()))
                .willReturn(newAccessToken);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/auth/refresh")
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andReturn().getResponse().addCookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()));

        // then
        result
                .andDo(print())
                .andDo(document("api-auth-refresh",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
//                                headerWithName("cookie").description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName("Cookie").description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName(HttpHeaders.SET_COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
//                                headerWithName(HttpHeaders.COOKIE).description(REFRESH_TOKEN + "=" + "{리프레시 토큰}"),
                                headerWithName(AUTHORIZATION_HEADER).description("엑세스 토큰")
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

