package io.schildmeijer.castle;

import com.ning.http.client.Realm;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.filter.FilterContext;
import com.ning.http.client.filter.FilterException;
import com.ning.http.client.filter.RequestFilter;

public class RealmRequestFilter implements RequestFilter {

  private final Realm realm;

  public RealmRequestFilter(final Realm realm) {
    this.realm = realm;
  }

  @Override
  public <T> FilterContext<T> filter(final FilterContext<T> ctx) throws FilterException {
    final Request decorator = new RequestBuilder(ctx.getRequest())
        .setRealm(realm)
        .build();

    return new FilterContext.FilterContextBuilder<>(ctx)
        .request(decorator)
        .build();
  }
}
