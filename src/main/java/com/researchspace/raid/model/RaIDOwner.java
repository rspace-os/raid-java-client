package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaIDOwner {

  private String id;
  private String schemaUri;
  private Long servicePoint;

}
