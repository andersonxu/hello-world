package testing.grid_PC;

import pg09.features.*;

public class GridTravelAlgorithmTest {
	
	public static void main(String[] args) {
		GridTraveler gt = new GridTraveler(1, 2);

//		System.out.println(gt.mapToString());
//		gt.getGrids().remove(gt.findGrid(1,1));
//		gt.getGrids().remove(gt.findGrid(-1,1));
//		
		gt.setCurrentGrid(gt.findGrid(0, 0));
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.markTransGridUp();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markUnreachableGrid(GridTraveler.OBSTACLE);
		System.out.println("Obstacle " + gt.gridToString(gt.getNextGrid()));
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markUnreachableGrid(GridTraveler.SURVIVOR);
		System.out.println("Survivor " + gt.gridToString(gt.getNextGrid()));
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markUnreachableGrid(GridTraveler.BOUNDARY);
		System.out.println("Boundary " + gt.gridToString(gt.getNextGrid()));
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
		
		gt.markArrivedGrid();
		gt.setNextGrid();
		gt.printStatus();
	
		int i=0;
		while(gt.getNextGrid() != null && i <=100) {
			gt.markArrivedGrid();
			if(gt.getCurrentGrid() != null)
				gt.setNextGrid();
			gt.printStatus();
			i++;
		}
	
		System.out.println(gt.mapToString());
	}
}
