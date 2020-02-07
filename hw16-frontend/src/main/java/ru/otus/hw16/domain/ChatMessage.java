package ru.otus.hw16.domain;

import java.util.Objects;

public class ChatMessage {


    private String id;
    private String type;
    private String content;
    private String sender;

    public ChatMessage(MessageType type, String content, String sender) {
        this.type = type.getValue();
        this.content = content;
        this.sender = sender;
    }

    public ChatMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum MessageType {
        CHAT("CHAT"),
        JOIN("JOIN"),
        LEAVE("LEAVE");

        private final String value;

        MessageType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type.getValue();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatMessage: id = " + id
                + ", type = " + type
                + ", content = " + content
                + ", sender = " + sender;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return  id.equals(that.id) &&
                type.equals(that.type) &&
                content.equals(that.content) &&
                sender.equals(that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,type, content, sender);
    }


}