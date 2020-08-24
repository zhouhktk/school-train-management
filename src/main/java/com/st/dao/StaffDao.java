package com.st.dao;

import com.st.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffDao {
    int deleteByNumber(String number);

    int insert(Staff record);

    Staff selectByNumber(String number);

    int update(Staff record);

    List<Staff> pageQuery(@Param("page") int page, @Param("nums") int limit);

    int getStaffNums();

    List<Staff> getAll();

}