package com.st.controller;

import com.st.data.BigStaff;
import com.st.data.Code;
import com.st.data.ResponseVo;
import com.st.entity.Department;
import com.st.entity.Staff;
import com.st.service.DepartmentService;
import com.st.service.StaffService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 员工控制器
 * @author : zhoufeng
 * @date : 2020/8/18
 */
@RestController
@RequestMapping("/staff")
@Scope("session")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取单个员工的信息
     * @param number
     * @return
     */
    @GetMapping("/getinfo")
    public ResponseVo getinfo(@RequestParam("number") String number){
        Staff staff = staffService.selectByNumber(number);
        if (staff!=null){
            String departName = departmentService.selectByNumber(staff.getDepartNumber()).getDepartName();
            BigStaff bigStaff = new BigStaff();
            bigStaff.setDepartName(departName);
            BeanUtils.copyProperties(staff, bigStaff);
            return new ResponseVo(Code.SUCCESS, "查询员工信息成功！", bigStaff);
        }else {
            return new ResponseVo(Code.NOTFOUND, "未找到该员工信息！", null);
        }
    }

    /**
     * 获取员工列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getlist")
    public ResponseVo getlist(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        List<Staff> staffList = staffService.pageQuery((page-1)*limit, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("staffList", addDepartNameValue(staffList));
        map.put("count", staffService.getStaffNums());
        if (staffList.size()>0){
            return new ResponseVo(Code.SUCCESS, "获取员工信息列表成功！", map);
        }else{
            return new ResponseVo(Code.SUCCESS, "获取员工信息列表失败！", map);
        }
    }

    /**
     * 添加员工，使用MD5对密码进行加密，
     * 判断departNumber是否存在
     * @param bigstaff
     * @return
     */
    @PostMapping("/insert")
    public ResponseVo insert(@RequestBody BigStaff bigstaff){
        if (bigstaff.getNumber()!=null&&bigstaff.getPassword()!=null
                &&bigstaff.getDepartName()!=null){
            Department dep = departmentService.selectByDepartName(bigstaff.getDepartName());
            if (dep!=null){
                String hex = DigestUtils.md5DigestAsHex(bigstaff.getPassword().getBytes());
                bigstaff.setPassword(hex);
                bigstaff.setDepartNumber(dep.getNumber());
            }else {
                return new ResponseVo(Code.FAILED, "该部门不存在！", null);
            }

        }else{
            return new ResponseVo(Code.FAILED, "添加员工信息失败！", null);
        }
        if (staffService.selectByNumber(bigstaff.getNumber())!=null){
            return new ResponseVo(Code.FAILED, "该员工编号已存在！", null);
        }


        Staff staff = new Staff();
        BeanUtils.copyProperties(bigstaff, staff, "departName");
        int res = staffService.insert(staff);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "添加员工信息成功！", null);
        }else{
            return new ResponseVo(Code.FAILED, "添加员工信息失败！", null);
        }
    }

    /**
     * 修改员工信息
     * @param bigstaff
     * @return
     */
    @PostMapping("/update")
    public ResponseVo update(@RequestBody BigStaff bigstaff){
        if (bigstaff.getDepartName()!=null){
            Department dep = departmentService.selectByDepartName(bigstaff.getDepartName());
            if (dep==null){
                return new ResponseVo(Code.FAILED, "所输入部门信息不存在！", null);
            }else{
                bigstaff.setDepartNumber(dep.getNumber());
            }
        }
        //密码不变问题
        String orgpass = staffService.selectByNumber(bigstaff.getNumber()).getPassword();
        if (bigstaff.getPassword()!=null&&!"".equals(bigstaff.getPassword())&&!orgpass.equals(bigstaff.getPassword())){
            String hex = DigestUtils.md5DigestAsHex(bigstaff.getPassword().getBytes());
            bigstaff.setPassword(hex);
        }
        Staff staff = new Staff();
        BeanUtils.copyProperties(bigstaff, staff, "departName");
        int res = staffService.update(staff);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "修改员工信息成功！", null);
        }else {
            return new ResponseVo(Code.FAILED, "修改员工信息失败！", null);
        }
    }

    /**
     * 进行软删除，把状态标记为2
     * @param number
     * @return
     */
    @GetMapping("/delete")
    public ResponseVo delete(@RequestParam("number") String number){
        Staff staff = staffService.selectByNumber(number);
        staff.setStatus(2);
        int res = staffService.update(staff);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "删除员工信息成功！", null);
        }else {
            return new ResponseVo(Code.FAILED, "删除员工信息失败！", null);
        }
    }

    /**
     * 获取所有员工
     * @return
     */
    @GetMapping("/getall")
    public ResponseVo getAll(){
        List<Staff> staffs = staffService.getAll();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("staffList", staffs);
        map.put("count", staffs.size());
        if (staffs.size()>0){
            return new ResponseVo(Code.SUCCESS, "获取所有员工信息成功！", map);
        }else{
            return new ResponseVo(Code.SUCCESS, "获取所有员工信息失败！", map);
        }

    }



    /**
     * 将Staff对象的信息复制到BigStaff对象中
     * @param list
     * @return
     */
    private List<BigStaff> addDepartNameValue(List<Staff> list){
        List<BigStaff> bs = new ArrayList<>();
        for (Staff staff : list) {
            BigStaff bigStaff = new BigStaff();
            BeanUtils.copyProperties(staff, bigStaff);
            String departName = departmentService.selectByNumber(staff.getDepartNumber()).getDepartName();
            bigStaff.setDepartName(departName);
            bs.add(bigStaff);
        }
        return bs;
    }

}
