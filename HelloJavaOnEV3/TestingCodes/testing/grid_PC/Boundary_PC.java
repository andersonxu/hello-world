package testing.grid_PC;

import pg09.features.GridTraveler;
import pg09.features.MessageSender;

/**
 * run as Java Application
 * 
 * A sim testing for the grid travel algorithm
 * Test the features of marking boundary, obstacle, and survivor
 * 
 * @author sunruoxi
 *
 */
public class Boundary_PC {
	public static void main(String[] args) throws Exception {
		GridTraveler gt = new GridTraveler(2, 2);
		MessageSender sender = new MessageSender("129.127.147.228", 3000);
		
//		gt.setCurrentGrid(gt.findGrid(0, 0));
//		
//		sender.send("Start");
//		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		gt.markArrivedGrid();
//		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		gt.markUnreachableGrid(GridTraveler.BOUNDARY);
//		sender.send("boundary!");
//		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		gt.markArrivedGrid();
//		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		gt.markUnreachableGrid(GridTraveler.SURVIVOR);
//		sender.send("survivor!");
//		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		gt.markArrivedGrid();
//		
//		gt.setNextGrid();
//		sender.send(gt.statusToString());
//		gt.markUnreachableGrid(GridTraveler.OBSTACLE);
//		sender.send("obstacle!");
//
//		while(gt.getCurrentGrid() != null) {
//			gt.setNextGrid();
////			sender.send(gt.statusToString());
//			gt.markArrivedGrid();	
//		}
		
		sender.send(gt.mapToString());

		sender.send("END");
		sender.close();
		
	}
}
