package io.schildmeijer.castle;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CreateUserResponse {

  public String id;

  @SerializedName("created_at")
  public String createdAt;

  @SerializedName("updated_at")
  public String updatedAt;

  @SerializedName("last_seend_at")
  public String lastSeenAt;

  @SerializedName("flagged_at")
  public String flagged_at;

  public String email;

  public String name;

  public String username;

  public double risk;


  @SerializedName("devices_count")
  public String devicesCount;

  @SerializedName("custom_attributes")
  public Map<String, Object> customAttributes;

  @Override
  public String toString() {
    return "CreateUserResponse{" +
           "id='" + id + '\'' +
           ", created_at='" + createdAt + '\'' +
           ", updated_at='" + updatedAt + '\'' +
           ", last_seen_at='" + lastSeenAt + '\'' +
           ", flagged_at='" + flagged_at + '\'' +
           ", email='" + email + '\'' +
           ", name='" + name + '\'' +
           ", username='" + username + '\'' +
           ", risk=" + risk +
           ", devices_count='" + devicesCount + '\'' +
           ", custom_attributes=" + customAttributes +
           '}';
  }
}
