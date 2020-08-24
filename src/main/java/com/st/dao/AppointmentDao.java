package com.st.dao;

import com.st.entity.Appointment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppointmentDao {
    int deleteByNumber(String number);

    // int insert(Appointment record);

    // int insertSelective(Appointment record);

    int update(Appointment record);

    List<Appointment> toDoListPageQuery(@Param("page") Integer page, @Param("nums") Integer nums);
    List<Appointment> allowAndExpirePageQuery(@Param("page") Integer page, @Param("nums") Integer nums);

    List<Appointment> multiSelect(@Param("staffNumber") String staffNumber, @Param("roomNumber") String roomNumber,
                                  @Param("page") Integer page, @Param("nums") Integer nums);

    int getToDoNums();
    int getAllowAndExpireNums();
    int getMultiSelectNums(@Param("staffNumber") String staffNumber, @Param("roomNumber") String roomNumber);

    Appointment selectByNumber(String number);

    List<Appointment> selectExpiredAppointment();
}