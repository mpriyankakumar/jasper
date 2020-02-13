package com.temeos.jasperreport.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ReportGeneratorServiceImpl {

    public OutputStream exportToPdf(String fileName, String author, JasperPrint jasperPrint) {

        // print report to file
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        //exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
        OutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor(author);
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputStream;
    }

    public OutputStream exportToXlsx(String fileName, String sheetName, JasperPrint jasperPrint) {
        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        //exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
        OutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[]{sheetName});

        exporter.setConfiguration(reportConfig);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputStream;
    }

    public OutputStream exportToCsv(String fileName, JasperPrint jasperPrint) {
        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        //exporter.setExporterOutput(new SimpleWriterExporterOutput(fileName));
        OutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new PrintWriter(outputStream);
        exporter.setExporterOutput(new SimpleWriterExporterOutput(writer));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputStream;
    }

    public OutputStream exportToHtml(String fileName, JasperPrint jasperPrint) {
        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        OutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new PrintWriter(outputStream);
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(writer));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputStream;
    }
}
