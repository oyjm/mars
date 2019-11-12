package com.mars.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.mars.schedule.mapper"})
public class MarsScheduleApplication{
    public static void main(String[] args) {
        SpringApplication.run(MarsScheduleApplication.class, args);
    }
}
