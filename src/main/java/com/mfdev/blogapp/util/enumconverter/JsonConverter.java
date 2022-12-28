package com.mfdev.blogapp.util.enumconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Map<String, Object> attribute) {
    String content = null;

    try {
      content = mapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    return content;
  }

  @Override
  public Map<String, Object> convertToEntityAttribute(String dbData) {
    Map<String, Object> contentJson = null;

    try {
      contentJson = mapper.readValue(dbData, new TypeReference<HashMap<String, Object>>() {
      });
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    return contentJson;
  }
}
