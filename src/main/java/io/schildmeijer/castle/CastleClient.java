package io.schildmeijer.castle;

import com.google.common.util.concurrent.ListenableFuture;

public interface CastleClient {

  ListenableFuture<Boolean> track(final Event event, final RequestContext context);

}
