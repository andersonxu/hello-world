package featureTesting.grid;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.features.Actions;
import pg09.features.GridTraveler;
import pg09.features.MessageSender;

/**
 * Testing: travel in a grid map, and send message to PC when a grid point is reached
 * The obstacles can be avoided.
 * 
 * @author sunruoxi
 *
 */
public class Grid_Message {

	public static void main(String[] args) throws Exception {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(5.6, 5.68, "C", "D");
//		ev3.pressAnyKey();
		MessageSender sender = new MessageSender("10.201.135.191", 3000);
		GridTraveler gt = new GridTraveler(2, 1);
		
		pilot.getPilot().setAngularSpeed(30);
		pilot.getPilot().setLinearSpeed(5);
		
		pilot.setStep(20);
		gt.setGridWidth(20);
		gt.setCurrentGrid(gt.findGrid(0, 0));
		
		ev3.pressAnyKey();
		Brick.delay(1500);
		
		sender.send("Start");
		
		while(gt.getCurrentGrid() != null) {
			gt.setNextGrid();
			sender.send(gt.statusToString());
			
//			sender.send("travel...");
			Actions.moveToNextGrid(pilot, gt);
//			sender.send("arrivd. ");
			
			gt.markArrivedGrid();
//			ev3.pressAnyKey();
		}
		sender.send("END");
		sender.close();
	}

}
