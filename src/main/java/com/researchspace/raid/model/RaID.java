package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of = {"identifier"})
public class RaID {

  private RaIDMetadata metadata;
  private RaIDIdentifier identifier;
  private RaIDAccess access;
  private List<RaIDTitle> title;
  private Set<RaIDRelatedObject> relatedObject;

  public RaID(String identifier){
    this.identifier = new RaIDIdentifier(identifier);
  }

}
