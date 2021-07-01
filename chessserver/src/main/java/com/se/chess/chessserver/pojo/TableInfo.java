package com.se.chess.chessserver.pojo;

import lombok.Data;

@Data
public class TableInfo {
    private int count;
    private String user1;
    private String user2;
    public String getAnotherUser(String username){
        if(user1.equals(username)){
            return user2;
        }else{
            return user1;
        }
    }
    public void clearUser(String username){
        if(username.equals(user1)){
            this.count--;
            user1 = null;
        }else if(username.equals(user2)){
            this.count--;
            user2 = null;
        }
    }
    public void clearBothUsers(){
        user1 = user2 = null;
        count = 0;
    }
    public void addPlayer(String username){
        if(count < 2){
            count++;
            if(user1 == null){
                user1 = username;
            }else{
                user2 = username;
            }
        }
    }
}
