package kr.ac.jj.algo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ApplicationDTO;
import kr.ac.jj.algo.dto.AuthDTO;
import kr.ac.jj.algo.dto.UserDTO;
import kr.ac.jj.algo.service.ApplicationService;
import kr.ac.jj.algo.service.AuthService;
import kr.ac.jj.algo.service.UserService;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class ApplicationControllerTest {
    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private ApplicationService applicationService;

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
    public void getMyApplication() throws Exception {
        User user = new User();
        user.setId(0);
        user.setUsername("203268001");
        user.setName("아무개");
        user.setBirth(LocalDate.of(2012, 3, 22));
        user.setGender('M');
        user.setGrade((byte) 1);
        user.setStatus("입학");
        user.setAuthority(Authority.ROLE_APPLICANT);
        ApplicationDTO response = new ApplicationDTO();
        response.setUser(modelMapper.map(user, UserDTO.class));
        response.setIntroduction("안녕하세요. 저는 아무개입니다.");
        given(userService.loadUserById(any(Integer.class))).willReturn(user);
        given(applicationService.loadApplicationByUser(any(User.class))).willReturn(response);

        ResultActions result = this.mockMvc.perform(
                get("/api/v1/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
        );

        result.andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer auth credentials")
                        ),
                        responseFields(
                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("지원자"),
                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("학번"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("user.birth").type(JsonFieldType.STRING).description("생년월일"),
                                fieldWithPath("user.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("user.grade").type(JsonFieldType.NUMBER).description("학년"),
                                fieldWithPath("user.status").type(JsonFieldType.STRING).description("학적상태"),
                                fieldWithPath("user.authority").type(JsonFieldType.STRING).description("권한"),
                                fieldWithPath("introduction").type(JsonFieldType.STRING).description("소개글")
                        )
                ));

    }
}
