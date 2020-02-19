package com.temeos.jasperreport.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SimpleReportFiller {

    public JasperReport compileReport(final String mainReportFileName, final String reportFilePath, final String... subReportFileName) {
        if (subReportFileName.length != 0) {
            compile(reportFilePath, subReportFileName);
        }
        return compile(reportFilePath, mainReportFileName);
    }

    private JasperReport compile(final String reportFilePath, final String... reportFileName) {
        JasperReport jasperReport = null;
        for (final String fileName : reportFileName) {
            try {
                final InputStream reportStream = loadAsResource(reportFilePath, fileName).getInputStream();
                jasperReport = JasperCompileManager.compileReport(reportStream);
                JRSaver.saveObject(jasperReport, fileName.replace(".jrxml", ".jasper"));
            } catch (JRException | IOException ex) {
                Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jasperReport;
    }

    private Resource loadAsResource(String reportFilePath, String fileName) {
        try {
            final Path path = Paths.get(reportFilePath);
            final Path file = path.resolve(fileName);
            System.out.println("File Path::----" + file);
            final Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }

    public JasperPrint fillReport(final JasperReport jasperReport, final Map<String, Object> parameters, final DataSource dataSource) {
        try (final Connection connection = dataSource.getConnection()) {
            return JasperFillManager.fillReport(jasperReport, parameters, connection);
        } catch (JRException | SQLException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
