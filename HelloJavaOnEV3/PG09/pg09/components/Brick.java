package pg09.components;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;

public class Brick {
	private static EV3 ev3brick;
	private Keys btn;
	private TextLCD LCD;
	
	public Brick() {
		ev3brick = (EV3) BrickFinder.getLocal();
		this.btn = ev3brick.getKeys();
		this.LCD = ev3brick.getTextLCD();
	}
	
	public EV3 getBrick() {
		return ev3brick;
	}
	
	public Keys getButtons() {
		return this.btn;
	}
	
	public TextLCD getLCD() {
		return this.LCD;
	}
	
	public void pressAnyKey() {
		LCD.drawString("Press any key", 0, 1);
		btn.waitForAnyPress();
		LCD.clear();
	}
	 
	public void pressEnter() {
		LCD.drawString("Press Enter", 0, 1);
		while(escapeIsUp()) {
			
		}
		LCD.clear();
	}
	
	public boolean escapeIsUp() {
		return Button.ESCAPE.isUp();
	}
	
	public void clearLine(int n) {
		LCD.clear(n);
	}
	
	public void displayClr(String str, int x, int y ) {
		LCD.clear(y);
		LCD.drawString(str, x, y);
	}
	
	public void displayClr(String str, float num, int x, int y) {
		LCD.clear(y);
		LCD.drawString(str + String.format("%.2f", num), x, y);
	}
	
	public void display(String str, int x, int y ) {
		LCD.drawString(str, x, y);
	}
	
	public void displayClr(String str, float[] num, int x, int y) {
		LCD.clear(y);
		for(int i=0;i<num.length;i++) {
			LCD.drawString(String.format("%.2f", num[i]), x+str.length()+4*i, y);
		}
	}
	
	public void display(String str, float num, int x, int y) {
		LCD.drawString(str + String.format("%.2f", num), x, y);
	}
	
	public void display(String str, float[] num, int x, int y) {
		LCD.drawString(str, x, y);
		for(int i=0;i<num.length;i++) {
			LCD.drawString(String.format("%.2f", num[i]), x+str.length()+4*i, y);
		}
	}
	
	public void displayColor(String str, String color, int x, int y) {
		if(color == "BLACK" || color == "RED")
		{
			x = 1;
			y = 1;
		}
		LCD.drawString(str, x, y);
	}
	
	public static void delay(int ms) {
		Delay.msDelay(ms);
	}
	
	public static void LEDBlinkGreen() {
		Button.LEDPattern(4);
	}
	
	public static void LEDClose() {
		Button.LEDPattern(0);
	}
}
