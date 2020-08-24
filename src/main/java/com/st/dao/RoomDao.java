package com.st.dao;

import com.st.entity.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomDao {
    int deleteByNumber(String number);

    int insert(Room record);

    Room selectByNumber(String number);

    int update(Room record);

    List<Room> pageQuery(@Param("page") Integer page, @Param("nums") Integer nums);

    int getRoomNums();

    List<Room> getAll();

}