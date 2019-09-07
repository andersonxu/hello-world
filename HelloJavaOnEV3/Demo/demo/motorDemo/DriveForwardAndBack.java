package demo.motorDemo;

//import lejos.hardware.*;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;

//1. Run motors A and C in the forward direction.
//2. Display FORWARD in the top line.
//3. Wait until a button is pressed.
//4. Run motors A and C backward.
//5. Display BACKWARD in next line.
//6. Wait until a button is pressed.
//7. Stop both motors A and C

public class DriveForwardAndBack {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B); 
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
		
		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		// instantiated LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		
		// block the thread until a button is pressed
        buttons.waitForAnyPress();
        
        //move robot forward and display status on LCD, change directions when button is pressed 
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.forward(); 
        LCD.drawString("FORWARD", 0, 0);
        
        // block the thread until a button is pressed
        buttons.waitForAnyPress();
        //move robot backward and display status on LCD 
        LEFT_MOTOR.backward();
        RIGHT_MOTOR.backward(); 
        LCD.drawString("BACKWARD", 0, 1);
        
        // block the thread until a button is pressed
        buttons.waitForAnyPress();
        //stop robot and display status on LCD 
        LEFT_MOTOR.stop();
        RIGHT_MOTOR.stop(); 
        LCD.drawString("STOP", 0, 2);
        
        // exit program after any button pressed
        buttons.waitForAnyPress();
        
        LEFT_MOTOR.close();
        RIGHT_MOTOR.close();
	}

}
