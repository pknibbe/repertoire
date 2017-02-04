package entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * Created by peter on 2/1/2017.
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private int id;
    private int sender;
    private int receiver;
    private int read;
    private String content;

    public Message(int sender, int receiver, int read, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.read = read;
        this.content = content;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Messages {" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", read=" + read +
                ", content='" + content + '\'' +
                '}';
    }
}
