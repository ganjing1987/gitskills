package com.example.demo.Event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

public class MyPublisher implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	
	public void publishEvent(ApplicationEvent event){
		System.out.println("into My	Publisher's method");
		applicationContext.publishEvent(event);
	}
		
	
}
