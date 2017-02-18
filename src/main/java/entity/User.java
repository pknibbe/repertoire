package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
@ToString


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
  private String user_name;
  private String name;
  private String pw;
  private String role_name;

  public User() {
  }

  public User(String username, String name, String pw, String role) {
    this.user_name = username;
    this.name = name;
    this.pw = pw;
    this.role_name = role;
  }

}
