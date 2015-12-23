package io.schildmeijer.castle;

public class User {

  final String id;
  final String email;
  final String username;
  final String name;
  final String createdAt;

  private User(final String id, final String email, final String username, final String name, final String createdAt) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.name = name;
    this.createdAt = createdAt;
  }

  public static class UserBuilder {

    private String id;
    private String email;
    private String username;
    private String name;
    private String createdAt;

    public UserBuilder id(final String id) {
      this.id = id;
      return this;
    }

    public UserBuilder email(final String email) {
      this.email = email;
      return this;
    }

    public UserBuilder username(final String username) {
      this.username = username;
      return this;
    }

    public UserBuilder name(final String name) {
      this.name = name;
      return this;
    }

    public UserBuilder createdAt(final String createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public User build() {
      return new User(id, email, username, name, createdAt);
    }
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

}
