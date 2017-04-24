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
  private int playlist_id;

  private String name;
  @ManyToOne(optional = false)
  @JoinColumn(name="owner_id")
  private User owner;

  public Playlist() {
  }

  public Playlist(String name, User owner) {
      super();
      this.name = name;
      this.owner = owner;
  }

}
