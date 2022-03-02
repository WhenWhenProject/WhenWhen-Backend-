package backend.restdocs.plan;

import backend.api.controller.dto.request.PlanEnrollRequest;
import backend.api.controller.dto.request.SignUpRequest;
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
import java.util.Date;
import java.util.UUID;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnrollDocumentationTest extends ApiDocumentationTest {

    @Test
    public void enroll() throws Exception {
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

        PlanEnrollRequest request = new PlanEnrollRequest();
        request.setTitle("글리 정모");
        request.setStartDate(LocalDate.of(2017, 03, 02));
        request.setEndDate(LocalDate.of(2017, 03, 06));
        request.setExpectedMemberCnt(5L);
        request.setLocation("왕십리");
        request.setStartHour(16);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/plan/")
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(print())
                .andDo(document("api-plan-enroll",
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
                                fieldWithPath("title").type(JsonFieldType.STRING).description("일정 제목"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("조사 범위 시작 날짜"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("조사 범위 끝 날짜"),
                                fieldWithPath("expectedMemberCnt").type(JsonFieldType.NUMBER).description("조사에 참여할 사람 수"),
                                fieldWithPath("location").type(JsonFieldType.STRING).description("일정 위치"),
                                fieldWithPath("startHour").type(JsonFieldType.NUMBER).description("일정 시작 시간")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.create").type(JsonFieldType.STRING).description("생성 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

}
