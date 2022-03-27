package backend.restdocs;

import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.controller.dto.request.TokenRefreshRequest;
import backend.oauth.entity.RoleType;
import backend.restdocs.utils.ApiDocumentationTest;
import backend.token.JwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static backend.util.HeaderConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthDocumentationTest extends ApiDocumentationTest {

    @Test
    public void signUp() throws Exception {
        // given
        String username = "abc123";
        String password = "1234";
        String nickName = "baby";
        String email = "aaa@naver.com";

        SignUpRequest request = new SignUpRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setNickName(nickName);
        request.setEmail(email);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/auth/sign-up")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-auth-sign-up",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("계정 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일 주소").optional()
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("가입 성공 여부")
                        )
                ))

                .andExpect(status().isOk());
    }

    @Test
    public void login() throws Exception {
        // given
        String username = "abc123";
        String password = "1234";

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());
        given(userService.login(any()))
                .willReturn(jwtToken);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("계정 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void refresh() throws Exception {
        // given
        String username = "abc123";

        JwtToken originJwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());
        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setRefreshToken(originJwtToken.getRefreshToken());

        JwtToken newJwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());
        given(userService.refresh(any()))
                .willReturn(newJwtToken);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/auth/refresh")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + originJwtToken.getAccessToken())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-auth-refresh",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("엑세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
                        )
                ))
                .andExpect(status().isOk());
    }

}
