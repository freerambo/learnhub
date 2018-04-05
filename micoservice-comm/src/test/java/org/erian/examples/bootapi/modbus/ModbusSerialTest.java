package org.erian.examples.bootapi.modbus;

import java.util.List;

import org.erian.modules.jamod.model.ReadParams;
import org.junit.Test;

import org.erian.modules.jamod.model.SerialParams;
import org.erian.modules.jamod.util.ModbusSerialUtil;


/**
 * Unit test for simple App.
 */
public class ModbusSerialTest {
   
	
//	@Test
	public void inputRegistersTest(){ //  
		
		
		SerialParams sParams = new SerialParams("COM11", 9600, 8, 1, "even","rtu");

		ReadParams request = new ReadParams(1,2,1);
		
		List<Integer> results = ModbusSerialUtil.readInputRegisters(sParams, request);
		
		System.out.println(results.get(0));
		
	}
	
//	@Test
	public void readRegister(){ //  
		
		
		SerialParams sParams = new SerialParams("COM11", 9600, 8, 1, "even","rtu");

		ReadParams request = new ReadParams(3,102,1);
		
		List<Integer> results = ModbusSerialUtil.readRegisters(sParams, request);
		
		System.out.println(results.get(0));
		
	}
	
//	@Test
	public void readCoils(){ //  
		
		
		SerialParams sParams = new SerialParams("COM11", 9600, 8, 1, "even","rtu");

		ReadParams request = new ReadParams(3,102,1);
		
		List<Integer> results = ModbusSerialUtil.readCoils(sParams, request);
		
		System.out.println(results.get(0)+" - "+ results.get(1));
		
	}
	
	
	@Test
	public void readDiscretesInputs(){   
		
		
		SerialParams sParams = new SerialParams("COM11", 9600, 8, 1, "even","rtu");

		ReadParams request = new ReadParams(5,500,3);
		
		List<Integer> results = ModbusSerialUtil.readDiscretesInputs(sParams, request);
		
		System.out.println(results.get(0)+" - "+ results.get(1)+" - "+ results.get(2));
		
	}
	
}
