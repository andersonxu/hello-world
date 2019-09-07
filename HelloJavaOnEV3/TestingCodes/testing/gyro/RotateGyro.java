package testing.gyro;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;
import pg09.features.MessageSender;

public class RotateGyro {

//	private static Sensor sensor = new Sensor("S1", "S3", "S4");
	private static Sensor sensor = new Sensor("S4", Sensor.GYRO);
	private static Pilot pilot;
	private static int targetAngle = 0;
	
	public static void main(String[] args) throws Exception {
//		float start;
		float end;
		Brick ev3 = new Brick();
		MessageSender sender = new MessageSender("10.201.134.175", 3000);
		pilot = new Pilot(5.6, 5.68, "C", "D");
		
		pilot.getPilot().setAngularSpeed(30);
		pilot.getPilot().setLinearSpeed(3);
		
		Thread sensorReading = new Thread() {
			@Override
			public void run() {
				while(!isInterrupted()) {
//					sensor.readUltraSensor();
//					sensor.readColor();
					sensor.readAngle();
				}
			}
		};
		
		sensorReading.start();
		
		sender.send("Ready");
		ev3.pressAnyKey();
		Brick.delay(1500);
		
		pilot.rotate(90, sensor);
		pilot.rotate(-90, sensor);
		pilot.rotate(90, sensor);
		pilot.rotate(90, sensor);
		pilot.rotate(-90, sensor);
		pilot.rotate(-90, sensor);
		pilot.rotate(180, sensor);
		pilot.rotate(90, sensor);
		pilot.rotate(-180, sensor);
		pilot.rotate(90, sensor);
		pilot.rotate(-90, sensor);
		pilot.rotate(-90, sensor);
		
		
//		rotate(90);
//		rotate(90);
//		rotate(-90);
//		rotate(90);
//		rotate(180);
//		rotate(-90);
//		rotate(-180);
//		rotate(-90);
//		rotate(180);
//		rotate(-180);
		
		
//		int i = 3;
//		while(i > 0) {
//			sender.send("start: " + sensor.getAngle() + "");
//			pilot.getPilot().rotate(90);
//			targetAngle += 90;
//			Brick.delay(1000);
//			sender.send("stop: " + (end = sensor.getAngle()) + "");
//			pilot.getPilot().rotate(targetAngle - end);
//			sender.send("end: " + sensor.getAngle());
//			Brick.delay(1000);
////			sensor.resetGyro();
//			
//			sender.send("start: " + sensor.getAngle() + "");
//			pilot.getPilot().rotate(90);
//			targetAngle += 90;
//			Brick.delay(1000);
//			sender.send("stop: " + (end = sensor.getAngle()) + "");
//			pilot.getPilot().rotate(targetAngle - end);
//			sender.send("end: " + sensor.getAngle());
//			Brick.delay(1000);
//			
//			
//			sender.send("start: " + sensor.getAngle() + "");
//			pilot.getPilot().rotate(-90);
//			targetAngle -= 90;
//			Brick.delay(1000);
//			sender.send("stop: " + (end = sensor.getAngle()) + "");
//			pilot.getPilot().rotate(targetAngle - end);
//			sender.send("end: " + sensor.getAngle());
////			sensor.resetGyro();
//			Brick.delay(1000);
//			
//			i--;
//		}
		
//		sender.send((start = sensor.getAngle()) + "");
//		System.out.println(start + "");
//		
//		pilot.getPilot().rotate(90);
//		
//		sender.send((end = sensor.getAngle()) + "");
//		System.out.println(end + "");
//		
//		pilot.getPilot().rotate(90 + start - end);
//		sender.send(sensor.getAngle() + "");
//		System.out.println(sensor.getAngle());
		
		sensorReading.interrupt();
		sender.send("END");
		
		sensor.close();
		sender.close();

	}
	
	public static void rotate(int deg) {
		pilot.getPilot().rotate(deg);
		targetAngle += deg;
		Brick.delay(2000);
		pilot.getPilot().rotate(targetAngle - sensor.getAngle());
		Brick.delay(2000);
//		System.out.println(sensor.getAngle());
	}

}
