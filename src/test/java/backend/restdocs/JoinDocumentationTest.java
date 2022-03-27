package backend.restdocs;

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
import java.util.List;
import java.util.UUID;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static backend.util.HeaderConstant.*;
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

public class JoinDocumentationTest extends ApiDocumentationTest {

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
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
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
                                headerWithName(HEADER_AUTHORIZATION).description("엑세스 토큰")
                        ),
                        responseFields(
                                fieldWithPath("data.[].localDate").type(JsonFieldType.STRING).description("참여 정보 날짜"),
                                fieldWithPath("data.[].startHour").type(JsonFieldType.NUMBER).description("참여 정보 시작 시간"),
                                fieldWithPath("data.[].endHour").type(JsonFieldType.NUMBER).description("참여 정보 끝 시간")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void enroll() throws Exception {
        // given
        String username = "abc123";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        List<JoinInfoRequest> request = Arrays.asList(
                new JoinInfoRequest(LocalDate.of(2017, 03, 02), 17, 18),
                new JoinInfoRequest(LocalDate.of(2017, 03, 03), 17, 18),
                new JoinInfoRequest(LocalDate.of(2017, 03, 04), 17, 18)
        );

        // when
        ResultActions result = mockMvc.perform(
                post("/api/join/{planId}", 1L)
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
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
                                headerWithName(HEADER_AUTHORIZATION).description("엑세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("[].localDate").type(JsonFieldType.STRING).description("일정 제목"),
                                fieldWithPath("[].startHour").type(JsonFieldType.NUMBER).description("시작 시간"),
                                fieldWithPath("[].endHour").type(JsonFieldType.NUMBER).description("끝 시간")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("일정 투표 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        // given
        String username = "abc123";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        List<JoinInfoRequest> request = Arrays.asList(
                new JoinInfoRequest(LocalDate.of(2017, 03, 02), 17, 18),
                new JoinInfoRequest(LocalDate.of(2017, 03, 03), 17, 18),
                new JoinInfoRequest(LocalDate.of(2017, 03, 04), 17, 18)
        );

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/join/{planId}", 1L)
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-join-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("planId").description("일정 아이디")
                        ),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("엑세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("[].localDate").type(JsonFieldType.STRING).description("일정 제목"),
                                fieldWithPath("[].startHour").type(JsonFieldType.NUMBER).description("시작 시간"),
                                fieldWithPath("[].endHour").type(JsonFieldType.NUMBER).description("끝 시간")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("일정 투표 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void _delete() throws Exception {
        // given
        String username = "abc123";

        JwtToken jwtToken = tokenProvider.createJwtToken(username, RoleType.USER.getCode());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/join/{planId}", 1L)
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-join-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("planId").description("일정 아이디")
                        ),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("엑세스 토큰")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("삭제 성공 여부")
                        )
                ))
                .andExpect(status().isOk());
    }

}