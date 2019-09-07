package demo.motorDemo;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; import lejos.hardware.port.MotorPort;

//1. Start a rotation of 7,200 degrees.
//2. While the motor is rotating, display the Tachometer count at the position of row 0.
//3. When a button is pressed, stop the motor.
//4. After the motor has stopped, display the Tachometer count at the position of row 0.
//5. Record the two numbers read from Tachometers, and then wait for a button press to exit the program.


public class InterruptingRotation {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
        Keys buttons = ev3brick.getKeys();
        // sound two beeps before starting program
        Sound.twoBeeps();
        
        buttons.waitForAnyPress();
        
        // rotate 7200 degree during which the method has returned value all the time
        LEFT_MOTOR.rotate(7200, true);
        
        // return to true if the motor is always rotating
        while (LEFT_MOTOR.isMoving()) {
	        // display and refresh the tachometer reading all the time
	        LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 0);
            // determining if there is any button pressed, if yes then stop the motor
	        if (buttons.readButtons() > 0) 
	        	LEFT_MOTOR.stop();
        }
        // wait until the motor fully stopped 
        while (LEFT_MOTOR.getRotationSpeed() > 0)
        	;
        
        // display the tachometer reading after motor fully stopped
        LCD.drawString("Tacho Read: " + LEFT_MOTOR.getTachoCount(), 0, 1); 
        // block the thread until a button is pressed
        buttons.waitForAnyPress();
        
        LEFT_MOTOR.close();
    }
}
