package com.se.chess.chessserver.pojo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 在线用户的实体类
 */
public class User {
    private String username;

    public String getUsername() {
        return username;
    }

    public User(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void pushMessage(Message message){
        messages.add(message);
    }
    private List<Message> messages = new LinkedList<>();
    public List<Message> popMessages(){
        List<Message> list = new LinkedList<>();
        for (Message msg :
                messages) {
            list.add(msg);
        }
        messages.clear();
        return list;
    }

    //所在棋盘
    private String type;
    private int index;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        System.out.println("Index changed from " + this.index + " to " + index);
        this.index = index;

    }
}
