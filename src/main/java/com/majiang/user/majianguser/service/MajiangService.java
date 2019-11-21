package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import java.util.List;

public interface MajiangService {
    MajiangVo getAllmajiang();

    Integer addMajiang(majiangBean majiang);

    MajiangVo getAllMajiangUserBean();

    Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean);
    MajiangVo buyMajiang(String majiang, @CookieValue(required = false, value = "token") Cookie cookie);
}
