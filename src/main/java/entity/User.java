package entity;

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
  private String name;
  private int messageSet;
  private int credentialSet;
  private int playlistSet;
  private int privileges;

  public User() {
  }

  public User(String username, String name, int messageSet, int credentialSet, int playlistSet, int privileges) {
    this.username = username;
    this.name = name;
    this.messageSet = messageSet;
    this.credentialSet = credentialSet;
    this.playlistSet = playlistSet;
    this.privileges = privileges;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(int messageSet) {
        this.messageSet = messageSet;
    }

    public int getCredentialSet() {
        return credentialSet;
    }

    public void setCredentialSet(int credentialSet) {
        this.credentialSet = credentialSet;
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
                ", name='" + name + '\'' +
                ", messageSet=" + messageSet +
                ", credentialSet=" + credentialSet +
                ", playlistSet=" + playlistSet +
                ", privileges=" + privileges +
                '}';
    }
}
