package cn.leyou;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient  //开启eureka客户端
@SpringBootApplication
public class LyouUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyouUploadApplication.class,args);
    }
}
