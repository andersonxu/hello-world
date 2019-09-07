package pg09.features;

public class Grid {
	private int xPos;
	private int yPos;
	private Grid parent;
	
	public Grid(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public Grid getParent() {
		return parent;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public void setParent(Grid parent) {
		this.parent = parent;
	}	
	
}
