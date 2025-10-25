package entity;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Duck {

	public String name;
	public int hp, atk, def, st;
	public BufferedImage defaultImage;
	public Color c;
	public String realName;
	
	public Duck(String name, int hp, int atk, int def, int st, BufferedImage defaultImage, Color c, String realName) {
		
		this.name = name;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.st = st;
		this.defaultImage = defaultImage;
		this.c = c;
		this.realName = realName;
			
	}
	
	
	public String getPassive() {
		
		switch(name) {
		case "Pekin":
			return "Water Advantage: Gains 2 \nATK and DEF while fighting \non a Water Tile.";
		case "Mallard":
			return "Land Scout: Consecutive \nmovements on Land Tiles \nhave an escalating chance \nto increase DEF.";
		case "Duckling":
			return "Breadcrumb Connoisseur: \nUsing any consumable gives \nHP and ST. This effect is \nmore potent when eating \nbreadcrumbs.";
		case "Conqueror":
			return "Subjugator: Winning a fight \ndrains ST to increase ATK.";
			
		}
		
		return null;
	}
	
}
