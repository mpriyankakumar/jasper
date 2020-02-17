package com.temeos.jasperreport.controller;

import com.temeos.jasperreport.model.ReportParam;
import com.temeos.jasperreport.service.ReportService;
import com.temeos.jasperreport.util.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/jasper/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(path = "/{fileFormat}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> generateReport(@PathVariable final String fileFormat, @RequestBody ReportParam reportParam) {
        System.out.println(reportParam);
        final Resource resource = reportService.generateReport(reportParam, fileFormat);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + ReportUtil.removeFileExtension(reportParam.getMainReportName()) + "." + fileFormat.toLowerCase() + "\"")
                .body(resource);
    }
}
