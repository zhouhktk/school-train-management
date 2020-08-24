package com.st.service;

import com.st.dao.RoomDao;
import com.st.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : zhoufeng
 * @date : 2020/8/19
 */
@Transactional(readOnly = true)
@Service("roomService")
public class RoomService {
    @Autowired
    private RoomDao roomDao;

    @Transactional(readOnly = false)
    public int deleteByNumber(String number) {
        return roomDao.deleteByNumber(number);
    }

    @Transactional(readOnly = false)
    public int insert(Room record) {
        return roomDao.insert(record);
    }

    public Room selectByNumber(String number) {
        return roomDao.selectByNumber(number);
    }

    @Transactional(readOnly = false)
    public int update(Room record) {
        return roomDao.update(record);
    }

    public List<Room> pageQuery(Integer page, Integer limit) {
        return roomDao.pageQuery(page, limit);
    }

    public int getRoomNums() {
        return roomDao.getRoomNums();
    }

    public List<Room> getAll() {
        return roomDao.getAll();
    }
}
