package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @JsonIgnore
  private String prefix;

  @JsonIgnore
  private String suffix;

  public String getPrefix() {
    String[] urlSplit = this.getId().split("/");
    return urlSplit[urlSplit.length - 1];
  }

  public String getSuffix() {
    String[] urlSplit = this.getId().split("/");
    return urlSplit[urlSplit.length - 2];
  }


}
