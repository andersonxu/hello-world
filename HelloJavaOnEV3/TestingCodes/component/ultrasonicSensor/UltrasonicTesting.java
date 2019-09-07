//an example for ultrasonic sensor testing

package component.ultrasonicSensor;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
//import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor; 
import lejos.robotics.SampleProvider;

public class UltrasonicTesting {

	public static void main(String[] args) throws Exception {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
        TextLCD lcddisplay = ev3brick.getTextLCD();
        
        buttons.waitForAnyPress();
        
        // get a port instance
        // Port portS1 = ev3brick.getPort("S1");
        
        // Get an instance of the Ultrasonic EV3 sensor 
        EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
        
        // get an instance of this sensor in measurement mode
        SampleProvider sonicdistance = sonicSensor.getDistanceMode();
        
        // initialize an array of floats for fetching samples 
        float[] sample = new float[sonicdistance.sampleSize()];
        
        // fetch a sample
        
        
        while (buttons.getButtons() != Keys.ID_ESCAPE) { 
        	sonicdistance.fetchSample(sample, 0);
        	lcddisplay.clear();
        	lcddisplay.drawString("distance: " + sample[0], 0, 1);
        	try {
        		Thread.sleep(1000);
        	}
        	catch (Exception e) {
        		
        	}
        }
        
        sonicSensor.close();
	}
}
