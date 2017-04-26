package resistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class Role {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private String user_name;
  private String role_name;

  public Role() {
  }

  public Role(String username, String role) {
    this.user_name = username;
    this.role_name = role;
  }
}
