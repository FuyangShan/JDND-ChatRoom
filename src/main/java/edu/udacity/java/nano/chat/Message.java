package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {

    private String username;
    private String msg;
    private String type;
    private int onlineCount;

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message(){}

    public Message(String msg){
        this.msg = msg;
    }

}
