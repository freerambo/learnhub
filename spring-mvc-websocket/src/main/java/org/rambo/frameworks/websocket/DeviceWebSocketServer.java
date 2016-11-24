package org.rambo.frameworks.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.rambo.frameworks.spring.mvc.model.ContactBean;
import org.rambo.frameworks.spring.mvc.utils.BaseBean;

//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.spi.JsonProvider;

@ServerEndpoint("/device/actions")
public class DeviceWebSocketServer {
	private static final Logger LOGGER = Logger.getLogger(DeviceWebSocketServer.class);

	private static DeviceSessionHandler sessionHandler = new DeviceSessionHandler();

	static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		Device device = new Device();
		device.setEventID("source_ac_001");
		device.setEventTitle("AC Source");
		device.setUserID("user1");
		device.setStatus("Off");
		sessionHandler.addDevice(device);
		Device device1 = new Device();
		device1.setEventID("load_dc_001");
		device1.setEventTitle("DC LOAD");
		device1.setUserID("user2");
		device1.setStatus("Off");
		sessionHandler.addDevice(device1);
	}
	@OnOpen
	public void open(Session session) {
		sessionHandler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		sessionHandler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
		LOGGER.error("Error", error);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {

		HashMap<String, Object> jsonMessage = null;
		try {
			jsonMessage = objectMapper.readValue(message, HashMap.class);
			if ("add".equals(jsonMessage.get("action"))) {
				Device device = new Device();
				device.setEventID(jsonMessage.get("eventID").toString());
				device.setEventTitle(jsonMessage.get("eventTitle").toString());
				device.setUserID(jsonMessage.get("userID").toString());
				device.setStatus("Off");
				sessionHandler.addDevice(device);
			}

			if ("remove".equals(jsonMessage.get("action"))) {
				Integer id = Integer.parseInt(jsonMessage.get("id").toString());
				sessionHandler.removeDevice(id);
			}

			if ("toggle".equals(jsonMessage.get("action"))) {
				Integer id = Integer.parseInt(jsonMessage.get("id").toString());
				sessionHandler.toggleDevice(id);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class DeviceSessionHandler {

	private int deviceId = 0;
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	private static final Set<Device> devices = Collections.synchronizedSet(new HashSet<Device>());

	private static ObjectMapper mapper = new ObjectMapper();
	
	public void addSession(Session session) {
		sessions.add(session);
		for (Device device : devices) {
			DeviceBean addMessage = createAddMessage(device);
			sendToSession(session, addMessage);
//			sendToAllConnectedSessions(addMessage);
		}
	}

	public void removeSession(Session session) {
		sessions.remove(session);
		
	}

	public List<Device> getDevices() {
		return new ArrayList<Device>(devices);
	}

	public void addDevice(Device device) {
		device.setId(deviceId);
		devices.add(device);
		deviceId++;
		DeviceBean addMessage = createAddMessage(device);
		sendToAllConnectedSessions(addMessage);
	}

	public void removeDevice(int id) {
		Device device = getDeviceById(id);
		if (device != null) {
			devices.remove(device);
			sendToAllConnectedSessions(new DeviceBean(device, "remove"));
		}
	}

	public void toggleDevice(int id) {
		Device device = getDeviceById(id);
		if (device != null) {
			if ("On".equals(device.getStatus())) {
				device.setStatus("Off");
			} else {
				device.setStatus("On");
			}
			DeviceBean updateDevMessage = new DeviceBean(device, "toggle");
			sendToAllConnectedSessions(updateDevMessage);
		}
	}

	private Device getDeviceById(int id) {
		for (Device device : devices) {
			if (device.getId() == id) {
				return device;
			}
		}
		return null;
	}

	private DeviceBean createAddMessage(Device device) {
		return new DeviceBean(device, "add");
	}

	private void sendToAllConnectedSessions(DeviceBean message) {

		for (Session session : sessions) {
			sendToSession(session, message);
		}
	}

	private void sendToSession(Session session, DeviceBean message) {
		try {
			session.getBasicRemote().sendText(mapper.writeValueAsString(message));
		} catch (IOException ex) {
			sessions.remove(session);
			LOGGER.error("sendToSession ERROR", ex);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(DeviceSessionHandler.class);

}

/**
 * device
 */
class Device extends BaseBean {

	static final Map<String, String> typeColor = new HashMap<String, String>();
	static {
		typeColor.put("source_dc_001", "1");
		typeColor.put("source_ac_001", "2");
		typeColor.put("load_dc_001", "3");
		typeColor.put("load_ac_001", "4");
		typeColor.put("converters", "8");
		typeColor.put("others", "6");
	}

	private int id;
	private String eventID;
	private String eventTitle;
	private String userID;
	private String status;
	private String color;
	private int assignedPort;

	public Device() {
		this.color = generateColor();
		this.assignedPort = generatePort();
	}

	public int getId() {
		return id;
	}

	public String getEventID() {
		return eventID;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public String getUserID() {
		return userID;
	}

	public String getStatus() {
		return status;
	}

	public String getColor() {
		return color;
	}

	public int getAssignedPort() {
		return assignedPort;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
		this.color = "color0" + typeColor.get(eventID);
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setAssignedPort(int assignedPort) {
		this.assignedPort = assignedPort;
	}

	private int generatePort() {
		Random rn = new Random();
		return rn.nextInt(9999 - 8100 + 1) + 8100;
	}

	private String generateColor() {
		Random rn = new Random();
		return "color0" + (rn.nextInt(9) + 1);
	}
}

class DeviceBean extends BaseBean {

	private static final long serialVersionUID = 201410272153L;

	private final Device device;

	private final String action;

	public DeviceBean(Device device, String action) {
		this.device = device;
		this.action = action;
	}

	public Device getDevice() {
		return device;
	}

	public String getAction() {
		return action;
	}
}
