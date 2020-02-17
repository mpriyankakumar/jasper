package com.temeos.jasperreport.util;

public class ReportUtil {

    public static String removeFileExtension(final String fileName) {
        return fileName.substring(0, fileName.indexOf("."));
    }
}
