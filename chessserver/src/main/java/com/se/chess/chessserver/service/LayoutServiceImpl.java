package com.se.chess.chessserver.service;

import com.se.chess.chessserver.mapper.LayoutMapper;
import com.se.chess.chessserver.pojo.LayoutInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LayoutServiceImpl implements LayoutService{
    @Autowired
    private LayoutMapper layoutMapper;
    @Override
    public String insertLayout(LayoutInfo layoutInfo) {
        layoutMapper.insertLayout(layoutInfo);
        return layoutInfo.getId();
    }

    @Override
    public LayoutInfo getLayoutById(int id) {
        LayoutInfo layout = layoutMapper.getLayoutById(id);
        System.out.println(layout);
        return layout;
    }

    @Override
    public List<LayoutInfo> getAllLayoutsByUsername(String username) {
        return layoutMapper.getAllLayoutsByUsername(username);
    }

    @Override
    public void updateLayoutById(LayoutInfo layoutInfo) {
        layoutMapper.updateLayout(layoutInfo);
    }
}
