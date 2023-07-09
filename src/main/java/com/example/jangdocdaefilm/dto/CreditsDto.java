package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreditsDto {
  private String id;
  private List<CastDto> cast;
  private List<CrewDto> crew;
}
