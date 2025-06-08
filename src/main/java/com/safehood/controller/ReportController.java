package com.safehood.controller;

import com.safehood.dto.ReportDTO;
import com.safehood.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportDTO reportDTO) {
        reportService.createReport(reportDTO);
        return ResponseEntity.ok("Reporte creado exitosamente");
    }
    
    @GetMapping
    public ResponseEntity<?> getUserReports(@RequestParam String username) {
        return ResponseEntity.ok(reportService.getUserReports(username));
    }
}
