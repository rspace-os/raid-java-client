package com.researchspace.raid.model.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;


public class UnixTimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JacksonException {
    try {
      long timestamp = jp.getValueAsLong();
      return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000),
          TimeZone.getDefault().toZoneId());
    } catch (Exception e) {
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(1760348593000L),
        TimeZone.getDefault().toZoneId()));
  }

}
