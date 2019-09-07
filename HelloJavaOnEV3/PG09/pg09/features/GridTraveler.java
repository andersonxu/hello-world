package pg09.features;

import java.util.HashSet;
import java.util.Iterator;

public class GridTraveler {
	public static final int RIGHT = 0;
	public static final int LOWER = 1;
	public static final int LEFT = 2;
	public static final int UPPER = 3;
	
	public static final int OBSTACLE = 0;
	public static final int BOUNDARY = 1;
	public static final int SURVIVOR = 2;
	
	private float gridWidth;

	private int direction;
	private Grid currentGrid;
	private Grid nextGrid;
	private Grid transGridUp;
	private Grid transGridDown;
	private int currentFloor;
	private int nGrid;
	private HashSet<Grid> grids;
	private HashSet<Grid> canGoThrough;
	private HashSet<Grid> available;
	private HashSet<Grid> obstacles;
	private HashSet<Grid> boundaries;
	private HashSet<Grid> survivors;
//	private HashSet<Grid> transPoints;
	
	public GridTraveler(int nGrid, int nfloor) {
		this.nGrid = nGrid;
		this.grids = new HashSet<Grid>();
		this.obstacles = new HashSet<Grid>();
		this.boundaries = new HashSet<Grid>();
		this.survivors = new HashSet<Grid>();
		this.available = new HashSet<Grid>();
		this.canGoThrough = new HashSet<Grid>();
		this.currentFloor = 0;
		
		// initial the map according to nGrid
		for(int i=0;i<nfloor;i++) {
			for(int j=-nGrid+i*2*(nGrid+1);j<=nGrid+i*2*(nGrid+1);j++) {
				for(int k=-nGrid;k<=nGrid;k++) {
					Grid tmp = new Grid(j, k);
					this.grids.add(tmp);
					this.available.add(tmp);
					this.canGoThrough.add(tmp);
				}
			}
		}
		
		// now only support nfloor = 1 or 2
		// set the trans down point
		this.transGridUp = findGrid(2*(nGrid+1), 0);
//		this.transGridUp = findGrid(0,0); // for test only;
	
		this.direction = RIGHT;
	}
	
	public Grid findGrid(int x, int y) {
		for(Grid i : grids) {
			if(i.getxPos() == x && i.getyPos() == y) 
				return i;
		}
		return null;
	}
	
	public Grid findGrid(Grid g) {
		for(Grid i : grids) {
			if(i.getxPos() == g.getxPos() && i.getyPos() == g.getyPos()) 
				return i;
		}
		return null;
	}
	
	public Grid findAvailable(Grid g) {
		if(available != null) {
			for(Grid i : available) {
				if(i.getxPos() == g.getxPos() && i.getyPos() == g.getyPos()) 
					return i;
			}
		}
		return null;
	}
	
	public Grid findAvailable(int x, int y) {
		if(available != null) {
			for(Grid i : available) {
				if(i.getxPos() == x && i.getyPos() == y) 
					return i;
			}
		}
		return null;
	}
	
	public Grid findCanGoThrough(int x, int y) {
		if(canGoThrough != null) {
			for(Grid i : canGoThrough) {
				if(i.getxPos() == x && i.getyPos() == y) 
					return i;
			}
		}
		return null;
	}
	
	public Grid findAvailableNeighbour() {
		int x = currentGrid.getxPos();
		int y = currentGrid.getyPos();
		
		HashSet<Grid> neighbours = new HashSet<Grid>();
		
		neighbours.add(findAvailable(x+1,y));
		neighbours.add(findAvailable(x-1,y));
		neighbours.add(findAvailable(x,y+1));
		neighbours.add(findAvailable(x,y-1));
		neighbours.remove(null);
		
		Iterator<Grid> it = neighbours.iterator();
		switch(neighbours.size()) {
		case 1:
			return it.next();
		case 2:
			if(Math.random() > 0.5) {
				return it.next();
			}
			else {
				it.next();
				return it.next();
			}
		case 3:
			if(Math.random()*3 > 1) {
				return it.next();
			}
			else if(Math.random()*2 > 1) {
				it.next();
				return it.next();
			}
			else {
				it.next();
				it.next();
				return it.next();
			}
		}
		
		return null;
	}
	
	public void setNextGrid() {
		int x = currentGrid.getxPos();
		int y = currentGrid.getyPos();
		
		switch(this.direction) {
		case RIGHT:
			x++; break;
		case LEFT:
			x--; break;
		case UPPER:
			y--; break;
		case LOWER:
			y++; break;
		}
		
		// if: the next grid in current direction is visited
		if((this.nextGrid = findAvailable(x,y)) == null) {
			// if: there is no available neighbour of current grid
			if((this.nextGrid = findAvailableNeighbour()) == null) {
				// if: has unvisited grid in map
				if(available.size() != 0) {
					// ********** should not simply go back to parent******//
					// go back to current grid's parent
					this.nextGrid = currentGrid.getParent();
//					this.nextGrid = pathToNearestAvailable();
//					System.out.println(gridToString(nextGrid));
					// change the direction
					this.direction = changeDirection(nextGrid);
				}
				// all grids are visited
				else
					// set next grid to null
					this.nextGrid = null;
			}
			// if: there is an unvisited grid among the neighbour of current grid
			else {
				// change direction to the next grid
				// set its parent as current grid
				// remove the next grid from unvisited set
				this.direction = changeDirection(nextGrid);
				this.nextGrid.setParent(currentGrid);
				this.available.remove(nextGrid);
			}
		}
		// if: the next grid is not visited
		else {
			this.nextGrid.setParent(currentGrid);
			this.available.remove(nextGrid);
		}		
	}
	
	public Grid pathToNearestAvailable() {
		Grid nearest = findNearestAvailableGrid();
		HashSet<Grid> neighbours = new HashSet<Grid>();
		int distance;
		int tmp = 10000;
		Grid tmpGrid;
		Grid nearestNeighbour = null;
		System.out.println("target " + gridToString(nearest));
		
//		Grid leftGrid = findCanGoThrough(currentGrid.getxPos()-1, currentGrid.getyPos());
//		Grid rightGrid = findCanGoThrough(currentGrid.getxPos()+1, currentGrid.getyPos());
//		Grid upperGrid = findCanGoThrough(currentGrid.getxPos(), currentGrid.getyPos()-1);
//		Grid lowerGrid = findCanGoThrough(currentGrid.getxPos(), currentGrid.getyPos()+1);
		
		neighbours.add(findCanGoThrough(currentGrid.getxPos()-1, currentGrid.getyPos()));
		neighbours.add(findCanGoThrough(currentGrid.getxPos()+1, currentGrid.getyPos()));
		neighbours.add(findCanGoThrough(currentGrid.getxPos(), currentGrid.getyPos()-1));
		neighbours.add(findCanGoThrough(currentGrid.getxPos(), currentGrid.getyPos()+1));
		neighbours.remove(null);
		System.out.println(gridsToString(neighbours));
		Iterator<Grid> it = neighbours.iterator();
		while(it.hasNext()) {
			tmpGrid = it.next();
			System.out.println("test " + gridToString(tmpGrid));
			if(tmp > (distance = calDistance(tmpGrid, nearest))) {
				tmp = distance;
				nearestNeighbour = tmpGrid;
			}
		}
		System.out.println("nearest dis " + tmp);
		System.out.println("nearest " + gridToString(nearestNeighbour));
		return nearestNeighbour;
//		
//		float 
//		System.out.println("nearest " + gridToString(nearest));
//		
//		if(nearest.getxPos() > currentGrid.getxPos()) {
//			if(direction == RIGHT) 
//				if(findCanGoThrough(currentGrid.getxPos()+1, currentGrid.getyPos()) != null)
//				return findCanGoThrough(currentGrid.getxPos()+1, currentGrid.getyPos());
//			else {
//				if(nearest.getyPos() > currentGrid.getyPos()) 
//					return findCanGoThrough(currentGrid.getxPos(), currentGrid.getyPos()+1);
//				return findCanGoThrough(currentGrid.getxPos(), currentGrid.getyPos()-1);
//			}
//		}
//		else {
//			if(direction == LEFT) return findGrid(currentGrid.getxPos()-1, currentGrid.getyPos());
//			else {
//				if(nearest.getyPos() > currentGrid.getyPos()) 
//					return findGrid(currentGrid.getxPos(), currentGrid.getyPos()+1);
//				return findGrid(currentGrid.getxPos(), currentGrid.getyPos()-1);
//			}
//		}
	}
	
	public int calDistance(Grid g, Grid n) {
		if(g!=null) {
			return (g.getxPos()-n.getxPos())*(g.getxPos()-n.getxPos()) 
					+ (g.getyPos()-n.getyPos())*(g.getyPos()-n.getyPos());
		}
		return 10000;
	}
	
	public Grid findNearestAvailableGrid() {
		Grid nearest = null;
		float distance = 1000f;
		float temp;
		for(Grid i : available) {
			temp = (currentGrid.getxPos()-i.getxPos())*(currentGrid.getxPos()-i.getxPos()) 
					+ (currentGrid.getyPos()-i.getyPos())*(currentGrid.getyPos()-i.getyPos());
			if(distance >= temp) {
				distance = temp;
				nearest = i;
			}
		}
		return nearest;
	}
	
	public void markArrivedGrid() {
		this.currentGrid = this.nextGrid;
		if(this.currentGrid != null) {
			trans();
		}
	}
	
	public void markUnreachableGrid(int type) {
		switch(type) {
		case OBSTACLE:
			obstacles.add(nextGrid); 
			canGoThrough.remove(nextGrid);
			break;
		case BOUNDARY:
			Grid tmp;
			switch(direction) {
			case RIGHT:
				for(int i=-nGrid;i<=nGrid;i++) {
					for(int j=nextGrid.getxPos();j<=nGrid+currentFloor*2*(nGrid+1);j++) {
						if((tmp = findGrid(j, i)) != null){
							boundaries.add(tmp);
							grids.remove(tmp);
							available.remove(tmp);
							canGoThrough.remove(tmp);
						}
					}
				}
				break;
			case LEFT:
				for(int i=-nGrid;i<=nGrid;i++) {
					for(int j=nextGrid.getxPos();j>=-nGrid+currentFloor*2*(nGrid+1);j--) {
						if((tmp = findGrid(j, i)) != null){
							boundaries.add(tmp);
							available.remove(tmp);
							grids.remove(tmp);
							canGoThrough.remove(tmp);
						}
					}	
				}
				break;
			case UPPER:
				for(int i=-nGrid+currentFloor*2*(nGrid+1);i<=nGrid+currentFloor*2*(nGrid+1);i++) {
					for(int j=nextGrid.getyPos();j>=-nGrid;j--) {
						if((tmp = findGrid(i, j)) != null){
							boundaries.add(tmp);
							grids.remove(tmp);
							available.remove(tmp);
							canGoThrough.remove(tmp);
						}
					}	
				}
				break;
			case LOWER:
				for(int i=-nGrid+currentFloor*2*(nGrid+1);i<nGrid+currentFloor*2*(nGrid+1);i++) {
					for(int j=nextGrid.getyPos();j<=nGrid;j++) {
						if((tmp = findGrid(i, j)) != null){
							boundaries.add(tmp);
							grids.remove(tmp);
							available.remove(tmp);
							canGoThrough.remove(tmp);
						}
					}	
				}
				break;
			}
			boundaries.add(nextGrid);	// why this??***********// 
			canGoThrough.remove(nextGrid);
			break;
		case SURVIVOR:
			survivors.add(nextGrid); 
			canGoThrough.remove(nextGrid);
			break;
		}
	}
	
	public void markTransGridUp() {
		this.transGridUp = currentGrid; 
		trans();
	}
	
	public void trans() {
		// upstairs
		if(currentGrid == transGridUp) {
//			System.out.println("Trans to upstairs");
//			setCurrentGrid(transGridDown);
			currentGrid = transGridDown;
			currentFloor++;
			direction = LOWER;
//			setNextGrid();
		}
		else if(currentGrid == transGridDown){
//			if(secondFloorAllVisited()) {
////				System.out.println("Trans to downstairs");
////				currentGrid = transGridUp;
////				setCurrentGrid(transGridUp);
//				currentGrid = transGridUp;
//				direction = RIGHT;
//				currentFloor--;
////				setNextGrid();
//			}
			currentGrid = transGridUp;
			direction = RIGHT;
			currentFloor--;
		}
	}
	public int changeDirection(Grid g) {
		int x = currentGrid.getxPos();
		int y = currentGrid.getyPos();
		
		int newX = g.getxPos();
		int newY = g.getyPos();
		
		if(newX > x) return RIGHT;
		else if(newX < x) return LEFT;
		else if(newY > y) return LOWER;
		else return UPPER;
	}
	
	public boolean secondFloorAllVisited() {
		if(grids != null) {
			for(int i=8;i<=13;i++) {
				for(int j=-1;j<=3;j++) {
					if(findAvailable(i,j)!=null) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean isGroundFloorAllVisited() {
		if(grids != null) {
			for(int i=-3;i<=3;i++) {
				for(int j=-3;j<=3;j++) {
					if(findAvailable(i,j)!=null) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// *** getters and setters ***
	public int getDirection() {
		return direction;
	}
	
	public String getDirectionString() {
		switch (direction){
		case 0: return "RIGHT";
		case 1: return "DOWN";
		case 2: return "LEFT";
		case 3: return "UP";
		}
		return null;
	}

	public Grid getCurrentGrid() {
		return currentGrid;
	}

	public Grid getNextGrid() {
		return nextGrid;
	}

	public HashSet<Grid> getGrids() {
		return grids;
	}

	public HashSet<Grid> getObstacles() {
		return obstacles;
	}

	public HashSet<Grid> getBoundaries() {
		return boundaries;
	}

	public HashSet<Grid> getSurvivors() {
		return survivors;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setCurrentGrid(Grid currentGrid) {
		this.currentGrid = currentGrid;
		this.currentGrid.setParent(currentGrid);
		this.available.remove(currentGrid);
	}

	public void setNextGrid(Grid nextGrid) {
		this.nextGrid = nextGrid;
	}

	public void setGrids(HashSet<Grid> grids) {
		this.grids = grids;
	}

	public void setObstacles(HashSet<Grid> obstacles) {
		this.obstacles = obstacles;
	}

	public void setBoundaries(HashSet<Grid> boundaries) {
		this.boundaries = boundaries;
	}

	public void setSurvivors(HashSet<Grid> survivors) {
		this.survivors = survivors;
	}
	
	public float getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(float gridWidth) {
		this.gridWidth = gridWidth;
	}
	
	public void setTransGridUp(Grid g) {
		this.transGridUp = g;
//		grids.remove(g);
	}

	public Grid getTransGridUp() {
		return transGridUp;
	}

	public Grid getTransGridDown() {
		return transGridDown;
	}

	public void setTransGridDown(Grid transGridDown) {
		this.transGridDown = transGridDown;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	

	public HashSet<Grid> getCanGoThrough() {
		return canGoThrough;
	}

	public HashSet<Grid> getAvailable() {
		return available;
	}

	public void setCanGoThrough(HashSet<Grid> canGoThrough) {
		this.canGoThrough = canGoThrough;
	}

	public void setAvailable(HashSet<Grid> available) {
		this.available = available;
	}

	// for print
	public String gridToString(Grid g) {
		if(g != null)
			return g.getxPos() + " " + g.getyPos() + " ";
		return "null ";
	}
	
	public String directionToString(int d) {
		switch(d) {
		case RIGHT: return "right";
		case LEFT: return "left";
		case LOWER: return "lower";
		case UPPER: return "upper";
		default: return null;
		}
	}
	
	public void printStatus() {
		System.out.print("current " + gridToString(currentGrid));
		System.out.print("next " + gridToString(nextGrid));
//		System.out.print("direction " + directionToString(direction) + " ");
		if(currentGrid != null && findGrid(currentGrid) != null)
			System.out.print("check current: " + findGrid(currentGrid) + ", ");
		if(nextGrid != null && findGrid(nextGrid) != null)
			System.out.print("check next: " + findGrid(nextGrid));
		System.out.println();
	}
	
	public String statusToString() {
		String str = "";
//		str += "current " + gridToString(currentGrid);
//		str += "next " + gridToString(nextGrid);
		str += "c " + gridToString(currentGrid);
		str += "n " + gridToString(nextGrid);
//		str += "direction " + directionToString(direction) + " ";
//		if(currentGrid != null && findGrid(currentGrid) != null)
//			str += "check current: " + findGrid(currentGrid) + ", ";
//		if(nextGrid != null && findGrid(nextGrid) != null)
//			str += "check next: " + findGrid(nextGrid);
		
		return str;
	}
	
	public String gridsToString(HashSet<Grid> g) {
		String str = "";
		if(g.size() != 0) {
			for(Grid i : g) {
				str += gridToString(i);
			}
		}
		else {
			str = "null";
		}
		return str;
	}
	
	public String mapToString() {
		String str = "";
		str += "Unvisited: \n";
		str += gridsToString(this.grids) + "\n";
		
		str += "Available: \n";
		str += gridsToString(this.available) + "\n";
		
		str += "Can go through: \n";
		str += gridsToString(this.canGoThrough) + "\n";
		
		str += "Obstacles: \n";
		str += gridsToString(this.obstacles) + "\n";
		
		str += "Boundaries: \n";
		str += gridsToString(this.boundaries) + "\n";
		
		str += "Survivors: \n";
		str += gridsToString(this.survivors) + "\n";
		
		return str;
	}

	
	
}
