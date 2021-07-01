package com.se.chess.chessserver.controller;

import com.se.chess.chessserver.component.TableData;
import com.se.chess.chessserver.component.UserData;
import com.se.chess.chessserver.pojo.Message;
import com.se.chess.chessserver.pojo.TableInfo;
import com.se.chess.chessserver.pojo.TableUserInfo;
import com.se.chess.chessserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableController {
    @Autowired
    private TableData tableData;
    @Autowired
    private UserData userData;

    /**
     * 用户离开桌子
     * @param info 离开的用户，游戏类型与桌子index
     */
    @PostMapping("/leavetable")
    public void leaveTableHandler(@RequestBody TableUserInfo info){
        //找到那个桌子，清空
        TableInfo tableInfo = tableData.getTableInfos(info.getType())[info.getIndex()];
        tableInfo.clearUser(info.getUsername());

        //设置User数据结构中type与index清空
        User user = userData.getOnlineUserByUsername(info.getUsername());
        user.setType(null);
        user.setIndex(-1);

        //给所有其他在线用户发消息表示该用户离开桌子
        userData.sendMessageToAllUsersExceptSender(info.getUsername(),
                new Message(info.getUsername(), "leavetable", info.getType() + " " + info.getIndex()));
    }

    /**
     * 用户加入桌子
     * @param info
     */
    @PostMapping("/entertable")
    public void enterTableHandler(@RequestBody TableUserInfo info){
        //找到那个桌子，设置用户
        TableInfo tableInfo = tableData.getTableInfos(info.getType())[info.getIndex()];
        tableInfo.addPlayer(info.getUsername());

        //设置User数据结构中type与index
        User user = userData.getOnlineUserByUsername(info.getUsername());
        user.setType(info.getType());
        user.setIndex(info.getIndex());

        //给所有其他在线用户发消息表示该用户加入桌子
        userData.sendMessageToAllUsersExceptSender(info.getUsername(),
                new Message(info.getUsername(), "entertable", info.getType() + " " + info.getIndex()));
    }

    /**
     * 新用户获取所有桌子状态
     * @return
     */
    @GetMapping("/acquirealltablestatus")
    public List<TableInfo> acquireAllTableStatus(){
        return tableData.getAllTableInfos();
    }
}
