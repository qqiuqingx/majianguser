package com.majiang.user.majianguser;

import com.majiang.user.majianguser.bean.majiangBean;
import com.majiang.user.majianguser.bean.vo.MajiangVo;
import com.majiang.user.majianguser.service.MajiangService;
import net.minidev.json.JSONObject;
import org.apache.http.Header;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;


import java.net.URI;
import java.util.List;
import java.util.Random;
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
    public void sd() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2000, 5000, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5000));
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 1; i < 100; i++) {
            UserTest userTest = new UserTest(countDownLatch);
            threadPoolExecutor.execute(userTest);

        }
        //计数器减一，所有线程释放，并发访问
        countDownLatch.countDown();
    }


    static class UserTest implements Runnable {
        CountDownLatch latch;

        public UserTest(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            Random random = new Random();
            int i = random.nextInt(5);
            System.out.println("要定的桌数为:"+i);
            try {
                if (i > 0 && i < 5) {
                    long star = System.currentTimeMillis();
                    System.out.println("请求开始");
                    httpGet("http://localhost:8080/user/majiang/buy", String.valueOf(i));
                    long end = System.currentTimeMillis();
                    System.out.println("请求耗时：" + (end - star));
                }
            }catch (Exception e){
                System.out.println("不可描述错误");
                e.printStackTrace();
            } 

        }


        static JSONObject httpGet(String url, String strp) {
            CloseableHttpClient aDefault = HttpClients.createDefault();

            JSONObject jsonObject = null;
            RequestConfig build = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

            try {
                if (null != strp) {
                    HttpGet httpGet = new HttpGet(url + "?majiangKeyID=" + strp);
                    httpGet.setConfig(build);
                    httpGet.setHeader("Cookie", "token=386bf699-436a-48c9-afa3-e038f10cc5a3;JSESSIONID=FEC10C0C31E80B22D1B8D5A7D84F89D9");
                    CloseableHttpResponse execute = aDefault.execute(httpGet);
                    if (execute.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                        String str = EntityUtils.toString(execute.getEntity(), "utf-8");
                        System.out.println("str:"+str);

                    }
                }


            } catch (Exception e) {

            }
            return null;
        }


    }


}
