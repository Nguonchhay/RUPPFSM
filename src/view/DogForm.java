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

import model.FileProcess;
import model.Window;

public class DogForm extends JFrame implements ActionListener {
	public final int DELAY = 500;
	
	private JFrame object;
	private JLabel lblAnimate;
	private Map<String, String> animationGit;
	private JButton btnThief, btnFood, btnOwner, btnNight;
	private String action, command;
	private JTextField txtMessage;
	
	public DogForm() {
		super("Finite State Machine - Dog");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Window.setMaximize(this,false);
		object = this;
		assignGifs();
		initControls();
		action = "walk";
		command = "idle";
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
		animationGit.put("sleep", basePath + "/sleep.gif");
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
		pnlLeft.setBounds(x,  y + 35, 300, 500);
		
		JLabel lblCommand = new JLabel("Commands");
		lblCommand.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblCommand.setBounds(x + 50, y, 100, 30);
		pnlLeft.add(lblCommand);
		
		y += 100;
		btnThief = new JButton("Thief");
		btnThief.setBounds(x + 40, y, 220, 35);
		btnThief.addActionListener(this);
		
		btnFood = new JButton("Food");
		btnFood.setBounds(x + 40, y + 50, 220, 35);
		btnFood.addActionListener(this);
		
		btnOwner = new JButton("Owner");
		btnOwner.setBounds(x + 40, y + 100, 220, 35);
		btnOwner.addActionListener(this);
		
		btnNight = new JButton("Night");
		btnNight.setBounds(x + 40, y + 150, 220, 35);
		btnNight.addActionListener(this);
			
		panel.add(btnThief);
		panel.add(btnFood);
		panel.add(btnOwner);
		panel.add(btnNight);
		panel.add(pnlLeft);
	}
	
	public void initRightPanel(JPanel panel) {
		JPanel pnlRight = new JPanel(null);
		pnlRight.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlRight.setBounds(400,  50, 550, 500);
		
		JLabel lblCommand = new JLabel("Actions");
		lblCommand.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblCommand.setBounds(250, 15, 100, 30);
		pnlRight.add(lblCommand);
		
	    lblAnimate = new JLabel();
	    lblAnimate.setBounds(25, 10, 500, 400);
		repaintGif("watching");
		
		txtMessage = new JTextField(50);
		txtMessage.setBounds(25, 400, 500, 35);
		
	    pnlRight.add(lblAnimate);
	    pnlRight.add(txtMessage);
	    panel.add(pnlRight);
	}
	
	public void repaintGif(String gif) {
		 BufferedImage img;
			try {
				img = ImageIO.read(new File(animationGit.get("bark")));
				ImageIcon icon=new ImageIcon(img);
		        lblAnimate.setIcon(icon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnThief) {
			command = "thief";
		} else if (event.getSource() == btnFood) {
			command = "food";
		} else if (event.getSource() == btnOwner) {
			command = "owner";
		} else if (event.getSource() == btnNight) {
			command = "night";
		} else {
			command = "idle";
		}
	}
	
	class ThreadDog extends Thread {
		public void run(){				
			while(true){
				try {
					switch (command) {
					case "thief":
						action = "bark";
						break;
					case "food":
						action = "eat";
						break;
					case "owner":
						action = "welcome";
						break;
					case "night":
						action = "sleep";
						break;
					default:
						action = "walk";
						break;
					}
					
					Thread.sleep(DELAY);
					txtMessage.setText("");
					txtMessage.setBorder(BorderFactory.createLineBorder(Color.blue));
					Thread.sleep(200);
					txtMessage.setBorder(BorderFactory.createLineBorder(Color.black));
					txtMessage.setText("  " + action);
					repaintGif(action);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
