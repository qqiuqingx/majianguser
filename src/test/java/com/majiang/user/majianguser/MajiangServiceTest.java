package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.service.MajiangService;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.net.URI;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MajiangServiceTest {

    @Autowired
    MajiangService majiangService;

    @Test
    public void getAll() {
        MajiangVo allmajiang = majiangService.getAllmajiang();
        System.out.println(allmajiang);
    }

    @Test
    public void sd(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2000, 5000, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5000));
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i=1;i<100;i++){

        }
        countDownLatch.countDown();
    }


    static class UserTest implements Runnable{
        CountDownLatch latch;

        public UserTest(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {

        }


        static JSONObject httpGet(String url,String strp){
            CloseableHttpClient aDefault = HttpClients.createDefault();

            JSONObject jsonObject=null;
            RequestConfig build = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

            try{
                if (null!=strp){
                     HttpGet httpGet = new HttpGet(url+"?majiangKeyID="+strp);
                      httpGet.setConfig(build);
                    httpGet.setHeader("Cookie","token=85728783-ec97-40b6-b356-b3bead612f15;JSESSIONID=2242ABD361E13DC9EA97F39AFCF6DE7E");
                    CloseableHttpResponse execute = aDefault.execute(httpGet);
                    
                }



            }catch (Exception e){

            }
            return null;
        }


    }




}
