package org.erian.examples.bootapi.service.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * A common method for read/write through socket connection
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  8 Jul 2017 11:27:51 pm
 */
@Component
public class SocketConnection {
	
	@Value("${app.socket.timeout:2000}")
	int timeout; 
	@Value("${app.socket.on:true}")
	boolean on;
	
	@Autowired
	private CacheManager cacheManager;
	
	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

	private static final Logger logger = LoggerFactory.getLogger(SocketConnection.class);

	public String[] requestData(String ip, int port, String command) {
		String results = null;
		try {
			results = this.callDevice(ip, port, command);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		if (!StringUtils.isEmpty(results)) {
			return results.split(";");
		}
		return null;
	}

//	@Cacheable(value="SEC02", key="callDevice"+"#ip"+":"+"#port"+"#command")
	public String callDevice(final String ip,final int port, final String command) throws InterruptedException, ExecutionException {
		final Socket deviceSocket = this.getSocket(ip, port);

        Future<String> result = executor.submit(new Callable<String>(){
			@Override
			public String call() throws Exception {
				DataOutputStream dataOutputStream = null;
				BufferedReader bufferedReader = null;
				String data = "";
				byte[] byteCommand = command.getBytes();
				final StopWatch watch = new StopWatch();
				try {
					watch.start();
					dataOutputStream = new DataOutputStream(deviceSocket.getOutputStream());
					bufferedReader = new BufferedReader(
							new InputStreamReader(deviceSocket.getInputStream(), StandardCharsets.UTF_8));
					dataOutputStream.write(byteCommand);
					data = bufferedReader.readLine();
					watch.stop();
					logger.info("\nCommand -> " + command + "\tResponsed data ： " + data + "\tExec time(ms) : "
							+ watch.getLastTaskTimeMillis());
				} catch (IOException e) {
					logger.error(e.getMessage());
				} catch (Exception e) {
					logger.error(e.getMessage());
				} finally {
					try {
						if(dataOutputStream != null ){
							dataOutputStream.flush();
							dataOutputStream.close();
						}
						if (bufferedReader != null) {
							bufferedReader.close();
						}
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
				return data;
			}
		});
           
		return result.get();
		
	}
	
//	@Cacheable(value="SEC02", key="getSocket-"+"#ip"+":"+"#port")
	public synchronized Socket getSocket(String ip, int port) {
		Cache socketCache = this.cacheManager.getCache("SOCKET");
		ValueWrapper wrapper  = (ValueWrapper)socketCache.get(ip+":"+port);
//		if(null != deviceSocket && deviceSocket.isConnected()){
		Socket deviceSocket =  null;
		if(null != wrapper){
			deviceSocket = (Socket)wrapper.get();
		}
		if(null != deviceSocket && !deviceSocket.isClosed()){
			return deviceSocket;
		}else {
			try {
				socketCache.evict(ip+":"+port);
				deviceSocket = new Socket(ip, port);
				deviceSocket.setTcpNoDelay(on);
				deviceSocket.setKeepAlive(on);
				deviceSocket.setSoTimeout(timeout);
				socketCache.put(ip+":"+port, deviceSocket);
			} catch (IOException e) {
				logger.error("Error in creating the socket with " + ip+":"+port);
				return null;
			}
		}
		
		return deviceSocket;
	}
//	@Cacheable(value="SEC02", key="setDevice"+"#ip"+":"+"#port"+"#command")
	public void setDevice(final String ip,final int port, final String command) {

		final Socket deviceSocket = this.getSocket(ip, port);
		
		  executor.execute(new Runnable(){
				@Override
				public void run() {

				StopWatch watch = new StopWatch();
				DataOutputStream dataOutputStream = null;
				byte[] byteCommand = command.getBytes();
				try {
					watch.start();
					dataOutputStream = new DataOutputStream(deviceSocket.getOutputStream());
					dataOutputStream.write(byteCommand);
					logger.info("\nCommand -> " + command + "\tExec time(ms) : " + watch.getLastTaskTimeMillis());
				} catch (IOException e) {
					logger.error("IOException " + e.getMessage());
				} catch (Exception e) {
					logger.error("Exception " + e.getMessage());
				} finally {
					try {
						if (dataOutputStream != null) {
							dataOutputStream.flush();
							dataOutputStream.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
			}
		});
		
		
		
	}
}
