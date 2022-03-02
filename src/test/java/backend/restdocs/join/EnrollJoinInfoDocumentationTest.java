package backend.restdocs.join;

import backend.api.controller.dto.request.JoinEnrollRequest;
import backend.api.controller.dto.request.JoinInfoRequest;
import backend.api.service.dto.JoinDto;
import backend.api.service.dto.JoinInfoDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnrollJoinInfoDocumentationTest extends ApiDocumentationTest {

    @Test
    public void enrollJoinInfo() throws Exception {
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

        JoinInfoRequest request1 = new JoinInfoRequest();
        request1.setLocalDate(LocalDate.of(2017, 03, 02));
        request1.setStartHour(17);
        request1.setEndHour(18);

        JoinInfoRequest request2 = new JoinInfoRequest();
        request2.setLocalDate(LocalDate.of(2017, 03, 03));
        request2.setStartHour(17);
        request2.setEndHour(18);

        JoinInfoRequest request3 = new JoinInfoRequest();
        request3.setLocalDate(LocalDate.of(2017, 03, 04));
        request3.setStartHour(17);
        request3.setEndHour(18);

        ArrayList<JoinInfoRequest> joinInfoRequestList = new ArrayList<>(Arrays.asList(request1, request2, request3));

        JoinEnrollRequest joinEnrollRequest = new JoinEnrollRequest();
        joinEnrollRequest.setJoinInfoRequestList(joinInfoRequestList);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/join/{planId}", 1L)
                        .header(AUTHORIZATION_HEADER, "Bearer " + accessToken.getToken())
                        .cookie(new Cookie(REFRESH_TOKEN, refreshToken.getToken()))
                        .content(objectMapper.writeValueAsString(joinEnrollRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );



        // then
        result
                .andDo(print())
                .andDo(document("api-join-enroll",
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
                        requestFields(
                                fieldWithPath("joinInfoRequestList.[].localDate").type(JsonFieldType.STRING).description("일정 제목"),
                                fieldWithPath("joinInfoRequestList.[].startHour").type(JsonFieldType.NUMBER).description("시작 시간"),
                                fieldWithPath("joinInfoRequestList.[].endHour").type(JsonFieldType.NUMBER).description("끝 시간")
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
