package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import entity.Item;
import entity.Player;

public class UI {

	GamePanel gPanel;
	Graphics2D g2;
	Font motF;
	Color grayT;
	Color redT;
	Color blackB;
	Color customPurple;
	public int messageCounter = 0;
	public boolean inMenu = false;
	public boolean gameEnd = false;
	public boolean titleSwitch = false;
	public boolean isInItem = false, usedItem = false;
	public String currentDialogue;
	public int pauseCursor;
	public int invCursorRow = 0, invCursorCol = 0;
	public int itemPos = 0;

	public int itemSelector = 0; 
	// 0 MEANS OFF, 1 MEANS ON LEFT, 2 MEANS ON RIGHT

	public double score = 300000;

	public UI(GamePanel gPanel) {
		this.gPanel = gPanel;

		try {
			InputStream is = getClass().getResourceAsStream("/font/MotleyForcesRegular-w1rZ3.ttf");
			motF = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		grayT = new Color(175, 175, 175, 175);
		redT = new Color(175, 0, 0, 125);
		blackB = new Color(0, 0, 0, 200);
		customPurple = new Color(131,63,198);

		pauseCursor = 265;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;
		g2.setFont(motF);

		if(gameEnd) {

			g2.setColor(redT);
			g2.fillRect(0, 0, 1100, 800);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
			g2.setColor(Color.BLACK);
			g2.drawString("Game Over.", 300, 434);

			gPanel.strand = null;

		}else if(gPanel.gamestate == gPanel.play){
			drawPlayScreen();
		}else if(gPanel.gamestate == gPanel.pause){
			drawPauseScreen();
		}else if(gPanel.gamestate == gPanel.dialogue) {
			drawDialogueScreen();
		}else if(gPanel.gamestate == gPanel.title) {
			drawTitleScreen();
		}else if(gPanel.gamestate == gPanel.nameState) {
			drawNameScreen();
		}

	}


	public void drawNormalScreen() {
		BufferedImage image = null;

		g2.setColor(Color.BLACK);
		g2.fillRect(25, 25, 300, 20);
		g2.setColor(Color.RED);
		g2.fillRect(25, 25, (int)(300*Player.HP/Player.maxHP), 20);

		g2.setColor(Color.BLACK);
		g2.fillRect(25, 75, 300, 20);
		g2.setColor(Color.ORANGE);
		g2.fillRect(25, 75, (int)(300*Player.ST/Player.maxST), 20);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		g2.setColor(Color.WHITE);
		g2.drawString("HP", 150, 44);
		g2.drawString("ST", 150, 93);

		if(score > 500000) {
			score -= 200000/36000;
		}
		else if(score > 100000) {
			score -= 100000/36000;
		}else if(score > 0){
			score -= 50000/36000;
		}

		if(score < 0) {
			score = 0;
		}

		g2.setColor(grayT);
		g2.fillRect(842, 725, 178, 35);
		g2.setColor(Color.WHITE);
		g2.drawString("Score: " + (int)(score), 850, 750);


		if(Player.achievements.size() != 0) {

			if(messageCounter == 0){
				gPanel.playSFX(4);
			}

			try {
				image = ImageIO.read(getClass().getResourceAsStream("/side/buff.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			g2.setColor(Color.BLACK);
			g2.drawString(Player.achievements.get(0), 70, (int)(gPanel.tileSize*8.6));
			g2.drawImage(image, 5, (gPanel.tileSize*8), gPanel.tileSize, gPanel.tileSize, null);

			messageCounter++;

			if(messageCounter > 479) {
				messageCounter = 0;
				Player.achievements.remove(0);	
			}

		}
	}


	public void drawPlayScreen() {

		drawNormalScreen();

	}


	public void drawPauseScreen() {

		g2.setColor(Color.BLACK);
		g2.fillRect(25, 25, 300, 20);
		g2.setColor(Color.GRAY);
		g2.fillRect(25, 25, (int)(300*Player.HP/Player.maxHP), 20);

		g2.setColor(Color.BLACK);
		g2.fillRect(25, 75, 300, 20);
		g2.setColor(Color.GRAY);
		g2.fillRect(25, 75, (int)(300*Player.ST/Player.maxST), 20);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		g2.setColor(Color.WHITE);
		g2.drawString("HP", 150, 44);
		g2.drawString("ST", 150, 93);
		g2.drawString("Score: " + (int)(score), 850, 750);

		g2.setColor(grayT);
		g2.fillRect(0, 0, 1100, 800);

		g2.setColor(Color.BLACK);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
		g2.drawString(Player.YOUR_NAME, xMid(Player.YOUR_NAME)+3, 167);
		g2.setColor(customPurple);
		g2.drawString(Player.YOUR_NAME, xMid(Player.YOUR_NAME), 164);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		g2.setColor(Color.GRAY);
		g2.drawString("PAUSED", xMid("Paused")+3, 117);
		g2.setColor(Color.BLACK);
		g2.drawString("PAUSED", xMid("Paused"), 114);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		g2.drawString("Stats", 90, 265);
		g2.drawString("Items", 90, 355);
		g2.drawString("Skills", 90, 445);
		g2.drawString("Return", 90, 535);

		if(pauseCursor != 535) {
			drawSubWindow(325, 210, 625, 500);
		}

		drawSubWindow(0, gPanel.tileSize*11, (int)(gPanel.tileSize*1.5), gPanel.tileSize);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
		g2.drawString("ENTER", 13, (int)(gPanel.tileSize*11.6));
		drawSubWindow(220, gPanel.tileSize*11, (int)(gPanel.tileSize*1.5), gPanel.tileSize);
		g2.drawString("C", 259, (int)(gPanel.tileSize*11.6));

		g2.setColor(Color.BLACK);
		g2.drawString("Confirm", (int)(gPanel.tileSize*1.5)+6, (int)(gPanel.tileSize*11.6)+3);
		g2.setColor(customPurple);
		g2.drawString("Confirm", (int)(gPanel.tileSize*1.5)+3, (int)(gPanel.tileSize*11.6));

		g2.setColor(Color.BLACK);
		g2.drawString("Back", (int)(gPanel.tileSize*1.7)+213, (int)(gPanel.tileSize*11.6)+3);
		g2.setColor(customPurple);
		g2.drawString("Back", (int)(gPanel.tileSize*1.7)+210, (int)(gPanel.tileSize*11.6));

		if(!inMenu) {			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", 50, pauseCursor+5);

			drawInventory(false);

			g2.setColor(customPurple);
		}
		else {
			if(!isInItem) {
				g2.setColor(customPurple);
				g2.setStroke(new BasicStroke(5));
				g2.drawRoundRect(330, 215, 615, 490, 25, 25);
			}

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
			g2.setColor(Color.DARK_GRAY);
			g2.drawString(">", 50, pauseCursor+5);

			drawInventory(true);

			g2.setColor(Color.BLACK);
		}

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		g2.drawString(">", 45, pauseCursor);

		if(isInItem) {

			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.WHITE);
			g2.drawRoundRect(334, 523, 607, 180, 25, 25);

			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));

			int y = 555;

			Player.items_inventory.get(itemPos).description = Player.items_inventory.get(itemPos).setDescription();
			for(String line : Player.items_inventory.get(itemPos).description.split("\n")) {
				g2.drawString(line, 357, y);
				y += 40;
			}

			g2.setColor(Color.WHITE);
			drawSubWindow(334, 645, 303, 55);
			drawSubWindow(637, 645, 303, 55);

			g2.drawString("Cancel", 445, 680);
			g2.drawString("Use", 763, 680);

			if (itemSelector == 1) {
				g2.setColor(customPurple);
				g2.drawRoundRect(339, 650, 293, 45, 25, 25);
			}
			else if (itemSelector == 2) {
				g2.setColor(customPurple);
				g2.drawRoundRect(642, 650, 293, 45, 25, 25);
			}


		}

		if(usedItem) {
			drawItemUsage(Item.consumeMessage);
		}

	}


	public void drawInventory(boolean isInMenu) {		

		switch(pauseCursor) {

		case 265: 
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
			g2.setColor(Color.WHITE);
			g2.drawString("HP: " + (int)Player.HP + " / " + (int)Player.maxHP, 375, 290);
			g2.drawString("ATK: " + (int)Player.ATK, 375, 370);
			g2.drawString("DEF: " + Player.DEF, 375, 450);
			g2.drawString("ST: " + Player.ST + " / " + Player.maxST, 375, 530);
			break;
		case 355:
			int slotX = 355;
			int slotY = 230;
			int cursorWidth = 78, cursorHeight = 78;
			int cursorX = 355 + (cursorWidth * invCursorCol) + (20 * invCursorCol);
			int cursorY = 230 + (cursorHeight * invCursorRow) + (20 * invCursorRow);
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));

			for(int i = 0; i < Player.items_inventory.size(); i++){

				g2.drawImage(Player.items_inventory.get(i).img, slotX, slotY, null);
				g2.drawString(String.valueOf(Player.items_inventory.get(i).count), slotX+63, slotY+70);
				slotX += 98;

				if(i == 5 || i == 11) {
					slotX = 355;
					slotY += 98;
				}

			}

			if(isInMenu) {

				g2.setColor(Color.WHITE);
				g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			}

			break;


		}




	}


	public void drawItemUsage(String str) {

		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(330, 215, 615, 490, 25, 25);
		
		g2.setColor(customPurple);
		g2.drawRoundRect(334, 523, 607, 180, 25, 25);

		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));

		int y = 555;

		for(String line : str.split("\n")) {
			g2.drawString(line, 357, y);
			y += 40;
		}

	}


	public void drawDialogueScreen() {

		drawNormalScreen();

		int x = gPanel.tileSize*3;
		int y = gPanel.tileSize*9;
		int width = gPanel.screenWidth - (gPanel.tileSize*6);
		int height = (int)(gPanel.tileSize*2.75);

		drawSubWindow(x, y, width, height);

		x += (int)(gPanel.tileSize/2);
		y += (int)(gPanel.tileSize/2);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));

		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}

	}


	public void drawTitleScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
		g2.setColor(Color.BLACK);
		g2.drawString("Immersive Media", xMid("Immersive Media")+5, 125);
		g2.drawString("Ducksign", xMid("Ducksign")+5, 225);
		g2.setColor(new Color(131,63,198));
		g2.drawString("Immersive Media", xMid("Immersive Media"), 120);
		g2.drawString("Ducksign", xMid("Ducksign"), 220);

		g2.drawImage(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].defaultImage, 140, 350, gPanel.tileSize*4, gPanel.tileSize*4, null);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		g2.setColor(Color.BLACK);
		g2.drawString(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].name, 555, 405);
		g2.setColor(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].c);
		g2.drawString(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].name, 550, 400);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
		g2.setColor(Color.BLACK);
		g2.drawString("<", 155, 700);
		g2.setColor(Color.WHITE);
		g2.drawString("<", 150, 695);

		g2.setColor(Color.BLACK);
		g2.drawString(">", 285, 700);
		g2.setColor(Color.WHITE);
		g2.drawString(">", 280, 695);

		drawSubWindow(0, gPanel.tileSize*11, (int)(gPanel.tileSize*1.5), gPanel.tileSize);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
		g2.drawString("TAB", 25, (int)(gPanel.tileSize*11.6));

		g2.setColor(Color.BLACK);
		g2.drawString("SWITCH INFO", (int)(gPanel.tileSize*1.7)+3, (int)(gPanel.tileSize*11.6)+3);
		g2.setColor(new Color(131,63,198));
		g2.drawString("SWITCH INFO", (int)(gPanel.tileSize*1.7), (int)(gPanel.tileSize*11.6));

		if(!titleSwitch) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
			g2.setColor(Color.BLACK);
			g2.drawString("HP: ", 555, 505);
			g2.setColor(Color.RED);
			g2.drawString("HP: ", 550, 500);

			g2.setColor(Color.BLACK);
			g2.drawString("ATK: ", 555, 580);
			g2.setColor(new Color(131,63,198));
			g2.drawString("ATK: ", 550, 575);

			g2.setColor(Color.BLACK);
			g2.drawString("DEF: ", 555, 655);
			g2.setColor(new Color(0,119,60));
			g2.drawString("DEF: ", 550, 650);

			g2.setColor(Color.BLACK);
			g2.drawString("ST: ", 555, 730);
			g2.setColor(Color.YELLOW);
			g2.drawString("ST: ", 550, 725);

			g2.setColor(Color.BLACK);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].hp), 755, 505);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].atk), 755, 580);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].def), 755, 655);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].st), 755, 730);

			g2.setColor(Color.GRAY);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].hp), 750, 500);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].atk), 750, 575);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].def), 750, 650);
			g2.drawString(String.valueOf(gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].st), 750, 725);
		}

		else {

			drawSubWindow(550, 450, 375, 300);

			int x = 575, y = 490;

			currentDialogue = gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].getPassive();
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, x, y);
				y += 40;
			}

		}

	}


	public void drawNameScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(gPanel.tileSize*5, gPanel.tileSize*7, gPanel.tileSize*6, gPanel.tileSize);
		g2.drawString("Name the", xMid("Name the")+5, 225);
		g2.drawString("Duck", xMid("Duck")+5, 325);
		g2.setColor(new Color(131,63,198));
		g2.drawString("Name the", xMid("Name the"), 220);
		g2.drawString("Duck", xMid("Duck"), 320);

	}


	public void drawSubWindow(int x, int y, int width, int height) {

		g2.setColor(blackB);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

	}


	public int xMid (String str) {

		int length = (int)g2.getFontMetrics().getStringBounds(str, g2).getWidth();
		return gPanel.screenWidth/2 - length/2;

	}
}
