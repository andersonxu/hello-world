package releasedVersions;

import pg09.components.*;

/** use ultrasonic sensor to avoid collision. When an obstacle/survivor is found, the medium motor
 * will rotate 90 degree to change the direction of color sensor, and if the color of object is 
 * RED, the robot will display number of survivors on screen.
 * 
 * @author sunruoxi
 *
 */

public class V02_ObstacleAvoiding_SurvivorIdentify {
	public static void main(String[] args) {
		double step = 2, disHold = 6, disSafe = 2;
		float distance;
		String color;
		
		// initial
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(4.2, -9, "C", "D");
		Sensor sensor = new Sensor("S4", "S1");
		
		pilot.setMediumMotor("B");
		pilot.setSpeed(2, 10);
		
		// press any key to start
		ev3.pressAnyKey();
		
		distance = sensor.readUltraSensor();
		ev3.displayClr("distance: ", (int)distance, 0, 1);
		int nSurvivor = 0;
		
		while(ev3.escapeIsUp()) {
			pilot.getPilot().travel(step);
			distance = sensor.readUltraSensor();
			ev3.displayClr("distance: ", (int)distance, 0, 1);
			ev3.display("Find survivor: " + nSurvivor, 0, 2);
			
			if(distance < disHold) {
				pilot.getMediumMotor().rotate(90);
				
				color = sensor.readColor();
				if(color == "RED") {
					ev3.display("Find survivor: " + ++nSurvivor, 0, 2);
					Brick.LEDBlinkGreen();
					Brick.delay(3000);
					Brick.LEDClose();
				}
				Brick.delay(1000);
				
				pilot.getMediumMotor().rotate(-90);
				Brick.delay(1000);
				pilot.getPilot().travel(-disSafe);
				pilot.getPilot().rotate((Math.random()>0.5) ? 30:-30);
			}
		}
		sensor.close();
		pilot.close();
	}
}
