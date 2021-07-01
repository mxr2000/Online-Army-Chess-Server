package com.se.chess.chessserver.mapper;

import com.se.chess.chessserver.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(UserInfo userInfo);
    UserInfo getUserByUsernameAndPassword(String username, String password);
    UserInfo getUserByUsername(String username);
}
