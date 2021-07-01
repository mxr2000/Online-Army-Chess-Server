package com.se.chess.chessserver.controller;

import com.se.chess.chessserver.component.TableData;
import com.se.chess.chessserver.component.UserData;
import com.se.chess.chessserver.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    private UserData userData;
    @Autowired
    private TableData tableData;

    /**
     * 游戏中，玩家移动棋子
     * @param moveInfo
     */
    @PostMapping("/move")
    public void moveHandler(@RequestBody MoveInfo moveInfo){
        TableInfo tableInfo = tableData.getTableInfos(moveInfo.getType())[moveInfo.getIndex()];
        System.out.println(moveInfo.toString());
        String another = tableInfo.getAnotherUser(moveInfo.getUsername());

        userData.getOnlineUserByUsername(another).pushMessage(
                new Message(moveInfo.getUsername(), "move",
                        "" + moveInfo.getFromX() + " " + moveInfo.getFromY() + " " + moveInfo.getToX() + " " + moveInfo.getToY()));
    }

    /**
     * 玩家在摆棋子，游戏中，游戏结束后离开游戏
     * 清楚桌子与两个用户信息
     * @param info 离开游戏的用户名，桌子类型以及编号
     */
    @PostMapping("/leavegame")
    public void leaveGameHandler(@RequestBody TableUserInfo info){
        TableInfo tableInfo = tableData.getTableInfos(info.getType())[info.getIndex()];
        User user1 = userData.getOnlineUserByUsername(tableInfo.getUser1());
        User user2 = userData.getOnlineUserByUsername(tableInfo.getUser2());
        if(user1 != null){
            user1.setIndex(-1);
            user1.setType(null);
        }
        if(user2 != null){
            user2.setIndex(-1);
            user2.setType(null);
        }

        tableInfo.clearBothUsers();
        userData.sendMessageToAllUsersExceptSender(info.getUsername(),
                new Message(info.getUsername(), "leavegame", info.getType() + " " + info.getIndex()));
    }

    /**
     * 游戏结束后，一方玩家请求冲完
     * @param info
     */
    @PostMapping("/requestplayagain")
    public void requestPlayAgainHandler(@RequestBody TableUserInfo info){
        TableInfo tableInfo = tableData.getTableInfos(info.getType())[info.getIndex()];
        String another = tableInfo.getAnotherUser(info.getUsername());
        userData.getOnlineUserByUsername(another).pushMessage(
                new Message(info.getUsername(), "playerrequestreplay", ""));
    }

    /**
     * 在得到对方重玩请求后，同意重玩
     * @param info
     */
    @PostMapping("/replaygame")
    public void replayGameHandler(@RequestBody TableUserInfo info){
        TableInfo tableInfo = tableData.getTableInfos(info.getType())[info.getIndex()];
        String another = tableInfo.getAnotherUser(info.getUsername());
        userData.getOnlineUserByUsername(another).pushMessage(
                new Message(info.getUsername(), "playerassumereplay", ""));
    }

}
