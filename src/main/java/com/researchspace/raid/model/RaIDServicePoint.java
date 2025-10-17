package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaIDServicePoint {

  public Integer id;
  public String name;
  public String identifierOwner;
  public String repositoryId;
  public String prefix;
  public String groupId;
  public String searchContent;
  public String techEmail;
  public String adminEmail;
  public Boolean enabled;
  public Boolean appWritesEnabled;

}
