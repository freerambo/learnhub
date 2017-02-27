package com.erian.spring.boot.grid.test;


public class Test {
	
	
 public static void main1(String[] args) throws InterruptedException{
	 
	
	 
//	String result = SocketConnection.createSocketConnection("192.168.127.110", 2101, "output:coup?\n");
//		String result = SocketConnection.createSocketConnection("192.168.127.121", 4001, "MEAS:VOLT?;CURR?;POW?;RES?;:LOAD?;:MODE?\n");

//	 MEAS:VOLT?;CURR?;POW?;FREQ?;:MEAS:POW:APP?;
//	 :MEAS:CURR:AMPL:MAX?;:MEAS:POW:PFAC?;:VOLT:RANG?;:CURR?;:VOLT?;:MODE?;:OUTP?\n
//		String result = SocketConnection.createSocketConnection("192.168.127.110", 2101, "MEAS:VOLT:AC?;:VOLT:DC?;:MEAS:pow:AC?;:MEAS:curr:AC?;:freq?\n");
		/*
		CURRent:AC?:DC?
		: ACDC?
		: AMPLitude:MAXimum?
		: CREStfactor?
		: INRush?
*/

	 int freq = 60;
//	 String command ="FREQuency:LIMit " + freq +";:FREQuency:LIMit? ";
//	 trig:state?\n
	 
//	 String command ="volt:ac 180;:volt:ac? ";
	 
//		String result = SocketConnection.createSocketConnection("192.168.127.110", 2101, command+"\n");

		
//		
	 
/*	 for(int i=0; i < 5; i++){
		 String result = SocketConnection.testCache("192.168.127.110", 2101, command+"\n");

		 System.out.println("output： " + result); 

	 }
	*/
 
	
 }

}
