package io.schildmeijer.castle;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class CastleHttpClient implements CastleClient {

  public static class CastleHttpClientBuilder {

    private String secret;

    public CastleHttpClient build() {
      return new CastleHttpClient();
    }

    public CastleHttpClientBuilder secret(final String secret) {
      this.secret = secret;
      return this;
    }
  }

  public static CastleHttpClientBuilder builder() {
    return new CastleHttpClientBuilder();
  }

  @Override
  public ListenableFuture<Boolean> track(final Event event, final RequestContext context) {
    return Futures.immediateFuture(true);
  }
}
