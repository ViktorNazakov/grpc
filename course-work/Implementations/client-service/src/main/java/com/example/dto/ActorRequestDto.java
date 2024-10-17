package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorRequestDto {
    private String name;
    private String nationality;
    private String birthDate;
    private Double rating;
    private Boolean alive;
    private List<String> awards;
}