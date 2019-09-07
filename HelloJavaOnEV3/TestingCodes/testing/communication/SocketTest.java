package testing.communication;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import pg09.components.*;
/**
 * A simple socket communication testing
 * Should start the server java program before running this code
 * Will send the data of ultrasonic sensor to PC-end (the server socket), also display on LCD
 * 
 * @author sunruoxi
 *
 */
public class SocketTest {

	public static void main(String[] args) throws Exception{
		// terminal command to get IP address: ifconfig | grep "inet " | grep -v 127.0.0.1
		Socket sock = new Socket("175.45.84.198", 3000);
		OutputStream ostream = sock.getOutputStream(); 
		PrintWriter pwrite = new PrintWriter(ostream, true);
		
		Brick ev3 = new Brick();
		Sensor us = new Sensor("S2", Sensor.SONIC);
		
		String sendMessage;
		
		ev3.pressAnyKey();
			
		while(ev3.escapeIsUp()) {
			sendMessage = "distance: " + String.format("%.2f", us.readUltraSensor());
			ev3.displayClr(sendMessage, 0, 1);
			Brick.delay(1000);
			pwrite.println(sendMessage);       
			pwrite.flush();	
		}
		us.close();
		sock.close();		
	}
}
