package main;

import entity.Player;

public class DialogueManager {

	GamePanel gPanel;
	String[] dialogues = new String[50];
	int index = 0;

	public DialogueManager(GamePanel gPanel) {
		this.gPanel = gPanel;
		setDialogue();
	}

	public void setDialogue() {
		dialogues[0] = "";
		dialogues[1] = "You can either eat it or sell it at a shop.";
		dialogues[2] = "Land Scout activates, increasing DEF by 1.";
	}

	public void displayDialogue(int i) {
		index = i;
		
		if(i == 0) {
			dialogues[0] = Player.YOUR_NAME + " found a " + gPanel.player.foundCrumb + " breadcrumb.";
		}
		
		gPanel.ui.currentDialogue = dialogues[index];
	}

}
