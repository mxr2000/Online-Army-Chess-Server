package com.se.chess.chessserver.mapper;

import com.se.chess.chessserver.pojo.LayoutInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LayoutMapper {
    public List<LayoutInfo> getAllLayoutsByUsername(String username);
    public int insertLayout(LayoutInfo layoutInfo);
    public LayoutInfo getLayoutById(int id);
    public int updateLayout(LayoutInfo layoutInfo);
}
