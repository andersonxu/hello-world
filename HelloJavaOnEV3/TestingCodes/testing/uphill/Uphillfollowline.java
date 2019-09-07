package testing.uphill;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;

/**
 * Testing: Go upstairs follow the line
 * 
 * @author Zhengjian Li
 *

 */
public class Uphillfollowline {
	

	
	static Brick ev3 = new Brick();
	static Pilot pilot = new Pilot(4.2, -9, "C", "D");
	//only use color sensor in this mode
	static Sensor sensor = new Sensor("S1", "S3");
	static String endingColor = "";
	static String currentColor;
	static int currentColorID;
	static float currentColorValue;
	static float currentDistance;
	static int ifChangeSenorAngular = 0;
	static float changeSenorAngularDistance = (float) 3.00;
	static boolean ifFound = false;
	static boolean isArrive = false;
	
	public static void main(String[] args) throws Exception{
	// press any key to start
	
	endingColor = "RED";
	ev3.pressAnyKey();
	
    pilot.getPilot().setLinearSpeed(5);
    pilot.getPilot().setAngularSpeed(10);
    
    Thread t1 = new Thread() {
    	public void run() {    		
    		while(!isInterrupted()) {
	    		currentColor = sensor.readColor(); 				
				currentColorID = sensor.readColorID();
				currentColorValue = sensor.readColorValue();
				ev3.display(currentColor, currentColorValue, 0, 0);
    			}
    		}
    	};
    	
    Thread t2 = new Thread() {
	    	public void run() {    		
	    		while(!isInterrupted()) {
		    			if(currentColor == "WHITE")
		    			{
		    				int i = 25;
		    				int j = 25;
		    				boolean ifotherDirection = true;
		    				pilot.getPilot().stop();
		    				while(i>0){
		    					pilot.getPilot().rotate(-1);
		    					if(currentColor != "WHITE") {
		    						ifotherDirection = false;
		    						break;
		    					}
		    					i--;
		    				}	    				
		    				if(ifotherDirection)
		    				{
		    					pilot.getPilot().rotate((25-i));
		    					while(j>0){
			    					pilot.getPilot().rotate(1);
			    					if(currentColor != "WHITE") {
			    						break;
			    					}
			    					j--;
			    				}
		    				}
		    				if(ifotherDirection) pilot.getPilot().rotate(5);
		    				else pilot.getPilot().rotate(-5);
		    				pilot.getPilot().forward();
		    			}
	    			}
	    		}
	    };
	   
	//thread 3 for getting distance to the stair all time
	Thread t3 = new Thread() {
		public void run() {
			while(!isInterrupted()) {
				currentDistance = sensor.readUltraSensor();
			}
		}
	};
	
	
	Thread t4 = new Thread() {
		public void run() {
			while(!isInterrupted()) {
				if(currentDistance <  changeSenorAngularDistance && ifChangeSenorAngular == 0) {
					//change color senor angular
					ifChangeSenorAngular = 1;
					
				}
				else if(currentDistance > changeSenorAngularDistance && ifChangeSenorAngular == 1) {
					//change color senor angular back to normal
					ifChangeSenorAngular = 2;
				}
				else {
					//do nothing
				}
			}
		}
	};
	
    t1.start();
    t2.start();
    t3.start();
    t4.start();
    
    pilot.getPilot().forward();		
    
	while(currentColor != endingColor)
	{
		
	}
	
	pilot.getPilot().stop();
	t1.interrupt();
	t2.interrupt();
	t3.interrupt();
	t4.interrupt();
	
	

    sensor.close(); 
    
    ev3.pressAnyKey();
	}
}
