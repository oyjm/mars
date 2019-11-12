package com.mars.schedule.bean;

import java.io.Serializable;

public class SysScheduleInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String taskName;    //任务名称
    private String taskGroup;   //任务组名称
    private String cron;        //表达式
    private String className;   //执行Job类
    private String methodName;
    private String params;      //参数  多个用逗号分隔
    private int isStart;        //是否启动  0否  1启动
    private int status;         //是否删除  0正常  1删除
    private String desc;        //任务描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getIsStart() {
        return isStart;
    }

    public void setIsStart(int isStart) {
        this.isStart = isStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
