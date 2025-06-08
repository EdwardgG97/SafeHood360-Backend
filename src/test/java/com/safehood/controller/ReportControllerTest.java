package com.safehood.controller;

import com.safehood.dto.ReportDTO;
import com.safehood.model.Report;
import com.safehood.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private ReportDTO testReportDTO;

    @BeforeEach
    void setUp() {
        testReportDTO = new ReportDTO();
        testReportDTO.setUsername("testUser");
        testReportDTO.setDescription("Test description");
        testReportDTO.setLatitude(1.0);
        testReportDTO.setLongitude(1.0);
        testReportDTO.setEvidence("Test evidence");
    }

    @Test
    void createReport_ShouldReturnSuccessResponse() {
        // When
        ResponseEntity<?> response = reportController.createReport(testReportDTO);

        // Then
        verify(reportService, times(1)).createReport(testReportDTO);
        assertEquals("Reporte creado exitosamente", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getUserReports_ShouldReturnReports() {
        // Given
        List<Report> testReports = List.of(new Report());
        when(reportService.getUserReports("testUser"))
            .thenReturn(testReports);

        // When
        ResponseEntity<?> response = reportController.getUserReports("testUser");

        // Then
        verify(reportService, times(1)).getUserReports("testUser");
        assertEquals(testReports, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void createReport_ShouldPropagateException() {
        // Given
        RuntimeException exception = new RuntimeException("Test exception");
        doThrow(exception).when(reportService).createReport(any(ReportDTO.class));

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> reportController.createReport(testReportDTO));
    }

    @Test
    void getUserReports_ShouldPropagateException() {
        // Given
        RuntimeException exception = new RuntimeException("Test exception");
        doThrow(exception).when(reportService).getUserReports(any(String.class));

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> reportController.getUserReports("testUser"));
    }
}
