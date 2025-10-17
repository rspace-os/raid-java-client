package com.researchspace.raid.model.exception;

public class RaIDException extends RuntimeException {

  public RaIDException(String message) {
    super(message);
  }

  public RaIDException(Throwable cause) {
    super(cause);
  }

}
