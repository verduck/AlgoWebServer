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

    private final static String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE5MTYyMzkwMjJ9.LYnkjSJXgSuPtmZI4eoMW4KsUC8XfpoWn9K_P8SnCmND3ak--WAWK2yjt1_VB4XpFXGByJ16gy-amGAeD1wvThNpDZ1O4NHGk1hOrY8v5hbqhHWQU5A1YVW-WeJdk1fzM_SgQNDR20gJMWqfStuUEzmbYhxhSO749Xg5tX-L8eXDA1F3MAhroTXz7PgdM-syZw_AtgQZiXQlfIwZO5zvbvDoSKztoKDcUCCXwITTmSucV6RXKAnaNahVn9HHiMjxSum1DmneVolJCGzDJtpx5_z4-UkoAf8EJ_Gm4BqY3fbUtIi4RpH1ttvJ42_xXwmV-yBX3CZ0Wwpcmn06nBNbDriK50MJxcSIwjgVgBceRHqkR1Ep7Uc4_TSe_bQFP0_QcGS5TwIjp6Dr9fl85BIj5aRSX3uMJsXuz-1oZheUAhjWcfHAEdWSKJrMXGQttXctVA4m7OSEmOA1Tqv3ZdWu9TYvDwKCI-A7yf_6TvFe5T-PsnDSyVoUOAB_omVuk5Rb";

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
        response.setToken(TOKEN);
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
