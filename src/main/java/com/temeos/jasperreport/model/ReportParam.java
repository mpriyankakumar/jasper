package com.temeos.jasperreport.model;

import java.util.Arrays;
import java.util.HashMap;

public class ReportParam {

    private String mainReportName;
    private String[] subReportName;
    private String reportFilePath;
    private String reportFormat;
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

    public String getReportFilePath() {
        return reportFilePath;
    }

    public void setReportFilePath(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
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
        sb.append(", reportFilePath='").append(reportFilePath).append('\'');
        sb.append(", reportFormat='").append(reportFormat).append('\'');
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
