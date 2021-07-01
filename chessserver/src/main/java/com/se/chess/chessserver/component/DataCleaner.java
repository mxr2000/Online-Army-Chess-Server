package com.se.chess.chessserver.component;

import com.se.chess.chessserver.pojo.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DataCleaner {
    @Autowired
    private UserData userData;
    @Autowired
    private TableData tableData;
    @Scheduled(fixedRate = 10000)
    private void clearData(){
        for (TableInfo tableInfo :
                tableData.getAllTableInfos()) {
            String user1 = tableInfo.getUser1();
            String user2 = tableInfo.getUser2();
            if((user1 != null && userData.getOnlineUserByUsername(user1) == null)
                    || (user2 != null && userData.getOnlineUserByUsername(user2) == null)){
                tableInfo.clearBothUsers();
            }
        }
    }
}
