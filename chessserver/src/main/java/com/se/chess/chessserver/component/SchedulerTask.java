package com.se.chess.chessserver.component;

import com.se.chess.chessserver.pojo.Message;
import com.se.chess.chessserver.pojo.TableInfo;
import com.se.chess.chessserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

@Configuration
@EnableScheduling
public class SchedulerTask {
    @Autowired
    private UserData userData;
    @Autowired
    private TableData tableData;
    @Scheduled(fixedRate = 1000)
    private void configureTask() {
        List<String> offlineUsers = new ArrayList<>();
        for (String username : userData.getHeartbeatChances().keySet()) {
            int chances = userData.getHeartbeatChances().get(username) - 1;
            userData.getHeartbeatChances().put(username, chances);
            System.out.println(username + " " + chances);
            if (chances == 0) {
                offlineUsers.add(username);
            }
        }
        //从数据中移除离线用户
        Map<String, User> usersToLeave = new HashMap<>();
        for (String username : offlineUsers) {
            usersToLeave.put(username, userData.getOnlineUserByUsername(username));
            userData.removeUser(username);

        }
        //对在线并且是联系人发送离线信息
        for (String offlineUsername : offlineUsers) {
            User offlineUser = usersToLeave.get(offlineUsername);
            String type = offlineUser.getType();
            //若未在桌子中，继续循环
            if(type == null){
                continue;
            }
            int index = offlineUser.getIndex();

            //找到其所在的桌子
            TableInfo tableInfo = tableData.getTableInfos(type)[index];
            Message message;
            //如果在游戏中，清理对方数据，发送消息类型为leavegame
            if(tableInfo.getCount() == 2){
                String anotherUsername = tableInfo.getAnotherUser(offlineUsername);
                User anotherUser = userData.getOnlineUserByUsername(anotherUsername);
                anotherUser.setIndex(-1);
                anotherUser.setType(null);
                message = new Message(offlineUsername, "leavegame", type + " " + index);
            }else{ //此人在桌子上，但并未游戏
                message = new Message(offlineUsername, "leavetable", type + " " + index);
            }
            //清除桌子
            tableInfo.clearBothUsers();
            //为所有用户转发信息
            if(type.equals(TableData.OPEN_GAME_TYPE) || type.equals(TableData.CLOSE_GAME_TYPE)){
                for (String username : userData.getOnlineUsers().keySet()) {
                    System.out.println("offline: " + offlineUsername);
                    User user = userData.getOnlineUserByUsername(username);
                    user.pushMessage(message);
                }
            }

        }
    }
}
