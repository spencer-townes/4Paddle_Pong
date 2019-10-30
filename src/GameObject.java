import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JPanel;

public abstract class GameObject extends Rectangle {

	public static JPanel canvas;
	public Color color;
	
	public GameObject(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;
	}
}
