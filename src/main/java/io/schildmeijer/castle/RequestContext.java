package io.schildmeijer.castle;

public class RequestContext {

  public static class RequestContextBuilder {

    private  String cookieId;
    private  String ip;
    private  String headers;

    public RequestContextBuilder castleCookieId(final String cookieId) {
      this.cookieId = cookieId;
      return this;
    }

    public RequestContextBuilder castleIp(final String ip) {
      this.ip = ip;
      return this;
    }

    public RequestContextBuilder castleHeaders(final String headers) {
      this.headers = headers;
      return this;
    }

    public RequestContext build() {
      return new RequestContext(cookieId, ip, headers);
    }
  }

  public final String cookieId;
  public final String ip;
  public final String headers;

  public RequestContext(final String cookieId, final String ip, final String headers) {
    this.cookieId = cookieId;
    this.ip = ip;
    this.headers = headers;
  }

  public static RequestContextBuilder builder() {
    return new RequestContextBuilder();
  }
}
