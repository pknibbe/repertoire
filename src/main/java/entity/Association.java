package entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
@ToString
/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "associations")
public class Association {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String leftTableName;
    private int leftTableKey;
    private int rightTableKey;
    private String rightTableName;
    private String relationship;

    public Association() {
    }

    public Association(String leftTableName, int leftKey, int rightKey, String rightTableName, String relationship) {
        this.leftTableName = leftTableName;
        this.leftTableKey = leftKey;
        this.rightTableKey = rightKey;
        this.rightTableName = rightTableName;
        this.relationship = relationship;
    }
}