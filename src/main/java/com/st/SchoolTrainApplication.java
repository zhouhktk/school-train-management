package com.st;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan("com.st.dao")
@SpringBootApplication
public class SchoolTrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolTrainApplication.class, args);
    }

}
