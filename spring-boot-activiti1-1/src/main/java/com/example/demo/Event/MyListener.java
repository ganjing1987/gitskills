package com.example.demo.Event;

import org.springframework.context.ApplicationListener;

public class MyListener implements  ApplicationListener<MyEvent>{


	@Override
	public void onApplicationEvent(MyEvent event) {
         if(event instanceof MyEvent){
        	 System.out.println("into My Listener");
        	 MyEvent myEvent = (MyEvent)event;
        	 myEvent.print();
         }		
	}

	

}
