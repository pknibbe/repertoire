package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString

public class PresentablePlaylist {

  private int id;
  private String name;
  private int owner_id;
  private String owner_name;
  private int playing;

  public PresentablePlaylist(int id, String name, int owner_id, String owner_name) {
    this.id = id;
    this.name = name;
    this.owner_id = owner_id;
    this.owner_name = owner_name;
    this.playing = 0;
  }
}
