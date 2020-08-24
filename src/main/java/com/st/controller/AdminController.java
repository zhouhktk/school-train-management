package com.st.controller;

import com.st.data.Code;
import com.st.data.ResponseVo;
import com.st.entity.Admin;
import com.st.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 管理员登录和注销
 * @author : zhoufeng
 * @date : 2020/8/17
 */
@RestController
@RequestMapping("/admin")
@Scope("session")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * RequestBody注解表示接受json数据，使用ajax则要使用该注解，
     * 如果是form表单传来的数据则需要去掉
     * @param admin
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ResponseVo login(@RequestBody Admin admin, HttpSession session){
        if (!"".equals(admin.getNumber())&&!"".equals(admin.getPassword())){
            Admin ad = adminService.findByNumber(admin.getNumber());
            if (ad!=null && ad.getPassword().equals(admin.getPassword())){
                session.setAttribute("admin", ad);
                return new ResponseVo(Code.SUCCESS, "登录成功", ad);
            }else {
                return new ResponseVo(Code.FAILED, "用户名或密码错误", null);
            }
        }else {
            return new ResponseVo(Code.FAILED, "用户名或密码错误", null);
        }
    }

    @GetMapping("/logout")
    public ResponseVo logout(HttpSession session){
        session.removeAttribute("admin");
        return new ResponseVo(Code.SUCCESS, "注销成功", null);
    }

}
