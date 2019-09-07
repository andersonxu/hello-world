package pg09.features;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;

public class Actions {
	public static void collisionAvoid(Pilot pilot, double safeDistance) {
		pilot.getPilot().travel(-safeDistance);
		pilot.getPilot().rotate((Math.random()>0.5) ? 90:-90);
	}
	
	public static void boundaryAvoid(Pilot pilot) {
		pilot.getPilot().rotate((Math.random()>0.5) ? 90:-90);
	}
	
	public static void reportSurvivor() {
		Brick.LEDBlinkGreen();
		Brick.delay(3000);
		Brick.LEDClose();
	}
	
	public static void objectIdentify() {
		
	}
	
	public static void moveToNextGrid(Pilot pilot, GridTraveler gt) {
		float step = pilot.getStep();
		int n = (int)(gt.getGridWidth()/step);
		
		switch((gt.getDirection() - pilot.getDirection() + 4)%4){
			case 1:
				pilot.getPilot().rotate(90); 
				pilot.setDirection(pilot.getDirection()+1);break;
			case 2:
				step = -step; break;
			case 3:
				pilot.getPilot().rotate(-90); 
				pilot.setDirection(pilot.getDirection()-1);break;
		}
		
		if(gt.getNextGrid()!=null) {
			for(int i=0;i<n;i++) {
				pilot.getPilot().travel(step);
			}
		}
		
	}
	
	public static void moveToNextGrid(Pilot pilot, Sensor sensor, GridTraveler gt, MessageSender s, Brick ev3) {
//		final String backgroundColor = "WHITE";
		final String boundaryColor = "BLACK";
		float step = pilot.getStep();
		float distance;
		float safeDistance = 5f;
		int n = (int)(gt.getGridWidth()/step);
		String color = "";	
		
		if(gt.getNextGrid()!=null) {
			for(int i=0;i<n;i++) {
				s.send(gt.statusToString() + " gt: " + gt.getDirection() + " pilot: " + pilot.getDirection()
				+ " " + sensor.getColor() + " angle: " + sensor.getAngle()
				+ " target: " + pilot.getTargetAngle() + " dis: " + sensor.getDistance());
				if(!ev3.escapeIsUp()) break;
//				Brick.delay(2000);
				distance = sensor.getDistance();
				color = sensor.getColor();
//				s.send(color + i);
				if(distance > step && !color.equals(boundaryColor)) {
					pilot.getPilot().travel(step);
					if(i == n-1) {
						gt.markArrivedGrid();
					}
				}
				else if(distance <= step) {
					pilot.getPilot().travel(distance - safeDistance);
					pilot.getMediumMotor().rotate(-90);
					Brick.delay(1000);
					if(sensor.getColor().equals("RED")) {
						gt.markUnreachableGrid(GridTraveler.SURVIVOR);
						s.send("survivor " + gt.gridToString(gt.getNextGrid()));
					}
					else {
						gt.markUnreachableGrid(GridTraveler.OBSTACLE);
						s.send("obstacle " + gt.gridToString(gt.getNextGrid()));
					}
					pilot.getMediumMotor().rotate(90);
					pilot.getPilot().travel(safeDistance - distance);
					
					pilot.getPilot().travel(-i*step);
					break;
				}
				else if(color.equals(boundaryColor)) {
					gt.markUnreachableGrid(GridTraveler.BOUNDARY);
					s.send("boundary " + gt.gridToString(gt.getNextGrid()));
					pilot.getPilot().travel(-i*step);
					break;
				}
			}
		}
		else {
			gt.markArrivedGrid();
		}
		gt.setNextGrid();
		rotate(gt, pilot, sensor, s);
		if(sensor.getColor().equals("RED") || gt.getNextGrid().equals(gt.getTransGridUp())) {
			if(gt.getTransGridUp() != null)
				gt.setTransGridUp(gt.getNextGrid());
			// *************doing uphill***********
			Uphillfollowline fLine = new Uphillfollowline();
			fLine.upordownStairs(pilot, sensor, ev3, s);
//			System.out.println("Changed floor");
//			ev3.pressAnyKey();
			s.send("stop");
			gt.trans();
			if(gt.getCurrentFloor() == 0) {
				pilot.setDirection(GridTraveler.LOWER);
				pilot.setTargetAngle(pilot.getTargetAngle() - 270);
			}
			else {
				pilot.setDirection(GridTraveler.RIGHT);
				pilot.setTargetAngle(pilot.getTargetAngle() + 270);
			}
//			s.send("stop");
		}
	}
	
	public static void rotate(GridTraveler gt, Pilot pilot, Sensor sensor, MessageSender s) {
		switch((gt.getDirection() - pilot.getDirection() + 4)%4){
		case 1:
//			s.send("gt " + gt.getDirection() + "pilot " + pilot.getDirection());
//			s.send("rotate 90");
			pilot.rotate(90, sensor);
			pilot.setDirection((pilot.getDirection()+1)%4);
//			s.send("gt " + gt.getDirection() + "pilot " + pilot.getDirection());
			break;
		case 2:
//			s.send("gt " + gt.getDirection() + "pilot " + pilot.getDirection());
//			s.send("rotate 180");
			pilot.rotate(180, sensor);
			pilot.setDirection((pilot.getDirection()+2)%4);
//			s.send("gt " + gt.getDirection() + "pilot " + pilot.getDirection());
			break;
		case 3:
//			s.send("gt " + gt.getDirection() + "pilot " + pilot.getDirection());
//			s.send("rotate -90"); 
			pilot.rotate(-90, sensor);
			pilot.setDirection((pilot.getDirection()+3)%4);
//			s.send("gt " + gt.getDirection() + "pilot " + pilot.getDirection());
			break;
		default:
			pilot.rotate(0, sensor);
		}
		
	}
}
