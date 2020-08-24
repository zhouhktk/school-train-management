package com.st.controller;

import com.st.data.Code;
import com.st.data.ResponseVo;
import com.st.entity.Appointment;
import com.st.entity.Room;
import com.st.service.AppointmentService;
import com.st.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author : zhoufeng
 * @date : 2020/8/20
 */
@RestController
@RequestMapping("/app")
@Scope("session")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private RoomService roomService;


    /**
     * 获取代办预约信息列表
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/gettodolist")
    public ResponseVo getTodDoList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        List<Appointment> appointments = appointmentService.toDoListPageQuery((page - 1) * limit, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("appList", appointments);
        map.put("count", appointmentService.getToDoNums());
        if (appointments.size() > 0) {
            return new ResponseVo(Code.SUCCESS, "获取代办预约信息列表成功！", map);
        } else {
            return new ResponseVo(Code.SUCCESS, "获取代办预约信息列表失败！", map);
        }
    }

    /**
     * 获取已经确认和过期的预约信息
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getfinishlist")
    public ResponseVo getFinishList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        List<Appointment> appointments = appointmentService.allowAndExpirePageQuery((page - 1) * limit, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("appList", appointments);
        map.put("count", appointmentService.getAllowAndExpireNums());
        if (appointments.size() > 0) {
            return new ResponseVo(Code.SUCCESS, "获取已经确认和过期的预约信息列表成功！", map);
        } else {
            return new ResponseVo(Code.SUCCESS, "获取已经确认和过期的预约信息列表失败！", map);
        }
    }

    /**
     * 修改预约的状态
     *
     * @param appointment
     * @return
     */
    @PostMapping("/updatestatus")
    public ResponseVo updateStatus(@RequestBody Appointment appointment) {
        int res = appointmentService.update(appointment);
        if (res != 0) {
            return new ResponseVo(Code.SUCCESS, "修改预约状态成功！", null);
        } else {
            return new ResponseVo(Code.FAILED, "修改预约状态失败！", null);
        }
    }

    /**
     * 删除预约信息
     * @param number
     * @return
     */
    @GetMapping("/delete")
    public ResponseVo delete(@RequestParam("number") String number){
        Appointment app = appointmentService.selectByNumber(number);
        if(app!=null){
            Room room = roomService.selectByNumber(app.getRoomNumber());
            room.setRestTimes(room.getRestTimes()+1);
            roomService.update(room);
        }
        int res = appointmentService.deleteByNumber(number);
        if (res != 0) {
            return new ResponseVo(Code.SUCCESS, "删除预约状态成功！", null);
        } else {
            return new ResponseVo(Code.FAILED, "删除预约状态失败！", null);
        }
    }

    /**
     * 根据员工编号和房间号查找订单
     * @param staffNumber
     * @param roomNumber
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getinfo")
    public ResponseVo multiSelectAppointment(@RequestParam(value = "staffNumber", defaultValue = "") String staffNumber,
                                             @RequestParam(value = "roomNumber", defaultValue = "") String roomNumber,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("limit") Integer limit){
        if ("".equals(staffNumber) && "".equals(roomNumber==null)){
            return new ResponseVo(Code.FAILED, "查询预约信息列表失败！", null);
        }
        List<Appointment> appointments = appointmentService.multiSelect(staffNumber, roomNumber, (page - 1) * limit, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("appList", appointments);
        map.put("count", appointmentService.getMultiSelectNums(staffNumber, roomNumber));
        if (appointments.size() > 0) {
            return new ResponseVo(Code.SUCCESS, "查询预约信息列表成功！", map);
        } else {
            return new ResponseVo(Code.SUCCESS, "查询预约信息列表失败！", map);
        }
    }

}
