package com.algo.algoweb.dto.Dataset;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMain {
    @XmlElementWrapper(name = "Parameters")
    @XmlElement(name = "Parameter")
    private List<Parameter> parameters = new ArrayList<>();

    @XmlElement(name = "Dataset")
    private List<Dataset> datasets = new ArrayList<>();

    public Dataset findDatasetById(String id) {
        for (Dataset d : datasets) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }
}



