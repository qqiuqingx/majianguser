package com.majiang.user.majianguser.service;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;

import java.util.List;

public interface MajiangService {
    List<majiangBean> getAllmajiang();

    Integer addMajiang(majiangBean majiang);

    List<MajiangUserBean> getAllMajiangUserBean();

    Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean);
}
