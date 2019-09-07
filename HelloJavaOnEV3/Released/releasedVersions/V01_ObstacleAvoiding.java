package releasedVersions;

import pg09.components.*;

/** Testing: feature of obstacle avoiding. If an object is detected, the robot will move backward and rotate
 * to another direction, then keep moving.
 * 
 * @author sunruoxi
 *
 */

public class V01_ObstacleAvoiding {
	
	public static void main(String[] args) {
		double step = 2, disHold = 15, disSafe = 2;
		float distance;
		
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(4.2, -9, "C", "D");
		Sensor sonicSensor = new Sensor("S1",Sensor.SONIC);
		
		pilot.setSpeed(2, 10);
		
		ev3.pressAnyKey();
		
		distance = sonicSensor.readUltraSensor();
		
		while(ev3.escapeIsUp()) {
			pilot.getPilot().travel(step);
			distance = sonicSensor.readUltraSensor();
			ev3.displayClr("distance: " + String.format("%.2f", distance), 0, 1);
			
			if(distance < disHold) {
				pilot.getPilot().travel(-disSafe);
				pilot.getPilot().rotate((Math.random()>0.5) ? 30:-30);
			}
		}
		sonicSensor.close();
	}
	
}
