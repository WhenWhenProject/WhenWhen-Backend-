package backend.restdocs;

import backend.restdocs.utils.ApiDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static backend.restdocs.utils.ApiDocumentUtils.getDocumentRequest;
import static backend.restdocs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestDocumentationTest extends ApiDocumentationTest {

    @Test
    public void test() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(
                get("/api/test/hello")
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andDo(document("api-test-hello",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("테스트 데이터")
                        )
                ))
                .andExpect(status().isOk());
    }

}