package com.majiang.user.majianguser.controller.exceptionAdvice;

import com.majiang.user.majianguser.exception.UserException;
import com.majiang.user.majianguser.exception.majiangRunTimeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler({majiangRunTimeException.class})
    public Map<String,Object> UserExceptionAdvice(HttpServletRequest request, HttpServletResponse response, majiangRunTimeException e ){
        System.out.println("进入自定义移仓处理器————————————————————————");
        HashMap<String, Object> map = new HashMap<>();
            map.put("message",e.getMessage());
            map.put("code",e.getCode());
        return map;
    }

}
