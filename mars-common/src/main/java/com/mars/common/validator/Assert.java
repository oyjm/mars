package com.mars.common.validator;

import com.mars.common.exception.MarsException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new MarsException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new MarsException(message);
        }
    }
}