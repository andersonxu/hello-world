package demo.navigationDemo;

import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class DifferentialPilot_basicMovement {

	public static void main(String[] args) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		DifferentialPilot pilot = new DifferentialPilot(3.1, 17.5, Motor.A,Motor.C); 
		Keys buttons = ev3brick.getKeys();
		
		pilot.travel(20, true); // 20 centimeters
		pilot.rotate(90.0);
		
		while (pilot.isMoving()) {
			if (buttons.getButtons() == Keys.ID_ESCAPE) 
				pilot.stop();
			buttons.waitForAnyPress();
		}
	}

}
