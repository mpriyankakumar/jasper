package com.temeos.jasperreport.controller;

import com.temeos.jasperreport.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(path = "/sds/{fileFormat}", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<?> generateReport(@PathVariable final String fileFormat) {
        final Resource resource = reportService.generateReport(fileFormat);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "sample."+fileFormat.toLowerCase() + "\"")
                .body(resource);
    }
}
