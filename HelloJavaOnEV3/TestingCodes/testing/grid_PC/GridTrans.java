package testing.grid_PC;

import pg09.features.GridTraveler;
import pg09.features.MessageSender;

public class GridTrans {
	public static void main(String[] args) throws Exception {
		// create a grid matrix, set the initial direction of the robot as RIGHT
		GridTraveler gt = new GridTraveler(4, 2);
		// to get the IP address on Mac: ifconfig | grep "inet " | grep -v 127.0.0.1
		MessageSender sender = new MessageSender("129.127.147.228", 3000);
		
		gt.setTransGridUp(gt.findGrid(1,1));
		
		gt.setCurrentGrid(gt.findGrid(0, 0));
		
		sender.send("Start");
		
		gt.setNextGrid();
		sender.send(gt.statusToString());
		gt.markArrivedGrid();
		
		gt.setNextGrid();
		sender.send(gt.statusToString());
		gt.markUnreachableGrid(GridTraveler.BOUNDARY);
		sender.send("boundary!");
		
		gt.setNextGrid();
		sender.send(gt.statusToString());
		gt.markArrivedGrid();
		
		gt.setNextGrid();
		sender.send(gt.statusToString());
		gt.markUnreachableGrid(GridTraveler.SURVIVOR);
		sender.send("survivor!");
		
		gt.setNextGrid();
		sender.send(gt.statusToString());
		gt.markArrivedGrid();
		
		gt.setNextGrid();
		sender.send(gt.statusToString());
		gt.markUnreachableGrid(GridTraveler.OBSTACLE);
		sender.send("obstacle!");

		while(gt.getCurrentGrid() != null) {
			gt.setNextGrid();
			sender.send(gt.statusToString());
			gt.markArrivedGrid();	
		}
		
		// send END to let the message server close
		
		
		sender.send(gt.mapToString());
		
		sender.send("END");
		sender.close();
		
	}
}
