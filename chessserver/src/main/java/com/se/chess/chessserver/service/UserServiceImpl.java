package com.se.chess.chessserver.service;

import com.se.chess.chessserver.mapper.UserMapper;
import com.se.chess.chessserver.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserInfo signUp(String username, String password) {
        UserInfo userInfo = new UserInfo(username, password);
        if (userMapper.getUserByUsername(username) == null && userMapper.insertUser(userInfo) == 1) {
            return userInfo;

        }else{
            return null;
        }
    }

    @Override
    public UserInfo login(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, password);
    }
}
