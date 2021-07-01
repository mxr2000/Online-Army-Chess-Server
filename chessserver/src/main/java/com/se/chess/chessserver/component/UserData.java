package com.se.chess.chessserver.component;

import com.se.chess.chessserver.pojo.Message;
import com.se.chess.chessserver.pojo.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class UserData {
    private Map<String, User> onlineUsers = new HashMap<>();
    public User getOnlineUserByUsername(String username){
        return onlineUsers.get(username);
    }

    /**
     * 添加在线用户，设置心跳时间
     * @param username
     */
    public void addUser(String username){
        onlineUsers.put(username, new User(username));
        heartbeatChances.put(username, 5);
    }

    /**
     * 清楚离线用户，清楚心跳表entry
     * @param username
     */
    public void removeUser(String username){
        onlineUsers.remove(username);
        heartbeatChances.remove(username);
    }

    public Map<String, User> getOnlineUsers() {
        return onlineUsers;
    }

    /**
     * 将消息发给除了sender的所有的在线用户
     * @param sender
     * @param message
     */
    public void sendMessageToAllUsersExceptSender(String sender, Message message){
        for(User user : onlineUsers.values()){
            if(!user.getUsername().equals(sender)){
                user.pushMessage(message);
            }
        }
    }


    private Map<String, Integer> heartbeatChances = new HashMap<>();

    public Map<String, Integer> getHeartbeatChances() {
        return heartbeatChances;
    }
}
