import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Gameplay extends JPanel  implements KeyListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int[] snakexLength = new int [750];
	private int[] snakeyLength = new int [750];
	
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon leftmouth;
	
	private int lengthofsnake = 3;
	private int moves = 0;
	
	private int [] enemyxPos = { 25, 50, 75, 100, 
								125, 150, 175, 200,
								225, 250, 275, 300,
								325, 350, 375, 400,
								425, 450, 475, 500,
								525, 550, 575, 600,
								625, 650, 675, 700,
								725, 750, 775, 800,
								825, 850};
	
	private int [] enemyyPos = { 75, 100, 125, 150, 
								175, 200, 225, 250, 
								275, 300, 325, 350, 
								375, 400, 425, 450, 
								475, 500, 525, 550, 
								575, 600, 625};
	
	private ImageIcon enemyimage;
	
	private Random random = new Random(System.currentTimeMillis());
	
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);

	private int score = 0;			
	
	private Timer timer;
	private int delay = 100;
	
	private ImageIcon snakeimage;
	private ImageIcon titleImage;
	
	public Gameplay () {
		
		addKeyListener(this);
		setFocusable(true); //Sets the ability to focus, will help end game
		setFocusTraversalKeysEnabled(false); //Enables the keys to focus 
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
		//Default Position of snake
		if(moves == 0) {
			snakexLength[2] = 50;
			snakexLength[1] = 75;
			snakexLength[0] = 100;
			
			snakeyLength[2] = 100;
			snakeyLength[1] = 100;
			snakeyLength[0] = 100;
		}
		
		//Draw Title Border
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 851, 55);
		
		//Draw Title Image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//Draw Border for gameplay
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		
		//Draw Background for gamplay
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		//Draw scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Score:" + score, 780, 30);
		
		//Draw length of snake
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length:" + lengthofsnake, 780, 50);
		
		//Draw snake
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakexLength[0], snakeyLength[0]);
		for(int a = 0; a < lengthofsnake; a++) {
			
			if (a==0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, snakexLength[a], snakeyLength[a]);
			}
			
			if (a==0 && left) {
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g, snakexLength[a], snakeyLength[a]);
			}
			
			if (a==0 && up) {
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, snakexLength[a], snakeyLength[a]);
			}
			
			if (a==0 && down) {
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g, snakexLength[a], snakeyLength[a]);
			}
			
			if(a != 0) {
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, g, snakexLength[a], snakeyLength[a]);
			}
		}
		
		//Draw bad guy and randomize position
		enemyimage = new ImageIcon("enemy.png");
		enemyimage.paintIcon(this, g, enemyxPos[xpos], enemyyPos[ypos]);
		if((enemyxPos[xpos] == snakexLength[0]) && enemyyPos[ypos] == snakeyLength[0]) {
			score++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);		
		}
		
		//Game over screen
		for(int b = 1; b < lengthofsnake; b++) {
			if(snakexLength[b] == snakexLength[0] && snakeyLength[b] == snakeyLength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("Game Over", 300, 300);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Space to RESTART", 350, 340);
			}
		}
		
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		//When snake hits border will loop back to the other side of the screen
		if(right) {
			for(int r = lengthofsnake -1; r >=0; r--) {
				snakeyLength[r+1] = snakeyLength[r];
			}
			
			for(int r = lengthofsnake; r >=0; r--) {
				if(r==0) {
					snakexLength[r] = snakexLength[r] + 25;
				}
				else {
					snakexLength[r] = snakexLength[r-1];
				}
				if(snakexLength[r] > 850) {
					snakexLength[r] = 25;
				}
			}
			
			System.out.println("Right");
			repaint();
		}
		
		if(left) {
			for(int r = lengthofsnake -1; r >=0; r--) {
				snakeyLength[r+1] = snakeyLength[r];
			}
			
			for(int r = lengthofsnake; r >=0; r--) {
				if(r==0) {
					snakexLength[r] = snakexLength[r] - 25;
				}
				else {
					snakexLength[r] = snakexLength[r-1];
				}
				if(snakexLength[r] < 25) {
					snakexLength[r] = 850;
				}
			}
			
			System.out.println("Left");
			repaint();
		}

		if(up) {
			for(int r = lengthofsnake -1; r >=0; r--) {
				snakexLength[r+1] = snakexLength[r];
			}
			
			for(int r = lengthofsnake; r >=0; r--) {
				if(r==0) {
					snakeyLength[r] = snakeyLength[r] - 25;
				}
				else {
					snakeyLength[r] = snakeyLength[r-1];
				}
				if(snakeyLength[r] < 75) {
					snakeyLength[r] = 625;
				}
			}
			
			System.out.println("Up");
			repaint();
		}
		
		if(down) {
			for(int r = lengthofsnake -1; r >=0; r--) {
				snakexLength[r+1] = snakexLength[r];
			}
			
			for(int r = lengthofsnake; r >=0; r--) {
				if(r==0) {
					snakeyLength[r] = snakeyLength[r] + 25;
				}
				else {
					snakeyLength[r] = snakeyLength[r-1];
				}
				if(snakeyLength[r] > 625) {
					snakeyLength[r] = 75;
				}
			}
			
			System.out.println("Down");
			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Reset Game
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			repaint();
		}

		//Controls
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moves++;
			right = true;
			
			//Will keep snake moving
			if(!left ) {
				right = true;
			}
			else {
				right = false;
				left = true;
			}
			
			up = false;
			down = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			left = true;
			
			if(!right) {
				left = true;
			}
			else {
				left = false;
				right = true;
			}
			
			up = false;
			down = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			up = true;
			
			if(!down) {
				up = true;
			}
			else {
				up = false;
				down = true;
			}
			
			left = false;
			right = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			down = true;
			
			if(!up) {
				down = true;
			}
			else {
				down = false;
				up = true;
			}
			
			left = false;
			right = false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
