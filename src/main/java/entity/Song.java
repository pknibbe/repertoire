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
@Table(name = "songs")
public class Song {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private String location;
  private String description;
  @ManyToOne(optional = false)
  @JoinColumn(name="playlist_id")
  private Playlist playlist;

  public Song() {
  }

  public Song(String location, Playlist playlist) {
    this.location = location;
    this.description = null;
    this.playlist = playlist;
  }

  public Song(String location, String description, Playlist playlist) {
    this.location = location;
    this.description = description;
    this.playlist = playlist;
  }

}
