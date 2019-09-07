package pg09.features;

//package testing.uphill;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;

/**
 * Testing: Go or Down upstairs follow the line
 * 
 * @author Zhengjian Li
 *
 */
public class Uphillfollowline {

	private  static Brick ev3;
	private  static Pilot pilot;
	private  static Sensor sensor;
	
	
	private static String currentColor;
	private static int currentColorID;
	private static String pastColor;
	private static float currentColorValue;
	private static boolean ifChange = false;
	private static int changeNumber = 0;
	private static String lastfind = ""; 
	private static int guessmode = 1;//1 up 0 down
	private static int continuright = 0;
	private static int continuleft = 0;
	private static int first_direction;
	private static boolean ifFind = false; 	
	private static Thread rotate;


	
	public int upordownStairs(Pilot cpilot, Sensor csensor, Brick brick, MessageSender s) {
		ev3 = brick;
		pilot = cpilot;
		sensor = csensor;
		
	    pilot.getPilot().setLinearSpeed(6);
	    pilot.getPilot().setAngularSpeed(40);
    
	    Thread t1 = new Thread() {
	    	public void run() {    		
	    		while(!isInterrupted()) {
		    		currentColor = sensor.readColor(); 				
					if((currentColor == "BLACK" || currentColor == "WHITE") && (pastColor == "RED"))
					{
						changeNumber = 1;					
					}
		    		currentColorID = sensor.readColorID();
		    		currentColorValue = sensor.readColorValue();				
		    		pastColor = currentColor;	    		
		    	}
    		}
    	};
    	
    	Thread t2 = new Thread() {
	    	public void run() {    		
	    		while(!isInterrupted()) {
	    			
	    			int find_line = 0;//the line is on the left of robot --> 1 / right --> 2
	    			first_direction  = 0;
	    			
	    			if(lastfind == "") first_direction = -60;
	    			else if(lastfind == "left") first_direction = -60;//last time find the line while turn left
	    			else first_direction = 60;//last time find the line while turn right
	    			
	    			if(continuright >= 2) first_direction = 60;
	    			else if(continuleft >= 2) first_direction = -60;
	    			else {
	    				//do nothing
	    			}
	    			

		    		if(currentColor == "WHITE")
		    			{    				
		    			    
		    			    
		    				pilot.getPilot().stop();
		    				ifFind = false;
		    				
		    				boolean ifotherDirection = true;   				
		    				int i =3;
		    				
		    				rotate = new Thread() {
			    				public void run() {
			    					while(!isInterrupted()) {
			    						if(currentColor != "WHITE") 
			    							{	
			    								pilot.getPilot().stop();
			    								ifFind = true;
			    								break;
			    							}
			    						}
			    					}
			    			};
			    			
			    			//first find the line in one rotate direction
		    				rotate.start();
		    				pilot.getPilot().rotate(first_direction);			
		    				rotate.interrupt();
		    				
		    				if(ifFind){
	    						ifotherDirection = false;	    						
	    						if(first_direction > 0 ) {
	    							//if find the line while turn right next time first check left side
	    							find_line = 2;
	    							lastfind = "right";
	    							continuright += 1;
	    							continuleft = 0;
	    						}
	    						else {
	    							//if find the line while turn left next time first check right side
	    							find_line = 1;
	    							lastfind = "left";
	    							continuleft += 1;
	    							continuright = 0;
	    						}
	    					}
		    				
		    				//if didn't find the line in the last rotate
		    				if(ifotherDirection) {
		    					pilot.getPilot().rotate(-(first_direction - 2));
		    					
		    					rotate = new Thread() {
		    						public void run() {
		    							while(!isInterrupted()) {
		    								if(currentColor != "WHITE") 
		    									{	
		    										pilot.getPilot().stop();
		    										ifFind = true;
		    										break;
		    									}
		    							}
		    						}
		    					};
		    					
		    					rotate.start();
		    					pilot.getPilot().rotate(-first_direction);
			    				rotate.interrupt();
			    				
			    				if(ifFind) {
		    						if(first_direction > 0 ) {
		    							//if find the line while turn left next time first check right side
		    							find_line = 1;
		    							lastfind = "left";
		    							continuleft += 1;
		    							continuright = 0;
		    						}
		    						else {
		    							//if find the line while turn right next time first check left side
		    							find_line = 2;
		    							lastfind = "right";
		    							continuright += 1;
		    							continuleft = 0;
		    						}
		    						
		    					}		
		    				}
		    					    				
		    				//after find the black line
		    				/*if(find_line == 1) {
		    					pilot.getPilot().rotate(-5);
		    				}
		    				else {
		    					
		    					pilot.getPilot().rotate(5);
		    				}*/
		    							
		    				pilot.getPilot().forward();
		    			}//if current color != white
	    			}
	    		}
	    };
	   
	
	

	Thread t3 = new Thread() {
		public void run() {
			while(!isInterrupted()) {
				if(currentColor == "RED" && !ifChange) {
					//change color Sensor angular
					
					if(continuright > continuleft) guessmode = 1;
					else guessmode = 0;
					
					pilot.getPilot().stop();
					//when first find the red line, change the angular until it found the right weight
					if(guessmode == 1) {
						pilot.getMediumMotor().rotate(-60);
						pilot.getPilot().travel(6);
					}
					else {//guess mode == 0
						pilot.getMediumMotor().rotate(-60); 
						pilot.getPilot().travel(4);
						pilot.getMediumMotor().rotate(10);
						
					}
					
					//need test again until it stable
					pilot.getPilot().forward();
									
					try
					{
						if(guessmode == 1)
						{
							Thread.sleep(3000);	
						    pilot.getMediumMotor().rotate(20);
						    Thread.sleep(1000);	
						    pilot.getMediumMotor().rotate(20);
						    Thread.sleep(1000);	
						    pilot.getMediumMotor().rotate(20);
						    ifChange = true;
						}
						else {

							Thread.sleep(1000);	
						    pilot.getMediumMotor().rotate(20);
						    Thread.sleep(800);	
					        pilot.getPilot().travel(3);
						    pilot.getMediumMotor().rotate(25);
						    ifChange = true;
						}
					}
					catch(InterruptedException ex)
					{
					    //Thread.currentThread().interrupt();
					}
				}
			}
		}
	};
	
	
	
    t1.start();
	pilot.getPilot().travel(14);
	
	while(currentColor != "BLACK" && currentColor != "WHITE") {
		pilot.getPilot().travel(2);
	}
	
    ev3.displayClr("start finding line", 0, 0);
    t2.start();
    t3.start();
    
    pilot.getPilot().forward();		
    
	while((currentColor != "RED" || !ifChange ) && ev3.escapeIsUp())
	{
		if(ifChange)t3.interrupt();
	}
	
	pilot.getPilot().stop();
	System.out.println("check1");
	s.send("check1");
	
	t1.interrupt();
	t2.interrupt();
	
	pilot.getPilot().stop();
	Brick.delay(1000);
	
	
//	System.out.println("check2");
//	s.send("check2");
	
	//pilot.getPilot().stop();
	//pilot.getPilot().rotate(90 - csensor.getAngle()%90);
	//pilot.getPilot().travel(10);
//	System.out.println("check3");
//	s.send("check3");

	
//	pilot.getPilot().stop();
	
//	pilot.getPilot().rotate(90 - csensor.getAngle()%90);
//	pilot.getPilot().travel(30);
	s.send("check4");
	
	
	ev3.displayClr("up or downhilling ending", 0, 0);

//	return (int)(90 - csensor.getAngle()%90);
	
		return 0;
	}
	
}
