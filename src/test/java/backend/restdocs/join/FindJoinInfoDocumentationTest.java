package backend.restdocs.join;

import backend.api.controller.dto.request.PlanEnrollRequest;
import backend.api.service.dto.JoinDto;
import backend.api.service.dto.JoinInfoDto;
import backend.api.service.dto.PlanDto;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.restdocs.ApiDocumentationTest;
import backend.token.AuthToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FindJoinInfoDocumentationTest extends ApiDocumentationTest {

    @Test
    public void findJoinInfo() throws Exception {
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

        UserDto usedDto = UserDto.builder()
                .id(1L)
                .username(username)
                .nickName(nickName)
                .email(email)
                .profileImageUrl(null)
                .providerType(null)
                .roleType(RoleType.USER)
                .build();

        PlanDto planDto = PlanDto.builder()
                .id(1L)
                .host(usedDto)
                .title("멋사 저녁 회식")
                .startDate(LocalDate.of(2017, 03, 02))
                .endDate(LocalDate.of(2017, 03, 06))
                .expectedMemberCnt(5L)
                .fixed(false)
                .linkUrl(UUID.randomUUID().toString())
                .location("왕십리")
                .startHour(16)
                .build();

        JoinDto joinDto = JoinDto.builder()
                .id(1L)
                .userDto(usedDto)
                .planDto(planDto)
                .build();

        JoinInfoDto joinInfo1 = JoinInfoDto.builder()
                .id(1L)
                .joinDto(joinDto)
                .localDate(LocalDate.of(2017, 03, 02))
                .startHour(16)
                .endHour(18)
                .build();

        JoinInfoDto joinInfo2 = JoinInfoDto.builder()
                .id(2L)
                .joinDto(joinDto)
                .localDate(LocalDate.of(2017, 03, 03))
                .startHour(16)
                .endHour(18)
                .build();

        JoinInfoDto joinInfo3 = JoinInfoDto.builder()
                .id(3L)
                .joinDto(joinDto)
                .localDate(LocalDate.of(2017, 03, 04))
                .startHour(16)
                .endHour(18)
                .build();

        ArrayList<JoinInfoDto> joinInfoDtoList = new ArrayList<>(Arrays.asList(joinInfo1, joinInfo2, joinInfo3));

        given(joinService.findJoinInfoList(any(), any()))
                .willReturn(joinInfoDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/join/{planId}", 1L)
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(print())
                .andDo(document("api-join-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("planId").description("일정 아이디")
                        ),
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
                                fieldWithPath("body.join-info-list.[].localDate").type(JsonFieldType.STRING).description("참여 정보 날짜"),
                                fieldWithPath("body.join-info-list.[].startHour").type(JsonFieldType.NUMBER).description("참여 정보 시작 시간"),
                                fieldWithPath("body.join-info-list.[].endHour").type(JsonFieldType.NUMBER).description("참여 정보 끝 시간")
                         )
                ))
                .andExpect(status().isOk());
    }

}
