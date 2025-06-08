package com.safehood.dto;

import lombok.Data;

@Data
public class ReportDTO {
    private String username;
    private String description;
    private Double latitude;
    private Double longitude;
    private String evidence; // Base64 encoded image
}
