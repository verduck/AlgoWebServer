package kr.ac.jj.algo.service;

import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.AuthDTO;
import kr.ac.jj.algo.dto.Dataset.*;
import kr.ac.jj.algo.dto.UserDTO;
import kr.ac.jj.algo.exception.ErrorCode;
import kr.ac.jj.algo.exception.ApiException;
import kr.ac.jj.algo.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;

@Service
public class AuthService {
    private final String url = "https://instar.jj.ac.kr/XMain";
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthService(final AuthenticationManager authenticationManager, final PasswordEncoder passwordEncoder, ModelMapper modelMapper, final UserService userService, final JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    private RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    public AuthDTO authenticate(AuthDTO.Request request) {
        AuthDTO response = new AuthDTO();
        RestTemplate restTemplate = restTemplate();
        XMain xMain = loginJJInstar(restTemplate, request.getUsername(), request.getPassword());
        Dataset dataset = xMain.findDatasetById("ds_info");
        HashMap<String, String> datasetMap = new HashMap<>();
        if (dataset == null) {
            throw new ApiException(ErrorCode.AUTH_BAD_REQUEST);
        }
        Row row = dataset.getRows().get(0);
        for (Col c : row.getCols()) {
            datasetMap.put(c.getId(), c.getValue());
        }

        User user;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getUsername())
            );
            user = userService.loadUserByUsername(request.getUsername());
        } catch (BadCredentialsException e) {
            user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getUsername()));
            user.setName(datasetMap.get("MEM_NM"));
            user.setAuthority(Authority.ROLE_APPLICANT);
            user = userService.createUser(user);
        }

        if (user.getUpdatedAt() == null || user.getUpdatedAt().plusWeeks(1).isBefore(LocalDateTime.now())) {
            xMain = defaultJJInstar(restTemplate, request.getUsername());
            dataset = xMain.findDatasetById("ds_list");
            datasetMap.clear();
            if (dataset != null) {
                row = dataset.getRows().get(0);
                for (Col c : row.getCols()) {
                    datasetMap.put(c.getId(), c.getValue());
                }
                if (user.getUpdatedAt() == null) {
                    user.setBirth(LocalDate.parse(datasetMap.get("BIRTH"), DateTimeFormatter.ofPattern("yyyyMMdd")));
                    user.setGender(datasetMap.get("GENDER").equals("남") ? 'M' : 'F');
                }
                user.setGrade(Byte.parseByte(datasetMap.get("HAKG_YEAR_EHJ")));
                user.setStatus(datasetMap.get("HAKJ_BD_CODE"));
                userService.updateUser(user);
            }
        }

        response.setUser(modelMapper.map(user, UserDTO.class));
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public AuthDTO reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AuthDTO response = new AuthDTO();
        Cookie[] cookies = httpServletRequest.getCookies();
        String refresh = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("refresh")) {
                    refresh = c.getValue();
                    break;
                }
            }
        }
        if (refresh == null) {
            throw new ApiException(ErrorCode.NO_TOKEN);
        } else if (jwtService.isExpired(refresh)) {
            throw new ApiException(ErrorCode.EXPIRED_TOKEN);
        } else {
            int userId = jwtService.getUserId(refresh);
            User user = userService.loadUserById(userId);
            response.setUser(modelMapper.map(user, UserDTO.class));
            response.setToken(jwtService.generateToken(user));
        }
        return response;
    }

    public XMain loginJJInstar(final RestTemplate restTemplate, String username, String password) {
        XMain result;

        XMain requestBody = new XMain();
        requestBody.getParameters().add(new Parameter("fsp_action", "JJLogin"));
        requestBody.getParameters().add(new Parameter("fsp_cmd", "login"));
        requestBody.getParameters().add(new Parameter("GV_USER_ID", "undefined"));
        requestBody.getParameters().add(new Parameter("GV_IP_ADDRESS", "undefined"));
        requestBody.getParameters().add(new Parameter("fsp_logId", "all"));
        requestBody.getParameters().add(new Parameter("MAX_WRONG_COUNT", "5"));

        Dataset dataset = new Dataset("ds_cond");
        dataset.getColumns().add(new Column("SYSTEM_CODE", "STRING", "256"));
        dataset.getColumns().add(new Column("MEM_ID", "STRING", "10"));
        dataset.getColumns().add(new Column("MEM_PW", "STRING", "15"));
        dataset.getColumns().add(new Column("MEM_IP", "STRING", "20"));
        dataset.getColumns().add(new Column("ROLE_GUBUN1", "STRING", "256"));
        dataset.getColumns().add(new Column("ROLE_GUBUN2", "STRING", "256"));
        Row row = new Row();
        row.getCols().add(new Col("SYSTEM_CODE", "INSTAR_WEB"));
        row.getCols().add(new Col("MEM_ID", username));
        row.getCols().add(new Col("MEM_PW", password));
        dataset.getRows().add(row);
        requestBody.getDatasets().add(dataset);

        dataset = new Dataset("fsp_sd_cmd");
        dataset.getColumns().add(new Column("TX_NAME", "STRING", "100"));
        dataset.getColumns().add(new Column("TYPE", "STRING", "10"));
        dataset.getColumns().add(new Column("SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("KEY_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("KEY_INCREMENT", "INT", "10"));
        dataset.getColumns().add(new Column("CALLBACK_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("INSERT_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("UPDATE_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("DELETE_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("SAVE_FLAG_COLUMN", "STRING", "200"));
        dataset.getColumns().add(new Column("USE_INPUT", "STRING", "1"));
        dataset.getColumns().add(new Column("USE_ORDER", "STRING", "1"));
        dataset.getColumns().add(new Column("KEY_ZERO_LEN", "INT", "10"));
        dataset.getColumns().add(new Column("BIZ_NAME", "STRING", "100"));
        dataset.getColumns().add(new Column("PAGE_NO", "INT", "10"));
        dataset.getColumns().add(new Column("PAGE_SIZE", "INT", "10"));
        dataset.getColumns().add(new Column("READ_ALL", "STRING", "1"));
        dataset.getColumns().add(new Column("EXEC_TYPE", "STRING", "2"));
        dataset.getColumns().add(new Column("EXEC", "STRING", "1"));
        dataset.getColumns().add(new Column("FAIL", "STRING", "1"));
        dataset.getColumns().add(new Column("FAIL_MSG", "STRING", "200"));
        dataset.getColumns().add(new Column("EXEC_CNT", "INT", "1"));
        dataset.getColumns().add(new Column("MSG", "STRING", "200"));
        row = new Row();
        dataset.getRows().add(row);
        requestBody.getDatasets().add(dataset);

        dataset = new Dataset("gds_user");
        requestBody.getDatasets().add(dataset);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        headers.setContentType(new MediaType("application", "xml", StandardCharsets.UTF_8));
        HttpEntity<XMain> request = new HttpEntity<>(requestBody, headers);
        result = restTemplate.postForObject(url, request, XMain.class);
        return result;
    }

    private XMain defaultJJInstar(final RestTemplate restTemplate, String studentId) {
        XMain result;

        XMain requestBody = new XMain();
        requestBody.getParameters().add(new Parameter("fsp_action", "xDefaultAction"));
        requestBody.getParameters().add(new Parameter("fsp_cmd", "execute"));
        requestBody.getParameters().add(new Parameter("GV_USER_ID", "undefined"));
        requestBody.getParameters().add(new Parameter("GV_IP_ADDRESS", "undefined"));
        requestBody.getParameters().add(new Parameter("fsp_logId", "all"));
        requestBody.getParameters().add(new Parameter("DATE_YY", "sung"));
        requestBody.getParameters().add(new Parameter("DATE_HAKGI", "p"));
        requestBody.getParameters().add(new Parameter("HAKBUN", studentId));

        Dataset dataset = new Dataset("fsp_ds_cmd");
        dataset.getColumns().add(new Column("TX_NAME", "string", "100"));
        dataset.getColumns().add(new Column("TYPE", "string", "10"));
        dataset.getColumns().add(new Column("SQL_ID", "string", "200"));
        dataset.getColumns().add(new Column("KEY_SQL_ID", "string", "200"));
        dataset.getColumns().add(new Column("KEY_INCREMENT", "INT", "10"));
        dataset.getColumns().add(new Column("CALLBACK_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("INSERT_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("UPDATE_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("DELETE_SQL_ID", "STRING", "200"));
        dataset.getColumns().add(new Column("SAVE_FLAG_COLUMN", "STRING", "200"));
        dataset.getColumns().add(new Column("USE_INPUT", "STRING", "1"));
        dataset.getColumns().add(new Column("USE_ORDER", "STRING", "1"));
        dataset.getColumns().add(new Column("KEY_ZERO_LEN", "INT", "10"));
        dataset.getColumns().add(new Column("BIZ_NAME", "STRING", "100"));
        dataset.getColumns().add(new Column("PAGE_NO", "INT", "10"));
        dataset.getColumns().add(new Column("PAGE_SIZE", "INT", "10"));
        dataset.getColumns().add(new Column("READ_ALL", "STRING", "1"));
        dataset.getColumns().add(new Column("EXEC_TYPE", "STRING", "2"));
        dataset.getColumns().add(new Column("EXEC", "STRING", "1"));
        dataset.getColumns().add(new Column("FAIL", "STRING", "1"));
        dataset.getColumns().add(new Column("FAIL_MSG", "STRING", "200"));
        dataset.getColumns().add(new Column("EXEC_CNT", "INT", "1"));
        dataset.getColumns().add(new Column("MSG", "STRING", "200"));
        Row row = new Row();
        row.getCols().add(new Col("TX_NAME"));
        row.getCols().add(new Col("TYPE", "N"));
        row.getCols().add(new Col("SQL_ID", "com_member:COM_INFO_JJUP_R01"));
        row.getCols().add(new Col("KEY_SQL_ID"));
        row.getCols().add(new Col("KEY_INCREMENT", "0"));
        row.getCols().add(new Col("CALLBACK_SQL_ID"));
        row.getCols().add(new Col("INSERT_SQL_ID"));
        row.getCols().add(new Col("UPDATE_SQL_ID"));
        row.getCols().add(new Col("DELETE_SQL_ID"));
        row.getCols().add(new Col("SAVE_FLAG_COLUMN"));
        row.getCols().add(new Col("USE_INPUT"));
        row.getCols().add(new Col("USE_ORDER"));
        row.getCols().add(new Col("KEY_ZERO_LEN", "0"));
        row.getCols().add(new Col("BIZ_NAME"));
        row.getCols().add(new Col("PAGE_NO"));
        row.getCols().add(new Col("PAGE_SIZE"));
        row.getCols().add(new Col("READ_ALL"));
        row.getCols().add(new Col("EXEC_TYPE", "B"));
        row.getCols().add(new Col("EXEC"));
        row.getCols().add(new Col("FAIL"));
        row.getCols().add(new Col("FAIL_MSG"));
        row.getCols().add(new Col("EXEC_CNT", "0"));
        row.getCols().add(new Col("MSG"));
        dataset.getRows().add(row);
        requestBody.getDatasets().add(dataset);

        dataset = new Dataset("gds_user");
        requestBody.getDatasets().add(dataset);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        headers.setContentType(new MediaType("application", "xml", StandardCharsets.UTF_8));
        headers.setAccept(Collections.singletonList(new MediaType("application", "xml", StandardCharsets.UTF_8)));
        HttpEntity<XMain> request = new HttpEntity<>(requestBody, headers);
        result = restTemplate.postForObject(url, request, XMain.class);

        return result;
    }
}
