package com.st.interceptor;

import com.st.data.Code;
import com.st.exception.AdminException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义拦截器
 * @author : zhoufeng
 * @date : 2020/8/17
 */
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("admin");
        if (admin!=null){
            return true;
        }
        //未登录时抛出异常，交由自定义异常处理程序处理
        throw new AdminException(Code.NOT_LOGIN, "未登录");
    }
}
