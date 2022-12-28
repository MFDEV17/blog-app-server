package com.mfdev.blogapp.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ExceptionUtil {

  public static ResponseEntity<String> uniqueExceptionHandler(DataIntegrityViolationException e) {
    String errMessage = Objects.requireNonNull(e.getRootCause()).getMessage();
    String key = StringUtils
            .capitalize(StringUtils.substringBetween(errMessage, "(", ")"));

    String value = StringUtils.substringBetween(errMessage, "=(", ")");
    String response = String.format("%s '%s' already exists", key, value);

    return ResponseEntity.badRequest().body(response);
  }
}
