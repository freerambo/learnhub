/**
 * Created by teocci on 7/5/16.
 */
window.onload = init;
var socket = new WebSocket("ws://" + document.location.host + "/device/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);
    var d = device.device;
    if (device.action === "details") {
        printDeviceElement(d);
    }
    
    
    if (device.action === "add") {
        printDeviceElement(d);
    }
    if (device.action === "remove") {
        document.getElementById(d.id).remove();
        //device.parentNode.removeChild(device);
    }
    if (device.action === "toggle") {
        var node = document.getElementById(d.id);
        var statusText = node.children[3];
        if (d.status === "On") {
            statusText.innerHTML = "Status: " + d.status + " (<a href=\"#\" OnClick=toggleDevice(" + d.id + ")>Turn off</a>)";
        } else if (d.status === "Off") {
            statusText.innerHTML = "Status: " + d.status + " (<a href=\"#\" OnClick=toggleDevice(" + d.id + ")>Turn on</a>)";
        }
    }
}

function addDevice(eventID, eventTitle, userID) {
    var DeviceAction = {
        action: "add",
        eventID: eventID,
        eventTitle: eventTitle,
        userID: userID
    };
    socket.send(JSON.stringify(DeviceAction));
}

function removeDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}
function deviceDetail(element, eventTile){
	var deviceType= eventTile;
	  var id = element;
	    var DeviceAction = {
	        action: "details",
	        id: id,
	        eventTile: deviceType
	    };
	    socket.send(JSON.stringify(DeviceAction));
	    if("DC_LOAD" == deviceType){
		    $( "#devicePanel" ).load( "devices/dcload.htm" );

	    }  else{
		    $( "#devicePanel" ).load( "devices/dcsource.htm" );
	    }

}

function toggleDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "toggle",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    var content = document.getElementById("content");

    var deviceDiv = document.createElement("div");
    $(deviceDiv).attr("id", device.id);
    $(deviceDiv).attr("class", "device " + device.color);
    
    $(deviceDiv).appendTo($("#content"));
    //content.appendChild(deviceDiv);

    var eventTitle = document.createElement("span");
    eventTitle.setAttribute("class", "deviceEventTitle");
    eventTitle.innerHTML = device.eventTitle;
    deviceDiv.appendChild(eventTitle);

    var eventID = document.createElement("span");
    eventID.innerHTML = "<b>Device ID:</b> " + device.eventID;
    deviceDiv.appendChild(eventID);

    var userID = document.createElement("span");
    userID.innerHTML = "<b>Supplier ID:</b> " + device.userID;
    deviceDiv.appendChild(userID);

    var deviceStatus = document.createElement("span");
    if (device.status === "On") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
    } else if (device.status === "Off") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        //deviceDiv.setAttribute("class", "device off");
    }
    deviceDiv.appendChild(deviceStatus);

    var devicePort = document.createElement("span");
    devicePort.innerHTML = "<b>Assigned Port:</b> " + device.assignedPort;
    deviceDiv.appendChild(devicePort);

    var removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=deviceDetail(" + device.id + ",\'"+ device.eventTitle +"\')>Details   </a> " +"<a href=\"#\" OnClick=removeDevice(" + device.id + ")>    Remove</a>";
    deviceDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addDeviceForm").style.display = '';
}

function hideForm() {
    document.getElementById("addDeviceForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("addDeviceForm");
    var eventID = form.elements["device_event"].value;
    var eventTitle = getEventTitle("device_event");
    console.log(eventTitle);
    var userID = form.elements["device_userID"].value;
    hideForm();
    document.getElementById("addDeviceForm").reset();
    addDevice(eventID, eventTitle, userID);
}

function getEventTitle(elementId) {
    var elt = document.getElementById(elementId);

    if (elt.selectedIndex == -1)
        return null;

    return elt.options[elt.selectedIndex].text;
}

function init() {
    hideForm();
}