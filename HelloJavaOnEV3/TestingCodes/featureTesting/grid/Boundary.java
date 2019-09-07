package featureTesting.grid;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.components.Sensor;
import pg09.features.Actions;
import pg09.features.GridTraveler;
import pg09.features.MessageSender;

public class Boundary {
	public static void main(String[] args) throws Exception {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(5.8, 5.347, "C", "D");
		MessageSender sender = new MessageSender("10.201.133.140", 3000);
		GridTraveler gt = new GridTraveler(3, 1);
		Sensor us = new Sensor("S1", Sensor.SONIC);
		
		pilot.setSpeed(5, 30);
		pilot.setStep(15);
		gt.setGridWidth(15);
		gt.setCurrentGrid(gt.findGrid(0, 0));
		
		sender.send("Start");
		
		while(gt.getCurrentGrid() != null && ev3.escapeIsUp()) {
			gt.setNextGrid();
			sender.send(gt.statusToString());
			
			Actions.moveToNextGrid(pilot, us, gt, sender, ev3);
			
			gt.markArrivedGrid();
		}
		sender.send("END");
		sender.close();
	}
}
