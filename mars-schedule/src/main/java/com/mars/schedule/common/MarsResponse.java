package com.mars.schedule.common;

import java.util.HashMap;
import java.util.Map;

public class MarsResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public MarsResponse() {
        put("code", 0);
        put("msg", "success");
    }

    public static MarsResponse error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static MarsResponse error(String msg) {
        return error(500, msg);
    }

    public static MarsResponse error(int code, String msg) {
        MarsResponse r = new MarsResponse();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static MarsResponse ok(String msg) {
        MarsResponse r = new MarsResponse();
        r.put("msg", msg);
        return r;
    }

    public static MarsResponse ok(Map<String, Object> map) {
        MarsResponse r = new MarsResponse();
        r.putAll(map);
        return r;
    }

    public static MarsResponse ok() {
        return new MarsResponse();
    }

    @Override
    public MarsResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

