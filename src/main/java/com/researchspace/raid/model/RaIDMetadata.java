package com.researchspace.raid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.researchspace.raid.model.utils.UnixTimestampToLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaIDMetadata {

  @JsonDeserialize(using = UnixTimestampToLocalDateTimeDeserializer.class)
  LocalDateTime created;

  @JsonDeserialize(using = UnixTimestampToLocalDateTimeDeserializer.class)
  LocalDateTime updated;

}
