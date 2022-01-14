package com.algo.algoweb.dto.Dataset;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> dataset) {
        this.datasets = dataset;
    }
}



