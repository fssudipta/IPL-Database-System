package data.buyNsell;

import java.io.Serializable;

public class MessageInfo implements Serializable {
    private MessageHeader messageHeader;
    private String message;

    public MessageInfo(MessageHeader messageHeader, String message) {
        this.messageHeader = messageHeader;
        this.message = message;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}