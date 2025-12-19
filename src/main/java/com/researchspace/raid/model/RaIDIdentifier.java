package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RaIDIdentifier extends RaIDBriefIdentifier {

  private RaIDRegistrationAgency registrationAgency;
  private RaIDOwner owner;
  private String raidAgencyUrl;
  private String license;
  private Long version;

  @JsonIgnore
  private String prefix;

  @JsonIgnore
  private String suffix;

  public RaIDIdentifier(String identifier){
    super(identifier);
  }

  public String getPrefix() {
    String[] urlSplit = this.getId().split("/");
    return urlSplit[urlSplit.length - 1];
  }

  public String getSuffix() {
    String[] urlSplit = this.getId().split("/");
    return urlSplit[urlSplit.length - 2];
  }


}
