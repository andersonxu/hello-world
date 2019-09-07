// travel in a two-dimensional plane
//starting at A(0, 0),
//go to B(50, 50),
//then, go back A(0, 0)
//then go to C(-50, 50),
//then finally come back to A(0, 0).

package demo.navigator;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis; 
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.TextMenu;

public class Travel2DPlane {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	
	public static void main(String[] args) {
		// menu options for displaying navigator controlling 
		String[] menuItems = new String[1];
		menuItems[0] = "Task 1: Navigator";
		
		// display menu
		TextMenu menu = new TextMenu(menuItems); 
		menu.setTitle("NavRobot");
		
		Wheel[] wheels = {WheeledChassis.modelWheel(LEFT_MOTOR, 2.8).offset(-9), 
				WheeledChassis.modelWheel(RIGHT_MOTOR, 2.8).offset(9)};
		
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);	
		MovePilot pilot = new MovePilot(chassis);
		Navigator navbot = new Navigator(pilot);
		
		// run routine based on menu choice 
		switch (menu.select()) {
			case 0:
			navigate(navbot);
			break; 
		}
	}
	
	private static void navigate(Navigator nav) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		TextLCD lcddisplay = ev3brick.getTextLCD();
		
		// set up way points
		Waypoint wp1 = new Waypoint(50, 50); 
		Waypoint wp2 = new Waypoint(0, 0); 
		Waypoint wp3 = new Waypoint(-50, 50);
		
		// clear menu
        lcddisplay.clear();
        
        // display current location
        lcddisplay.drawString("At 0, 0", 0, 0);
        lcddisplay.drawString("Press ENTER", 0, 2);
        lcddisplay.drawString("to continue", 0, 3);
        
        // wait until ENTER key is pressed
        while (buttons.waitForAnyPress() != Keys.ID_ENTER)
        	Thread.yield();
        
        // navigate to way point one, display new location
        lcddisplay.clear();
        nav.goTo(wp1);
        lcddisplay.drawString("At 50, 50", 0, 0);
        lcddisplay.drawString("Press ENTER", 0, 2);
        lcddisplay.drawString("to continue", 0, 3);
        
        // wait until ENTER key is pressed
        while (buttons.waitForAnyPress() != Keys.ID_ENTER)
        	Thread.yield();
        
        // navigate to way point two, display new location
        lcddisplay.clear();
        nav.goTo(wp2);
        lcddisplay.drawString("At 0, 0", 0, 0);
        lcddisplay.drawString("Press ENTER", 0, 2);
        lcddisplay.drawString("to continue", 0, 3);
        
        // wait until ENTER key is pressed
        while (buttons.waitForAnyPress() != Keys.ID_ENTER)
        	Thread.yield();
        
        // navigate to way point 3, display new location
        lcddisplay.clear();
        nav.goTo(wp3);
        lcddisplay.drawString("At -50, 50", 0, 0);
        lcddisplay.drawString("Press ENTER", 0, 2);
        lcddisplay.drawString("to continue", 0, 3);
        
        // wait until ENTER key is pressed
        while (buttons.waitForAnyPress() != Keys.ID_ENTER)
        	Thread.yield();
        
        // navigate to way point 2, display new location
        lcddisplay.clear();
        nav.goTo(wp2);
        lcddisplay.drawString("At 0, 0", 0, 0);
        lcddisplay.drawString("Press ENTER", 0, 2);
        lcddisplay.drawString("to exit", 0, 3);
        
        // wait until ENTER key is pressed
        while (buttons.waitForAnyPress() != Keys.ID_ENTER)
        	Thread.yield();

	}

}
