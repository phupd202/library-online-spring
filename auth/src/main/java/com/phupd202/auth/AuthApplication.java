package com.phupd202.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args); // hàm run sẽ return 1 một đối tượng ApplicationContext--> đại diện cho Spring IoC
	}

}
