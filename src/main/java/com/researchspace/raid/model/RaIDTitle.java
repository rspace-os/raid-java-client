package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaIDTitle {
  public String text;
  public RaIDType type;
  public String startDate;
  public String endDate;
  public RaIDLanguage language;
}
