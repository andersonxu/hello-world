package demo.navigationDemo;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis; 
import lejos.robotics.navigation.*;

//an example for tracing out a square

public class TracingOutASquare {
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);

	public static void main(String[] args) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		
		buttons.waitForAnyPress();
		
		Wheel[] wheels = {WheeledChassis.modelWheel(LEFT_MOTOR, 2.8).offset(-9), 
				WheeledChassis.modelWheel(RIGHT_MOTOR, 2.8).offset(9)};
		
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		
		MovePilot pilot = new MovePilot(chassis);

		// loop 4 times to trace out a square
		for (int sides = 0; sides < 4; sides++) {
			pilot.travel(100);
	        pilot.rotate(90);
		}
		
		while (pilot.isMoving()) {
			if (buttons.getButtons() == Keys.ID_ESCAPE) 
				pilot.stop();
		}
		
		buttons.waitForAnyPress();
		                        
	}

}
