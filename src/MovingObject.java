import java.awt.Color;

public abstract class MovingObject extends GameObject {
	public int xSpeed, ySpeed;
	
	public MovingObject(	int x, int y, int width, int height, Color color, int xSpeed, int ySpeed) {
		super(x, y, width, height, color);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public abstract boolean move();
}
