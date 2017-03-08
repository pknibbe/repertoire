package entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
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
@Table(name = "songs")
public class Song {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private String location;
  private String name;
  private String performer;
  private String duration;

  public Song() {
  }

  public Song(String location, String name, String performer, String duration) {
    this.location = location;
    this.name = name;
    this.performer = performer;
    this.duration = duration;
  }

}
