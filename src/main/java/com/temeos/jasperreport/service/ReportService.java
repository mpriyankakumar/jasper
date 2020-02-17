package com.temeos.jasperreport.service;

import com.temeos.jasperreport.model.ReportParam;
import com.temeos.jasperreport.util.ReportUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;

@Component
public class ReportService {

    @Autowired
    private SimpleReportFiller simpleReportFiller;

    @Autowired
    private ReportGeneratorServiceImpl reportGeneratorServiceImpl;

    @Autowired
    private DataSource dataSource;


    public Resource generateReport(final ReportParam reportParam, final String fileFormat) {
        final JasperReport jasperReport = simpleReportFiller.compileReport(reportParam.getMainReportName(), reportParam.getSubReportName());

        final JasperPrint jasperPrint = simpleReportFiller.fillReport(jasperReport, reportParam.getParameters(), dataSource);

        ByteArrayOutputStream outputStream;
        switch (fileFormat.toUpperCase()) {
            case "PDF":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToPdf(jasperPrint);
                break;

            case "XLSX":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToXlsx(ReportUtil.removeFileExtension(reportParam.getMainReportName()), jasperPrint);
                break;

            case "CSV":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToCsv(jasperPrint);
                break;

            case "HTML":
                outputStream = (ByteArrayOutputStream) reportGeneratorServiceImpl.exportToHtml(jasperPrint);
                break;
            default:
                throw new RuntimeException("Invalid file format.");
        }
        byte[] bytes = outputStream.toByteArray();
        return (bytes.length != 0) ? new ByteArrayResource(bytes) : null;
    }
}
