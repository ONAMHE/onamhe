package cn.leyou;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.swing.*;


@SpringBootApplication
@EnableDiscoveryClient//将对象注册到eureka的容器中
@EnableFeignClients//开启fenign的方法，可以更加优雅的调用接口函数
public class LeYouGoodsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeYouGoodsWebApplication.class);
    }
}
