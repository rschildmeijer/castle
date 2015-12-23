package io.schildmeijer.castle;

public class CastleErrorResponse {

  public String type;
  public String message;

  @Override
  public String toString() {
    return "type='" + type + '\'' +
           ", message='" + message + "'";
  }
}
