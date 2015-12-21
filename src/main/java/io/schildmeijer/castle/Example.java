package io.schildmeijer.castle;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import com.spotify.futures.FuturesExtra;

public class Example {

  public static void main(String[] args) {
    final CastleClient castle = CastleHttpClient.builder()
        .secret(":HCL1SVj3nw4KmL7YpzpivxNyDsUYjbgq")
        .build();

    final Event event = Event.builder()
        .name(Event.LOGIN_SUCCEEDED)
        .userId("roger")
        .details(ImmutableMap.builder().put("filename", "lolcatz.jpg").build())
        .build();

    final RequestContext context = RequestContext.builder()
        .castleCookieId("a97b492d-dcc3-4fc1-87d6-65682955afa5")
        .castleIp("87.12.111.29")
        .castleHeaders("{\"User-Agent\": \"Mozilla/5.0\", \"Accept\": \"text/html\", \"Accept-Language\": \"en-us,en;q=0.5\"}")
        .build();

    final ListenableFuture<Boolean> track = castle.track(event, context);

    FuturesExtra.addSuccessCallback(track, flag -> System.out.println("tracking ok"));
    FuturesExtra.addFailureCallback(track, flag -> System.out.println("tracking failed"));
  }
}
