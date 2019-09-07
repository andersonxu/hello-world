package releasedVersions;


import pg09.components.*;
import pg09.features.*;

public class V04_Milestone_1 {

	public static void main(String[] args) {
		
		double step = 2.0;
		double distanceThreshold = 6.0;
		double safeDistance = 2.0;
		float distance;
		float[] location;
		String color;
		int nSurvivor = 0;
		
		// initial
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(5.8, 5.347, "C", "D");
		Sensor sensor = new Sensor("S4", "S1");
		pilot.setMediumMotor("B");
		
		pilot.getPilot().setLinearSpeed(3);
		pilot.getPilot().setAngularSpeed(20);
		
		// press any key to start
		ev3.pressAnyKey();
		
		while(ev3.escapeIsUp()) {
			location = pilot.readLocation();
			ev3.display("Current: " + String.format("%.1f", location[0]) +  ", " + String.format("%.1f", location[1]), 0, 1);
			
			distance = sensor.readUltraSensor();
			
			if(sensor.readColor() == "BLACK") {
				Actions.boundaryAvoid(pilot);
			}
			else {
				pilot.getPilot().travel(step);
				
				if(distance < distanceThreshold) {
					pilot.getMediumMotor().rotate(-90);
					Brick.delay(1000);
					
					color = sensor.readColor();
					if(color == "RED") {
						ev3.display(++nSurvivor + ": ", 0, nSurvivor+1);
						Actions.reportSurvivor();
						location = pilot.readLocation();
						ev3.display("(" + String.format("%.1f", location[0]) +  ", " + String.format("%.1f", location[1]) + ")", 3, nSurvivor+1);
						
					}
					
					pilot.getMediumMotor().rotate(90);
					Brick.delay(1000);
					
					Actions.collisionAvoid(pilot, safeDistance);
				}
			}
		}
		sensor.close();
		pilot.close();
	}

}
