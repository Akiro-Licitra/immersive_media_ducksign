package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Item;
import entity.Player;

public class KeyboardInput implements KeyListener{

	public boolean upPressed, leftPressed, downPressed, rightPressed;
	GamePanel gPanel;

	public KeyboardInput(GamePanel gPanel) {
		this.gPanel = gPanel;
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if(gPanel.gamestate == gPanel.play) {

			if(!gPanel.player.isMoving) {

				switch(code) {

				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					upPressed = true; break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					leftPressed = true; break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					downPressed = true; break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					rightPressed = true; break;
				case KeyEvent.VK_TAB :
					upPressed = false;
					leftPressed = false;
					downPressed = false;
					rightPressed = false;
					break;

				}

			}

		}

		else if(gPanel.gamestate == gPanel.pause) {

			switch(code) {

			case KeyEvent.VK_TAB :
				upPressed = false;
				leftPressed = false;
				downPressed = false;
				rightPressed = false;
				break;

			}

		}


	}


	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if(gPanel.gamestate == gPanel.play) {

			switch(code) {

			case KeyEvent.VK_TAB :
				upPressed = false;
				leftPressed = false;
				downPressed = false;
				rightPressed = false;
				gPanel.gamestate = gPanel.pause; break;

			}


		}
		else if(gPanel.gamestate == gPanel.pause) {

			if(gPanel.ui.usedItem) {

				switch(code) {

				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_C:
					gPanel.ui.usedItem = false;
					break;

				}

			}
			else {


				if(!gPanel.ui.inMenu) {

					switch(code) {
					case KeyEvent.VK_W :
					case KeyEvent.VK_UP :
					case KeyEvent.VK_A :
					case KeyEvent.VK_LEFT :
						gPanel.playSFX(5);
						if(gPanel.ui.pauseCursor == 265) {
							gPanel.ui.pauseCursor = 535;
						}else {
							gPanel.ui.pauseCursor -= 90;
						}
						break;
					case KeyEvent.VK_S :
					case KeyEvent.VK_DOWN :
					case KeyEvent.VK_D :
					case KeyEvent.VK_RIGHT :
						gPanel.playSFX(5);
						if(gPanel.ui.pauseCursor == 535) {
							gPanel.ui.pauseCursor = 265;
						}else {
							gPanel.ui.pauseCursor += 90;
						}
						break;
					case KeyEvent.VK_TAB :
					case KeyEvent.VK_C :
						returnToPlay();
						break;
					case KeyEvent.VK_ENTER :
					case KeyEvent.VK_SPACE :
						if(gPanel.ui.pauseCursor == 535) {
							returnToPlay();
							break;
						}
						gPanel.playSFX(6);
						gPanel.ui.inMenu = true;
						break;
					}

				}
				else if (!gPanel.ui.isInItem){

					switch(code) {

					case KeyEvent.VK_C :
						gPanel.ui.inMenu = false;
						break;
					case KeyEvent.VK_TAB :
						returnToPlay();
						break;
					case KeyEvent.VK_W :
					case KeyEvent.VK_UP :
						gPanel.playSFX(5);
						if(gPanel.ui.invCursorRow != 0) {
							gPanel.ui.invCursorRow --;
							gPanel.ui.itemPos -= 6;
						}
						else {
							gPanel.ui.invCursorRow = 2;
							gPanel.ui.itemPos += 12;
						}
						break;
					case KeyEvent.VK_A :
					case KeyEvent.VK_LEFT :
						gPanel.playSFX(5);
						if(gPanel.ui.invCursorCol != 0) {
							gPanel.ui.invCursorCol --;
							gPanel.ui.itemPos --;
						}
						else {
							gPanel.ui.invCursorCol = 5;
							gPanel.ui.itemPos += 5;
						}
						break;
					case KeyEvent.VK_S :
					case KeyEvent.VK_DOWN :
						gPanel.playSFX(5);
						if(gPanel.ui.invCursorRow != 2) {
							gPanel.ui.invCursorRow ++;
							gPanel.ui.itemPos += 6;
						}
						else {
							gPanel.ui.invCursorRow = 0;
							gPanel.ui.itemPos -= 12;
						}
						break;
					case KeyEvent.VK_D :
					case KeyEvent.VK_RIGHT :
						gPanel.playSFX(5);
						if(gPanel.ui.invCursorCol != 5) {
							gPanel.ui.invCursorCol ++;
							gPanel.ui.itemPos ++;
						}
						else {
							gPanel.ui.invCursorCol = 0;
							gPanel.ui.itemPos -= 5;
						}
						break;
					case KeyEvent.VK_ENTER :
					case KeyEvent.VK_SPACE :
						if(gPanel.ui.pauseCursor == 355) {
							if(Player.items_inventory.size()-1 >= gPanel.ui.itemPos) {
								gPanel.playSFX(6);
								gPanel.ui.isInItem = true;
								if(Player.items_inventory.get(gPanel.ui.itemPos).canConsume) {
									gPanel.ui.itemSelector = 1;
								}
							}
						}
						break;

					}

				}

				else {

					switch(code) {

					case KeyEvent.VK_C :
						gPanel.ui.isInItem = false;
						gPanel.ui.itemSelector = 0;
						break;
					case KeyEvent.VK_TAB :
						returnToPlay();
						break;
					case KeyEvent.VK_W:
					case KeyEvent.VK_A:
					case KeyEvent.VK_S:
					case KeyEvent.VK_D:
					case KeyEvent.VK_UP:
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_RIGHT:
						gPanel.playSFX(5);
						if(gPanel.ui.itemSelector == 1) {
							gPanel.ui.itemSelector = 2;
						}else {
							gPanel.ui.itemSelector = 1;
						}	
						break;
					case KeyEvent.VK_ENTER:
					case KeyEvent.VK_SPACE:
						if(gPanel.ui.itemSelector == 1) {
							gPanel.ui.isInItem = false;
							gPanel.ui.itemSelector = 0;
						}
						else {
							Item temp = Player.items_inventory.get(gPanel.ui.itemPos);
							if(temp.canConsume) {
								gPanel.playSFX(7);
								gPanel.ui.usedItem = true;
								temp.consumeItem();
							}
							if(temp.count == 0) {
								Player.items_inventory.remove(gPanel.ui.itemPos);
							}
							gPanel.ui.isInItem = false;
							gPanel.ui.itemSelector = 0;
						}

					}

				}

			}

		}

		else if(gPanel.gamestate == gPanel.dialogue) {

			if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
				switch(gPanel.dm.index) {
				case 0 :
					gPanel.dm.index = 1;
					gPanel.dm.displayDialogue(1);
					break;
				case 1 :
					gPanel.gamestate = gPanel.play; 
					break;
				case 2 :
					gPanel.gamestate = gPanel.play;
					gPanel.player.checkSpace2();
					break;

				}

			}

		}

		else if(gPanel.gamestate == gPanel.title) {

			switch(code) {
			case KeyEvent.VK_LEFT :
			case KeyEvent.VK_A :
				if(Player.GLOBAL_DUCK_INDEX == 0) {
					Player.GLOBAL_DUCK_INDEX = gPanel.player.ducks.length-1;
				}
				else {
					Player.GLOBAL_DUCK_INDEX --;
				}
				break;
			case KeyEvent.VK_RIGHT :
			case KeyEvent.VK_D :
				if(Player.GLOBAL_DUCK_INDEX == gPanel.player.ducks.length-1) {
					Player.GLOBAL_DUCK_INDEX = 0;
				}
				else {
					Player.GLOBAL_DUCK_INDEX ++;
				}
				break;
			case KeyEvent.VK_ENTER :
				gPanel.gamestate = gPanel.nameState;
				gPanel.player.duckType = gPanel.player.ducks[Player.GLOBAL_DUCK_INDEX].realName;
				gPanel.player.setDefaultValues();
				gPanel.player.getPlayerImage();
				break;
			case KeyEvent.VK_TAB :
				if(gPanel.ui.titleSwitch) {
					gPanel.ui.titleSwitch = false;
				}else {
					gPanel.ui.titleSwitch = true;
				}
				break;

			}

		}


	}

	public void returnToPlay() {
		gPanel.ui.pauseCursor = 265;
		gPanel.ui.invCursorRow = 0;
		gPanel.ui.invCursorCol = 0;
		gPanel.ui.itemPos = 0;
		gPanel.ui.inMenu = false;
		gPanel.ui.isInItem = false;
		upPressed = false;
		leftPressed = false;
		downPressed = false;
		rightPressed = false;
		gPanel.ui.itemSelector = 0;
		gPanel.gamestate = gPanel.play; 
	}


}

