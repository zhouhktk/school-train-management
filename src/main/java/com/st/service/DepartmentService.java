package com.st.service;

import com.st.dao.DepartmentDao;
import com.st.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : zhoufeng
 * @date : 2020/8/18
 */
@Transactional(readOnly = true)
@Service("departmentService")
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    @Transactional(readOnly = false)
    public int deleteByNumber(String number) {
        return departmentDao.deleteByNumber(number);
    }

    @Transactional(readOnly = false)
    public int insert(Department record) {
        return departmentDao.insert(record);
    }

    public Department selectByNumber(String number) {
        return departmentDao.selectByNumber(number);
    }

    @Transactional(readOnly = false)
    public int update(Department record) {
        return departmentDao.update(record);
    }

    public List<Department> pageQuery(Integer page, Integer nums) {
        return departmentDao.pageQuery(page, nums);
    }

    public Department selectByDepartName(String departName) {
        return departmentDao.selectByDepartName(departName);
    }

    public int getDepartNums() {
        return departmentDao.getDepartNums();
    }


    public List<Department> getAll() {
        return departmentDao.getAll();
    }


}
