import java.awt.Color;
import java.awt.Graphics;

public class Paddle extends GameObject implements iDrawable {

	//======================================================= Properties

	private enuPaddleType paddleType;

	//======================================================= Constructors

	public Paddle(int x, int y, int width, int height, Color color, enuPaddleType paddleType) {
		super(x, y, width, height, color);
		this.paddleType = paddleType;
	}

	//======================================================= Methods

	public boolean didHit(Ball b) {
		if(this.intersects(b)) {
			if (paddleType == enuPaddleType.HORIZONTAL) {
				b.ySpeed = - b.ySpeed;
				if(y<canvas.getHeight()/2) b.y = y+height;
				else b.y = y-height;

			}else {
				b.xSpeed = -b.xSpeed;
				if(x < canvas.getWidth()/2)	b.x = x + width;
				else b.x = x - b.width;
			}		
			return true;
		}
		return false;
	}

	public void slide(int x, int y) {
		if(paddleType == enuPaddleType.VERTICAL) {
			this.y = y - height/2;
			if(y < 5)
				this.y = 5;
			else if(this.y + height > canvas.getHeight()-5)
				this.y = canvas.getHeight() - 5;
		} else {
			this.x = x - width/2;
			if(this.x < 5) 
				this.x = 5;
			else if(this.x + width > canvas.getWidth()-5)
				this.x = canvas.getWidth() - 5;
		}
	}


	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}

}