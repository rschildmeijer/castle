package io.schildmeijer.castle;

import java.util.Map;

public class Event {

  public static class EventBuilder {

    private String name;
    private String userId;
    private Map<Object, Object> details;

    public EventBuilder name(final String name) {
      this.name = name;
      return this;
    }

    public EventBuilder userId(final String userId) {
      this.userId = userId;
      return this;
    }

    public EventBuilder details(final Map<Object, Object> details) {
      this.details = details;
      return this;
    }

    public Event build() {
      return new Event(name, userId, details);
    }
  }

  public static final String LOGIN_SUCCEEDED = "$login.succeeded";

  public Event(final String name, final String userId, final Map<Object, Object> details) {

  }

  public static EventBuilder builder() {
    return new EventBuilder();
  }
}
