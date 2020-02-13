package com.temeos.jasperreport.service;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReportService {

    @Autowired
    private SimpleReportFiller simpleReportFiller;

    @Autowired
    private ReportGeneratorServiceImpl reportGeneratorServiceImpl;

    @Autowired
    private DataSource dataSource;


    public Resource generateReport(final String fileFormat) {
        final JasperReport jasperReport = simpleReportFiller.compileReport("employeeEmailReport.jrxml", "employeeReport.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Employee Report Example");
        parameters.put("minSalary", 15000.0);
        parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        final JasperPrint jasperPrint = simpleReportFiller.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = null;
        switch (fileFormat.toUpperCase()) {
            case "PDF":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToPdf("employeeReport.pdf", "baeldung", jasperPrint);
                break;

            case "XLSX":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToXlsx("employeeReport.xlsx", "Employee Data", jasperPrint);
                break;

            case "CSV":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToCsv("employeeReport.csv", jasperPrint);
                break;

            case "HTML":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToHtml("employeeReport.html", jasperPrint);
                break;
        }

        byte[] bytes = outputStream.toByteArray();
        Resource resource = new ByteArrayResource(bytes);
        return resource;
    }
}
