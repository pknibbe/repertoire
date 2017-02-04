package entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "playlists")
public class PlaylistSet {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private int id;
    private String name;
    private int role;

    public PlaylistSet() {
    }

    public PlaylistSet(String name, int role) {
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
