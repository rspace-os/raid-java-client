package com.researchspace.raid.model.exception;

public class RaIDConnectionException extends RuntimeException {

  public RaIDConnectionException(String message) {
    super(message);
  }

  public RaIDConnectionException(Throwable cause) {
    super(cause);
  }

}
