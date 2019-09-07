package testing.calibration;

import pg09.components.Brick;
import pg09.components.Pilot;

public class LinearCalibration {

	public static void main(String[] args) {
		final double DIAMETER = 5.6, OFFSET = 5.68;
		final String LEFT = "C", RIGHT = "D";
		final double DISTANCE = 80;
		
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(DIAMETER, OFFSET, LEFT, RIGHT);
		
		pilot.getPilot().setLinearSpeed(5);
		
		ev3.pressAnyKey();
		pilot.getPilot().travel(DISTANCE);
		
	}

}
