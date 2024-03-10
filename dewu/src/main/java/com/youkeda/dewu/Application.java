package com.youkeda.dewu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //表明这是一个Spring Boot应用程序的入口类，并且会自动扫描当前包和子包中的组件
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);  //传入应用程序的主配置类，args是命令行参数
	}

}
