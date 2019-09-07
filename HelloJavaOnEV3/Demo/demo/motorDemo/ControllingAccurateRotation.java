package demo.motorDemo;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;

//1. Set the speed to 720.
//2. Rotate the motor one complete revolution.
//3. Display the tachometer reading on the LCD, row 0.
//4. Rotate the motor to angle 360.
//5. Display the tachometer reading on the LCD, row 1.
//6. Wait for a button press to give you time to read the LCD.
//7. Clear the LCD. 

public class ControllingAccurateRotation {

	public static void main(String[] args) {
		
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		Keys buttons = ev3brick.getKeys();
		buttons.waitForAnyPress();
		                
		// set motor to move 720 degrees per second
		LEFT_MOTOR.setSpeed(720);
		// rotate the motor one full revolution
		LEFT_MOTOR.rotate(360);
		// display the tacho count in row 0 
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 0);
		
		// rotate to the angle 360
		LEFT_MOTOR.rotateTo(360);
		// display the tacho count in row 1 
		LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 1);
		
		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		LCD.clear();
		LEFT_MOTOR.close();
	}

}
