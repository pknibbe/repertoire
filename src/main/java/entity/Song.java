package entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "songs")
public class Song {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private int userid;
  private String location;
  private String name;
  private String performer;
  private String duration;

  public Song() {
  }

  public Song(int userid, String location, String name, String performer, String duration) {
    this.userid = userid;
    this.location = location;
    this.name = name;
    this.performer = performer;
    this.duration = duration;
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPerformer() {
    return performer;
  }

  public void setPerformer(String performer) {
    this.performer = performer;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
}
