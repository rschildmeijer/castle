package io.schildmeijer.castle;

import com.google.common.util.concurrent.ListenableFuture;

public interface CastleClient {

  ListenableFuture<Boolean> track(final Event event, final RequestContext context);

  ListenableFuture<CreateUserResponse> createUser(final User user, final RequestContext context);

  void close();
}
