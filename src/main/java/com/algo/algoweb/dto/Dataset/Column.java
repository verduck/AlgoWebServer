package com.algo.algoweb.dto.Dataset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Column {
    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "size")
    private String size;

    public Column() {}

    public Column(String id, String type, String size) {
        this.id = id;
        this.type = type;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}