package com.erian.spring.boot.grid.test;

import com.erian.spring.boot.grid.service.SocketConnection;

public class GridTest {
	
	public static void main1(String[] args){
		
//		String command = "SYST:ERR?\n";

		//Set values
		String command = "sour:freq 62;:volt:ac 138;:volt:lim:ac 198;:POW:prot 8488;:curr 27;sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr:lim?;:outp:rel?\n";

		
		//		sour:freq 58;:volt:ac 125;:volt:lim:ac 170;:POW:prot 7000;:curr 25;

//	 	String setCommand = "sour:freq 58;:volt:ac 121;:volt:lim:ac 180;:POW:prot 8200;:curr 29;\n";

//		SocketConnection.setDevice("192.168.127.110", 2101, setCommand);
		String result = SocketConnection.callDevice("192.168.127.110", 2101, command);

		System.out.println("output： " + result); 

	}

}
