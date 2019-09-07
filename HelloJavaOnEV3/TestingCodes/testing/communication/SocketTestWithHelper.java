package testing.communication;

import pg09.components.*;
import pg09.features.*;

/**
 * A simple socket communication testing (using helper classes)
 * Should start the server java program on PC before running this code
 * Will send the data of ultrasonic sensor to PC-end (the server socket), also display on LCD
 * 
 * @author sunruoxi
 *
 */

public class SocketTestWithHelper {
	public static void main(String[] args) throws Exception{
		
		Brick ev3 = new Brick();
		Sensor us = new Sensor("S2", Sensor.SONIC);
		// the IP address here is the IP address of PC
		// to get the IP address on Mac
		// ifconfig | grep "inet " | grep -v 127.0.0.1
		MessageSender sender = new MessageSender("10.201.133.140", 3000);
		
		String sendMessage;
		
		ev3.pressAnyKey();
			
		while(ev3.escapeIsUp()) {
			sendMessage = "distance " + String.format("%.2f", us.readUltraSensor());
			ev3.displayClr(sendMessage, 0, 1);
			Brick.delay(1000);
			sender.send(sendMessage);
		}	
		us.close();
		sender.close();		
	}
}
