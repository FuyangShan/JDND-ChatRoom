package edu.udacity.java.nano.chat;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

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
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        //TODO: add send message method.
        for (Session session : onlineSessions.values()) {
            Message newMessageToAll = new Message(msg);
            newMessageToAll.setUserName(users.get(session.getId()));
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
        //TODO: add on open connection.
        //add session
        onlineSessions.put(username, session);
        //add users
        users.put(session.getId(), username);
        Message loginMessage = new Message();
        loginMessage.setUserName(username);
        loginMessage.setContent(Message.getUserName() + " just come into the room");
        sendMessageToAll(loginMessage.getContent());
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //TODO: add send message.

        //create a new message on jsonStr
        Message thisMessage = new Message(jsonStr);
        if (jsonStr != null) {
            thisMessage.setType("SPEAK");
            String thisUser = users.get(session.getId());
            thisMessage.setUserName(thisUser);
            thisMessage.setContent(thisUser + " : " + jsonStr);
        }

        //sendMessageToAll(message);
        if (thisMessage.getType().equals("SPEAK")) {
            sendMessageToAll(thisMessage.getContent());
        }
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //TODO: add close connection.
        onlineSessions.remove(users.get(session.getId()));

        Message closeMessage = new Message();
        String closeUser = users.get(session.getId());
        closeMessage.setContent(closeUser + " just left the room");
        sendMessageToAll(closeMessage.getContent());
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
