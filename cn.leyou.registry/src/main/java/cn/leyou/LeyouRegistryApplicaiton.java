package cn.leyou;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer   //声明这是一个eureka的服务器
public class LeyouRegistryApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(LeyouRegistryApplicaiton.class,args);
    }
}
