package com.st.service;

import com.st.dao.AdminDao;
import com.st.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : zhoufeng
 * @date : 2020/8/17
 */
@Transactional(readOnly = true)
@Service("adminService")
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public Admin findByNumber(String number){
        return adminDao.findByNumber(number);
    }
}
