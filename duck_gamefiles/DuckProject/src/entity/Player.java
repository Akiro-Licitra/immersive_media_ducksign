package entity;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.*;
import main.GamePanel;
import main.KeyboardInput;
import main.UtilityTool;

public class Player extends Entity{

	GamePanel gPanel;
	KeyboardInput k;

	boolean canMove = true;
	int arrX, arrY;
	private int coulomb, mallardChance;
	boolean c1 = false, c2 = false, c3 = false, c4 = false;
	boolean corners = false, big_attack = false, big_defense = false;

	public static double HP, ATK;
	public static int DEF, ST;
	public static double maxHP;
	public static int maxST;
	public String foundCrumb;
	public static String YOUR_NAME;
	public boolean isMoving = false;
	Item normalCrumb, redCrumb, purpleCrumb, greenCrumb, yellowCrumb, goldCrumb;

	public static ArrayList<String> achievements = new ArrayList<String>();
	public static ArrayList<Item> items_inventory = new ArrayList<Item>();
	public static int GLOBAL_DUCK_INDEX = 0;
	public Duck[] ducks = new Duck[4];

	public String duckType = "pekin";

	public Player(GamePanel gPanel, KeyboardInput k) {
		this.gPanel = gPanel;
		this.k = k;

		duckType = "pekin";
		Duck pekin = new Duck("Pekin", 26, 5, 2, 30, setup("right"), Color.WHITE, "pekin");

		duckType = "mallard";
		Duck mallard = new Duck("Mallard", 22, 3, 3, 45, setup("right"), new Color(0,119,60), "mallard");

		duckType = "duckling";
		Duck duckling = new Duck("Duckling", 34, 4, 1, 20, setup("right"), Color.YELLOW, "duckling");

		duckType = "evil";
		Duck conqueror = new Duck("Conqueror", 20, 1, 0, 100, setup("right"), Color.DARK_GRAY, "evil");

		ducks[0] = pekin;
		ducks[1] = mallard;
		ducks[2] = duckling;
		ducks[3] = conqueror;

		setItems();
	}


	public void setDefaultValues() {
		x = (int)(6.5*gPanel.tileSize);
		y = (int)(6.5*gPanel.tileSize);
		speed = 3;
		dir = "right";
		arrX = 3;
		arrY = 3;
		GamePanel.infoArr[arrY][arrX] = 1;

		switch(duckType) {
		case "pekin":
			HP = 26;
			ATK = 5;
			DEF = 2;
			ST = 30;
			break;
		case "mallard":
			HP = 22;
			ATK = 3;
			DEF = 3;
			ST = 45;
			mallardChance = 2;
			break;
		case "duckling":
			HP = 34;
			ATK = 4;
			DEF = 1;
			ST = 20;
			break;
		case "evil":
			HP = 20;
			ATK = 1;
			DEF = 0;
			ST = 100;
			break;
		}


		maxHP = HP;
		maxST = ST;
	}


	public void setItems() {

		normalCrumb = new Item("normal_bc", true);
		redCrumb = new Item("red_bc", true);
		purpleCrumb = new Item("purple_bc", true);
		greenCrumb = new Item("green_bc", true);
		yellowCrumb = new Item("yellow_bc", true);
		goldCrumb = new Item("gold_bc", true);

	}


	public void refresh() {

		if(canMove) {
			if(k.upPressed && arrY > 0) {
				isMoving = true;
				int i = 0;
				playTileSound();
				try {
					while(i < 128 / speed) {
						if(gPanel.gamestate == gPanel.play) {
							dir = "up";
							y -= speed;
							Thread.sleep(10);
							gPanel.repaint();
							i++;
						}
					}
					dir = "left";
					canMove = false;
					isMoving = false;
					k.upPressed = false;
					k.leftPressed = false;
					k.downPressed = false;
					k.rightPressed = false;
					GamePanel.infoArr[arrY][arrX]--;
					arrY--;
					GamePanel.infoArr[arrY][arrX]++;
					checkSpace1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}else if(k.leftPressed && arrX > 0) {
				isMoving = true;
				int i = 0;
				playTileSound();
				try {
					while(i < 128 / speed) {
						if(gPanel.gamestate == gPanel.play) {
							dir = "left";
							x -= speed;
							Thread.sleep(10);
							gPanel.repaint();
							i++;
						}
					}
					canMove = false;
					isMoving = false;
					k.upPressed = false;
					k.leftPressed = false;
					k.downPressed = false;
					k.rightPressed = false;
					GamePanel.infoArr[arrY][arrX]--;
					arrX--;
					GamePanel.infoArr[arrY][arrX]++;
					checkSpace1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}else if(k.downPressed && arrY < 5) {
				isMoving = true;
				int i = 0;
				playTileSound();
				try {
					while(i < 128 / speed) {
						if(gPanel.gamestate == gPanel.play) {
							dir = "down";
							y += speed;
							Thread.sleep(10);
							gPanel.repaint();
							i++;
						}
					}
					dir = "right";
					canMove = false;
					isMoving = false;
					k.upPressed = false;
					k.leftPressed = false;
					k.downPressed = false;
					k.rightPressed = false;
					GamePanel.infoArr[arrY][arrX]--;
					arrY++;
					GamePanel.infoArr[arrY][arrX]++;
					checkSpace1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}else if(k.rightPressed && arrX < 7) {
				isMoving = true;
				int i = 0;
				playTileSound();
				try {
					while(i < 128 / speed) {
						if(gPanel.gamestate == gPanel.play) {
							dir = "right";
							x += speed;
							Thread.sleep(10);
							gPanel.repaint();
							i++;
						}
					}
					canMove = false;
					isMoving = false;
					k.upPressed = false;
					k.leftPressed = false;
					k.downPressed = false;
					k.rightPressed = false;
					GamePanel.infoArr[arrY][arrX]--;
					arrX++;
					GamePanel.infoArr[arrY][arrX]++;
					checkSpace1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}


	}


	public void getPlayerImage() {

		up = setup("up");
		left = setup("left");
		down = setup("down");
		right = setup("right");

	}


	public BufferedImage setup(String imageName) {

		UtilityTool uT = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + duckType + "_" + imageName + ".png"));
			image = uT.scaleImage(image, gPanel.tileSize, gPanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}

		return image;

	}


	public void playTileSound() {
		gPanel.playSFX(GamePanel.field[arrY][arrX]);
	}


	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch(dir) {
		case "up":
			image = up;
			break;
		case "left":
			image = left;
			break;
		case "down":
			image = down;
			break;
		case "right":
			image = right;
			break;
		}
		g2.drawImage(image, x, y, null);

	}


	public void checkSpace1() {

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		Runnable continueTurn = () -> {
			canMove = true;		
		};

		executorService.schedule(continueTurn, 667, TimeUnit.MILLISECONDS);
		executorService.shutdown();

		if(ST > 0) {
			ST--;
		}else{
			gPanel.playSFX(3);
			HP--;
			if(HP <= 0) {
				gPanel.stopMusic();
				gPanel.ui.gameEnd = true;
			}
		}

		checkAchievement();

		if(GLOBAL_DUCK_INDEX == 1) {
			mallardCheck();
		}
		else {
			checkSpace2();
		}

	} 


	public void checkSpace2() {

		coulomb = (int)(Math.random()*100)+1;

		if(coulomb <= 100) {

			coulomb = (int)(Math.random()*100)+1;

			if(coulomb <= 48) {
				foundCrumb = "normal";
			}
			else if(coulomb <= 60) {
				foundCrumb = "red";
			}
			else if(coulomb <= 72) {
				foundCrumb = "purple";
			}
			else if(coulomb <= 84) {
				foundCrumb = "green";
			}
			else if(coulomb <= 96) {
				foundCrumb = "yellow";
			}
			else  {
				foundCrumb = "golden";
			}

			updateInventory(foundCrumb);
			gPanel.dm.displayDialogue(0);
			gPanel.gamestate = gPanel.dialogue;
			gPanel.ui.score += 1000;

		}

		checkAchievement();

	}


	public void checkAchievement() {

		if(!corners) {
			if(arrX == 0 && arrY == 0){
				c1 = true;
			}else if(arrX == 7 && arrY == 0) {
				c2 = true;
			}else if(arrX == 0 && arrY == 5) {
				c3 = true;
			}else if(arrX == 7 && arrY == 5) {
				c4 = true;
			}
		}

		if(c1 && c2 && c3 && c4) {
			achievements.add("Around the World");
			gPanel.ui.score += 125000;
			ST += 15;
			HP += 5;
			if(ST > maxST) {
				ST = maxST;
			}
			if(HP > maxHP) {
				HP = maxHP;
			}
			c1 = false;
			corners = true;
		}

		if(!big_attack) {
			if(ATK >= 9) {
				achievements.add("The Best Defense");
				gPanel.ui.score += 40000;
				DEF += 3;
				big_attack = true;
			}
		}

		if(!big_defense) {
			if(DEF >= 7) {
				achievements.add("The Best Offense");
				gPanel.ui.score += 60000;
				ATK += 4;
				big_defense = true;
			}
		}


	}


	public void mallardCheck() {

		if(GamePanel.field[arrY][arrX] == 0) {
			coulomb = (int)(Math.random()*100)+1;
			if(mallardChance >= coulomb) {
				DEF++;
				gPanel.dm.displayDialogue(2);
				gPanel.gamestate = gPanel.dialogue;
				mallardChance = 2;
			}
			else {
				mallardChance += (int)(Math.random()*3)+2;
				checkSpace2();
			}
		}
		else {
			mallardChance = 2;
			checkSpace2();
		}
	}


	public void updateInventory(String foundCrumb) {

		switch(foundCrumb) {
		
		case "normal":
			if(normalCrumb.count == 0) {
				items_inventory.add(normalCrumb);
			}
			normalCrumb.count++;
			break;
			
		case "red":
			if(redCrumb.count == 0) {
				items_inventory.add(redCrumb);
			}
			redCrumb.count++;
			break;
			
		case "purple":
			if(purpleCrumb.count == 0) {
				items_inventory.add(purpleCrumb);
			}
			purpleCrumb.count++;
			break;
			
		case "green":
			if(greenCrumb.count == 0) {
				items_inventory.add(greenCrumb);
			}
			greenCrumb.count++;
			break;
			
		case "yellow":
			if(yellowCrumb.count == 0) {
				items_inventory.add(yellowCrumb);
			}
			yellowCrumb.count++;
			break;
			
		case "golden":
			if(goldCrumb.count == 0) {
				items_inventory.add(goldCrumb);
			}
			goldCrumb.count++;
			break;

		}

	}
}
