package io.schildmeijer.castle;

public class CastleException extends RuntimeException {

  private final CastleErrorResponse error;

  public CastleException(final CastleErrorResponse error) {
    this.error = error;
  }

  public String getType() {
    return error.type;
  }

  public String getMessage() {
    return error.message;
  }

  @Override
  public String toString() {
    return "CastleException{" +
           "error=" + error +
           '}';
  }
}
