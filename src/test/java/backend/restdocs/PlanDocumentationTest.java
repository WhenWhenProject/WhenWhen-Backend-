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
                        .title("?????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(2L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(3L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build()
        ));

        given(planService.findAllCreatedByMe(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/host/all")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-findAllCreatedByMe",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.[].hostNickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                fieldWithPath("data.[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.[].expectedMemberCnt").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ???"),
                                fieldWithPath("data.[].fixed").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                fieldWithPath("data.[].linkUrl").type(JsonFieldType.STRING).description("?????? URL ??????"),
                                fieldWithPath("data.[].location").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startHour").type(JsonFieldType.NUMBER).description("?????? ?????? ??????")
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
                        .title("?????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(2L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(3L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build()
        ));

        given(planService.findAllParticipatedIn(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/all")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-findAllParticipatedIn",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.[].hostNickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                fieldWithPath("data.[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.[].expectedMemberCnt").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ???"),
                                fieldWithPath("data.[].fixed").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                fieldWithPath("data.[].linkUrl").type(JsonFieldType.STRING).description("?????? URL ??????"),
                                fieldWithPath("data.[].location").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startHour").type(JsonFieldType.NUMBER).description("?????? ?????? ??????")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void findAllFixed() throws Exception {
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
                        .title("?????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(true)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(2L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(true)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(3L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(true)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build()
        ));

        given(planService.findAllFixed(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/fixed/all")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-findAllFixed",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.[].hostNickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                fieldWithPath("data.[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.[].expectedMemberCnt").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ???"),
                                fieldWithPath("data.[].fixed").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                fieldWithPath("data.[].linkUrl").type(JsonFieldType.STRING).description("?????? URL ??????"),
                                fieldWithPath("data.[].location").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startHour").type(JsonFieldType.NUMBER).description("?????? ?????? ??????")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void findAllUnfixed() throws Exception {
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
                        .title("?????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(2L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build(),
                PlanDto.builder()
                        .id(3L)
                        .host(hostDto)
                        .title("????????? ?????? ??????")
                        .startDate(LocalDate.of(2017, 03, 02))
                        .endDate(LocalDate.of(2017, 03, 06))
                        .expectedMemberCnt(5L)
                        .fixed(false)
                        .linkUrl(UUID.randomUUID().toString())
                        .location("?????????")
                        .startHour(16)
                        .build()
        ));

        given(planService.findAllUnfixed(any()))
                .willReturn(planDtoList);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/plan/unfixed/all")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-findAllUnfixed",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data.[].hostNickName").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                fieldWithPath("data.[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ??? ??????"),
                                fieldWithPath("data.[].expectedMemberCnt").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ???"),
                                fieldWithPath("data.[].fixed").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                fieldWithPath("data.[].linkUrl").type(JsonFieldType.STRING).description("?????? URL ??????"),
                                fieldWithPath("data.[].location").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].startHour").type(JsonFieldType.NUMBER).description("?????? ?????? ??????")
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
        request.setTitle("?????? ??????");
        request.setStartDate(LocalDate.of(2017, 03, 02));
        request.setEndDate(LocalDate.of(2017, 03, 06));
        request.setExpectedMemberCnt(5L);
        request.setLocation("?????????");
        request.setStartHour(16);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/plan/")
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
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
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("?????? ?????? ??? ??????"),
                                fieldWithPath("expectedMemberCnt").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ???"),
                                fieldWithPath("location").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("startHour").type(JsonFieldType.NUMBER).description("?????? ?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("?????? ?????? ??????")
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
                        .header(HEADER_AUTHORIZATION, HEADER_ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-plan-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("planId").description("?????? ?????????")
                        ),
                        requestHeaders(
                                headerWithName(HEADER_AUTHORIZATION).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                        )
                ))
                .andExpect(status().isOk());
    }

}
