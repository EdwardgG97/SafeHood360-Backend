package com.safehood.service;

import com.safehood.dto.ReportDTO;
import com.safehood.model.Report;
import com.safehood.model.User;
import com.safehood.repository.ReportRepository;
import com.safehood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public void createReport(ReportDTO reportDTO) {
        // Buscar el usuario
        User user = userRepository.findByUsername(reportDTO.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Crear nuevo reporte
        Report report = new Report();
        report.setUser(user);
        report.setDescription(reportDTO.getDescription());
        report.setLatitude(reportDTO.getLatitude());
        report.setLongitude(reportDTO.getLongitude());
        report.setEvidence(reportDTO.getEvidence());
        report.setCreatedAt(LocalDateTime.now());
        
        // Guardar el reporte
        reportRepository.save(report);
    }
    
    public List<Report> getUserReports(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return reportRepository.findByUser(user);
    }
}
