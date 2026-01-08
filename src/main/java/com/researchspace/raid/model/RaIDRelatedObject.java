package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RaIDRelatedObject extends RaIDBriefIdentifier {

  private static final String DOI_SCHEMA_URL = "https://doi.org/";
  private static final String EVENT_TYPE_ID = "https://vocabulary.raid.org/relatedObject.type.schema/260";
  private static final String EVENT_TYPE_SCHEMA = "https://vocabulary.raid.org/relatedObject.type.schema/329";
  private static final String OUTPUT_CATEGORY_ID = "https://vocabulary.raid.org/relatedObject.category.id/190";
  private static final String OUTPUT_CATEGORY_SCHEMA = "https://vocabulary.raid.org/relatedObject.category.schemaUri/386";

  private RaIDType type;
  private List<RaIDCategory> category;


  public RaIDRelatedObject(String identifier){
    super(identifier, DOI_SCHEMA_URL);
    this.type = new RaIDType(EVENT_TYPE_ID, EVENT_TYPE_SCHEMA);
    this.category = List.of(new RaIDCategory(OUTPUT_CATEGORY_ID, OUTPUT_CATEGORY_SCHEMA));
  }

}
