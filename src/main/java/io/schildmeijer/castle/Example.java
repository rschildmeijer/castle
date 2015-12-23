package io.schildmeijer.castle;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import com.spotify.futures.FuturesExtra;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Example {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    final CastleClient castle = CastleHttpClient.builder()
        .withPooling(true)
        .requestTimeout(10_000) //ms
        .concurrency(2^5) // max outstanding
        .secret("YOUR-API-KEY")
        .build();

    create(castle); // create a user

    //track(castle);  // emit an event
    castle.close();
  }

  private static void track(final CastleClient castle) {
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

  private static void create(final CastleClient castle) throws ExecutionException, InterruptedException {
    final User user = User.builder()
        .id("roger-" + UUID.randomUUID())
        .email("schildmeijer@apache.org")
        .username("schildmeijer")
        .name("Roger Schildmeijer")
        .createdAt("2012-12-02T00:30:08.276Z")
        .build();

    final RequestContext context = RequestContext.builder()
        .castleCookieId("a97b492d-dcc3-4fc1-87d6-65682955afa5")
        .castleIp("87.12.111.29")
        .castleHeaders("{\"User-Agent\": \"Mozilla/5.0\", \"Accept\": \"text/html\", \"Accept-Language\": \"en-us,en;q=0.5\"}")
        .build();

    final ListenableFuture<CreateUserResponse> creation = castle.createUser(user, context);

    FuturesExtra.addSuccessCallback(creation, System.out::println);
    FuturesExtra.addFailureCallback(creation, System.out::println);

    try {
      creation.get(); //block
    } catch (final Exception e) {
      //ignore
    }
  }
}
