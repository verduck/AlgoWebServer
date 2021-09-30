package com.algo.algoweb.dto.Dataset;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Dataset {
    @NonNull
    @XmlAttribute(name = "id")
    private String id;

    @XmlElementWrapper(name = "ColumnInfo")
    @XmlElement(name = "Column")
    private List<Column> columns = new ArrayList<>();

    @XmlElementWrapper(name = "Rows")
    @XmlElement(name ="Row")
    private List<Row> rows = new ArrayList<>();
}
