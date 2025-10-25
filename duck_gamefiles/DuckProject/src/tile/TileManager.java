package tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.*;

public class TileManager {

	GamePanel gPanel;
	Tile[] tile;


	public TileManager(GamePanel gPanel) {

		this.gPanel = gPanel;

		tile = new Tile[10];

		getTileImage();

	}


	public void getTileImage() {

		setup(0, "grass");
		setup(1, "water");

	}


	public void setup(int index, String imageName) {

		UtilityTool uT = new UtilityTool();

		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uT.scaleImage(tile[index].image, gPanel.tileSize, gPanel.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g2) {

		int tSize = gPanel.tileSize;
		int type = 0;
		int r2, c2;

		for(int row = 0; row < GamePanel.screenRow / 2; row++) {

			r2 = 2*row;

			for(int col = 0; col < GamePanel.screenCol / 2; col++) {

				type = GamePanel.field[row][col];
				c2 = 2*col;
				g2.drawImage(tile[type].image, c2*tSize, r2*tSize, null);
				g2.drawImage(tile[type].image, c2*tSize, (r2+1)*tSize, null);
				g2.drawImage(tile[type].image, (c2+1)*tSize, r2*tSize, null);
				g2.drawImage(tile[type].image, (c2+1)*tSize, (r2+1)*tSize, null);

			}

		}
	}

}
