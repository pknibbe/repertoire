package entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
@ToString

@Entity
@Table(name = "messages")
public class Message {


    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    protected int id;

    protected String subject;
    protected int sender;
    protected int receiver;
    protected int readFlag;
    protected String content;

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
