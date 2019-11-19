package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     * Some concept : session ; client ; server ; endpoint ?
     * Map<String, Session> onlineSessions ==> String == userName?
     */

    private Session session;
    private int onlineCount;
    private static Set<String> users = new HashSet<>();
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>(); //string --> ID,

    private static void sendMessageToAll(Message msg) throws IOException {
        //TODO: add send message method.
        System.out.println("In Send message method" + msg.getMsg());
        for (Session session : onlineSessions.values()) {
            String str = "";
            if (msg.getType().equals("ENTER") && onlineSessions.containsKey(msg.getUsername())) {
                str = msg.getUsername() + " just enter the room.";
            } else if (msg.getType().equals("LEAVE")) {
                str = msg.getUsername() + " just left the room.";
            } else if (msg.getType().equals("SPEAK")){
                str = msg.getUsername() + " said: " + msg.getMsg();
            }
            msg.setMsg(str);
            String json = JSON.toJSONString(msg);
            session.getBasicRemote().sendText(json);
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     * Does user == client ??
     * Anyone login with a "username" would be user right? How should I retrieve that data into here?
     *
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("username") String username) throws IOException {
        //"@PathParam("username") String username" auto read the form and bind the data to variable "username" . But where to find?
        //add session
        onlineSessions.put(session.getId(), session);
        //add users
        users.add(username);
        onlineCount = users.size();

        Message loginMsg = new Message();
        loginMsg.setUserName(username);
        loginMsg.setType("ENTER");
        loginMsg.setOnlineCount(users.size() + 1);
        sendMessageToAll(loginMsg);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException {
       //create a new message on jsonStr
        Message onMsg = new Message();
        onMsg.setUserName(session.getId());
        onMsg.setType("SPEAK");
        onMsg.setMsg(jsonStr);

        //sendMessageToAll(message);
        sendMessageToAll(onMsg);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        //TODO: add close connection.
        onlineSessions.remove(session.getId());
        users.remove(session.getId());

        Message closeMsg = new Message();
        closeMsg.setUserName(session.getId());
        closeMsg.setType("LEAVE");
        closeMsg.setOnlineCount(users.size() - 1);

        sendMessageToAll(closeMsg);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
