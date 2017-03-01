package com.erian.spring.boot.grid.test;

import com.erian.spring.boot.grid.service.SocketConnection;

public class TestGrid {
	
	
 public static void main1(String[] args) throws InterruptedException{
	
	 
//	 ;:SYST:ERR?;:outp:relay?
//	 	String command = "outp:rel?;:sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr?;\n";
		
//	 String command = "SOUR:freq?\n";
//	 String result = SocketConnection.createSocketConnection("192.168.127.110", 2101, command);

	 String result =  "";
	
		String off = "outp off;outp?\n";
	 	
	 	result = SocketConnection.callDevice("192.168.127.110", 2101, off);
		System.out.println("output： " + result); 
	 
//		String set = "sour:freq 50;:volt:ac 115;:volt:lim:ac 150;:POW:prot 8000;:curr 20;sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr:lim?;:outp?\n";

		String set = "sour:freq 50;:volt:ac 115;:volt:lim:ac 150;:POW:prot 8000;:curr 20;sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr:lim?;:outp?\n";
		
//	 	String command = "meas:volt:ac?;:meas:curr:ac?;:meas:pow:ac?;:meas:freq?\n";
	 	
	 	 result = SocketConnection.callDevice("192.168.127.110", 2101, set);


		System.out.println("output： " + result); 
		
		 /**
		  * set of/off
		  */
		 	String init = "outp on;outp?\n";
		 	
		 	result = SocketConnection.callDevice("192.168.127.110", 2101, init);
			System.out.println("output： " + result); 
//			
//			
		 	String command = "meas:volt:ac?;:meas:curr:ac?;:meas:pow:ac?;:meas:freq?\n";

		 	result = SocketConnection.callDevice("192.168.127.110", 2101, command);
			System.out.println("output： " + result); 
			


		
//	 	String set = "outp:rel?;:sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr?;\n";

//	 	String set = "sour:freq 56;:volt:ac 121;:volt:lim:ac 180;:POW:prot 7000;:curr 29;\n";
//	 	:outp:rel?;:sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr?;\n";

//	 	result = SocketConnection.callDevice("192.168.127.110", 2101, set);

	 	
//		System.out.println("output： " + result); 
		
		
		
		
		
 }

}
