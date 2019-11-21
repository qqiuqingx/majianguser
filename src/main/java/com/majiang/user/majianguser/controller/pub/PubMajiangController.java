package com.majiang.user.majianguser.controller.pub;

import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.controller.MajiangController;
import com.majiang.user.majianguser.service.MajiangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/pub")
public class PubMajiangController {
    @Autowired
    MajiangService majiangService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PubMajiangController.class);



    @RequestMapping(value = "/getAllMajiang",method = RequestMethod.GET)
    @ResponseBody
    public MajiangVo getAllmajiang(){
        LOGGER.warn("PubMajiangController.getAllmajiang>>>>>>>>>>>>>>>>>>>>>>>.");
        return majiangService.getAllmajiang();
    }

}
