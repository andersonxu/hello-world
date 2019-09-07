package featureTesting.grid;

import pg09.components.Brick;
import pg09.components.Pilot;
import pg09.features.Actions;
import pg09.features.Grid;
import pg09.features.GridTraveler;

/**
 * Testing: travel by one grid
 * 
 * @author sunruoxi
 *
 */
public class TravelOneGrid {
	public static void main(String[] args) {
		Brick ev3 = new Brick();
		Pilot pilot = new Pilot(4.2, -9, "C", "D");
		GridTraveler gt = new GridTraveler(1, 1);
		
		pilot.setSpeed(5, 30);
		pilot.setStep(2);
		pilot.setDirection(0);
		gt.setGridWidth(20);
		Grid a = new Grid(0,0);
		Grid b = new Grid(-1,0);
		
		gt.setCurrentGrid(a);
		gt.setNextGrid(b);
		
		ev3.pressAnyKey();
		
		Actions.moveToNextGrid(pilot, gt);
		
	}
}
