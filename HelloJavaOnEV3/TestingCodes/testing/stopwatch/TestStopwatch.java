package testing.stopwatch;

import lejos.utility.Stopwatch;
import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;
import pg09.features.GridTraveler;
import pg09.features.MessageSender;
import pg09.features.Uphillfollowline;

public class TestStopwatch {
	public static Sensor sensor;
	
	public static void main(String[] args) throws Exception {
		int nSurvivor = 0;
		Stopwatch sw = new Stopwatch();
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(5.6, 5.68, "C", "D");
		MessageSender sender = new MessageSender("172.20.10.6", 3000);
		GridTraveler gt = new GridTraveler(3, 2);
		
		sensor = new Sensor("S1", "S3", "S4");
		pilot.setMediumMotor("B");
		
		pilot.getPilot().setAngularSpeed(30);
		pilot.getPilot().setLinearSpeed(5);
		
		int step = 5000;
		float safeDistance = 3.5f;
		
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
//		sender.send("down:" + gt.gridToString(gt.getTransGridDown()));
//		sender.send("up:" + gt.gridToString(gt.getTransGridUp()));
		
		gt.setCurrentGrid(gt.findGrid(0, 0));
		gt.setNextGrid();
		
//		sender.send("Ready");
		
		Brick.delay(1500);
		
//		sender.send("Start");

		while(gt.getCurrentGrid() != null && ev3.escapeIsUp() && nSurvivor < 2) {
			int timer = 0;
			sw.reset();
			
			sender.send(gt.statusToString());
//			sender.send(gt.statusToString() + " gt: " + gt.getDirectionString() + " pilot: " + pilot.getDirection()
//			+ " color: " + sensor.getColor() + " angle: " + sensor.getAngle()
//			+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance() + " time: " + sw.elapsed());
			
			pilot.getPilot().forward();
			while((!(sensor.getColor().equals("RED"))
					&& !(sensor.getColor().equals("BLACK"))) 
					&& sensor.getDistance() >= safeDistance
					&& (timer = sw.elapsed()) < step) {}
			pilot.getPilot().stop();
			
//			sender.send("timer: " + timer);
			
			if(sensor.getDistance() < safeDistance) {
				pilot.getMediumMotor().rotate(-90);
				Brick.delay(1000);
				if(sensor.getColor().equals("RED")){
					gt.markUnreachableGrid(GridTraveler.SURVIVOR);
					sender.send("survivor " + gt.gridToString(gt.getNextGrid()));
					nSurvivor ++;
				}
				else {
					gt.markUnreachableGrid(GridTraveler.OBSTACLE);
					sender.send("obstacle " + gt.gridToString(gt.getNextGrid()));
				}
				pilot.getMediumMotor().rotate(90);
				
				sw.reset();
				pilot.getPilot().backward();
				while(sw.elapsed()<timer) {}
				pilot.getPilot().stop();
			}
			else if(sensor.getColor().equals("RED")) {
				sender.send("trans point reached");
				gt.markArrivedGrid();
				if(gt.getTransGridDown() == null) {
					gt.setTransGridDown(gt.getCurrentGrid());
					gt.getGrids().add(gt.getCurrentGrid());
					gt.getAvailable().add(gt.getCurrentGrid());
					gt.getCanGoThrough().add(gt.getCurrentGrid());
				}
					
//				if(gt.isGroundFloorAllVisited()) {
				if(true) {
//					sender.send("go downstairs");
					if(gt.getCurrentGrid().equals(gt.getTransGridDown())) {
						gt.setCurrentGrid(gt.getTransGridUp());
//						gt.setDirection(GridTraveler.RIGHT);
						gt.setCurrentFloor(gt.getCurrentFloor()-1);
//						pilot.setDirection(GridTraveler.RIGHT);
						pilot.setTargetAngle(pilot.getTargetAngle() - 360);
//						pilot.setTargetAngle(pilot.getTargetAngle() + 270);
						
					}
					else if(gt.getCurrentGrid().equals(gt.getTransGridUp())) {
						gt.setCurrentGrid(gt.getTransGridDown());
						gt.setDirection(GridTraveler.RIGHT);
						gt.setCurrentFloor(gt.getCurrentFloor()+1);
						pilot.setDirection(GridTraveler.RIGHT);
						pilot.setTargetAngle(pilot.getTargetAngle() + 270);
					}
					
					Uphillfollowline fLine = new Uphillfollowline();
					pilot.getPilot().rotate(fLine.upordownStairs(pilot, sensor, ev3, sender));
//					ev3.pressAnyKey();
					
//					sender.send("arrived underground");
//					sender.send(gt.statusToString() + " gt: " + gt.getDirectionString() + " pilot: " + pilot.getDirection()
//					+ " color: " + sensor.getColor() + " angle: " + sensor.getAngle()
//					+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance() + " time: " + sw.elapsed());
//					ev3.pressAnyKey();
					
//					sender.send("arrived underground");
//					pilot.getPilot().rotate( - (sensor.getAngle()+360)%360);
					pilot.getPilot().rotate( - (sensor.getAngle()+360)%360);
					pilot.getPilot().travel(25);
					
//					sender.send("position update");
//					sender.send(gt.statusToString() + " gt: " + gt.getDirectionString() + " pilot: " + pilot.getDirection()
//					+ " color: " + sensor.getColor() + " angle: " + sensor.getAngle()
//					+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance() + " time: " + sw.elapsed());
//					ev3.pressAnyKey();
				}
				
			}
			else if(sensor.getColor().equals("BLACK")) {
				gt.markUnreachableGrid(GridTraveler.BOUNDARY);
				sender.send("boundary " + gt.gridToString(gt.getNextGrid()));
				
				sw.reset();
				pilot.getPilot().backward();
				while(sw.elapsed()<timer) {}
				pilot.getPilot().stop();
			}
			else {
				gt.markArrivedGrid();
			}
			
			gt.setNextGrid();
//			sender.send("set next");
//			sender.send(gt.statusToString() + " gt: " + gt.getDirectionString() + " pilot: " + pilot.getDirection()
//			+ " color: " + sensor.getColor() + " angle: " + sensor.getAngle()
//			+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance() + " time: " + sw.elapsed());
			
			switch((gt.getDirection() - pilot.getDirection() + 4)%4){
			case 1:
				pilot.rotate(90, sensor);
				pilot.setDirection((pilot.getDirection()+1)%4);
				break;
			case 2:
				pilot.rotate(180, sensor);
				pilot.setDirection((pilot.getDirection()+2)%4);
				break;
			case 3: 
				pilot.rotate(-90, sensor);
				pilot.setDirection((pilot.getDirection()+3)%4);
				break;
//			default:
//				pilot.rotate(0, sensor);
			}
//			sender.send("Rotated");
			
//			sender.send(gt.statusToString() + " gt: " + gt.getDirectionString() + " pilot: " + pilot.getDirection()
//			+ " color: " + sensor.getColor() + " angle: " + sensor.getAngle()
//			+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance() + " time: " + sw.elapsed());
			
			pilot.getPilot().stop();
			Brick.delay(1000);
			
		}
//		sender.send(gt.mapToString());
		
		sensorReading.interrupt();
		sender.send("END");
		sender.close();
		sensor.close();
	}
}
