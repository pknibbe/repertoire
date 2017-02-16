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
 * Created by peter on 2/15/2017.
 */
@Entity
@Table(name = "user_roles")
public class Role {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String username;
    private String rolename;

    public Role() {
    }

    public Role(String username, String rolename) {
        this.username = username;
        this.rolename = rolename;
    }
}
