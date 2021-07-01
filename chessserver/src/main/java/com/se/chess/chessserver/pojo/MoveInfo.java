package com.se.chess.chessserver.pojo;

import lombok.Data;

@Data
public class MoveInfo {
    private String username;
    private String type;
    private int index;
    private int fromX;
    private int toX;
    private int fromY;
    private int toY;

    @Override
    public String toString() {
        return "MoveInfo{" +
                "username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", index=" + index +
                ", fromX=" + fromX +
                ", toX=" + toX +
                ", fromY=" + fromY +
                ", toY=" + toY +
                '}';
    }
}
