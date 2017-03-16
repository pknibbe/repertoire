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
  private String description;
  private int playlist_id;

  public Song() {
  }

  public Song(String location, String description, int playlist_id) {
    this.location = location;
    this.description = description;
    this.playlist_id = playlist_id;
  }

}
