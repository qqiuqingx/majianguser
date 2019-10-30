package com.majiang.user.majianguser.exception;

import com.majiang.user.majianguser.enums.UserExceptionEnum;

public class MyShiroException extends majiangRunTimeException {
    public MyShiroException(UserExceptionEnum message) {
        super(message.getMessage(), message.getCode());
    }

}
