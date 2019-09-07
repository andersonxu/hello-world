package testing.grid_PC;

import pg09.features.GridTraveler;
import pg09.features.MessageSender;

/**
 * run as Java Application
 * 
 * A sim testing working on PC-end, to test the grid travel algorithm
 * A message server （GossipServer.java) must be running before the execute of this test starts
 * 
 * @author sunruoxi
 *
 */
public class GridTest_Message_PC {
	
	
	public static void main(String[] args) throws Exception {
		// create a grid matrix, set the initial direction of the robot as RIGHT
		GridTraveler gt = new GridTraveler(1, 2);
		gt.setTransGridUp(gt.findGrid(1, 0)); 
		// to get the IP address on Mac: ifconfig | grep "inet " | grep -v 127.0.0.1
		MessageSender sender = new MessageSender("172.20.10.6", 3000);
		GridMap map = new GridMap();
		int xpos, ypos;
		
		// set start grid as (0,0)
		sender.send("Start");
		
		gt.setCurrentGrid(gt.findGrid(0, 0));
		if(gt.getCurrentGrid() != null) gt.setNextGrid();
		sender.send(gt.statusToString());
		
		int i = 0;
		while(gt.getNextGrid() != null && i <=100) {
			xpos = gt.getNextGrid().getxPos();
			ypos = gt.getNextGrid().getyPos();
			
			if(map.findAvailable(xpos, ypos) != null) {
				gt.markArrivedGrid();
//				if(gt.getCurrentGrid().getxPos() == 0 && gt.getCurrentGrid().getyPos() == 0) {
//					gt.markTransGridUp();
//				}
			}
			else if(map.findObstacle(xpos, ypos) != null) {
				gt.markUnreachableGrid(GridTraveler.OBSTACLE);
				sender.send("Obstacle " + gt.gridToString(gt.getNextGrid()));
			}
			else if(map.findSurvivor(xpos, ypos) != null) {
				gt.markUnreachableGrid(GridTraveler.SURVIVOR);
				sender.send("Survivor " + gt.gridToString(gt.getNextGrid()));
			}
			else {
				gt.markUnreachableGrid(GridTraveler.BOUNDARY);
				sender.send("Boundary " + gt.gridToString(gt.getNextGrid()));
			}
			
			if(gt.getCurrentGrid() != null) gt.setNextGrid();
			sender.send(gt.statusToString());
//			sender.send(gt.gridsToString(gt.getGrids()));
			i++;
		}
		
		System.out.println(gt.mapToString());
		
		// send END to let the message server close
		sender.send("END");
		sender.close();
		
	}	
}
