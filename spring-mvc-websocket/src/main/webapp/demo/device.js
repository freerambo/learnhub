/**
 * Created by zhuyb on 14/12/2016.
 */
var myApp = angular.module('myApp', ['ngWebsocket']);

// Refer to http://clintberry.com/2013/angular-js-websocket-service/
// https://github.com/wilk/ng-websocket#installation

myApp.service("Device", function ($websocket) {
    var self = this;
    var ws = $websocket.$new({
        url: "ws://" + window.location.host + ":81",
        mock: true,
        reconnect: true
    });
    this.data = {};

    ws.$on("$open", function() {
        console.log('open a ws connection!');
        ws.$emit("get", "device");
    });
    ws.$on("$message", function (message) {
        console.log("WS Received", message);
        for(var key in message) {
            self.data[key] = message[key];
        }
        console.log(self.data); // At this point, self.data contains the proper data.
    });
    ws.$on('$close', function () {
        console.log('closed the ws connection!');
    });
    this.send = function (obj) {
        ws.$emit("set", obj);
    };
});

myApp.controller("MyCtrl",function(Device,$scope,$parse) {

    // http://stackoverflow.com/questions/18875486/setting-dynamic-scope-variables-in-angularjs-scope-some-string
    // This will not work as assigning variables like this will not "drill down"
// It will assign to a variables named the exact string, dots and all.
    var the_string = 'life.meaning';
    $scope[the_string] = 42;
    // console.log($scope.life.meaning);  // <-- Nope! This is undefined.
    console.log($scope['life.meaning']);  // <-- It is in here instead!

    var model = $parse(the_string);

// Assigns a value to it
    model.assign($scope, 42);

// Apply it to the scope
// $scope.$apply(); <- According to comments, this is no longer needed

    console.log($scope.life.meaning);  // logs 42



    $scope.device = {
        "info" : {
            "deviceId" : "DCLoad01",
            "deviceModel" : "Chroma63211",
            "macAddress" : "",
            "ip" : "192.168.127.121",
            "port" : 4001,
            "protocol" : "tcp/ip"
        },
        "settings" : [
            {
                "name":  "status" ,
                "type": "radio",
                "data" : [
                    ["on", "on"],
                    ["off", "off"]
                ]
            },
            {
                "name" :"mode",
                "type": "radio",
                "data" : [
                    ["0", "CCL"],
                    ["1", "CCH"],
                    ["2", "CRL"],
                    ["3", "CRH"],
                    ["4", "CVL"],
                    ["5", "CVH"],
                    ["6", "CPL"],
                    ["7", "CPH"]
                ]
            },
            {
                "name" :"channel",
                "type": "radio",
                "data" : [
                    ["channelA", "channelA"],
                    ["channelB", "channelB"]
                ]
            },
            {
                "name" :"inputValue",
                "type": "text",
                "placeholder": "Please input a value ",
                "value": "50"
            }],

        "variables" : [
            {
                "name" : "voltage",
                "unit" : "V",
                "type" : "float"
            },
            {
                "name" : "current",
                "unit" : "A",
                "type" : "float"
            },
            {
                "name" : "power",
                "unit" : "W",
                "type" : "float"
            },
            {
                "name" : "resistance",
                "unit" : "Ω",
                "type" : "float"
            },
            {
                "name" : "energy",
                "unit" : "kWh",
                "type" : "float"
            },
            {
                "name" : "mode",
                "type" : "String"
            },
            {
                "name" : "channel",
                "type" : "String"
            },
            {
                "name" : "deviceStatus",
                "type" : "String"
            }
        ],
        "commands" : {
            "read" : {
                "command" : "MEAS:VOLT?;CURR?;POW?;RES?;:LOAD?;:MODE?\n",
                "parameters" : "voltage,current,power,resistance,deviceStatus,mode"
            },
            "set" : {
                "CCH" : {
                    "channelA" : "MODE CCH;CURR:STAT A;STAT:L1 x;:MODE?;:CURR:STAT:L1?\n",
                    "channelB" : "MODE CCH;CURR:STAT B;STAT:L2 x;:MODE?;:CURR:STAT:L2?\n",
                    "setParameters" : "currentValue",
                    "getParameters" : "mode,currentValue"
                },
                "CCL" : {
                    "channelA" : "MODE CCL;CURR:STAT A;STAT:L1 x;:MODE?;:CURR:STAT:L1?\n",
                    "channelB" : "MODE CCL;CURR:STAT B;STAT:L2 x;:MODE?;:CURR:STAT:L2?\n",
                    "setParameters" : "currentValue",
                    "getParameters" : "mode,currentValue"
                },
                "CRH" : {
                    "channelA" : "MODE CRH;RES A;RES:L1 x;:MODE?;:RES:L1?\n",
                    "channelB" : "MODE CRH;RES B;RES:L2 x;:MODE?;:RES:L2?\n",
                    "setParameters" : "resistanceValue",
                    "getParameters" : "mode,resistanceValue"
                },
                "CRL" : {
                    "channelA" : "MODE CRL;RES A;RES:L1 x;:MODE?;:RES:L1?\n",
                    "channelB" : "MODE CRL;RES B;RES:L2 x;:MODE?;:RES:L2?\n",
                    "setParameters" : "resistanceValue",
                    "getParameters" : "mode,resistanceValue"
                },
                "CPH" : {
                    "channelA" : "MODE CPH;POW A;POW:L1 x;:MODE?;:POW:L1?\n",
                    "channelB" : "MODE CPH;POW B;POW:L2 x;:MODE?;:POW:L2?\n",
                    "setParameters" : "powerValue",
                    "getParameters" : "mode,powerValue"
                },
                "CPL" : {
                    "channelA" : "MODE CPL;POW A;POW:L1 x;:MODE?;:POW:L1?\n",
                    "channelB" : "MODE CPL;POW B;POW:L2 x;:MODE?;:POW:L2?\n",
                    "setParameters" : "powerValue",
                    "getParameters" : "mode,powerValue"
                },
                "CVH" : {
                    "channelA" : "MODE CVH;VOLT A;VOLT:L1 x;:MODE?;:VOLT:L1?\n",
                    "channelB" : "MODE CVH;VOLT B;VOLT:L2 x;:MODE?;:VOLT:L2?\n",
                    "setParameters" : "voltageValue",
                    "getParameters" : "mode,voltageValue"
                },
                "CVL" : {
                    "channelA" : "MODE CVL;VOLT A;VOLT:L1 x;:MODE?;:VOLT:L1?\n",
                    "channelB" : "MODE CVL;VOLT B;VOLT:L2 x;:MODE?;:VOLT:L2?\n",
                    "setParameters" : "voltageValue",
                    "getParameters" : "mode,voltageValue"
                },
                "status" : {
                    "on" : "turn_on_DCload",
                    "off" : "turn_off_DCload"
                }
            }
        }
    };


    $scope.data= Device.data;



});


