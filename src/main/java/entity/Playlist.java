package entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "playlist")
public class Playlist {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private int song;

  public Playlist() {
  }

  public Playlist(int song) {
    this.song = song;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSong() {
    return song;
  }

  public void setSong(int song) {
    this.song = song;
  }
}
