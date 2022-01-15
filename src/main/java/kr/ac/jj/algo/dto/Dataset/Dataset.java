package kr.ac.jj.algo.dto.Dataset;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dataset {
    @XmlAttribute(name = "id")
    private String id;

    @XmlElementWrapper(name = "ColumnInfo")
    @XmlElement(name = "Column")
    private List<Column> columns = new ArrayList<>();

    @XmlElementWrapper(name = "Rows")
    @XmlElement(name ="Row")
    private List<Row> rows = new ArrayList<>();

    public Dataset() {}

    public Dataset(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
