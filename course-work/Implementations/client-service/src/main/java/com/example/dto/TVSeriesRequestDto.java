package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TVSeriesRequestDto {
    private String title;
    private String genre;
    private Integer seasons;
    private Boolean ongoing;
    private Double rating;
}
