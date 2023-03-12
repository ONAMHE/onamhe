package cn.leyou.upload.service;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * 这个类是用来解决跨域问题的
 */
@Configuration
public class FastdfsCorsConfiguration {
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://manage.leyou.com");//允许跨域源不能是* 否者无法使用
        config.addAllowedMethod("*");//allow request method include( get post *)
        config.addAllowedHeader("*"); //允许跨域的请求头类型
        config.setMaxAge(1728000L);//一次请求后在这个时间里内可以不用请求直接跨域请求   一般使用默认的既可以了
        config.setAllowCredentials(true);//允许携带cookie信息
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);//拦截所有的路径
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);//当有多个filter过滤器的时候要加这个来表明这个优先
        return bean;
    }

}