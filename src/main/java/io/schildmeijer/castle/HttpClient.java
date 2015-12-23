package io.schildmeijer.castle;

import com.google.common.util.concurrent.ListenableFuture;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Request;
import com.ning.http.client.Response;

import static com.ning.http.client.extra.ListenableFutureAdapter.asGuavaFuture;

public class HttpClient {

  private final AsyncHttpClient client;

  public HttpClient(final AsyncHttpClientConfig config) {
    this.client = new AsyncHttpClient(config);
  }

  public ListenableFuture<Response> execute(final Request request) {
    return asGuavaFuture(client.executeRequest(request));
  }

  public void close() {
    client.close();
  }

}