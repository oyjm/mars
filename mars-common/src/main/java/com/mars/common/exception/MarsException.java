package com.mars.common.exception;

/**
 * 自定义异常类
 * @author oliver
 * @email jmouyang@hotmail.com
 * @date 2019年10月29日 下午16:56:19
 */
public class MarsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MarsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MarsException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MarsException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MarsException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    private String msg;

    private int code = 500;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}

