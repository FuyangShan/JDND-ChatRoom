package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {
     // TODO: add message model.
    private String text;

    //in case it is null?
    public Message(){
    }

    //constructor
    public Message(String text) {
        this.text = text;
    }

    //getter and setter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
