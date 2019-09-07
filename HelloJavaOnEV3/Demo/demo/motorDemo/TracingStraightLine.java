package demo.motorDemo;

import lejos.hardware.BrickFinder; 
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

// could be used for speed and wheel diameter testing
//1. Create a new stopwatch.
//2. Start the two motors A and C running forward.
//3. Calculate the elapsed time until it reaches 10,000 ms, displaying the elapsed time on the LCD screen.
//4. Stop the two motors.
//5. Calculate the ratio of distance to time in centimeters per second.

public class TracingStraightLine {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);
	
	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		Keys buttons = ev3brick.getKeys();
		   
		buttons.waitForAnyPress();
		// instantiated a stopwatch class for setting up the 
		Stopwatch watch = new Stopwatch();
		// Begin running both motors 
		LEFT_MOTOR.forward(); 
		RIGHT_MOTOR.forward();
		
		// Clear the screen 
		LCD.clear();
		// Reset the time on the watch
		watch.reset();
		
		// Display the elapsed time on the LCD until 10000ms 
		while (watch.elapsed() < 10000) {
			Thread.yield();
			LCD.drawString("" + watch.elapsed(), 0, 0);
		}
		
		// Stop the motors after 5 seconds 
		LEFT_MOTOR.stop(); 
		RIGHT_MOTOR.stop();
		buttons.waitForAnyPress();
	}

}
