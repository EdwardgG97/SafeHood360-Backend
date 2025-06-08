package com.safehood.service;

import com.safehood.dto.ReportDTO;
import com.safehood.model.Report;
import com.safehood.model.User;
import com.safehood.repository.ReportRepository;
import com.safehood.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportService reportService;

    private ReportDTO testReportDTO;
    private User testUser;
    private Report testReport;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");
        
        testReportDTO = new ReportDTO();
        testReportDTO.setUsername("testUser");
        testReportDTO.setDescription("Test description");
        testReportDTO.setLatitude(1.0);
        testReportDTO.setLongitude(1.0);
        testReportDTO.setEvidence("Test evidence");

        testReport = new Report();
        testReport.setUser(testUser);
        testReport.setDescription("Test description");
        testReport.setLatitude(1.0);
        testReport.setLongitude(1.0);
        testReport.setEvidence("Test evidence");
        testReport.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createReport_ShouldCreateReportSuccessfully() {
        // Given
        when(userRepository.findByUsername(testReportDTO.getUsername()))
            .thenReturn(Optional.of(testUser));

        // When
        reportService.createReport(testReportDTO);

        // Then
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void createReport_ShouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findByUsername(testReportDTO.getUsername()))
            .thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> reportService.createReport(testReportDTO));
    }

    @Test
    void getUserReports_ShouldReturnReportsForUser() {
        // Given
        when(userRepository.findByUsername("testUser"))
            .thenReturn(Optional.of(testUser));
        when(reportRepository.findByUser(testUser))
            .thenReturn(java.util.List.of(testReport));

        // When
        var result = reportService.getUserReports("testUser");

        // Then
        verify(reportRepository, times(1)).findByUser(testUser);
        verify(userRepository, times(1)).findByUsername("testUser");
        assert !result.isEmpty();
    }

    @Test
    void getUserReports_ShouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findByUsername("nonExistingUser"))
            .thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> reportService.getUserReports("nonExistingUser"));
    }
}
