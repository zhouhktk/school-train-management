package com.st.service;

import com.st.dao.AppointmentDao;
import com.st.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : zhoufeng
 * @date : 2020/8/20
 */
@Transactional(readOnly = true)
@Service("appointmentService")
public class AppointmentService {
    @Autowired
    private AppointmentDao appointmentDao;

    @Transactional(readOnly = false)
    public int deleteByNumber(String number){
        return appointmentDao.deleteByNumber(number);
    }

    // int insert(Appointment record);

    // int insertSelective(Appointment record);

    @Transactional(readOnly = false)
    public int update(Appointment record){
        return appointmentDao.update(record);
    }

    public List<Appointment> toDoListPageQuery(Integer page, Integer nums){
        return appointmentDao.toDoListPageQuery(page, nums);
    }

    public List<Appointment> allowAndExpirePageQuery(Integer page, Integer nums){
        return appointmentDao.allowAndExpirePageQuery(page, nums);
    }

    public List<Appointment> multiSelect(String staffNumber, String roomNumber,
                                  Integer page, Integer nums){
        return appointmentDao.multiSelect(staffNumber, roomNumber, page, nums);
    }

    public int getToDoNums(){
        return appointmentDao.getToDoNums();
    }

    public int getAllowAndExpireNums(){
        return appointmentDao.getAllowAndExpireNums();
    }

    public int getMultiSelectNums(String staffNumber,String roomNumber){
        return appointmentDao.getMultiSelectNums(staffNumber, roomNumber);
    }


    public Appointment selectByNumber(String number){
        return appointmentDao.selectByNumber(number);
    }

    public List<Appointment> selectExpiredAppointment(){
        return appointmentDao.selectExpiredAppointment();
    }

}
