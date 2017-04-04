package entity;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
@ToString

public class PresentableMessage extends Message {
    private String senderName;
    //TODO deal with senderName parameter
    public PresentableMessage(Message message, String senderName) {
        this.subject = message.getSubject();
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
        this.readFlag = message.getReadFlag();
        this.content = message.getContent();
        this.senderName = senderName;
    }

}
