package featureTesting.grid;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;
import pg09.features.Actions;
import pg09.features.GridTraveler;
import pg09.features.MessageSender;

public class Avoiding {
	static String color;
	static float distance;
	public static Sensor sensor;

	public static void main(String[] args) throws Exception {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(5.6, 5.68, "C", "D");
		MessageSender sender = new MessageSender("172.20.10.6", 3000);
		GridTraveler gt = new GridTraveler(3, 2);
		
		sensor = new Sensor("S1", "S3", "S4");
		pilot.setMediumMotor("B");
		
		pilot.getPilot().setAngularSpeed(30);
		pilot.getPilot().setLinearSpeed(5);
		pilot.setStep(4f);
		gt.setGridWidth(20f);
		
		gt.setCurrentGrid(gt.findGrid(0, 0));
		
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
		sender.send(gt.gridToString(gt.getTransGridDown()));
		sender.send("Ready");
		
//		ev3.pressAnyKey();
		Brick.delay(1500);
		
		sender.send("Start");
		gt.setNextGrid();
		while(gt.getCurrentGrid() != null && ev3.escapeIsUp()) {
//			gt.setNextGrid();
			sender.send(gt.statusToString() + " gt: " + gt.getDirection() + " pilot: " + pilot.getDirection()
					+ " " + sensor.getColor() + " angle: " + sensor.getAngle()
					+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance());
			
			Actions.moveToNextGrid(pilot, sensor, gt, sender, ev3);
		}
		
//		while(gt.getCurrentGrid() != gt.findGrid(2, 0) && ev3.escapeIsUp()) {
//			gt.setNextGrid();
//			sender.send(gt.statusToString());
//			
//			Actions.moveToNextGrid(pilot, sensor, gt, sender);
//		}
		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		
//		Actions.moveToNextGrid(pilot, sensor, gt, sender, ev3);
		
		sender.send(gt.mapToString());
		
		sensorReading.interrupt();
		sender.send("END");
		sender.close();
	}

}
