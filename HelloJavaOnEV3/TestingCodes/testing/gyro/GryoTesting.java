package testing.gyro;

import pg09.components.Brick;
import pg09.components.Sensor;
import pg09.features.MessageSender;

public class GryoTesting {
	private static Sensor sensor = new Sensor("S1", "S3", "S4");
	public static void main(String[] args) throws Exception {
		Brick ev3 = new Brick();
		
		MessageSender sender = new MessageSender("10.201.134.175", 3000);
		
		Thread sensorReading = new Thread() {
			@Override
			public void run() {
				while(!isInterrupted()) {
					sensor.readUltraSensor();
					sensor.readColor();
					sensor.readAngle();
				}
			}
		};
		
		sensorReading.start();
		
		ev3.pressAnyKey();
		
		while(ev3.escapeIsUp()) {
			ev3.display(sensor.getAngle()+"", 1, 1);
//			sender.send("angle: " + sensor.getAngle());
			sender.send(sensor.getColor() + " " + sensor.getDistance() + " " +sensor.getAngle());
			Brick.delay(2000);
		}
		
		sensorReading.interrupt();
		sender.send("END");
		
		sensor.close();
		sender.close();
		

	}

}
