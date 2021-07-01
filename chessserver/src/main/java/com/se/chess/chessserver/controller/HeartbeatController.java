package com.se.chess.chessserver.controller;

import com.se.chess.chessserver.component.UserData;
import com.se.chess.chessserver.pojo.Message;
import com.se.chess.chessserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HeartbeatController {
    @Autowired
    private UserData userData;
    @GetMapping("heartbeat/{username}")
    public List<Message> heartbeatHandler(@PathVariable("username")String username){
        User user = userData.getOnlineUserByUsername(username);
        userData.getHeartbeatChances().put(username, 5);
        List<Message> messages = user.popMessages();
        //messages.add(new Message(username, "test", "Fuck u"));测试test消息
        return messages;
    }
}
