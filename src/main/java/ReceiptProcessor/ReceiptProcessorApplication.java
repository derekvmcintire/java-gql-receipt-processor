package ReceiptProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReceiptProcessorApplication {

	public static void main(String[] args) {
		System.out.println("Starting ReceiptProcessor application...");

		SpringApplication.run(ReceiptProcessorApplication.class, args);

		System.out.println("ReceiptProcessor application started successfully.");
	}
}
