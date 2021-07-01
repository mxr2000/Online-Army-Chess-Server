package com.se.chess.chessserver.component;

import com.se.chess.chessserver.pojo.TableInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class TableData {
    private static final int TABLE_COUNT = 5;
    public static final String OPEN_GAME_TYPE  = "open";
    public static final String CLOSE_GAME_TYPE = "close";
    private TableInfo[][] tableInfos;
    public TableData(){
        tableInfos = new TableInfo[2][TABLE_COUNT];
        for (int i = 0; i < TABLE_COUNT; i++) {
            tableInfos[0][i] = new TableInfo();
            tableInfos[1][i] = new TableInfo();
        }
    }
    public TableInfo[] getOpenGameTableInfos(){
        return tableInfos[0];
    }

    public TableInfo[] getCloseGameTableInfos(){
        return tableInfos[1];
    }
    public TableInfo[] getTableInfos(String type){
        if(type.equals(OPEN_GAME_TYPE)){
            return getOpenGameTableInfos();
        }else{
            return getCloseGameTableInfos();
        }
    }

    public List<TableInfo> getAllTableInfos(){
        List<TableInfo> list = new LinkedList<>();
        for(int i = 0; i < TABLE_COUNT; i++){
            list.add(tableInfos[0][i]);
        }
        for(int i = 0; i < TABLE_COUNT; i++){
            list.add(tableInfos[1][i]);
        }
        return list;
    }

}
