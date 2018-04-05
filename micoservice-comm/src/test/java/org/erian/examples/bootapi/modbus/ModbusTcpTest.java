package org.erian.examples.bootapi.modbus;

import java.net.InetAddress;

import org.erian.modules.jamod.util.ModbusTcpUtil;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class ModbusTcpTest {
   

//	@Test
	public void readTest(){ 
//		int i = ModbusTcpUtil.readData("172.21.76.119", 502, 0, 1, "F01");
//		System.out.println("F01 " + i);
//		i = ModbusTcpUtil.readData("172.21.76.119", 502, 22, 2, "F02");
//		System.out.println("F02 " + i);
//		i = ModbusTcpUtil.readData("172.21.76.119", 502, 33, 3, "F03");
//		System.out.println("F03 " + i);
//		i = ModbusTcpUtil.readData("172.21.76.119", 502, 44, 4, "F04");
//		System.out.println("F04 " + i);
	}
	
//	@Test
	public void writeTest(){ //  
    	ModbusTcpUtil.writeData("172.21.76.119", 502, 3, 30, 30, "F06");
    	ModbusTcpUtil.writeData("172.21.76.119", 502, 1, 2, 1, "F05");
	}
	
	
}
