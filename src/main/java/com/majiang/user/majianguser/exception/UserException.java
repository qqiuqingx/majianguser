package com.majiang.user.majianguser.exception;

import com.majiang.user.majianguser.enums.UserExceptionEnum;

public class UserException extends majiangRunTimeException {
    public UserException(UserExceptionEnum userInserException) {
        super(userInserException.getMessage(),userInserException.getCode());
    }


}
