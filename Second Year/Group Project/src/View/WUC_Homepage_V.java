package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;


import Controller.WUC_Controller;

public class WUC_Homepage_V {
	
	private JPanel jPMain, jPMain_2, jPFooter, jPNav;
	private JButton jBLogout;
	private JLabel jLFooter_Info;
	private JMenuBar jMBNav;
	private JMenu jMStudents, jMStaff, jMModules;
	private JMenuItem jMIStud_Create, jMIStud_Display, jMIStud_Amend, 
			jMIStud_Delete, jMIStaff_Create, jMIStaff_Display, jMIStaff_Amend, 
			jMIStaff_Delete, jMIModules_Create, jMIModules_Display, jMIModules_Amend, 
			jMIModules_Delete;
	
	private WUC_Controller controller;
	
	public WUC_Homepage_V(WUC_Controller controller){
		
		this.controller = controller;
		
		UIManager.put("MenuBar.background", Color.WHITE);        
        UIManager.put("MenuItem.background", Color.CYAN);
        UIManager.put("Menu.selectionBackground", Color.CYAN);
        
		jPMain = new JPanel();
		jPMain.setBackground(new Color(117, 240, 237));
		jPMain.setBounds(0, 0, 1024, 768);
		jPMain.setLayout(null);
		
		jBLogout = new JButton("Logout");
		jBLogout.setBackground(Color.WHITE);
		jBLogout.setBounds(887, 11, 107, 31);
		jBLogout.addActionListener(controller);
		jBLogout.setActionCommand("Logout");
		jPMain.add(jBLogout);
		
		jPMain_2 = new JPanel();
		jPMain_2.setBackground(Color.WHITE);
		jPMain_2.setBounds(33, 211, 955, 437);
		jPMain_2.setLayout(null);
		jPMain.add(jPMain_2);
		
		jPFooter = new JPanel();
		jPFooter.setBackground(new Color(184, 238, 229));
		jPFooter.setBounds(0, 684, 1018, 87);
		jPFooter.setLayout(null);
		jPMain.add(jPFooter);
		
		jPNav = new JPanel();
		jPNav.setBackground(new Color(255, 255, 255));
		jPNav.setBounds(0, 133, 1018, 37);
		jPNav.setLayout(null);
		jPMain.add(jPNav);
		
		
        
		jMBNav = new JMenuBar();
		jMBNav.setOpaque(true);
		jMBNav.setBounds(539, 0, 170, 40); //setBounds(x, y, width, height)
		
		jPNav.add(jMBNav);
		jMBNav.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		jMStudents = new JMenu("Students");
		jMBNav.add(jMStudents);
		
		jMIStud_Create = new JMenuItem("Create");
		jMStudents.add(jMIStud_Create);
		
		jMIStud_Display = new JMenuItem("Display");
		jMStudents.add(jMIStud_Display);
		
		jMIStud_Amend = new JMenuItem("Amend");
		jMStudents.add(jMIStud_Amend);
		
		jMIStud_Delete = new JMenuItem("Delete");
		jMStudents.add(jMIStud_Delete);
		
		jMStaff = new JMenu("Staff");
		jMBNav.add(jMStaff);
		
		jMIStaff_Create = new JMenuItem("Create");
		jMStaff.add(jMIStaff_Create);
		
		jMIStaff_Display = new JMenuItem("Display");
		jMStaff.add(jMIStaff_Display);
		
		jMIStaff_Amend = new JMenuItem("Amend");
		jMStaff.add(jMIStaff_Amend);
		
		jMIStaff_Delete = new JMenuItem("Delete");
		jMStaff.add(jMIStaff_Delete);
		
		jMModules = new JMenu("Modules");
		jMBNav.add(jMModules);
		
		jMIModules_Create = new JMenuItem("Create");
		jMModules.add(jMIModules_Create);
		
		jMIModules_Display = new JMenuItem("Display");
		jMModules.add(jMIModules_Display);
		
		jMIModules_Amend = new JMenuItem("Amend");
		jMModules.add(jMIModules_Amend);
		
		jMIModules_Delete = new JMenuItem("Delete");
		jMModules.add(jMIModules_Delete);
		
		jLFooter_Info = new JLabel("Copyright  2015 - 2016 | Woodlands University College");
		jLFooter_Info.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLFooter_Info.setBounds(352, 22, 400, 33);
		jPFooter.add(jLFooter_Info);
		
		ImageIcon logo = new ImageIcon("img/logo.jpg");
		JLabel jLLogo = new JLabel(logo);
		jLLogo.setBounds(33, 11, 190, 96);
		jPMain.add(jLLogo);
		
		ImageIcon header = new ImageIcon("img/header.jpg");
		JLabel jLHead = new JLabel(header);
		jLHead.setBounds(0, 0, 1018, 136);	
		jPMain.add(jLHead);
		
		this.controller.addHomeView(this);
		
	}
	
	public JPanel getPanel() {
		return this.jPMain;
	}
}
