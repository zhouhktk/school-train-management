package com.st.schedule;

import com.st.entity.Appointment;
import com.st.entity.Room;
import com.st.service.AppointmentService;
import com.st.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务
 * @author : zhoufeng
 * @date : 2020/8/22
 */
@Component
public class ScheduledService {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private RoomService roomService;


    /**
     * 定时检查预约信息是否过期，
     * 如果过期则将预约状态信息改为过期
     */
    // @Scheduled(cron = "0/10 * * * * ? ")
    @Async
    @Scheduled(cron = "0 0/15 * * * ?")
    public void checkAppointmentExpiredDate(){
        List<Appointment> apps = appointmentService.selectExpiredAppointment();
        if (apps.size()!=0){
            for (Appointment app : apps) {
                Room room = roomService.selectByNumber(app.getRoomNumber());
                if (room!=null){
                    room.setRestTimes(room.getRestTimes()+1);
                    roomService.update(room);
                }
                app.setStatus(2);
                appointmentService.update(app);
            }
        }
    }

}
