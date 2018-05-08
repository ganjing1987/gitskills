package com.example.demo.Event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MyTest {

	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring/application-database.xml");
		MyPublisher myPublisher = (MyPublisher)context.getBean("myPublisher");
		myPublisher.publishEvent(new MyEvent("1"));
	}
	
}
