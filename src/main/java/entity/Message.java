package entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
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
@Table(name = "messages")
public class Message {


    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    protected int id;

    private String subject;
    private int sender;
    private int receiver;
    private int readFlag;
    private String content;

    public Message(String subject, int sender, int receiver, int read, String content) {
        this.subject = subject;
        this.sender = sender;
        this.receiver = receiver;
        this.readFlag = read;
        this.content = content;
    }

    public Message() {
    }


}
