package com.se.chess.chessserver.service;

import com.se.chess.chessserver.pojo.LayoutInfo;

import java.util.List;

public interface LayoutService {
    String insertLayout(LayoutInfo layoutInfo);
    LayoutInfo getLayoutById(int id);
    List<LayoutInfo> getAllLayoutsByUsername(String username);
    void updateLayoutById(LayoutInfo layoutInfo);
}
