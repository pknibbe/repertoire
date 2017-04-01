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
@Table(name = "playlists")
public class Playlist {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private String name;
  private int owner_id;

  public Playlist() {
  }

  public Playlist(String name, int owner) {
    this.name = name;
    this.owner_id = owner;
  }

}
