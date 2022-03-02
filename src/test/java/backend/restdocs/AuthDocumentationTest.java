package backend.restdocs;

import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.oauth.entity.RoleType;
import backend.restdocs.utils.ApiDocumentationTest;
import backend.token.JwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static backend.util.HeaderConstant.HEADER_ACCESS_TOKEN;
import static backend.util.HeaderConstant.HEADER_REFRESH_TOKEN;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.signUp").type(JsonFieldType.STRING).description("회원가입 성공 여부")
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

        // when
        ResultActions result = mockMvc.perform(
                post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        result.andReturn().getResponse().addHeader(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken());
        result.andReturn().getResponse().addHeader(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken());

        // then
        result
                .andDo(document("api-auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("계정 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.login").type(JsonFieldType.STRING).description("로그인 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void refresh() throws Exception {
        // given
        String username = "abc123";

        JwtToken originJwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        JwtToken newJwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        // when
        ResultActions result = mockMvc.perform(
                get("/api/auth/refresh")
                        .header(HEADER_ACCESS_TOKEN, originJwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, originJwtToken.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andReturn().getResponse().addHeader(HEADER_ACCESS_TOKEN, newJwtToken.getAccessToken());
        result.andReturn().getResponse().addHeader(HEADER_REFRESH_TOKEN, newJwtToken.getRefreshToken());

        // then
        result
                .andDo(document("api-auth-refresh",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("기존 엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("기존 리프레시 토큰")
                        ),
                        responseHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("새로운 엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("새로운 리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.refresh").type(JsonFieldType.STRING).description("갱신 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

}
