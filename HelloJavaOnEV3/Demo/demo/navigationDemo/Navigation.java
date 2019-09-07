package demo.navigationDemo;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;

public class Navigation {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);

	public static void main(String[] args) throws InterruptedException {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		buttons.waitForAnyPress();
		
		String message = "MOTOR Testing: ";
		// set up the motor A speed, i.e. 100 degrees per second
		LEFT_MOTOR.setSpeed(200); 
		// motor A moving forward
		LEFT_MOTOR.forward();
		
		// displaying number of degrees rotated on the LCD until an ESCAPE button is pressed!
		while (buttons.getButtons() != Keys.ID_ESCAPE) { 
			LCD.clear();
			LCD.drawString(message, 0, 1); 
			LCD.drawInt(LEFT_MOTOR.getTachoCount(), 0, 2);
			Thread.sleep(1000); 
			LCD.refresh();
		}

	}

}
