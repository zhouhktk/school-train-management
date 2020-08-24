package com.st.dao;

import com.st.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentDao {
    int deleteByNumber(String number);

    int insert(Department record);

    Department selectByNumber(String number);

    int update(Department record);

    List<Department> pageQuery(@Param("page") Integer page, @Param("nums") Integer nums);

    Department selectByDepartName(String departName);

    int getDepartNums();

    List<Department> getAll();

}