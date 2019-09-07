package featureTesting.milestone1;

import pg09.components.Sensor;
import pg09.features.Actions;
import pg09.components.Brick;
import pg09.components.Pilot;

public class Avoid_Survivor_Boundary {

	public static void main(String[] args) {
		double step = 2, disHold = 6, safeDistance = 2;
		float distance;
		float[] location;
		String color;
		
		// initial
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(6.6, 7.5, "C", "D");
		Sensor sensor = new Sensor("S4", "S1");
		pilot.setMediumMotor("B");
		
		pilot.getPilot().setLinearSpeed(3);
		pilot.getPilot().setAngularSpeed(20);
		
		// press any key to start
		ev3.pressAnyKey();
		
		distance = sensor.readUltraSensor();
		ev3.displayClr("distance: ", (int)distance, 0, 1);
		int nSurvivor = 0;
		
		while(ev3.escapeIsUp()) {
			distance = sensor.readUltraSensor();
			location = pilot.readLocation();
			
			ev3.displayClr("distance: ", (int)distance, 0, 1);
			ev3.displayClr("x: ", location[0], 0, 3);
			ev3.display("y: ", location[1], 7, 3);
			
			ev3.display("Find survivor: " + nSurvivor, 0, 5);
			
			if(sensor.readColor() == "BLACK") {
				Actions.boundaryAvoid(pilot);
			}
			else {
				pilot.getPilot().travel(step);
				
				if(distance < disHold) {
					pilot.getMediumMotor().rotate(90);
					Brick.delay(1000);
					
					color = sensor.readColor();
					if(color == "RED") {
						ev3.display("Find survivor: " + ++nSurvivor, 0, 5);
						Actions.reportSurvivor();
					}
					
					pilot.getMediumMotor().rotate(-90);
					Brick.delay(1000);
					
					Actions.collisionAvoid(pilot, safeDistance);
				}
			}
		}
		sensor.close();
	}

}
