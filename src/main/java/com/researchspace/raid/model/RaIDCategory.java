package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaIDCategory extends RaIDBriefIdentifier {

  public RaIDCategory(String identifier, String schemaUri) {
    super(identifier, schemaUri);
  }

}
