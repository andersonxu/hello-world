package demo.navigator;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis; 
import lejos.robotics.navigation.*;

//an example for navigation testing

public class NavigationTesting {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C); 
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);

	public static void main(String[] args) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		
		buttons.waitForAnyPress();
		
		Wheel[] wheels = {WheeledChassis.modelWheel(LEFT_MOTOR, 6.6).offset(-7.5), 
				WheeledChassis.modelWheel(RIGHT_MOTOR, 6.6).offset(7.5)};
		
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		
		MovePilot pilot = new MovePilot(chassis);
		
		Navigator navtest = new Navigator(pilot);
		
		pilot.setLinearSpeed(5);
		pilot.setAngularSpeed(30);
		
		// define a new waypoint as destination
		Waypoint wp1 = new Waypoint (10, 0);
		Waypoint wp2 = new Waypoint (30, 0);
		
		// robot moves to the destination waypoint
		navtest.goTo(wp1);
		buttons.waitForAnyPress();
		navtest.goTo(wp2);
		buttons.waitForAnyPress();
		pilot.travel(-8);
		buttons.waitForAnyPress();
		navtest.goTo(wp1);
		
		buttons.waitForAnyPress();
	}

}
