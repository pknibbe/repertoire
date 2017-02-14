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
  private int privileges;
  private String username;
  private String name;

  public User() {
  }

  public User(String username, String name, int privileges) {
    this.username = username;
    this.name = name;
    this.privileges = privileges;
  }


}
