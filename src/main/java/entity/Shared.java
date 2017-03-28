package entity;

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

/**
 * A list of shared playlists and the users that share them
 * Created by peter on 3/28/2017.
 */

@Entity
@Table(name = "shared")
public class Shared {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;
  private int playlist_id;
  private int shared_with;

  public Shared() {
  }

  public Shared(int playlist_id, int shared_with) {
    this.playlist_id = playlist_id;
    this.shared_with = shared_with;
  }

}
