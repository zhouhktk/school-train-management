package com.st.dao;

import com.st.entity.Admin;


public interface AdminDao {

    Admin findByNumber(String number);

}