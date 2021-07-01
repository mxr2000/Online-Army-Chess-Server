package com.se.chess.chessserver.service;

import com.se.chess.chessserver.pojo.UserInfo;

public interface UserService {
    UserInfo signUp(String username, String password);
    UserInfo login(String username, String password);
}
