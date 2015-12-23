package io.schildmeijer.castle;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Realm;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.ning.http.client.extra.ThrottleRequestFilter;
import com.spotify.futures.FuturesExtra;


import static java.nio.charset.StandardCharsets.*;

public class CastleHttpClient implements CastleClient {

  public static class CastleHttpClientBuilder {

    private String secret;
    private boolean pooling = true;
    private int requestTimeout = 30_000;  //ms
    private int concurrency = 128;

    public CastleHttpClient build() {
      return new CastleHttpClient(secret, pooling, requestTimeout, concurrency);
    }

    public CastleHttpClientBuilder secret(final String secret) {
      this.secret = secret;
      return this;
    }

    public CastleHttpClientBuilder withPooling(final boolean pooling) {
      this.pooling = pooling;
      return this;
    }

    public CastleHttpClientBuilder requestTimeout(final int requestTimeout) {
      this.requestTimeout = requestTimeout;
      return this;
    }

    public CastleHttpClientBuilder concurrency(final int concurrency) {
      this.concurrency = concurrency;
      return this;
    }
  }

  private final HttpClient client;

  private  CastleHttpClient(final String secret, final boolean pooling, final int requestTimeout, final int concurrency) {
    final Realm realm = new Realm.RealmBuilder()
        .setUsePreemptiveAuth(true) // send basic authentication before the server gives an unauthorized response
        .setPassword(secret)
        .setScheme(Realm.AuthScheme.BASIC)
        .build();

    final AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
        .setAllowPoolingConnections(pooling)
        .setRequestTimeout(requestTimeout)  //ms
        .addRequestFilter(new ThrottleRequestFilter(concurrency /*concurrency*/, 0 /*fast fail if concurrency limit reached*/))
        .addRequestFilter(new RealmRequestFilter(realm))
        .build();
    client = new HttpClient(config);
  }

  public static CastleHttpClientBuilder builder() {
    return new CastleHttpClientBuilder();
  }

  @Override
  public ListenableFuture<Boolean> track(final Event event, final RequestContext context) {
    return Futures.immediateFuture(true);
  }

  @Override
  public ListenableFuture<CreateUserResponse> createUser(final User user, final RequestContext context) {
    final RequestBuilder builder = new RequestBuilder()
        .setUrl("https://api.castle.io/v1/users.json")
        .setMethod("POST")
        .addFormParam("id", user.id)
        .addFormParam("email", user.email)
        .addFormParam("username", user.username)
        .addFormParam("name", user.name)
        .addFormParam("created_at", user.createdAt);

    setCastleHeaders(builder, context);

    final ListenableFuture<Response> reply = client.execute(builder.build());
    final ListenableFuture<CreateUserResponse> transformation = FuturesExtra.asyncTransform(reply, response ->  {
      if (response.getStatusCode() != 201) {
        final CastleErrorResponse error = new Gson().fromJson(response.getResponseBody(UTF_8.name()), CastleErrorResponse.class);
        return Futures.immediateFailedFuture(new CastleException(error));
      }

      final CreateUserResponse creation = new Gson().fromJson(response.getResponseBody(UTF_8.name()), CreateUserResponse.class);
      return Futures.immediateFuture(creation);
    });

    return transformation;
  }

  private static void setCastleHeaders(final RequestBuilder builder, final RequestContext context) {
    if (context.cookieId != null) {
      builder.addHeader("X-Castle-Cookie-Id", context.cookieId);
    }

    if (context.ip != null) {
      builder.addHeader("X-Castle-Ip", context.ip);
    }

    if (context.headers != null) {
      builder.addHeader("X-Castle-Headers", context.headers);
    }

  }

  @Override
  public void close() {
    client.close();
  }
}
