package com.dj.myfile;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dj.myfile.mapper")
public class MyFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFileApplication.class, args);
	}

}
