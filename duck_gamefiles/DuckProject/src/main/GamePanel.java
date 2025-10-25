package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	int originalTileSize = 16;
	int scaler = 4;

	public int tileSize = originalTileSize * scaler;
	public static int screenRow = 12;
	public static int screenCol = 16;
	public int screenHeight = tileSize * screenRow;
	public int screenWidth = tileSize * screenCol;

	//EVERYTHING

	KeyboardInput k = new KeyboardInput(this);
	public DialogueManager dm = new DialogueManager(this);
	TileManager tileManager = new TileManager(this);
	public UI ui = new UI(this);
	Sound music = new Sound();
	Sound sfx = new Sound();
	Player player = new Player(this, k);
	int fps = 120;
	private JTextField textField;
	private boolean showTextField = false;
	Font motF;

	public static int[][] field = new int[screenRow/2][screenCol/2];
	public static int[][] infoArr = new int[screenRow/2][screenCol/2];
	public int gamestate;
	public int play = 0, pause = 1, dialogue = 2, title = 3, nameState = 4;
	Thread strand;

	//INSTANTIATED

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(k);
		this.setFocusable(true);
		setLayout(null);

		try {
			InputStream is = getClass().getResourceAsStream("/font/MotleyForcesRegular-w1rZ3.ttf");
			motF = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		textField = new JTextField();
		textField.setBackground(Color.BLACK);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setForeground(new Color(131,63,198));
		this.setFont(motF);
		textField.setFont(this.getFont().deriveFont(Font.PLAIN, 50F));
		textField.setBounds(tileSize*5, tileSize*7, tileSize*6, tileSize);

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.YOUR_NAME = textField.getText();
				if(textField.getText().isEmpty()) {
					Player.YOUR_NAME = "Duck";
				}
				gamestate = play;
				chooseMap();
			//	playMusic(2);
				showTextField = false;
				textField.setVisible(false);
				repaint();
			}
		});

		textField.setVisible(true);
		add(textField);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && gamestate == nameState) {
					// Change the variable and show the JTextField
					showTextField = true;
					textField.setVisible(true);
					textField.requestFocusInWindow();
				}
			}
		});
	}


	public void startGameThread() {
		strand = new Thread(this);
		strand.start();
	}


	@Override
	public void run() {

		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while(strand != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if(delta > 1) {
				refresh();
				repaint();
				delta--;
			}

		}
	}


	public void refresh() {

		if(gamestate == play) {
			player.refresh();
		}
		else if(gamestate == pause) {

		}

	}


	public void loadMap() {

		gamestate = title;

	}


	public void chooseMap() {

		InputStream is;

		try{

			if(Player.YOUR_NAME.equalsIgnoreCase("julia")) {
				is = getClass().getResourceAsStream("/maps/map_secret.txt");
			}else {
				int map = (int)(Math.random()*3)+1;
				String mapName = "/maps/map";
				if(map < 10) {
					mapName += "0";
				}
				mapName += String.valueOf(map);
				mapName += ".txt";
				is = getClass().getResourceAsStream(mapName);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for(int r = 0; r < screenRow / 2; r++) {

				String line = br.readLine();

				for(int c = 0; c < screenCol / 2; c++) {

					String nums[] = line.split(" ");
					int num = Integer.parseInt(nums[c]);
					field[r][c] = num;

				}

			}

			br.close();

		}catch(Exception e) {
		}
	}


	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		if(gamestate == title || gamestate == nameState) {

			ui.draw(g2);

		}

		else if (gamestate != nameState){
			tileManager.draw(g2);
			player.draw(g2);
			ui.draw(g2);
		}

		if(gamestate != nameState) {
			g2.dispose();
		}

	}


	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}


	public void stopMusic() {
		music.stop();
	}


	public void playSFX(int i) {
		sfx.setFile(i);
		sfx.play();
	}




}

