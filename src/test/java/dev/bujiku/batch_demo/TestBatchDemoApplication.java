package dev.bujiku.batch_demo;

import org.springframework.boot.SpringApplication;

public class TestBatchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(BatchDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
