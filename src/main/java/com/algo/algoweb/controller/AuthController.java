package com.algo.algoweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public @ResponseBody HashMap<String, Object> authenticate(@RequestBody HashMap<String, Object> body) {
    HashMap<String, Object> response = new HashMap<>();

    return response;
  }

  private HashMap<String, Object> authenticateJJ(String username, String password) {
    HashMap<String, Object> response = new HashMap<>();

    Root root = new Root();
    Parameters parameters = new Parameters();
    parameters.parameters.add(new Parameter("fsp_action", "JJLogin"));
    parameters.parameters.add(new Parameter("fsp_cmd", "login"));
    parameters.parameters.add(new Parameter("GV_USER_ID", "undefined"));
    parameters.parameters.add(new Parameter("GV_IP_ADDRESS", "undefined"));
    parameters.parameters.add(new Parameter("fsp_logId", "all"));
    parameters.parameters.add(new Parameter("MAX_WRONG_COUNT", "5"));
    root.parameters = parameters;

    Dataset dataset = new Dataset("ds_cond");
    ColumnInfo columnInfo = new ColumnInfo();
    columnInfo.columns.add(new Column("SYSTEM_CODE", "STRING", "256"));
    columnInfo.columns.add(new Column("MEM_ID", "STRING", "10"));
    columnInfo.columns.add(new Column("MEM_PW", "STRING", "15"));
    columnInfo.columns.add(new Column("MEM_IP", "STRING", "20"));
    columnInfo.columns.add(new Column("ROLE_GUBUN1", "STRING", "256"));
    columnInfo.columns.add(new Column("ROLE_GUBUN2", "STRING", "256"));
    dataset.columnInfo = columnInfo;
    Rows rows = new Rows();
    Row row = new Row();
    row.cols.add(new Col("SYSTEM_CODE", "INSTAR_WEB"));
    row.cols.add(new Col("MEM_ID", username));
    row.cols.add(new Col("MEM_PW", password));
    rows.rows.add(row);
    dataset.rows = rows;
    root.datasets.add(dataset);

    dataset = new Dataset("fsp_sd_cmd");
    columnInfo = new ColumnInfo();
    columnInfo.columns.add(new Column("TX_NAME", "STRING", "100"));
    columnInfo.columns.add(new Column("TYPE", "STRING", "10"));
    columnInfo.columns.add(new Column("SQL_ID", "STRING", "200"));
    columnInfo.columns.add(new Column("KEY_SQL_ID", "STRING", "200"));
    columnInfo.columns.add(new Column("KEY_INCREMENT", "INT", "10"));
    columnInfo.columns.add(new Column("CALLBACK_SQL_ID", "STRING", "200"));
    columnInfo.columns.add(new Column("INSERT_SQL_ID", "STRING", "200"));
    columnInfo.columns.add(new Column("UPDATE_SQL_ID", "STRING", "200"));
    columnInfo.columns.add(new Column("DELETE_SQL_ID", "STRING", "200"));
    columnInfo.columns.add(new Column("SAVE_FLAG_COLUMN", "STRING", "200"));
    columnInfo.columns.add(new Column("USE_INPUT", "STRING", "1"));
    columnInfo.columns.add(new Column("USE_ORDER", "STRING", "1"));
    columnInfo.columns.add(new Column("KEY_ZERO_LEN", "INT", "10"));
    columnInfo.columns.add(new Column("BIZ_NAME", "STRING", "100"));
    columnInfo.columns.add(new Column("PAGE_NO", "INT", "10"));
    columnInfo.columns.add(new Column("PAGE_SIZE", "INT", "10"));
    columnInfo.columns.add(new Column("READ_ALL", "STRING", "1"));
    columnInfo.columns.add(new Column("EXEC_TYPE", "STRING", "2"));
    columnInfo.columns.add(new Column("EXEC", "STRING", "1"));
    columnInfo.columns.add(new Column("FAIL", "STRING", "1"));
    columnInfo.columns.add(new Column("FAIL_MSG", "STRING", "200"));
    columnInfo.columns.add(new Column("EXEC_CNT", "INT", "1"));
    columnInfo.columns.add(new Column("MSG", "STRING", "200"));
    dataset.columnInfo = columnInfo;
    rows = new Rows();
    row = new Row();
    rows.rows.add(row);
    dataset.rows = rows;
    root.datasets.add(dataset);

    dataset = new Dataset("gds_user");
    columnInfo = new ColumnInfo();
    dataset.columnInfo = columnInfo;
    rows = new Rows();
    dataset.rows = rows;
    root.datasets.add(dataset);

    try {
      JAXBContext context = JAXBContext.newInstance(Root.class);
      Marshaller ms = context.createMarshaller();
      ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      ms.marshal(root, System.out);

    } catch (JAXBException e) {
      System.out.println(e);
    }

    response.put("token", "Hello World");
    return response;
  }
}

@XmlRootElement(name = "Root", namespace = "http://www.nexacroplatform.com/platform/dataset")
class Root {
  @XmlElement(name = "Parameters", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public Parameters parameters;

  @XmlElement(name = "Dataset", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public List<Dataset> datasets = new ArrayList<>();
}

class Parameters {
  @XmlElement(name = "Parameter", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public List<Parameter> parameters = new ArrayList<>();
}

class Parameter {
  @XmlAttribute(name = "id")
  public String id;

  @XmlValue
  public String value;

  public Parameter(String id, String value) {
    this.id = id;
    this.value = value;
  }
}

class Dataset {
  @XmlAttribute(name = "id")
  public String id;

  @XmlElement(name = "ColumnInfo", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public ColumnInfo columnInfo;

  @XmlElement(name = "Rows", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public Rows rows;

  public Dataset(String id) {
    this.id = id;
  }
}

class ColumnInfo {
  @XmlElement(name = "Column", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public List<Column> columns = new ArrayList<>();
}

class Column {
  @XmlAttribute(name = "id")
  public String id;

  @XmlAttribute(name = "type")
  public String type;

  @XmlAttribute(name = "size")
  public String size;

  public Column(String id, String type, String size) {
    this.id = id;
    this.type = type;
    this.size = size;
  }
}

class Rows {
  @XmlElement(name = "Row", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public List<Row> rows = new ArrayList<>();
}

class Row {
  @XmlElement(name = "Col", namespace = "http://www.nexacroplatform.com/platform/dataset")
  public List<Col> cols = new ArrayList<>();
}

class Col {
  @XmlAttribute(name = "id")
  public String id;

  @XmlValue
  public String value;

  public Col(String id, String value) {
    this.id = id;
    this.value = value;
  }
}