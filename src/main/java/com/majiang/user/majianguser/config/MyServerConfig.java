package com.majiang.user.majianguser.config;

import com.majiang.user.majianguser.runner.MyCommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Configuration
public class MyServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerConfig.class);
    private static int serverport;
    public static String getUrl(){
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            LOGGER.error("错误:",e);
        }
        return "http://"+localHost.getHostAddress()+":"+serverport;
    }
    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        serverport=webServerInitializedEvent.getWebServer().getPort();
    }
}
