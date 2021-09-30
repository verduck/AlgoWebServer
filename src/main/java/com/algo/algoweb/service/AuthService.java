package com.algo.algoweb.service;

import java.nio.charset.Charset;

import javax.print.attribute.standard.Media;

import com.algo.algoweb.dto.AuthRequest;
import com.algo.algoweb.dto.Dataset.Col;
import com.algo.algoweb.dto.Dataset.Column;
import com.algo.algoweb.dto.Dataset.Dataset;
import com.algo.algoweb.dto.Dataset.Parameter;
import com.algo.algoweb.dto.Dataset.Row;
import com.algo.algoweb.dto.Dataset.XMain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
  private final String url = "https://instar.jj.ac.kr/XMain";

  public XMain loginJJInstar(AuthRequest authRequest) {
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
    row.getCols().add(new Col("MEM_ID", authRequest.getUsername()));
    row.getCols().add(new Col("MEM_PW", authRequest.getPassword()));
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

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(3000);
    factory.setReadTimeout(3000);
    RestTemplate restTemplate = new RestTemplate(factory);
    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
    headers.setContentType(new MediaType("application", "xml", Charset.forName("UTF-8")));

    HttpEntity<XMain> request = new HttpEntity<XMain>(requestBody, headers);
    result = restTemplate.postForObject(url, request, XMain.class);
    return result;
  }
}
