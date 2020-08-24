package com.st.service;

import com.st.dao.StaffDao;
import com.st.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工管理
 *
 * @author : zhoufeng
 * @date : 2020/8/18
 */
@Transactional(readOnly = true)
@Service("staffService")
public class StaffService {
    @Autowired
    private StaffDao staffDao;

    @Transactional(readOnly = false)
    public int deleteByNumber(String number) {
        return staffDao.deleteByNumber(number);
    }

    @Transactional(readOnly = false)
    public int insert(Staff record) {
        return staffDao.insert(record);
    }

    public Staff selectByNumber(String number) {
        return staffDao.selectByNumber(number);
    }

    @Transactional(readOnly = false)
    public int update(Staff record) {
        return staffDao.update(record);
    }

    public List<Staff> pageQuery(int page, int limit) {
        return staffDao.pageQuery(page, limit);
    }

    public int getStaffNums() {
        return staffDao.getStaffNums();
    }

    public List<Staff> getAll() {
        return staffDao.getAll();
    }

}
