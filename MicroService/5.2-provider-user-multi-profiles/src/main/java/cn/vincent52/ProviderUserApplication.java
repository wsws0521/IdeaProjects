package cn.vincent52;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient  // 4.5 这是spring-cloud-common项目中的高度抽象注解，可同时支持Eureka、Zookeeper、Consul等
//@EnableEurekaClient   // 4.5 这是spring-cloud-netflix项目中的注解，只能配合classpath中的Eureka一起工作
@SpringBootApplication
public class ProviderUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }
}
