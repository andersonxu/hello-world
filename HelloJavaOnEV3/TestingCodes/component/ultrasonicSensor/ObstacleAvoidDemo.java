/** obstacle avoiding */

package component.ultrasonicSensor;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
//import lejos.utility.Delay;
import lejos.hardware.lcd.TextLCD;

public class ObstacleAvoidDemo { 
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);

	public static void main(String[] args) {
		//int delay = 500;
		float distance;
		double disHold = 15, disSafe = 2;
		//int avoidRotation = 30;
		double diameter = 4.2, offset = -9;
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();
		
		MovePilot pilot = createDiffMovePilot(diameter, offset);
	
		setSpeed(pilot, 2, 10);
		
		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider sonicdistance = sonicSensor.getDistanceMode();
		
    	lcddisplay.drawString("Press any key", 0, 1);
		buttons.waitForAnyPress();
		
		distance = readSensor(sonicdistance, lcddisplay);
		
		while (Button.ESCAPE.isUp()) {
        	
        	pilot.travel(3);
        	distance = readSensor(sonicdistance, lcddisplay);
        	
        	if (distance <= disHold) {
        		pilot.travel(-disSafe);
				pilot.rotate(randomRedirection());
			}
			//Delay.msDelay(delay);
		}
		
		sonicSensor.close();
	}
	
	public static float readSensor(SampleProvider sp, TextLCD LCD) {
		float dis = 0;
		float[] sample = new float[sp.sampleSize()];
		
		sp.fetchSample(sample, 0);
		dis = sample[0]*100;
		
		LCD.clear();
    	LCD.drawString("distance: " + String.format("%.2f", dis) + "cm", 0, 1);
		
		return dis;
	}
	
	public static int randomRedirection() {
		if(Math.random()>0.5) {
			return 30;
		}
		else {
			return -30;
		}
	}
	
	public static MovePilot createDiffMovePilot(double diameter, double offset) {
		Wheel[] wheels = {WheeledChassis.modelWheel(LEFT_MOTOR, diameter).offset(-offset), 
				WheeledChassis.modelWheel(RIGHT_MOTOR, diameter).offset(offset)};
		
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		
		return new MovePilot(chassis);
	}
	
	public static void setSpeed(MovePilot pilot, float linearSpeed, float angularSpeed) {
		pilot.setLinearSpeed(linearSpeed);
		pilot.setAngularSpeed(angularSpeed);
	}
}
