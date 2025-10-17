package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaID {

  private RaIDMetadata metadata;
  private RaIDIdentifier identifier;
  private RaIDAccess access;
  private List<RaIDTitle> title;


}
