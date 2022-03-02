package backend.restdocs;

import backend.api.controller.dto.request.PlanEnrollRequest;
import backend.api.service.dto.PlanDto;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.restdocs.utils.ApiDocumentationTest;
import backend.token.JwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static backend.util.HeaderConstant.HEADER_ACCESS_TOKEN;
import static backend.util.HeaderConstant.HEADER_REFRESH_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlanDocumentationTest extends ApiDocumentationTest {

    @Test
    void findAllCreatedByMe() throws Exception {
        // given
        String username = "abc123";
        String nickName = "baby";
        String email = "aaa@naver.com";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        UserDto hostDto = UserDto.builder()
                .id(1L)
                .username(username)
                .nickName(nickName)
                .email(email)
                .profileImageUrl(null)
                .providerType(null)
                .roleType(RoleType.USER)
                .build();

        ArrayList<PlanDto> planDtoList = new ArrayList<>(Arrays.asList(
                PlanDto.builder()
                        .id(1L)
                        .host(hostDto)
                        .title("멋사 저녁 회식")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("왕십리")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(2L)
                        .host(hostDto)
                        .title("디프만 저녁 회식")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("왕십리")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(3L)
                        .host(hostDto)
                        .title("알로하 저녁 회식")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("왕십리")
                        .startHour(16)
                        .build()
        ));

        given(planService.findAllCreatedByMe(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/host/all")
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-findAllCreatedByMe",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
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


    @Test
    void findAllParticipatedIn() throws Exception {
        // given
        String username = "abc123";
        String nickName = "baby";
        String email = "aaa@naver.com";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());


        UserDto hostDto = UserDto.builder()
                .id(1L)
                .username(username)
                .nickName(nickName)
                .email(email)
                .profileImageUrl(null)
                .providerType(null)
                .roleType(RoleType.USER)
                .build();

        ArrayList<PlanDto> planDtoList = new ArrayList<>(Arrays.asList(
                PlanDto.builder()
                        .id(1L)
                        .host(hostDto)
                        .title("멋사 저녁 회식")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("왕십리")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(2L)
                        .host(hostDto)
                        .title("디프만 저녁 회식")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("왕십리")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(3L)
                        .host(hostDto)
                        .title("알로하 저녁 회식")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("왕십리")
                        .startHour(16)
                        .build()
        ));

        given(planService.findAllParticipatedIn(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/all")
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-findAllParticipatedIn",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
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

    @Test
    public void create() throws Exception {
        // given
        String username = "abc123";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

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
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
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

    @Test
    public void delete_() throws Exception {
        // given
        String username = "abc123";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/plan/{planId}", 1L)
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("planId").description("일정 아이디")
                        ),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.delete").type(JsonFieldType.STRING).description("삭제 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

}
