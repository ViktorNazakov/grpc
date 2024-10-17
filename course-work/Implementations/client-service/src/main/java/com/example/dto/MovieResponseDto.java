package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDto {
    private Integer id;
    private String title;
    private String genre;
    private String releaseYear;
    private Float durationMinutes;
    private Double rating;
}
