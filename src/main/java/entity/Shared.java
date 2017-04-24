package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@ToString

@Entity
@Table(name = "shared")
public class Shared {

  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy="increment")
  private int id;

  @OneToOne
  @JoinColumn(name="playlist_id")
  private Playlist playlist;

  @OneToOne
  @JoinColumn(name="shared_with")
  private User recipient;

  public Shared() {
  }

  public Shared(Playlist playlist, User recipient) {
    this.playlist = playlist;
    this.recipient = recipient;
  }

}
