package cn.leyou.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 这个类是用来解决跨域问题的
 */
@Configuration
public class LeyouCorsConfigration{
    @Bean
    public CorsWebFilter corsWebFilter(){

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://manage.leyou.com");//允许跨域源不能是* 否者无法使用
        config.addAllowedOrigin("www.leyou.com");
        config.addAllowedMethod("*");//allow request method include( get post *)
        config.addAllowedHeader("*"); //允许跨域的请求头类型
        config.setMaxAge(1728000L);//一次请求后在这个时间里内可以不用请求直接跨域请求   一般使用默认的既可以了
        config.setAllowCredentials(true);//允许携带cookie信息
        configSource.registerCorsConfiguration("/**",config);
        return new CorsWebFilter(configSource);
    }

}