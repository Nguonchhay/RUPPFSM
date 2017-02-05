package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.DogAction;
import model.DogCommand;
import model.FileProcess;
import model.Window;

public class DogForm extends JFrame implements ActionListener {
	public final int DELAY = 300;
	
	private JFrame object;
	private JLabel lblAnimate;
	private Map<String, String> animationGit;
	private JButton btnThiefArrives, btnThiefLeaves, btnThiefGivesMeat;
	private JButton btnBossArrives, btnBossLeaves, btnBossGivesMeat;
	private JButton btnFinishEating;
	private String action, command, previousAction;
	private JTextField txtMessage;
	private boolean isAlive;
	
	public DogForm() {
		super("Finite State Machine - Dog");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Window.setMaximize(this,false);
		isAlive = true;
		action = previousAction = DogAction.WATCH;
		command = "idle";
		object = this;
		assignGifs();
		initControls();
		ThreadDog threadDog = new ThreadDog();
		threadDog.start();
	}
	
	public void assignGifs() {
		animationGit = new HashMap<String, String>();
		String basePath = "images";
		animationGit.put("watch", basePath + "/watch.gif");
		animationGit.put("welcome", basePath + "/welcome.gif");
		animationGit.put("bark", basePath + "/bark.gif");
		animationGit.put("eat", basePath + "/eat.gif");
		animationGit.put("eat-poisoned", basePath + "/eat-poisoned.gif");
		animationGit.put("die", basePath + "/die.gif");
	}
	
	public void initControls() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(Window.getWindowWidth(), Window.getWindowHeight());
		panel.setBounds(0, 0, Window.getWindowWidth(), Window.getWindowHeight());
		
		initLeftPanel(panel);
		initRightPanel(panel);
		
		getContentPane().setLayout(null);
		getContentPane().add(panel);
	}
	
	public void initLeftPanel(JPanel panel) {
		int x = 50, y = 15;
		JPanel pnlLeft = new JPanel(null);
		pnlLeft.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlLeft.setBounds(x,  y + 35, 500, 500);
		
		JLabel lblCommand = new JLabel("Commands");
		lblCommand.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblCommand.setBounds(200, y, 100, 30);
		pnlLeft.add(lblCommand);
		
		// Thief commands
		y += 100;
		btnThiefArrives = new JButton("Thief arrives");
		btnThiefArrives.setBounds(x + 40, y, 100, 35);
		btnThiefArrives.addActionListener(this);
		panel.add(btnThiefArrives);
		
		btnThiefLeaves = new JButton("Thief leaves");
		btnThiefLeaves.setBounds(x + 40 + 100, y, 100, 35);
		btnThiefLeaves.addActionListener(this);
		panel.add(btnThiefLeaves);
		
		btnThiefGivesMeat = new JButton("Thief gives poisoned meat");
		btnThiefGivesMeat.setBounds(x + 40 + 100 + 100, y, 200, 35);
		btnThiefGivesMeat.addActionListener(this);
		panel.add(btnThiefGivesMeat);
		
		// Boss commands
		btnBossArrives = new JButton("Boss arrives");
		btnBossArrives.setBounds(x + 40, y + 50, 100, 35);
		btnBossArrives.addActionListener(this);
		panel.add(btnBossArrives);
		
		btnBossLeaves = new JButton("Boss leaves");
		btnBossLeaves.setBounds(x + 40 + 100, y + 50, 100, 35);
		btnBossLeaves.addActionListener(this);
		panel.add(btnBossLeaves);
		
		btnBossGivesMeat = new JButton("Boss gives meat");
		btnBossGivesMeat.setBounds(x + 40 + 100 + 100, y + 50, 150, 35);
		btnBossGivesMeat.addActionListener(this);
		panel.add(btnBossGivesMeat);
		
		// Eating commands	
		btnFinishEating = new JButton("Finish eating");
		btnFinishEating.setBounds(x + 40, y + 100, 100, 35);
		btnFinishEating.addActionListener(this);
		panel.add(btnFinishEating);
		panel.add(pnlLeft);
	}
	
	public void initRightPanel(JPanel panel) {
		JPanel pnlRight = new JPanel(null);
		pnlRight.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlRight.setBounds(600,  50, 550, 500);
		
		JLabel lblCommand = new JLabel("Performing Actions");
		lblCommand.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblCommand.setBounds(200, 15, 200, 30);
		pnlRight.add(lblCommand);
		
	    lblAnimate = new JLabel();
	    lblAnimate.setBounds(25, 10, 500, 400);
		repaintGif(action);
		
		txtMessage = new JTextField(50);
		txtMessage.setBounds(25, 400, 500, 35);
		
	    pnlRight.add(lblAnimate);
	    pnlRight.add(txtMessage);
	    panel.add(pnlRight);
	}
	
	public void repaintGif(String gif) {
		 BufferedImage img;
			try {
				img = ImageIO.read(new File(animationGit.get(gif)));
				ImageIcon icon=new ImageIcon(img);
		        lblAnimate.setIcon(icon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnThiefArrives) {
			command = DogCommand.THIEF_ARRIVES;
		} else if (event.getSource() == btnThiefLeaves) {
			command = DogCommand.THIEF_LEAVES;
		} else if (event.getSource() == btnThiefGivesMeat) {
			command = DogCommand.THIEF_GIVES_MEAT;
		} else if (event.getSource() == btnBossArrives) {
			command = DogCommand.BOSS_ARRIVES;
		} else if (event.getSource() == btnBossLeaves) {
			command = DogCommand.BOSS_LEAVES;
		} else if (event.getSource() == btnBossGivesMeat) {
			command = DogCommand.BOSS_GIVES_MEAT;
		} else if (event.getSource() == btnFinishEating) {
			command = DogCommand.FINISH_EATING;
		}
	}
	
	class ThreadDog extends Thread {
		public void run() {				
			while (isAlive) {
				try {
					switch (command) {
						case DogCommand.THIEF_ARRIVES:
							action = DogAction.BARK;
							break;
						case DogCommand.THIEF_LEAVES:
							switch (action) {
								case DogAction.WELCOME:
									action = DogAction.WELCOME;
									break;
								case DogAction.EAT:
									action = DogAction.EAT;
									break;
								case DogAction.EAT_POISONED:
									action = DogAction.EAT_POISONED;
									break;
								default:
									action = DogAction.WATCH;
							}
							break;
						case DogCommand.THIEF_GIVES_MEAT:
							switch (action) {
								case DogAction.EAT:
									break;
								case DogAction.WELCOME:
									break;
								default:
									action = DogAction.EAT_POISONED;
							}
							break;
						case DogCommand.BOSS_ARRIVES:
							switch (action) {
								case DogAction.BARK:
									break;
								default:
									action = DogAction.WELCOME;
							}
							break;
						case DogCommand.BOSS_LEAVES:
							switch (action) {
								case DogAction.BARK:
									action = DogAction.BARK;
									break;
								case DogAction.EAT_POISONED:
									action = DogAction.EAT_POISONED;
									break;
								case DogAction.EAT:
									action = DogAction.EAT;
									break;
								default:
									action = DogAction.WATCH;
							}
							break;
						case DogCommand.BOSS_GIVES_MEAT:
							action = DogAction.EAT;
							break;
						case DogCommand.FINISH_EATING:
							switch (action) {
								case DogAction.EAT_POISONED:
									action = DogAction.DIE;
									isAlive = false;
									break;
								default:
									action = DogAction.WATCH;
							}
							break;
						default:
							action = DogAction.WATCH;
					}

					Thread.sleep(DELAY);
					txtMessage.setText("");
					txtMessage.setBorder(BorderFactory.createLineBorder(Color.blue));
					Thread.sleep(100);
					txtMessage.setBorder(BorderFactory.createLineBorder(Color.black));
					txtMessage.setText(" " + action);
					repaintGif(action);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			txtMessage.setText(" System was terminate because dog was dead.");
		}
	}
}
