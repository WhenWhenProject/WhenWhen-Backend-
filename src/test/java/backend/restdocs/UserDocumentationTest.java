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
import static backend.util.HeaderConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-user-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????").optional(),
                                fieldWithPath("data.profileImageUrl").type(JsonFieldType.STRING).description("????????? ????????? ??????").optional(),
                                fieldWithPath("data.providerType").type(JsonFieldType.STRING).description("OAuth ????????? ??????").optional(),
                                fieldWithPath("data.roleType").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ))
                .andExpect(status().isOk())
                .andDo(print());
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

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/user/my-info")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
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
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("????????? ?????????"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ????????? ??????").optional()
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                        )
                ))
                .andExpect(status().isOk());
    }

}
