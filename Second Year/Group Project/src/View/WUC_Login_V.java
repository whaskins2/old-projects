package View;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

import Controller.WUC_Controller;
import Model.WUC_Login_M;


public class WUC_Login_V {
	
	private JPanel jPMain;
	private JButton jBSubmit;
	private JTextField jTUser;
	private JTextField jTPass;
	private JLabel jlLogo, jLUser, jLPassword, jLHead;
	
	private WUC_Controller controller;
	private WUC_Login_M model;
	
	public WUC_Login_V(WUC_Controller controller, WUC_Login_M model){
		
		this.controller = controller;
		this.model = model;
		
		jPMain = new JPanel();
		jPMain.setBackground(Color.BLACK);
		jPMain.setBounds(0, 0, 1024, 768);
		jPMain.setLayout(null);
		
		ImageIcon logo = new ImageIcon("img/logo.jpg");
		jlLogo = new JLabel(logo);
		jlLogo.setHorizontalAlignment(SwingConstants.CENTER);
		jlLogo.setBounds(0, 169, 1008, 104);
		jPMain.add(jlLogo);
				
		jLUser = new JLabel("Username");
		jLUser.setFont(new Font("Calibri", Font.PLAIN, 20));
		jLUser.setBounds(350, 358, 134, 20);
		jPMain.add(jLUser);
		
		jLPassword = new JLabel("Password");
		jLPassword.setFont(new Font("Calibri", Font.PLAIN, 20));
		jLPassword.setBounds(350, 401, 110, 20);
		jPMain.add(jLPassword);
		
		jLHead = new JLabel("Welcome To WUC Administration Log-In");
		jLHead.setHorizontalAlignment(SwingConstants.CENTER);
		jLHead.setFont(new Font("Calibri", Font.PLAIN, 30));
		jLHead.setBounds(0, 284, 1008, 53);
		jPMain.add(jLHead);
		
		jTUser = new JTextField();
		jTUser.setBounds(460, 356, 210, 26);
		jTUser.setColumns(10);
		jPMain.add(jTUser);
		
		jTPass = new JTextField();
		jTPass.setColumns(10);
		jTPass.setBounds(460, 398, 210, 26);
		jPMain.add(jTPass);
		
		jBSubmit = new JButton("Submit");
		jBSubmit.setFont(new Font("Calibri", Font.PLAIN, 20));
		jBSubmit.setBounds(448, 446, 115, 29);
		jBSubmit.addActionListener(controller);
		jBSubmit.setActionCommand("Login_Submit");
		jPMain.add(jBSubmit);
		
		ImageIcon login_background = new ImageIcon("img/login_bg.jpg");
		JLabel lblNewLabel = new JLabel(login_background);
		lblNewLabel.setBounds(0, 0, 1024, 768);
		jPMain.add(lblNewLabel);
		
		
		this.controller.addLoginView(this);
	}
	
	public int getID() {
		int user = Integer.parseInt(jTUser.getText());
		return user;
	}
	
	public String getPassword(){
		return this.jTPass.getText();
	}
	
	public JPanel getPanel() {
		return this.jPMain;
	}
	
}
