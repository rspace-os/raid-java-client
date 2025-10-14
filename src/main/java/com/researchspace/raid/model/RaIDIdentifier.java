package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaIDIdentifier {

  private String id;
  private String schemaUri;
  private RaIDRegistrationAgency registrationAgency;
  private RaIDOwner owner;
  private String raidAgencyUrl;
  private String license;
  private Long version;

}
