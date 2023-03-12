package cn.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient//将服务加入微服务中
@EnableFeignClients//调用接口
public class LeyouSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouSearchApplication.class);
    }
}
