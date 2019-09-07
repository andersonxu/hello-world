package testing.calibration;

import pg09.components.Brick;
import pg09.components.Pilot;

public class RotationCalibration {

	public static void main(String[] args) {
		final double DIAMETER = 5.6, OFFSET = 5.3;
		final String LEFT = "C", RIGHT = "D";
		final double DEGREE = 360*2;
		
		
		//float linearSpeed = 3, angularSpeed = 10;
		
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(DIAMETER, OFFSET, LEFT, RIGHT);
		
		pilot.getPilot().setAngularSpeed(30);
		pilot.getPilot().setLinearSpeed(5);
		
		ev3.pressAnyKey();
		Brick.delay(1500);
		pilot.getPilot().rotate(-DEGREE);
//		pilot.getPilot().rotate(-DEGREE);
		
	}

}
