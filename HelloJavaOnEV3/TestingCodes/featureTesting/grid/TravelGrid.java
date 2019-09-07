package featureTesting.grid;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.features.Actions;
import pg09.features.GridTraveler;

public class TravelGrid {

	public static void main(String[] args) {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(4.2, -9, "C", "D");
		GridTraveler gt = new GridTraveler(2, 1);
		
		pilot.setSpeed(5, 30);
		pilot.setStep(20);
		gt.setGridWidth(20);
		
		ev3.pressAnyKey();
		
		while(gt.getCurrentGrid() != null) {
//			System.out.print("set next grid -> ");
			gt.setNextGrid();
			Actions.moveToNextGrid(pilot, gt);
//			gt.printStatus();
//			System.out.print("travel...");
//			System.out.print("arrivd. ");
			gt.markArrivedGrid();
		}
		
	}

}
