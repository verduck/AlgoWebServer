package com.algo.algoweb.dto.Dataset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {
    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "size")
    private String size;
}