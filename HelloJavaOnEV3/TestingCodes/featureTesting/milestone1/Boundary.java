package featureTesting.milestone1;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;

/** Testing: whether the robot can stop when a boundary is detected
 * 
 * @author sunruoxi
 *
 */

public class Boundary {

	public static void main(String[] args) {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(4.2, -9, "C", "D");
		Sensor colorSensor = new Sensor("S3", Sensor.COLOR);
		
		String color;
		
		ev3.pressAnyKey();
		
		while(ev3.escapeIsUp()) {
			pilot.getPilot().travel(1);
			color = colorSensor.readColor();
			ev3.displayClr("Color: " + color, 0, 1);
			if(color == "BLUE") {
				ev3.displayClr("Stop!", 0, 2);
				pilot.getPilot().stop();
				break; 
			}
			Brick.delay(1000);
		}
		colorSensor.close();
		
	}

}
