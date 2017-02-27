package com.erian.spring.boot.grid.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by nhdevika on 12/15/2016.
 */
@Component
public class SocketConnection {

	private static final Logger logger = LoggerFactory.getLogger(SocketConnection.class);

	public static String[] requestData(String ip, int port, String command) {
		String results = callDevice(ip, port, command);
		if (!StringUtils.isEmpty(results)) {
			return results.split(";");
		}
		return null;
	}

	@Cacheable(value="deviceValues", key="#ip+#port+#command")
	public static String callDevice(String ip, int port, String command) {
		StopWatch watch = new StopWatch();
		Socket deviceSocket = null;
		DataOutputStream dataOutputStream = null;
		BufferedReader bufferedReader = null;

		String data = "";
		byte[] byteCommand = command.getBytes();

		try {
			watch.start();
			deviceSocket = new Socket(ip, port);
			deviceSocket.setSoTimeout(3000);
			dataOutputStream = new DataOutputStream(deviceSocket.getOutputStream());
			bufferedReader = new BufferedReader(
					new InputStreamReader(deviceSocket.getInputStream(), StandardCharsets.UTF_8));
			dataOutputStream.write(byteCommand);
			data = bufferedReader.readLine();
			watch.stop();
			logger.info("\nCommand -> " + command + "\tResponsed data ： " + data + "\tExec time(ms) : "
					+ watch.getLastTaskTimeMillis());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				dataOutputStream.flush();
				dataOutputStream.close();
				bufferedReader.close();
				deviceSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return data;
	}
	
	@Cacheable(value="deviceValues", key="#ip+#port+#command")
	public static void setDevice(String ip, int port, String command) {
		// String command = (String)commands.get("command");
		StopWatch watch = new StopWatch();
		Socket deviceSocket = null;
		DataOutputStream dataOutputStream = null;

		String data = "";
		byte[] byteCommand = command.getBytes();

		try {
			watch.start();
			deviceSocket = new Socket(ip, port);
			deviceSocket.setSoTimeout(3000);
			dataOutputStream = new DataOutputStream(deviceSocket.getOutputStream());
			dataOutputStream.write(byteCommand);
			// Thread.sleep(20);
			logger.info("\nCommand -> " + command + "\tExec time(ms) : " + watch.getLastTaskTimeMillis());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				dataOutputStream.flush();
				dataOutputStream.close();
				deviceSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
