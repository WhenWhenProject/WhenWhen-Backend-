package backend.restdocs.plan;


import backend.api.service.dto.PlanDto;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.restdocs.ApiDocumentationTest;
import backend.token.AuthToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllPlanParticipatedInDocumentationTest extends ApiDocumentationTest {

    @Test
    void getAllPlanParticipatedIn() throws Exception {
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

        UserDto host = UserDto.builder()
                .id(1L)
                .username(username)
                .nickName(nickName)
                .email(email)
                .profileImageUrl(null)
                .providerType(null)
                .roleType(RoleType.USER)
                .build();

        PlanDto plan1 = PlanDto.builder()
                .id(1L)
                .host(host)
                .title("멋사 저녁 회식")
                .startDate(LocalDate.of(2017, 03, 02))
                .endDate(LocalDate.of(2017, 03, 06))
                .expectedMemberCnt(5L)
                .fixed(false)
                .linkUrl(UUID.randomUUID().toString())
                .location("왕십리")
                .startHour(16)
                .build();

        PlanDto plan2 = PlanDto.builder()
                .id(2L)
                .host(host)
                .title("디프만 저녁 회식")
                .startDate(LocalDate.of(2017, 03, 02))
                .endDate(LocalDate.of(2017, 03, 06))
                .expectedMemberCnt(5L)
                .fixed(false)
                .linkUrl(UUID.randomUUID().toString())
                .location("왕십리")
                .startHour(16)
                .build();

        PlanDto plan3 = PlanDto.builder()
                .id(3L)
                .host(host)
                .title("알로하 저녁 회식")
                .startDate(LocalDate.of(2017, 03, 02))
                .endDate(LocalDate.of(2017, 03, 06))
                .expectedMemberCnt(5L)
                .fixed(false)
                .linkUrl(UUID.randomUUID().toString())
                .location("왕십리")
                .startHour(16)
                .build();

        ArrayList<PlanDto> planDtoList = new ArrayList<>(Arrays.asList(plan1, plan2, plan3));

        given(planService.getAllPlanParticipatedIn(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/all")
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(print())
                .andDo(document("api-plan-all",
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
                                fieldWithPath("body.list.[].id").type(JsonFieldType.NUMBER).description("일정 식별자"),
                                fieldWithPath("body.list.[].hostNickName").type(JsonFieldType.STRING).description("주최자 이름"),
                                fieldWithPath("body.list.[].title").type(JsonFieldType.STRING).description("일정 제목"),
                                fieldWithPath("body.list.[].startDate").type(JsonFieldType.STRING).description("조사 범위 시작 날짜"),
                                fieldWithPath("body.list.[].endDate").type(JsonFieldType.STRING).description("조사 범위 끝 날짜"),
                                fieldWithPath("body.list.[].expectedMemberCnt").type(JsonFieldType.NUMBER).description("조사에 참여할 사람 수"),
                                fieldWithPath("body.list.[].fixed").type(JsonFieldType.BOOLEAN).description("일정 확정 여부"),
                                fieldWithPath("body.list.[].linkUrl").type(JsonFieldType.STRING).description("일정 URL 링크"),
                                fieldWithPath("body.list.[].location").type(JsonFieldType.STRING).description("일정 위치"),
                                fieldWithPath("body.list.[].startHour").type(JsonFieldType.NUMBER).description("일정 시작 시간")
                        )
                ))
                .andExpect(status().isOk());
    }

}
