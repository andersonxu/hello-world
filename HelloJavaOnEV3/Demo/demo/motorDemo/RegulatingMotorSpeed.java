package demo.motorDemo;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

//1. Create a new stopwatch.
//2. Start the two motors A and C running at 1 revolution/second. (One revolution is 360.)
//3. Every 200 milliseconds, display all two tachometer count values in the same row.
//4. Repeat step 3 four times, using a different row each time.
//5. Print the maximum difference that you see between the motor tachometer counts.

public class RegulatingMotorSpeed {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	
	/*****************************************************************************
	* Displays the tachometer count for each motor 
	* @param row to print the count in
	* @return Returns the difference between the tacho counts of the two motors
	******************************************************************************/
	private static int displayTachoCounts(int row) {
		// store the tacho counts for the two motors
		int tachoCountLeft = LEFT_MOTOR.getTachoCount();
		int tachoCountRight = RIGHT_MOTOR.getTachoCount(); 
		// display
		LCD.drawString("M1: " + tachoCountLeft + " M2: " + tachoCountRight, 0, row);
		// return the difference in the tacho counts 
		return Math.abs(tachoCountLeft - tachoCountRight);
	}
	
	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		buttons.waitForAnyPress();
		
		// the row to print on
		int tachoRow = 0;
		// instantiated a stopwatch class for setting up the timer
		Stopwatch sw = new Stopwatch();
		// set the motor speed to 1 revolution per second 
		LEFT_MOTOR.setSpeed(360); 
		RIGHT_MOTOR.setSpeed(360);
		
		// motors moving forward 
		LEFT_MOTOR.forward(); 
		RIGHT_MOTOR.forward();

		// variables for determining the maximum difference in tacho counts
		int maxTachoCountDiff = Integer.MIN_VALUE; 
		int currTachoCountDiff;
		
		// perform four repetitions of the test 
		for (int i = 0; i < 4; i++) {
			// wait for 200 milliseconds 
			sw.reset();
			while (sw.elapsed() < 2000)
				Thread.yield();
			
			// display the tacho counts and reset the max, if changed 
			currTachoCountDiff = displayTachoCounts(tachoRow++);
			if (currTachoCountDiff > maxTachoCountDiff)
				maxTachoCountDiff = currTachoCountDiff;
		}
		
		// stop the motors 
		LEFT_MOTOR.stop(); 
		RIGHT_MOTOR.stop();
		
        // display the maximum difference in tacho counts, then wait for exit
		LCD.drawString("Max diff: " + maxTachoCountDiff, 0, tachoRow); 
		buttons.waitForAnyPress();
	}

}
