import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tester extends JPanel{
	
	//=======================================================Properties
	
	ArrayList<Ball> balls = new ArrayList<Ball>();
	ArrayList<Paddle> paddles = new ArrayList<Paddle>();
	ScoreCard sc = new ScoreCard();
	
	JFrame window = new JFrame("4 Paddle Pong");
	Timer tmr = null;
	Random rnd = new Random();
	int hitCount = 0, delay = 40, numBalls = 2, score = 0;
	
	//======================================================= Clean Method
	public Tester() {
		sc.load();
		window.setBounds(100,100,500,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(this);
		window.setVisible(true);
		GameObject.canvas = this;
		
		paddles.add(new Paddle(5, 250, 20, 80, Color.RED, enuPaddleType.VERTICAL));
		paddles.add(new Paddle(getWidth()-25, 250, 20, 80, Color.RED, enuPaddleType.VERTICAL));
		
		paddles.add(new Paddle(250, 5, 80, 20, Color.RED, enuPaddleType.HORIZONTAL));
		paddles.add(new Paddle(250, getHeight()-25, 80, 20, Color.RED, enuPaddleType.HORIZONTAL));
		


		tmr = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Ball b : balls) {
					if(b.move()) {
						tmr.stop();
						repaint();
						String scan = JOptionPane.showInputDialog("Input Your Name: ");
						sc.addScore(scan, score*numBalls);
						sc.save();
						sc.displayScoreCard();
						break;
						
						
					}else {
						for(Paddle p: paddles) {
							if(p.didHit(b)) {
								if(++hitCount == 5) {
									hitCount = 0;
									delay -=5;
									tmr.setDelay(Math.max(1, delay));
									tmr.restart();
									window.setTitle("4 Paddle Pong: " + delay);

								}
								score++;
							}
						}
					}
				}
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override public void mouseMoved(MouseEvent e) {
				for(Paddle p : paddles) p.slide(e.getX(), e.getY());
				repaint();
			}
			@Override public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
		
		addBalls(numBalls);
		tmr.start();

		

	}

	//======================================================= Various Methods
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Ball b: balls) b.draw(g);
		for(Paddle p: paddles) p.draw(g);
	}
	
	public void addBalls(int num) {
		for(int i = 0; i<num; i++) {
			balls.add(new Ball(getWidth()/2-10, getHeight()/2-10, 20, 20,
					Color.PINK,
					(rnd.nextInt(3)*5) *(rnd.nextBoolean() ? 1: -1), (rnd.nextInt(3)*5) *(rnd.nextBoolean() ? 1: -1)));
		}
		
	}
	
	//======================================================= MAIN
	public static void main(String[] args) {
		new Tester();
	}

}
