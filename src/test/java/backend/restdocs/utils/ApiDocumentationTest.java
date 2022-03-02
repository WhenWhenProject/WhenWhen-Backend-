package backend.restdocs.utils;

import backend.api.service.JoinService;
import backend.api.service.PlanService;
import backend.api.service.UserService;
import backend.config.properties.AppProperties;
import backend.token.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public abstract class ApiDocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtTokenProvider tokenProvider;

    @Autowired
    protected AppProperties appProperties;

    @MockBean
    protected JoinService joinService;

    @MockBean
    protected PlanService planService;

    @MockBean
    protected UserService userService;

}
