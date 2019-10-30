import java.awt.Color;
import java.awt.Graphics;

public class Ball extends MovingObject implements iDrawable {
	
	//======================================================= Constructors
	public Ball(int x, int y, int width, int height, Color color, int xSpeed, int ySpeed) {
		super(x, y, width, height, color, xSpeed, ySpeed);

	}
	
	
	//======================================================= Methods
	@Override
	public boolean move() {
		x += xSpeed;
		y += ySpeed;
		return !canvas.getBounds().contains(this);
	}	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
	}
}