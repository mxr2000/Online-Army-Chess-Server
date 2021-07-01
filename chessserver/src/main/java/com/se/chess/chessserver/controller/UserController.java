package com.se.chess.chessserver.controller;

import com.se.chess.chessserver.component.UserData;
import com.se.chess.chessserver.pojo.Message;
import com.se.chess.chessserver.pojo.User;
import com.se.chess.chessserver.pojo.UserInfo;
import com.se.chess.chessserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserData userData;

    /**
     * 用户登录，成功则加入在线用户
     * @param user 用户上传的用户名与密码
     * @return 验证成功返回"success", 失败返回"failure"，改用户已经在线也返回failure
     */
    @PostMapping("/login")
    public String loginHandler(@RequestBody UserInfo user){
        if(userData.getOnlineUserByUsername(user.getUsername()) != null){
            return "failure";
        }
        if(userService.login(user.getUsername(), user.getPassword()) != null){
            userData.addUser(user.getUsername());
            return "success";
        }
        return "failure";
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/signup")
    public String signupHandler(@RequestBody UserInfo user){
        if(userService.signUp(user.getUsername(), user.getPassword()) != null){
            return "success";
        }
        return "failure";
    }
}
