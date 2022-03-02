package backend.restdocs;

import backend.api.controller.dto.request.ChangeUserRequest;
import backend.api.service.dto.UserDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserDocumentationTest extends ApiDocumentationTest {

    @Test
    void find() throws Exception {
        // given
        String username = "abc123";
        String nickName = "baby";
        String email = "aaa@naver.com";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

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
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-user-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
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
    void update() throws Exception {
        // given
        String username = "abc123";
        String newNickName = "baby";
        String newEmail = "bbb@naver.com";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        ChangeUserRequest request = new ChangeUserRequest();
        request.setNickName(newNickName);
        request.setEmail(newEmail);

        given(userService.changeUserInfo(any(), any()))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .username(username)
                        .nickName(newNickName)
                        .email(newEmail)
                        .profileImageUrl(null)
                        .providerType(null)
                        .roleType(RoleType.USER)
                        .build());

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/user/my-info")
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-user-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
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
