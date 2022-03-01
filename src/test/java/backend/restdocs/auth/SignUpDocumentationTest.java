package backend.restdocs.auth;

import backend.api.controller.dto.request.SignUpRequest;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.restdocs.ApiDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignUpDocumentationTest extends ApiDocumentationTest {

    @Test
    public void signUp() throws Exception {
        // given
        String username = "abc123";
        String password = "1234";
        String nickName = "baby";
        String email = "aaa@naver.com";

        given(userService.signUp(any(), any(), any(), any()))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .username(username)
                        .nickName(nickName)
                        .email(email)
                        .profileImageUrl(null)
                        .providerType(null)
                        .roleType(RoleType.USER)
                        .build());

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
                .andDo(print())
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
                                fieldWithPath("body.user.nickName").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("body.user.email").type(JsonFieldType.STRING).description("이메일 주소"),
                                fieldWithPath("body.user.profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 주소").optional(),
                                fieldWithPath("body.user.providerType").type(JsonFieldType.STRING).description("oauth 타입").optional(),
                                fieldWithPath("body.user.roleType").type(JsonFieldType.STRING).description("계정 권한")
                        )
                ))
                .andExpect(status().isOk());
    }

}
