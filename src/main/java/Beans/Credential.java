package Beans;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "credentials")
public class Credential {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private int userid;
  private String site;
  private String username;
  private String password;

  public Credential() {
  }

  public Credential(int userid, String site, String username, String password) {
    this.userid = userid;
    this.site = site;
    this.username = username;
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserid() {
    return userid;
  }

  public void setUserid(int userid) {
    this.userid = userid;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
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
}
