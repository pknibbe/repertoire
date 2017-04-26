package resistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class Entry {

  private int id;
  private String name;

  public Entry(String name) {
    this.name = name;
  }

  public Entry() {}

}
