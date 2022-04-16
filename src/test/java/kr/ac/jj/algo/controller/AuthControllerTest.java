package kr.ac.jj.algo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.dto.AuthDTO;
import kr.ac.jj.algo.dto.UserDTO;
import kr.ac.jj.algo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.config.BeanIds;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletException;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class AuthControllerTest {
    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) throws ServletException {

        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        delegateProxyFilter.init(new MockFilterConfig(webApplicationContext.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));

        document = document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(document)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(delegateProxyFilter)
                .build();
    }

    @Test
    public void authenticate() throws Exception {
        UserDTO user = new UserDTO();
        user.setUsername("203268001");
        user.setName("아무개");
        user.setBirth(LocalDate.of(2012, 3, 22));
        user.setGender('M');
        user.setGrade((byte) 1);
        user.setStatus("입학");
        user.setAuthority(Authority.ROLE_USER);
        AuthDTO.Response response = new AuthDTO.Response();
        response.setMessage("로그인에 성공하였습니다.");
        response.setUser(user);
        response.setToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwIiwiaWF0IjoxNjUwMDk5MDIyLCJleHAiOjI1NTAwOTkwMjJ9.HXL8wPh2Yj41AB_yF-sK3a96XXPzAS3QaECsMDfVoQdUuUt0J1vcaR_sQAUwSLwSngDO_Z2qOXZ5vxMzGJxoJJLO1-QDoHCFUR2pWV8l0ufrmkNACNB2YZ8szo0pNs5ZNf5LoQU6O9zf6jU1zOucEng0aztQMCAwDdAxJmztsKnLN4lQYxjgJrP4n1kiajA_9yzIG6Z0SrMNkFmf_ZOK0gMlesCI8If_Z89uU2SVb5o4na-qXL4PaW4HQ37E6cAw_ez76-62dZJUyJspi1qozdNHU6MU-K1SQKEmmTCZUz2PFPNyvzfio-so5WkeQTAKMIy-kf_bheCDpEwHJfe7hvN0W03-SItrS2eYovz1aqW7c5w8X7hH7azsMrY_-Z84fsBGh9eIA4VXanOeKEL3Mhe3LjkRgydcWPR404J7oVpY_I-dZSyn__pZSCl3qe0isV7X0qEiVyB4sHevj4tMbUyT9GwxYm6_7c0bgzraKfISb5994Z-a-XQaZcPLR5jQ");
        given(authService.authenticate(any(AuthDTO.Request.class))).willReturn(response);

        AuthDTO.Request request = new AuthDTO.Request();
        request.setUsername("203268001");
        request.setPassword("1234qwer!");
        ResultActions result = this.mockMvc.perform(
                post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        result.andExpect(status().isOk())
                .andDo(document.document(
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("username").type(JsonFieldType.STRING).description("학번"),
                                PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("결과 설명"),
                                PayloadDocumentation.fieldWithPath("user").type(JsonFieldType.OBJECT).description("사용자 정보"),
                                PayloadDocumentation.fieldWithPath("user.username").type(JsonFieldType.STRING).description("학번"),
                                PayloadDocumentation.fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                                PayloadDocumentation.fieldWithPath("user.birth").type(JsonFieldType.STRING).description("생년월일"),
                                PayloadDocumentation.fieldWithPath("user.gender").type(JsonFieldType.STRING).description("성별"),
                                PayloadDocumentation.fieldWithPath("user.grade").type(JsonFieldType.NUMBER).description("학년"),
                                PayloadDocumentation.fieldWithPath("user.status").type(JsonFieldType.STRING).description("학적상태"),
                                PayloadDocumentation.fieldWithPath("user.authority").type(JsonFieldType.STRING).description("권한"),
                                PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING).description("토큰")
                        )
                ));

    }
}
