castle-java
===

What
----

An asynchronous java client for castle.io

Usage
-----

```java
// From castle/src/main/java/io/schildmeijer/castle/Example.java

// Set up the client
final CastleClient castle = CastleHttpClient.builder()
    .withPooling(true)
    .requestTimeout(10_000) //ms
    .concurrency(2^5) // max outstanding
    .secret("YOUR-API-KEY")
    .build();

// Create a new user
final User user = User.builder()
    .id("roger-" + UUID.randomUUID())
    .email("schildmeijer@example.com")
    .username("schildmeijer")
    .name("Roger Schildmeijer")
    .createdAt("2015-12-23T00:30:08.276Z")
    .build();

final RequestContext context = RequestContext.builder()
    .castleCookieId("a97b492d-dcc3-4fc1-87d6-65682955afa5")
    .castleIp("87.12.111.29")
    .castleHeaders("{\"User-Agent\": \"Mozilla/5.0\", \"Accept\": \"text/html\", \"Accept-Language\": \"en-us,en;q=0.5\"}")
    .build();

final ListenableFuture<CreateUserResponse> creation = castle.createUser(user, context);

FuturesExtra.addSuccessCallback(creation, System.out::println);
FuturesExtra.addFailureCallback(creation, System.out::println);
```

Notes
-----

* wip.
