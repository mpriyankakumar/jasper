package com.temeos.jasperreport;

import com.temeos.jasperreport.service.SimpleReportFiller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JasperreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(JasperreportApplication.class, args);
    }

    @Bean
    public SimpleReportFiller reportFiller() {
        return new SimpleReportFiller();
    }

}
