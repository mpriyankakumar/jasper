package com.temeos.jasperreport.model;

import java.util.Arrays;
import java.util.HashMap;

public class ReportParam {

    private String mainReportName;
    private String[] subReportName;
    private HashMap<String, Object> parameters;

    public String getMainReportName() {
        return mainReportName;
    }

    public void setMainReportName(String mainReportName) {
        this.mainReportName = mainReportName;
    }

    public String[] getSubReportName() {
        return subReportName;
    }

    public void setSubReportName(String[] subReportName) {
        this.subReportName = subReportName;
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportParam{");
        sb.append("mainReportName='").append(mainReportName).append('\'');
        sb.append(", subReportName=").append(Arrays.toString(subReportName));
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
