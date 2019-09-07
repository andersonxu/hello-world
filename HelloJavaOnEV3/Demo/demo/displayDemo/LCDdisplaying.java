//An example to test LCD displaying methods

package demo.displayDemo;

import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;

public class LCDdisplaying {

	public static void main(String[] args) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		// instantized LCD class for displaying
		TextLCD LCD = ev3brick.getTextLCD();
		
		// drawing text on the LCD screen based on coordinates
        LCD.drawString("Free RAM:", 0, 0);
        
        // displaying free memory on the LCD screen 
        LCD.drawInt((int) Runtime.getRuntime().freeMemory(), 0, 1);
        
        buttons.waitForAnyPress();
	}

}
