package com.se.chess.chessserver.controller;

import com.se.chess.chessserver.component.TableData;
import com.se.chess.chessserver.component.UserData;
import com.se.chess.chessserver.pojo.LayoutInfo;
import com.se.chess.chessserver.pojo.Message;
import com.se.chess.chessserver.pojo.TableInfo;
import com.se.chess.chessserver.service.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LayoutController {
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private TableData tableData;
    @Autowired
    private UserData userData;

    /**
     * 用户根据id更新棋子布局
     * @param layout
     */
    @PostMapping("/updatelayout")
    public void updateLayoutHandler(@RequestBody LayoutInfo layout){
        layoutService.updateLayoutById(layout);
    }

    @PostMapping("/createlayout")
    public String createLayoutHandler(@RequestBody LayoutInfo layout){
        return layoutService.insertLayout(layout);
    }

    @GetMapping("/acquirealllayouts/{username}")
    public List<LayoutInfo> acquireAllLayoutsHandler(@PathVariable("username")String username){
        return layoutService.getAllLayoutsByUsername(username);
    }

    /**
     * 用户选择了当前游戏的布局
     * @param map
     */
    @PostMapping("/submitlayout")
    public void submitLayoutHandler(@RequestBody Map<String, String> map){
        System.out.println(map);
        String username = map.get("username");
        String type = map.get("type");
        int index = Integer.parseInt(map.get("index"));
        int id = Integer.parseInt(map.get("id"));

        //找到当前棋卓的另一名玩家
        TableInfo[] tableInfos = tableData.getTableInfos(type);
        TableInfo tableInfo = tableInfos[index];
        String another = tableInfo.getAnotherUser(username);

        //告诉对方本方布局已经完成
        userData.getOnlineUserByUsername(another).pushMessage(new Message(username, "playerlayoutready", "" + id));
    }

    /**
     * 用户在对方已经提交布局后根据id号得到对方布局
     * @param id
     * @return
     */
    @GetMapping("/acquireplayerlayout/{id}")
    public LayoutInfo acquirePlayerLayoutHandler(@PathVariable("id")int id){
        System.out.println(id);
        return layoutService.getLayoutById(id);
    }
}
