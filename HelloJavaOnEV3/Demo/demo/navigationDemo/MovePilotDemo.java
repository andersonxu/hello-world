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

public class MovePilotDemo {
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);

	public static void main(String[] args) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		
		buttons.waitForAnyPress();
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 2.8).offset(-9); 
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 2.8).offset(9);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		// travel 100 centimeter
		pilot.travel(100);
		// rotate 90 degrees
		pilot.rotate(90.0);

		// press the ESCAPE button to stop moving 
		while (pilot.isMoving()) {
			if (buttons.getButtons() == Keys.ID_ESCAPE) 
				pilot.stop();
		}
		// block the thread until a button is pressed
		buttons.waitForAnyPress();	
	}

}
