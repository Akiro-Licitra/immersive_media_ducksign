package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.UtilityTool;

public class Item {

	String type;
	public BufferedImage img;
	public boolean canConsume;
	public int count;
	public String description;
	public static String consumeMessage;

	public Item(String type, boolean canConsume) {	
		this.type = type;
		img = getImage();
		this.canConsume = canConsume;
		count = 0;
		description = setDescription();
	}


	public BufferedImage getImage() {
		UtilityTool uT = new UtilityTool();
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/items/" + type + ".png"));
			image = uT.scaleImage(image, 78, 78);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String setDescription() {

		switch(type) {

		case "normal_bc":
			return "Normal Breadcrumb: Chance to restore health.";
		case "red_bc":
			return "Red Breadcrumb: Provides some sustenance.";
		case "purple_bc":
			return "Purple Breadcrumb: Sharpens " + Player.YOUR_NAME + "'s beak.";
		case "green_bc":
			return "Green Bredcrumb: Adds a protective feather \nlayer.";
		case "yellow_bc":
			return "Yellow Breadcrumb: Restores some stamina.";
		case "gold_bc":
			return "Golden Breadcrumb: A miniature feast and the \npossession of an affluent noble.";

		}
		return null;
	}


	public void consumeItem() {

		consumeMessage = "Item has been used.";
		
		switch (type) {

		case "normal_bc":
			int rand = (int)(Math.random()*100)+1;
			if(rand <= 65) {
				consumeMessage = Player.YOUR_NAME + " has healed for 1 HP.";
				Player.HP++;
				if(Player.HP > Player.maxHP) {
					Player.HP = Player.maxHP;
				}
			}
			else {
				consumeMessage = "Nothing happens.";
			}
			break;

		case "red_bc":
			consumeMessage = Player.YOUR_NAME + " has healed for 4 HP.";
			Player.HP += 4;
			if(Player.HP > Player.maxHP) {
				Player.HP = Player.maxHP;
			}
			break;
			
		case "purple_bc":
			consumeMessage = Player.YOUR_NAME + " has gained 1 ATK.";
			Player.ATK++;
			break;
			
		case "green_bc":
			consumeMessage = Player.YOUR_NAME + " has gained 1 DEF.";
			Player.DEF++;
			break;
			
		case "yellow_bc":
			consumeMessage = Player.YOUR_NAME + " has restored 8 ST.";
			Player.ST += 8;
			if(Player.ST > Player.maxST) {
				Player.ST = Player.maxST;
			}
			break;
			
		case "gold_bc":
			consumeMessage = Player.YOUR_NAME + " has increased all stats.";
			Player.HP = Player.maxHP;
			Player.ST = Player.maxST;
			Player.ATK+=2;
			Player.DEF+=2;
			break;

		}

		count--;
	}

}
