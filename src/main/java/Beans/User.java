package Beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private String username;
  private String password;
  private String email;
  private String text;
  private int playlistSet;
  private int privileges;

  public User() {
  }

  public User(String username, String password, String email, String text, int privileges) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.text = text;
    this.privileges = privileges;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getPlaylistSet() {
    return playlistSet;
  }

  public void setPlaylistSet(int playlistSet) {
    this.playlistSet = playlistSet;
  }

  public int getPrivileges() {
    return privileges;
  }

  public void setPrivileges(int privileges) {
    this.privileges = privileges;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", text='" + text + '\'' +
            ", playlists=" + playlistSet +
            ", privileges=" + privileges +
            '}';
  }
}
