package org.erian.examples.bootapi.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.assertj.core.util.Lists;
import org.erian.examples.bootapi.BootApiApplication;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.*;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class DataPointTest {

	@Autowired
	private  DataPointDao testDao;

//	@Test
	public void find() {
		DataPoint demo = testDao.findOne(1001);
		System.out.println(demo);
		assertThat(demo.id).isEqualTo(1001);
	}
	
//	@Test
	public void findByDevice() {
		List<DataPoint> demo = testDao.getByDeviceId(1010);
		System.out.println(demo);
		assertThat(demo.size()).isEqualTo(3);
	}
	
	@Test
	public void deleteByDevice() {
		testDao.deleteByDeviceId(1042);
		System.out.println(1042);
	}
	
	
//	@Test
	public void findAll() {
		List<DataPoint> demos = testDao.findAll();
		assertThat(demos).hasSize(1);
	}
//	@Test
	public void save() {
		
		List<DataPoint> ls = Lists.newArrayList();
				
		for(String s : BICs){
			DataPoint demo = new DataPoint();
			demo.name = s;
			demo.type = "int32";
			demo.address = 0;
			demo.length = 0;
			demo.description = "this is bic2 " + s;
			demo.freq = "SEC01";
			demo.createdOn = new Date();
			demo.createdBy = "Yuanbo";
			demo.device = new Device(1030);
			demo.readOnly = true;
			demo.writeOnly = false;
			demo.inputExpression = null;
			demo.outputExpression = null;
			demo.unit = null;
			ls.add(demo);
		}
		
		testDao.save(ls);
//		System.out.println(demo.id);
//		assertThat(demo.id).isNotNull();
	}

	static String[] PVs = {
			"CH1_VOL","CH2_VOL","CH3_VOL","CH1_PRIOR","CH2_PRIOR","CH3_PRIOR","CH1_MODE","CH2_MODE",
			"CH3_MODE" ,"CH1_REF_POW","CH2_REF_POW","CH3_REF_POW","CH1_POWER","CH2_POWER","CH3_POWER",
			"INDUCTOR_CUR_CH1","INDUCTOR_CUR_CH2","INDUCTOR_CUR_CH3","OUTPUT_CUR","DC_BUS_VOL",
			"OUTPUT_RELAY_STATUS","INPUT_RELAY_STATUS"
	};
	
	static String[] BICs = {
			 "Operating_mode","Active_power_reference","DC_VP_droop_coefficient","Reactive_power_reference_rec",
			 "AC_bus_current_B","DC_bus_current","AC_bus_voltage_phase_A","AC_bus_current_A","DC_Reference_voltage",
			 "Heat_sink_temperature","Reactive_power_reference","AC_bus_Frequency","DC_output_power","AC_fP_droop_coefficient",
			 "DC_relay_ON_OFF_status","Environment_temperature","AC_Reference_voltage","AC_bus_voltage_phase_C",
			 "AC_output_active_power","Device_temperature","AC_VQ_droop_coefficient","AC_output_reactive_power","Fault_message",
			 "DC_bus_voltage","AC_Reference_frequency","Operation_status","AC_bus_voltage_phase_B","isolate_status","AC_bus_current_C","AC_relay_ON_OFF_status"
	};
	
	static String[] BATs = {
			"CH1_ONOFF_STATUS","INDUCTOR_CUR_CH1","INDUCTOR_CUR_CH2","INDUCTOR_CUR_CH3","OUTPUT_CUR","REF_VOL_CONFIRM",
			"DROOP_COEF_CONFIRM","CH2_ONOFF_STATUS","CH3_ONOFF_STATUS","INPUT_RELAY_STATUS","OUTPUT_RELAY_STATUS","BAT_MODE",
			"BAT_SOC","FAULT_MESSAGE","HEATSINK_TEMP","REF_POW_CONFIRM","BAT_VOL","DC_BUS_VOL","OPERATING_POW"
			};
	
}
