package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import lombok.ToString;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private String user_name;
  private String name;
  private String user_pass;
  private String role_name;

  public User() {}

  public User(String name) {
    this.id = 0;
    this.user_name = "unknown";
    this.name = name;
  }

  public User(String username, String name, String user_pass, String role) {
    this.user_name = username;
    this.name = name;
    this.user_pass = user_pass;
    this.role_name = role;
  }

  public User(int id) {
    this.id = id;
    this.name = "Guest";
    this.role_name = "Guest";
    this.user_name = "Guest";
    this.user_pass = "Guest";
  }
}
