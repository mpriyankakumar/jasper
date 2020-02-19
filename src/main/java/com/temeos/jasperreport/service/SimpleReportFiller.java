package com.temeos.jasperreport.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SimpleReportFiller {

    public JasperReport compileReport(final String mainReportFileName, final String... subReportFileName) {
        if (subReportFileName.length != 0) {
            compile(subReportFileName);
        }
        return compile(mainReportFileName);
    }

    private JasperReport compile(final String... reportFileName) {
        JasperReport jasperReport = null;
        for (final String fileName : reportFileName) {
            try {
                final InputStream reportStream = getClass().getResourceAsStream("/".concat(fileName));
                jasperReport = JasperCompileManager.compileReport(reportStream);
                JRSaver.saveObject(jasperReport, fileName.replace(".jrxml", ".jasper"));
            } catch (JRException ex) {
                Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jasperReport;
    }

    public JasperPrint fillReport(final JasperReport jasperReport, final Map<String, Object> parameters, final DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return JasperFillManager.fillReport(jasperReport, parameters, connection);
        } catch (JRException | SQLException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
            }
        }
        return null;
    }

}
