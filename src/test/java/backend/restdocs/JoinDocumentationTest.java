package backend.restdocs;

import backend.api.controller.dto.request.JoinEnrollRequest;
import backend.api.controller.dto.request.JoinInfoRequest;
import backend.api.service.dto.JoinDto;
import backend.api.service.dto.JoinInfoDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JoinDocumentationTest extends ApiDocumentationTest {

    @Test
    public void enroll() throws Exception {
        // given
        String username = "abc123";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        JoinEnrollRequest request = new JoinEnrollRequest();
        request.setJoinInfoRequestList(Arrays.asList(
                new JoinInfoRequest(LocalDate.of(2017, 03, 02), 17, 18),
                new JoinInfoRequest(LocalDate.of(2017, 03, 03), 17, 18),
                new JoinInfoRequest(LocalDate.of(2017, 03, 04), 17, 18)
        ));

        // when
        ResultActions result = mockMvc.perform(
                post("/api/join/{planId}", 1L)
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-join-enroll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("planId").description("일정 아이디")
                        ),
                        requestHeaders(
                                headerWithName(HEADER_ACCESS_TOKEN).description("엑세스 토큰"),
                                headerWithName(HEADER_REFRESH_TOKEN).description("리프레시 토큰")
                        ),
                        requestFields(beneathPath("joinInfoRequestList.[]").withSubsectionId("list"),
                                fieldWithPath("localDate").type(JsonFieldType.STRING).description("일정 제목"),
                                fieldWithPath("startHour").type(JsonFieldType.NUMBER).description("시작 시간"),
                                fieldWithPath("endHour").type(JsonFieldType.NUMBER).description("끝 시간")
                        ),
                        responseFields(
                                fieldWithPath("header.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("body.create").type(JsonFieldType.STRING).description("일정 생성 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        // given
        String username = "abc123";
        String nickName = "baby";
        String email = "aaa@naver.com";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        UserDto userDto = UserDto.builder()
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
                .host(userDto)
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
                .userDto(userDto)
                .planDto(planDto)
                .build();

        ArrayList<JoinInfoDto> joinInfoDtoList = new ArrayList<>(Arrays.asList(
                JoinInfoDto.builder()
                        .id(1L)
                        .joinDto(joinDto)
                        .localDate(LocalDate.of(2017, 03, 02))
                        .startHour(16)
                        .endHour(18)
                        .build(),
                JoinInfoDto.builder()
                        .id(2L)
                        .joinDto(joinDto)
                        .localDate(LocalDate.of(2017, 03, 03))
                        .startHour(16)
                        .endHour(18)
                        .build(),
                JoinInfoDto.builder()
                        .id(3L)
                        .joinDto(joinDto)
                        .localDate(LocalDate.of(2017, 03, 04))
                        .startHour(16)
                        .endHour(18)
                        .build()
        ));

        given(joinService.findAll(any(), any()))
                .willReturn(joinInfoDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/join/{planId}", 1L)
                        .header(HEADER_ACCESS_TOKEN, jwtToken.getAccessToken())
                        .header(HEADER_REFRESH_TOKEN, jwtToken.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-join-findAll",
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
                                fieldWithPath("body.list.[].localDate").type(JsonFieldType.STRING).description("참여 정보 날짜"),
                                fieldWithPath("body.list.[].startHour").type(JsonFieldType.NUMBER).description("참여 정보 시작 시간"),
                                fieldWithPath("body.list.[].endHour").type(JsonFieldType.NUMBER).description("참여 정보 끝 시간")
                        )
                ))
                .andExpect(status().isOk());
    }

}