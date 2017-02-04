package entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

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

    public Association() {
    }

    public Association(String leftTableName, int leftKey, int rightKey, String rightTableName) {
        this.leftTableName = leftTableName;
        this.leftTableKey = leftKey;
        this.rightTableKey = rightKey;
        this.rightTableName = rightTableName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeftTableName() {
        return leftTableName;
    }

    public void setLeftTableName(String leftTableName) {
        this.leftTableName = leftTableName;
    }

    public int getLeftTableKey() {
        return leftTableKey;
    }

    public void setLeftTableKey(int leftTableKey) {
        this.leftTableKey = leftTableKey;
    }

    public int getRightTableKey() {
        return rightTableKey;
    }

    public void setRightTableKey(int rightTableKey) {
        this.rightTableKey = rightTableKey;
    }

    public String getRightTableName() {
        return rightTableName;
    }

    public void setRightTableName(String rightTableName) {
        this.rightTableName = rightTableName;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", leftTableName='" + leftTableName + '\'' +
                ", leftTableKey=" + leftTableKey +
                ", rightTableKey=" + rightTableKey +
                ", rightTableName='" + rightTableName + '\'' +
                '}';
    }
}