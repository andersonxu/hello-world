package component.colorSensor;

import pg09.components.Brick;
import pg09.components.Sensor;
import pg09.features.MessageSender;

public class ColorSensorTesting {
	private static Sensor sensor;
	
	public static void main(String[] args) throws Exception {
		Brick ev3 = new Brick();
		sensor = new Sensor("S3", Sensor.COLOR);
		MessageSender sender = new MessageSender("172.20.10.6", 3000);
		
		Thread sensorReading = new Thread() {
			@Override
			public void run() {
				while(!isInterrupted()) {
//					sensor.readUltraSensor();
					sensor.readColor();
//					sensor.readAngle();
				}
			}
		};
		
		sensorReading.start();
		ev3.pressAnyKey();
		
		while(ev3.escapeIsUp()) {
			sender.send(sensor.getColor());
			Brick.delay(2000);
		}
		
		sensorReading.interrupt();
		
		sender.send("END");
		sensor.close();
		sender.close();
	}

}
