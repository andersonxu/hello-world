package testing.grid_PC;

import java.util.HashSet;

import pg09.features.Grid;

public class GridMap{
	HashSet<Grid> available;
	HashSet<Grid> obstacle;
	HashSet<Grid> survivor;
	Grid trans;
	
	public GridMap() {
		available = new HashSet<Grid>();
		obstacle = new HashSet<Grid>();
		survivor = new HashSet<Grid>();
		
//		for(int i=0;i<=4;i++) {
//			for(int j=0;j<=3;j++) {
//				available.add(new Grid(i,j));
//			}
//		}
//		
//		for(int i=9;i<=12;i++) {
//			for(int j=0;j<=2;j++) {
//				available.add(new Grid(i,j));
//			}
//		}
		
		for(int i=-1;i<=1;i++) {
			for(int j=-1;j<=1;j++) {
				available.add(new Grid(i, j));
			}
		}
		
		for(int i=2;i<=4;i++) {
			for(int j=-1;j<=1;j++) {
				available.add(new Grid(i, j));
			}
		}
		
//		trans = findAvailable(0,0);
//		
//		obstacle.add(findAvailable(1, 2));
//		available.remove(findAvailable(1, 2));
//		
//		survivor.add(findAvailable(10, 0));
//		available.remove(findAvailable(10, 0));
//		
//		obstacle.add(findAvailable(1, 0));
//		available.remove(findAvailable(1, 0));
//		survivor.add(findAvailable(3, 1));
//		available.remove(findAvailable(3, 1));
		
		obstacle.add(findAvailable(1, -1));
		available.remove(findAvailable(1, -1));
		obstacle.add(findAvailable(0, 1));
		available.remove(findAvailable(0, 1));
	}
	
	public Grid findAvailable(int x, int y) {
		for(Grid i : available) {
			if(i.getxPos() == x && i.getyPos() == y) return i;
		}
		return null;
	}
	
	public Grid findObstacle(int x, int y) {
		for(Grid i : obstacle) {
			if(i.getxPos() == x && i.getyPos() == y) return i;
		}
		return null;
	}
	
	public Grid findSurvivor(int x, int y) {
		for(Grid i : survivor) {
			if(i.getxPos() == x && i.getyPos() == y) return i;
		}
		return null;
	}

	public HashSet<Grid> getAvailable() {
		return available;
	}

	public HashSet<Grid> getObstacle() {
		return obstacle;
	}

	public HashSet<Grid> getSurvivor() {
		return survivor;
	}

	public void setAvailable(HashSet<Grid> available) {
		this.available = available;
	}

	public void setObstacle(HashSet<Grid> obstacle) {
		this.obstacle = obstacle;
	}

	public void setSurvivor(HashSet<Grid> survivor) {
		this.survivor = survivor;
	}
}
