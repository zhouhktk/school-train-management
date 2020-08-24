package com.st.entity;

import java.io.Serializable;

/**
 * 管理员
 * admin
 * @author 
 */
public class Admin implements Serializable {
    private Integer id;

    private String number;

    private String password;

    private Integer status;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}