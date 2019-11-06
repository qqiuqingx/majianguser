package com.majiang.user.majianguser.mapper;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface majiangMapper {

    List<majiangBean> getAllmajiang();

    Integer addMajiang(majiangBean majiang);

    List<MajiangUserBean> getAllMajiangUserBean();

    Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean);
}
