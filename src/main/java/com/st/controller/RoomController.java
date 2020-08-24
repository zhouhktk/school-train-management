package com.st.controller;

import com.st.data.Code;
import com.st.data.ResponseVo;
import com.st.entity.Room;
import com.st.service.RoomService;
import com.st.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zhoufeng
 * @date : 2020/8/19
 */
@RestController
@RequestMapping("/room")
@Scope("session")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private StaffService staffService;

    /**
     * 获取单个房间信息
     * @param number
     * @return
     */
    @GetMapping("/getinfo")
    public ResponseVo getInfo(@RequestParam("number") String number){
        Room room = roomService.selectByNumber(number);
        if (room!=null){
            return new ResponseVo(Code.SUCCESS, "查询房间信息成功！", room);
        }else {
            return new ResponseVo(Code.FAILED, "查询房间信息失败！", null);
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
        List<Room> rooms = roomService.pageQuery((page - 1) * limit, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("roomList", rooms);
        map.put("count", roomService.getRoomNums());
        if (rooms.size()>0){
            return new ResponseVo(Code.SUCCESS, "获取部门信息列表成功！", map);
        }else{
            return new ResponseVo(Code.SUCCESS, "获取部门信息列表失败！", map);
        }
    }

    /**
     * 添加房间信息
     * @param room
     * @return
     */
    @PostMapping("/insert")
    public ResponseVo insert(@RequestBody Room room){
        Room ro = null;
        if (room.getNumber()!=null&&room.getStaffNumber()!=null){
            if (staffService.selectByNumber(room.getStaffNumber())==null){
                return new ResponseVo(Code.FAILED, "所输入员工信息不存在！", null);
            }
            ro=roomService.selectByNumber(room.getNumber());
        }else {
            return new ResponseVo(Code.FAILED, "添加房间信息失败！", null);
        }

        if (ro!=null){
            return new ResponseVo(Code.FAILED, "该房间信息已存在！", null);
        }

        int res = roomService.insert(room);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "添加房间信息成功！", null);
        }else{
            return new ResponseVo(Code.FAILED, "添加房间信息失败！", null);
        }
    }

    /**
     * 修改房间信息
     * @param room
     * @return
     */
    @PostMapping("/update")
    public ResponseVo update(@RequestBody Room room){
        int res = roomService.update(room);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "修改房间信息成功！", null);
        }else {
            return new ResponseVo(Code.FAILED, "修改房间信息失败！", null);
        }
    }


    /**
     * 删除房间
     * @param number
     * @return
     */
    @GetMapping("/delete")
    public ResponseVo delete(@RequestParam("number") String number){
        Room room = roomService.selectByNumber(number);
        room.setStatus(1);
        int res = roomService.update(room);
        if (res!=0){
            return new ResponseVo(Code.SUCCESS, "删除房间信息成功！", null);
        }else {
            return new ResponseVo(Code.FAILED, "删除房间信息失败！", null);
        }
    }

    /**
     * 获取所有房间
     * @return
     */
    @GetMapping("/getall")
    public ResponseVo getAll(){
        List<Room> rooms = roomService.getAll();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("roomList", rooms);
        map.put("count", rooms.size());
        if (rooms.size()>0){
            return new ResponseVo(Code.SUCCESS, "获取所有房间信息成功！", map);
        }else{
            return new ResponseVo(Code.SUCCESS, "获取所有房间信息失败！", map);
        }
    }
}
