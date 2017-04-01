package entity;

/**
 * An extension of the base Message class to aid in presentation to the user
 * Created by peter on 3/22/2017.
 */
public class PresentableMessage extends Message {

    public PresentableMessage(Message message, String senderName) {
        this.subject = message.getSubject();
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
        this.readFlag = message.getReadFlag();
        this.content = message.getContent();
    }

}
