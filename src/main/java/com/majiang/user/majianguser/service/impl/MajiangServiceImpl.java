package com.majiang.user.majianguser.service.impl;

import com.majiang.user.majianguser.bean.MajiangUserBean;
import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.mapper.majiangMapper;
import com.majiang.user.majianguser.service.MajiangService;
import com.majiang.user.majianguser.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MajiangServiceImpl implements MajiangService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MajiangServiceImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private majiangMapper majiangMapper;
    @Value("${majiang.redis.majiangs}")
    private String majiangs;
    @Override
    public List<majiangBean> getAllmajiang() {
        List<majiangBean> o=null;
        //查缓存
         o = (List)redisUtils.get(majiangs);
        if (o==null){
            o = majiangMapper.getAllmajiang();
        }
        return null;
    }

    @Override
    public Integer addMajiang(majiangBean majiang) {
        return null;
    }

    @Override
    public List<MajiangUserBean> getAllMajiangUserBean() {
        return null;
    }

    @Override
    public Integer addAllMajiangUserBean(MajiangUserBean majiangUserBean) {
        return null;
    }
}
