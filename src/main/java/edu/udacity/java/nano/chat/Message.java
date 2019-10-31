package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {

    private static String userName;
    private String content;
    private String type;
    private String onlineCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(String onlineCount) {
        this.onlineCount = onlineCount;
    }


    public Message(){}

    public Message(String content){
        this.content = content;
    }
}
