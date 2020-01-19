package com.majiang.user.majianguser.mapper;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface majiangMapper {

    List<majiangBean> getAllmajiang();

    majiangBean getMajiang(Integer KeyID);

    Integer addMajiang(majiangBean majiang);

    List<MajiangUserBean> getAllMajiangUserBean(String userPhone);

    List<MajiangUserBean> getMUByKeyIDandUserPhone(@Param("majiangKeyID") String majiangKeyID, @Param("userPhone") String UserPhone);

    Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean);

    Integer updateMajiang(majiangBean majiangBean);

    MajiangUserBean getOrderByOrderID(String OrderID);

    Integer upOrder(MajiangUserBean majiangUserBean);

}
