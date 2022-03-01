package backend.restdocs;

import backend.api.service.JoinService;
import backend.api.service.PlanService;
import backend.api.service.UserService;
import backend.config.properties.AppProperties;
import backend.token.AuthTokenProvider;
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
// @WebMvcTest(controllers = {
//         AuthController.class,
//         JoinController.class,
//         PlanController.class,
//         UserController.class
// })
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public abstract class ApiDocumentationTest {

    protected final static String REFRESH_TOKEN = "refresh_token";
    protected final static String AUTHORIZATION_HEADER = "authorization";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JoinService joinService;

    @MockBean
    protected PlanService planService;

    @MockBean
    protected UserService userService;

    @Autowired
    protected AuthTokenProvider tokenProvider;

    @Autowired
    protected AppProperties appProperties;

}
