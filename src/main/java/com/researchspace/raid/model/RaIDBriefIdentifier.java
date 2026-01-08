package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of = {"id"})
public abstract class RaIDBriefIdentifier {

  protected String id;
  protected String schemaUri;

  protected RaIDBriefIdentifier(String identifier) {
    this.id = identifier;
  }

  protected RaIDBriefIdentifier(String identifier, String schemaUri) {
    this(identifier);
    this.schemaUri = schemaUri;
  }

}
