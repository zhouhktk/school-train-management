package com.st.controller;

import com.st.data.Code;
import com.st.data.ResponseVo;
import com.st.entity.Department;
import com.st.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 部门管理员
 * @author : zhoufeng
 * @date : 2020/8/18
 */
@RestController
@RequestMapping("/depart")
@Scope("session")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    /**
     * 查询某个员工信息
     * @param number
     * @return
     */
    @GetMapping("/getinfo")
    public ResponseVo getInfo(@RequestParam("number") String number){
        Department department = departmentService.selectByNumber(number);
        if (department!=null){
            return new ResponseVo(Code.SUCCESS, "查询部门信息成功！", department);
        }else {
            return new ResponseVo(Code.FAILED, "查询部门信息失败！", null);
        }
    }

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getlist")
    public ResponseVo getlist(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        List<Department> departments = departmentService.pageQuery((page - 1) * limit, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("departList", departments);
        map.put("count", departmentService.getDepartNums());
        if (departments.size()>0){
            return new ResponseVo(Code.SUCCESS, "获取部门信息列表成功！", map);
        }else{
            return new ResponseVo(Code.SUCCESS, "获取部门信息列表失败！", map);
        }
    }

    /**
     * 添加部门信息
     * @param department
     * @return
     */
    @PostMapping("/insert")
    public ResponseVo insert(@RequestBody Department department){
        Department de = null;
        if (department.getNumber()!=null&&department.getDepartName()!=null){
            de = departmentService.selectByNumber(department.getNumber());
        }else {
            return new ResponseVo(Code.FAILED, "添加部门信息失败！", null);
        }

        if (de!=null){
            return new ResponseVo(Code.FAILED, "该部门信息已存在！", null);
        }

        int res = departmentService.insert(department);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "添加部门信息成功！", null);
        }else{
            return new ResponseVo(Code.FAILED, "添加部门信息失败！", null);
        }
    }

    /**
     * 修改部门信息
     * @param department
     * @return
     */
    @PostMapping("/update")
    public ResponseVo update(@RequestBody Department department){
        int res = departmentService.update(department);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "修改部门信息成功！", null);
        }else {
            return new ResponseVo(Code.FAILED, "修改部门信息失败！", null);
        }
    }

    /**
     * 删除部门
     * @param number
     * @return
     */
    @GetMapping("/delete")
    public ResponseVo delete(@RequestParam("number") String number){
        Department depart = departmentService.selectByNumber(number);
        depart.setStatus(1);
        int res = departmentService.update(depart);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "删除部门信息成功！", null);
        }else {
            return new ResponseVo(Code.FAILED, "删除部门信息失败！", null);
        }
    }

    /**
     * 获取所有部门
     * @return
     */
    @GetMapping("/getall")
    public ResponseVo getAll(){
        List<Department> departments = departmentService.getAll();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("departList", departments);
        map.put("count", departments.size());
        if (departments.size()>0){
            return new ResponseVo(Code.SUCCESS, "获取所有部门信息成功！", map);
        }else{
            return new ResponseVo(Code.SUCCESS, "获取所有部门信息失败！", map);
        }
    }
}
