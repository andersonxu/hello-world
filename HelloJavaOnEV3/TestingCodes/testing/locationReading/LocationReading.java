package testing.locationReading;

import pg09.components.Pilot;
import pg09.components.Brick;

public class LocationReading {

	public static void main(String[] args) {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(4, -9, "C", "D");
		
		ev3.pressAnyKey();
		
		float[] location = new float[3];
		
		while(ev3.escapeIsUp()) {
			pilot.getPilot().travel(10);
			location = pilot.readLocation();
			ev3.displayClr("x: ", location[0], 0, 1);
			ev3.displayClr("y: ", location[1], 0, 2);
			ev3.displayClr("direction: ", location[2], 0, 3);
			
			pilot.getPilot().rotate(90);
			location = pilot.readLocation();
			ev3.displayClr("x: ", location[0], 0, 1);
			ev3.displayClr("y: ", location[1], 0, 2);
			ev3.displayClr("location: ", location[2], 0, 3);
			
		}
		
	}

}
