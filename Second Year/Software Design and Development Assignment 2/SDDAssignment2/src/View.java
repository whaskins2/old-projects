import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;


public class View extends JFrame {
	
	private Model model;
	private Controller controller;
	
	private JFrame sessionFrame;
	private JFrame childFrame;
	private JFrame invoiceFrame;
	
	private JPanel contentPane;
	public JPanel body;

	public JButton nav1;
	public JButton nav2;
	public JButton nav3;
	public JButton nav4;
	public JButton deleteChild;
	public JButton showInvoice;
	public JButton editChild;
	public JButton confirmEdit;
	public JButton btnSubmit;
	public JButton paid;
	public JButton submitAdmin;
	public JButton loginBtn;
	public JButton btnLogOut;
	private JButton[] childRowSelect;
	private JButton[] sessionDelete;
	private JButton[] adminDelete;
	
	private JTextField cFNText;
	private JTextField cSNText;
	private JTextField addressText;
	private JTextField dayText;
	private JTextField monthText;
	private JTextField yearText;
	private JTextField gTitleText;
	private JTextField gFNText;
	private JTextField gSNText;
	private JTextField txtSessionField;
	private JTextField txtCost1;
	private JTextField txtCost2;
	private JTextField txtCost3;
	private JTextField txtUserField;
	private JTextField loginUsername;
	
	private JTextArea allergyText;
	private JPasswordField txtPWField;
	private JPasswordField loginPassword;
	private JCheckBox siblingBox;
	private JList<String> sessionDisplay;
	private ArrayList<Session> enrolled = new ArrayList<Session>();
	
	private JLabel[] childFNameRow;
	private JLabel[] childSNameRow;
	private JLabel[] childDOBRow;
	private JLabel[] childSessionRow;
	private JLabel[] sessionTitleRow;
	private JLabel[] sessionIDRow;
	private JLabel[] sessionRemainingCapacityRow;
	private JLabel lblPageTitle;
	private JLabel[] adminUsernameRow;
	private JLabel[] adminIDRow;
	
	private int idHolder;
	boolean loggedIn = false; // stores whether the user is logged in.
	boolean fresh = false; //stores whether the system is being opened for the first time.
	
	public View(Model curModel, Controller controller) {
		
		this.model = curModel;
		this.controller = controller;
		this.controller.addView(this);
		
		fresh = model.openingState();
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 800);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//GUI is built
		buildHead();
		buildNav();
		buildBody();
		body.repaint();
		controller.addView(this);
		model.sessionCapacityUpdate(); //Session capacities are all updated on launch.

	}
	
	public void buildHead(){
		//Method that builds the top section of the gui, featuring the logout button and the title of each page.
		JPanel head = new JPanel();
		head.setBounds(0, 0, 1014, 71);
		contentPane.add(head);
		head.setLayout(null);
	
		JLabel lblPageTitle = new JLabel();
		lblPageTitle.setBounds(431, 16, 140, 27);
		lblPageTitle.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblPageTitle = lblPageTitle;
		head.add(lblPageTitle);
		
		JButton btnLogOut = new JButton("log out");
		btnLogOut.setBounds(916, 15, 83, 29);
		btnLogOut.addActionListener(this.controller);
		this.btnLogOut = btnLogOut;
		head.add(btnLogOut);
	}
	
	public void buildNav(){
		//Method that builds the navigation.
		JPanel navigation = new JPanel();
		navigation.setBounds(0, 71, 112, 689);
		navigation.setLayout(null);
		contentPane.add(navigation);
		
		JButton nav1 = new JButton("New Child");
		nav1.setBounds(12, 40, 90, 26);
		nav1.addActionListener(this.controller);
		nav1.setActionCommand("page1");
		this.nav1 = nav1;
		navigation.add(nav1);
		
		JButton nav2 = new JButton("Sessions");
		nav2.setBounds(12, 120, 90, 26);
		nav2.addActionListener(this.controller);
		nav2.setActionCommand("page2");
		this.nav2 = nav2;
		navigation.add(nav2);
		
		JButton nav3 = new JButton("Children");
		nav3.setBounds(12, 200, 90, 26);
		nav3.addActionListener(this.controller);
		nav3.setActionCommand("page3");
		this.nav3 = nav3;
		navigation.add(nav3);
		
		JButton nav4 = new JButton("Admin");
		nav4.setBounds(13, 280, 90, 26);
		nav4.addActionListener(this.controller);
		nav4.setActionCommand("page4");
		this.nav4 = nav4;
		navigation.add(nav4);
		navigation.repaint();
	}
	
	public void buildBody(){
			//Method that builds the main panel of the body.
			JPanel body = new JPanel();
			body.setBounds(114, 71, 900, 689);
			body.setLayout(null);
			contentPane.add(body);
			body.setBackground(Color.LIGHT_GRAY);
			this.body = body;
			
			
			fillBody(0);
	}
	
	public void fillBody(int state){
		//Method that takes an integer value and uses it to determine which methods to call to build the gui.
		if(state == 0){
			body.removeAll();
			body.repaint();
			buildLogin(false);
			lblPageTitle.setText("Login");
		}else if(state == 1){
			body.removeAll();
			body.repaint();
			if(loggedIn){
				buildChildPage();
				lblPageTitle.setText("New Child");
			}else{
				buildLogin(true);
			}
		}else if(state == 2){
			body.removeAll();
			body.repaint();
			if(loggedIn){
				buildSessionPage();
				lblPageTitle.setText("New Session");
			}else{
				buildLogin(true);
			}
		}else if(state == 3){
			body.removeAll();
			body.repaint();
			if(loggedIn){
				buildListChildrenPage();
				lblPageTitle.setText("List Children");
			}else{
				buildLogin(true);
			}
		}else if(state == 4){
			body.removeAll();
			body.repaint();
			if(loggedIn){
				buildAdminPage();
				lblPageTitle.setText("Administrators");
			}else{
				buildLogin(true);
			}
		}else if(state == 5){
			body.removeAll();
			body.repaint();
			buildLogin(true);
			lblPageTitle.setText("Login");
		}
	}
	
	public void buildLogin(boolean seen){
		//Method building the login page.
		JLabel uNPanel = new JLabel("Username:");
		uNPanel.setBounds(350, 180, 150, 26);
		uNPanel.setHorizontalAlignment(SwingConstants.CENTER);
		body.add(uNPanel);
		
		loginUsername = new JTextField();
		loginUsername.setBounds(350, 200, 150, 26);
		loginUsername.setHorizontalAlignment(SwingConstants.CENTER);
		body.add(loginUsername);
		loginUsername.setColumns(10);
		
		JLabel pWPanel = new JLabel("Password:");
		pWPanel.setBounds(350, 260, 150, 26);
		pWPanel.setHorizontalAlignment(SwingConstants.CENTER);
		body.add(pWPanel);
		
		loginPassword = new JPasswordField();
		loginPassword.setColumns(10);
		loginPassword.setBounds(350, 280, 150, 26);
		loginPassword.setHorizontalAlignment(SwingConstants.CENTER);
		body.add(loginPassword);
		
		JLabel lblNewLabel_5 = new JLabel("Login Here");
		lblNewLabel_5.setBounds(350, 120, 150, 20);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		body.add(lblNewLabel_5);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(365, 360, 120, 30);
		loginBtn.addActionListener(this.controller);
		loginBtn.setHorizontalAlignment(SwingConstants.CENTER);
		this.loginBtn = loginBtn;
		body.add(loginBtn);
		body.repaint();
		if(fresh){
			loginUsername.setText("username");
			loginPassword.setText("password");
			if(!seen){
				JOptionPane.showMessageDialog(null, "No admin file detected, by default the username is \"username\" and the password is \"password\". Please add your own admin.", "InfoBox: " + "No admin file", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void buildChildPage(){
		//Method building the new child page.
		JLabel lblChildFirstname = new JLabel("Child Firstname");
		lblChildFirstname.setBounds(82, 80, 115, 20);
		body.add(lblChildFirstname);
		
		JLabel lblChildSurname = new JLabel("Child Surname");
		lblChildSurname.setBounds(82, 160, 115, 20);
		body.add(lblChildSurname);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(82, 240, 115, 20);
		body.add(lblAddress);
		
		JLabel lblAllergyInfo = new JLabel("Allergy Info");
		lblAllergyInfo.setBounds(82, 400, 115, 20);
		body.add(lblAllergyInfo);
		
		JLabel lblBirthdate = new JLabel("DOB (DD/MM/YYYY)");
		lblBirthdate.setBounds(82, 320, 115, 20);
		body.add(lblBirthdate);
		
		cFNText = new JTextField();
		cFNText.setBounds(246, 77, 146, 26);
		body.add(cFNText);
		cFNText.setColumns(10);
		
		cSNText = new JTextField();
		cSNText.setColumns(10);
		cSNText.setBounds(246, 157, 146, 26);
		body.add(cSNText);
		
		addressText = new JTextField();
		addressText.setColumns(10);
		addressText.setBounds(246, 237, 146, 26);
		body.add(addressText);
		
		dayText = new JTextField();
		dayText.setColumns(10);
		dayText.setBounds(246, 317, 40, 26);
		body.add(dayText);
		
		monthText = new JTextField();
		monthText.setColumns(10);
		monthText.setBounds(300, 317, 40, 26);
		body.add(monthText);
		
		yearText = new JTextField();
		yearText.setColumns(10);
		yearText.setBounds(354, 317, 40, 26);
		body.add(yearText);
		
		allergyText = new JTextArea();
		allergyText.setBounds(246, 397, 146, 145);
		allergyText.setLineWrap(true);
		JScrollPane scrollAllergyText = new JScrollPane(allergyText);
		scrollAllergyText.setBounds(246, 397, 146, 145);
		body.add(scrollAllergyText);
		
		siblingBox = new JCheckBox("Registered Sibling");
		siblingBox.setBounds(465, 397, 130, 29);
		siblingBox.setOpaque(false);
		body.add(siblingBox);
		
		JLabel lblParentTitle = new JLabel("Guardian title");
		lblParentTitle.setBounds(465, 83, 115, 20);
		body.add(lblParentTitle);
		
		JLabel lblGuardianFirstname = new JLabel("Guardian Firstname");
		lblGuardianFirstname.setBounds(465, 163, 149, 20);
		body.add(lblGuardianFirstname);
		
		JLabel lblGuardianSurname = new JLabel("Guardian Surname");
		lblGuardianSurname.setBounds(465, 243, 149, 20);
		body.add(lblGuardianSurname);
		
		JLabel lblSession = new JLabel("Session");
		lblSession.setBounds(465, 323, 115, 20);
		body.add(lblSession);
		
		gTitleText = new JTextField();
		gTitleText.setColumns(10);
		gTitleText.setBounds(629, 80, 146, 26);
		body.add(gTitleText);
		
		gFNText = new JTextField();
		gFNText.setColumns(10);
		gFNText.setBounds(629, 160, 146, 26);
		body.add(gFNText);
		
		gSNText = new JTextField();
		gSNText.setColumns(10);
		gSNText.setBounds(629, 240, 146, 26);
		body.add(gSNText);
		
		JButton sessionSelect = new JButton("Select sessions");
		sessionSelect.setBounds(629, 317, 146, 26);
		sessionSelect.setActionCommand("sessionSelect");
		sessionSelect.addActionListener(this.controller);
		body.add(sessionSelect);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(660, 513, 115, 29);
		btnSubmit.setActionCommand("newChild");
		btnSubmit.addActionListener(this.controller);
		body.add(btnSubmit);
	}
	public void buildSessionPage(){
		//Method building the session page.
		int y = 42;
		ArrayList<Session> sessionList = model.fetchSessions();
		int size = sessionList.size();
		sessionTitleRow = new JLabel[size];
		sessionIDRow = new JLabel[size];
		sessionRemainingCapacityRow = new JLabel[size];
		sessionDelete = new JButton[size];
		JPanel[] viewBtnHolder = new JPanel[size];
		
		JLabel lblSession = new JLabel("New Session:");
		lblSession.setBounds(50, y, 115, 20);
		body.add(lblSession);
		
		JLabel lblSessionName = new JLabel("Title:");
		lblSessionName.setBounds(200, y, 115, 20);
		body.add(lblSessionName);
		
		txtSessionField = new JTextField();
		txtSessionField.setBounds(250, y, 146, 26);
		txtSessionField.setColumns(10);
		body.add(txtSessionField);
		
		y +=49;
		
		JLabel lblCost1 = new JLabel("0-2 Cost");
		lblCost1 .setBounds(50, y, 115, 20);
		body.add(lblCost1 );
		
		JLabel lblCost2  = new JLabel("2-3 Cost");
		lblCost2.setBounds(200, y, 115, 20);
		body.add(lblCost2);
		
		JLabel lblCost3 = new JLabel("3+ Cost");
		lblCost3.setBounds(350, y, 115, 20);
		body.add(lblCost3);

		txtCost1 = new JTextField();
		txtCost1.setBounds(125, y, 50, 26);
		txtCost1.setColumns(4);
		body.add(txtCost1);
		
		txtCost2 = new JTextField();
		txtCost2.setBounds(275, y, 50, 26);
		txtCost2.setColumns(4);
		body.add(txtCost2);
		
		txtCost3 = new JTextField();
		txtCost3.setBounds(425, y, 50, 26);
		txtCost3.setColumns(4);
		body.add(txtCost3);
		
		JButton submitSession = new JButton("Submit");
		submitSession.setBounds(650, y, 115, 29);
		submitSession.setActionCommand("newSession");
		submitSession.addActionListener(this.controller);
		body.add(submitSession);
		
		y+= 49;
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(50, y, 85, 20);
		body.add(lblTitle);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(250, y, 69, 20);
		body.add(lblID);
		
		JLabel lblSpots = new JLabel("Remaining Spots");
		lblSpots.setBounds(415, y, 120, 20);
		body.add(lblSpots);
		
		y+= 49;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, y, 805, 452);
		body.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));
		
		for(int i = 0; i<size;i++){
			Session session = sessionList.get(i);
			
			sessionTitleRow[i] = new JLabel(session.getName());
			sessionTitleRow[i].setVerticalAlignment(SwingConstants.TOP);
			sessionTitleRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(sessionTitleRow[i]);
			
			sessionIDRow[i] = new JLabel(Integer.toString(session.getID()));
			sessionIDRow[i].setVerticalAlignment(SwingConstants.TOP);
			sessionIDRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(sessionIDRow[i]);
			
			sessionRemainingCapacityRow[i] = new JLabel(Integer.toString(session.getSpotsLeft()));
			sessionRemainingCapacityRow[i].setVerticalAlignment(SwingConstants.TOP);
			sessionRemainingCapacityRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(sessionRemainingCapacityRow[i]);

			viewBtnHolder[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			panel_1.add(viewBtnHolder[i]);
			if(size>1){
				sessionDelete[i] = new JButton("Delete");
				sessionDelete[i].addActionListener(controller);
				sessionDelete[i].setActionCommand("s" + Integer.toString(session.getID()));
				viewBtnHolder[i].add(sessionDelete[i]);
				sessionDelete[i].setVerticalAlignment(SwingConstants.TOP);
			}
		}
		
		/*for(int i = 0; i < sessionList.size(); i++){
			Session session = sessionList.get(i);
			sessionTitleRow[i] = new JLabel(session.getName());
			y += 49;
			sessionTitleRow[i].setBounds(50, y, 120, 20);
			body.add(sessionTitleRow[i]);
			
			sessionIDRow[i] = new JLabel(Integer.toString(session.getID()));
			sessionIDRow[i].setBounds(250, y, 69, 20);
			body.add(sessionIDRow[i]);
			
			sessionRemainingCapacityRow[i] = new JLabel(Integer.toString(session.getSpotsLeft()));
			sessionRemainingCapacityRow[i].setBounds(450, y, 69, 20);
			body.add(sessionRemainingCapacityRow[i]);
			
			sessionDelete[i] = new JButton("Delete");
			sessionDelete[i].setBounds(650, y, 80, 20);
			sessionDelete[i].addActionListener(this.controller);
			sessionDelete[i].setActionCommand("s" + Integer.toString(session.getID()));
			body.add(sessionDelete[i]);
		}*/
	}
	
	public void updateChild(boolean newChild){
		//Method used to add or edit a child. Boolean variable passed in is used to determine what to do with the contents of the textfields.
		String cFirstname = "";
		String cSurname = null;
		String address = null;
		int day = 0;
		int month = 0;
		int year = 0;
		String allergyInfo = null;
		String gTitle = null;
		String gFirstname = null;
		String gSurname = null;
		boolean sibling = false;
		boolean valid = true;
		if(cFNText.getText().equals("") || cSNText.getText().equals("") || model.validateDate(dayText.getText(),monthText.getText(),yearText.getText())
				||  gTitleText.getText().equals("") || gFNText.getText().equals("") || gSNText.getText().equals("") || enrolled.size() == 0){
			valid = false;
		}else{
			cFirstname = cFNText.getText();
			cSurname = cSNText.getText();
			address = addressText.getText();
			day = Integer.parseInt(dayText.getText());
			month = Integer.parseInt(monthText.getText());
			year = Integer.parseInt(yearText.getText());
			allergyInfo = allergyText.getText();
			gTitle = gTitleText.getText();
			gFirstname = gFNText.getText();
			gSurname = gSNText.getText();
			sibling = siblingBox.isSelected();
		}
		if(valid){
			if(newChild){
				int childID = model.generateChildID();
				try{
					LocalDate paymentDue = LocalDate.now().plusMonths(1);
					LocalDate birthdate = LocalDate.of(year, month, day);
					Child child = new Child(childID, cFirstname, cSurname, address, birthdate, allergyInfo, gTitle, gFirstname, gSurname, sibling, enrolled);
					child.setPaymentDue(paymentDue);
					model.newChild(child);
					fillBody(3);
				}catch(DateTimeException e){
					JOptionPane.showMessageDialog(null, "Error. The entered date is not valid", "InfoBox: " + "Invalid Date", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				try{
					LocalDate birthdate = LocalDate.of(year, month, day);
					Child child = new Child(idHolder, cFirstname, cSurname, address, birthdate, allergyInfo, gTitle, gFirstname, gSurname, sibling, enrolled);
					model.changeChild(child, true);
					fillBody(3);
				}catch(DateTimeException e){
					JOptionPane.showMessageDialog(null, "Error. The entered date is not valid", "InfoBox: " + "Invalid Date", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "Error, one of the fields was invalid. Child and Guardian names must be provided along with a valid dob and at least one session.", "InfoBox: " + "Invalid entry", JOptionPane.INFORMATION_MESSAGE);
		}
		if(enrolled.size() > 0 && !valid){
		}else{
			enrolled = new ArrayList<Session>();
		}
	}
	public void buildSessionFrame(){
		//Builds the session frame used to select sessions.
		JFrame sessionFrame = new JFrame();
		sessionFrame.setVisible(true);
		sessionFrame.setResizable(false);
		sessionFrame.setBounds(100, 100, 480, 800);
		sessionFrame.setLocationRelativeTo(null);
		JPanel sessionPanel = new JPanel();
		sessionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		sessionFrame.setContentPane(sessionPanel);
		this.sessionFrame = sessionFrame;
		JPanel headPanel = new JPanel();
		headPanel.setPreferredSize(new Dimension(480, 100));
		sessionPanel.add(headPanel);
		JLabel sessionHead = new JLabel("Select all of the sessions you wish to enrol the child on from the list below.");
		JLabel sessionHead2 = new JLabel("Hold CTRL to select multiple elements.");
		headPanel.add(sessionHead);
		headPanel.add(sessionHead2);
		
		ArrayList<Session> sessionList = model.fetchSessions();
		String[] sessionArray = new String[sessionList.size()];
		for (int i = 0;i<sessionList.size();i++){
			Session session = sessionList.get(i);
			if(session.getSpotsLeft() > 0){
				sessionArray[i] = session.getName();
			}
		}
		
		JList<String> sessionDisplay = new JList<String>(sessionArray);
		this.sessionDisplay = sessionDisplay;
		sessionPanel.add(sessionDisplay);
		
		JButton sessionSubmit = new JButton("Submit");
		sessionSubmit.addActionListener(this.controller);
		sessionSubmit.setActionCommand("assignSessionsToChild");
		sessionPanel.add(sessionSubmit);
	}
	public void newSession(){
		//Retrieves the details from the session txt fields and creates a new session with them if they're valid.
		String sessionName = "";
		double cost1 = 0;
		double cost2 = 0;
		double cost3 = 0;
		boolean valid = true;
		if(txtSessionField.getText().equals("") || (!txtCost1.getText().matches("[0-9]+([,.][0-9]{1,2})?")) || (!txtCost2.getText().matches("[0-9]+([,.][0-9]{1,2})?"))
				|| (!txtCost3.getText().matches("[0-9]+([,.][0-9]{1,2})?"))){
			valid = false;
		}else{
			sessionName = txtSessionField.getText();
			cost1 = Double.parseDouble(txtCost1.getText());
			cost2 = Double.parseDouble(txtCost2.getText());
			cost3 = Double.parseDouble(txtCost3.getText());
		}
		if(valid){
			int newSessionId = model.generateSessionID();
			Session session = new Session(newSessionId,sessionName, 15, cost1, cost2, cost3);
			model.newSession(session);
			fillBody(2);
		}else{
			JOptionPane.showMessageDialog(null, "Error, one of the fields was invalid.", "InfoBox: " + "Invalid entry", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void selectedSessions(){
		//fetches all the selected sessions from the session frame.
		List selectedList = sessionDisplay.getSelectedValuesList();
		ArrayList<Session> sessions = model.fetchSessions();
		enrolled = new ArrayList<Session>();
		
		JPanel childSessionHolder = new JPanel();
		childSessionHolder.setBounds(629, 345, 146, 165);
		childSessionHolder.setLayout(new GridLayout(0,1));
		body.add(childSessionHolder);
		String[] sessionArray = new String[selectedList.size()];
		
		for(int i = 0; i < selectedList.size(); i++){
			String selectedSession = (String) selectedList.get(i);
			for (int c = 0;c<sessions.size();c++){
				Session comparisonSession = sessions.get(c);
				if (comparisonSession.getName().equals(selectedSession)){
					enrolled.add(comparisonSession);
					sessionArray[i] = selectedSession;
				}
			}
		}
		
		JList<String> sessionDisplay = new JList<String>(sessionArray);
		sessionDisplay.setEnabled(false);
		sessionDisplay.setSize(146,165);
		
		this.sessionDisplay = sessionDisplay;
		childSessionHolder.add(sessionDisplay);
		this.enrolled = enrolled;
		childSessionHolder.repaint();
		sessionFrame.dispose();
	}
	public void buildListChildrenPage(){
		//Method that builds the list children page.
		int y = 42;
		
		ArrayList<Child> children = model.fetchChildren();
		int size = children.size();
		
		childRowSelect = new JButton[size];
		childFNameRow = new JLabel[size];
		childSNameRow = new JLabel[size];
		childDOBRow = new JLabel[size];
		childSessionRow = new JLabel[size];
		JPanel[] viewBtnHolder = new JPanel[size];
		this.childRowSelect = childRowSelect;
		this.childFNameRow = childFNameRow;
		this.childSNameRow = childSNameRow;
		this.childDOBRow = childDOBRow;
		this.childSessionRow = childSessionRow;
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setBounds(50, y, 85, 20);
		body.add(lblNewLabel);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(210, y, 69, 20);
		body.add(lblSurname);
		
		JLabel lblDob = new JLabel("DOB");
		lblDob.setBounds(370, y, 69, 20);
		body.add(lblDob);
		
		JLabel lblNumberOfSessions = new JLabel("Number of sessions");
		lblNumberOfSessions.setBounds(480, y, 169, 20);
		body.add(lblNumberOfSessions);
		
		y += 49;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, y, 805, 550);
		body.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 5, 0, 0));
		
		for(int i = 0; i<size;i++){
			Child child = children.get(i);
			
			childFNameRow[i] = new JLabel(child.getFirstname());
			childFNameRow[i].setVerticalAlignment(SwingConstants.TOP);
			childFNameRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(childFNameRow[i]);
			
			childSNameRow[i] = new JLabel(child.getSurname());
			childSNameRow[i].setVerticalAlignment(SwingConstants.TOP);
			childSNameRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(childSNameRow[i]);
			
			childDOBRow[i] = new JLabel(model.formatDate(child.getBirthdate()));
			childDOBRow[i].setVerticalAlignment(SwingConstants.TOP);
			childDOBRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(childDOBRow[i]);
			
			String sessions = Integer.toString(child.getSessions().size());
			childSessionRow[i] = new JLabel(sessions);
			childSessionRow[i].setVerticalAlignment(SwingConstants.TOP);
			childSessionRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(childSessionRow[i]);

			viewBtnHolder[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			panel_1.add(viewBtnHolder[i]);
			childRowSelect[i] = new JButton("View");
			childRowSelect[i].addActionListener(controller);
			childRowSelect[i].setActionCommand("c" + Integer.toString(child.getID()));
			viewBtnHolder[i].add(childRowSelect[i]);
			childRowSelect[i].setVerticalAlignment(SwingConstants.TOP);

			}	
	}
	public void individualChild(int id){
		//Method that builds the frame storing information about a specific child.
		JFrame childFrame = new JFrame();
		childFrame.setVisible(true);
		childFrame.setResizable(false);
		childFrame.setBounds(100, 100, 600, 800);
		childFrame.setLocationRelativeTo(null);
		JPanel childPanel = new JPanel();
		childPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		childFrame.setContentPane(childPanel);
		childPanel.setLayout(new BorderLayout());
		this.childFrame = childFrame;
		Child child = model.findChild(id);

		JPanel namesAndDob = new JPanel();
		childPanel.add(namesAndDob, BorderLayout.NORTH);
		namesAndDob.setLayout(new GridLayout(0,2, 50,50));
		JLabel childName = new JLabel("Name:");
		namesAndDob.add(childName);
		JLabel childHead = new JLabel(child.getFirstname() + " " + child.getSurname(), SwingConstants.LEFT);

		namesAndDob.add(childHead);
		
		JLabel childDOBLabel = new JLabel("DOB:");
		namesAndDob.add(childDOBLabel);
		JLabel childDOB = new JLabel(model.formatDate(child.getBirthdate()), SwingConstants.LEFT);

		namesAndDob.add(childDOB);
		
		JLabel guardianLabel = new JLabel("Guardian:");
		namesAndDob.add(guardianLabel);
		JLabel guardian = new JLabel(child.getpTitle() + " " + child.getpFirstname() + " " + child.getpSurname(), SwingConstants.LEFT);
		namesAndDob.add(guardian);
		
		JLabel allergyLabel = new JLabel("Allergy information:");
		namesAndDob.add(allergyLabel);
		JLabel allergy = new JLabel(child.getAllergyInfo()); 
		namesAndDob.add(allergy);
		
		JLabel enrolledSessions = new JLabel("Sessions: ");
		namesAndDob.add(enrolledSessions);
		
		JPanel sessionHolder = new JPanel();
		childPanel.add(sessionHolder, BorderLayout.CENTER);
		
		
		ArrayList<Session> sessions = child.getSessions();
		JLabel[] sessionList = new JLabel[sessions.size()];
		for (int i = 0;i<sessions.size();i++){
				Session session = sessions.get(i);
				sessionList[i] = new JLabel(session.getName(), SwingConstants.LEFT);
				sessionHolder.add(sessionList[i]);
		}
		
		JPanel buttons = new JPanel();
		childPanel.add(buttons, BorderLayout.SOUTH);
		
		showInvoice = new JButton("invoice");
		showInvoice.addActionListener(this.controller);
		showInvoice.setActionCommand(Integer.toString(child.getID()));
		buttons.add(showInvoice);
		this.showInvoice = showInvoice;
		
		editChild = new JButton("edit");
		editChild.addActionListener(this.controller);
		editChild.setActionCommand(Integer.toString(child.getID()));
		buttons.add(editChild);
		this.editChild = editChild;
		
		deleteChild = new JButton("delete");
		deleteChild.addActionListener(this.controller);
		deleteChild.setActionCommand(Integer.toString(child.getID()));
		buttons.add(deleteChild);
		this.deleteChild = deleteChild;
	}
	public void closeSessionFrame(){
		//method that closes the sessionframe.
		sessionFrame.dispose();
	}
	public void closeChildFrame(){
		//method that closes the child frame.
		childFrame.dispose();
		fillBody(3);
	}
	public void buildInvoiceFrame(Child child){
		//method that builds the invoice frame.
		Invoice invoice = child.getInvoice();
		JFrame invoiceFrame = new JFrame();
		invoiceFrame.setVisible(true);
		invoiceFrame.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		invoiceFrame.setContentPane(contentPane);
		this.invoiceFrame = invoiceFrame;
		contentPane.setLayout(null);
		LocalDate currentDate = LocalDate.now();
		double[] charges = invoice.getChargeCosts();
		int height = charges.length * 50;
		invoiceFrame.setBounds(100, 100, 800, 900);
		ArrayList<String> chargeNames = invoice.getChargeNames();
		JLabel[] printCosts = new JLabel[charges.length];
		JLabel[] printNames = new JLabel[chargeNames.size()];
		
		JLabel lblInvoiceTitle = new JLabel("Invoice for " + child.getFirstname() + " " + child.getSurname());
		lblInvoiceTitle.setBounds(269, 16, 245, 20);
		contentPane.add(lblInvoiceTitle);
		
		JLabel lblNewLabel_1 = new JLabel("Guardian:");
		lblNewLabel_1.setBounds(118, 66, 94, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Date:");
		lblNewLabel_2.setBounds(118, 126, 94, 20);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Fees Due:");
		lblNewLabel_3.setBounds(118, 186, 94, 20);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel(child.getpTitle() + " " + child.getpFirstname() + " " + child.getpSurname());
		lblNewLabel.setBounds(329, 66, 224, 20);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel(currentDate.toString());
		label.setBounds(329, 126, 224, 20);
		contentPane.add(label);
		
		int y = 186;
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		JPanel priceContentPane = new JPanel();
		priceContentPane.setLayout(new GridLayout(0,2));
		JScrollPane scrollContentPane = new JScrollPane(priceContentPane);
		scrollContentPane.setBounds(329, y, 300, 324);
		contentPane.add(scrollContentPane);
		for(int i = 0; i < charges.length; i++){
			printNames[i] = new JLabel(chargeNames.get(i));
			printCosts[i] = new JLabel("£" + df.format(charges[i]));
			priceContentPane.add(printNames[i]);
			priceContentPane.add(printCosts[i]);
		}
		y+=384;
		JLabel lblDiscount = new JLabel("Sibling Discount Active: ");
		lblDiscount.setBounds(118, y, 224, 20);
		contentPane.add(lblDiscount);
		String sDiscount = "No";
		if(child.getSibling()){
			sDiscount = "Yes. 10% off.";
		}
		JLabel discount = new JLabel(sDiscount);
		discount.setBounds(329, y, 94, 20);
		contentPane.add(discount);
		y+=60;
		
		JLabel lblNewLabel_4 = new JLabel("Total Price:");
		lblNewLabel_4.setBounds(118, y, 94, 20);
		contentPane.add(lblNewLabel_4);
		
		JLabel label_2 = new JLabel("£" + df.format(invoice.getCost()));
		label_2.setBounds(329, y, 224, 20);
		contentPane.add(label_2);
		y+=60;
		
		JLabel lblDueBy = new JLabel("Payment due by: ");
		lblDueBy.setBounds(118, y, 224, 20);
		contentPane.add(lblDueBy);
		
		JLabel lblDueDate = new JLabel(model.formatDate(child.getPaymentDue()));
		lblDueDate.setBounds(329, y, 224, 20);
		contentPane.add(lblDueDate);
		y+=60;
		
		paid = new JButton("Invoice Paid");
		paid.setBounds(269, y, 224, 20);
		paid.addActionListener(this.controller);
		paid.setActionCommand(Integer.toString(child.getID()));
		contentPane.add(paid);
	}
	public void buildChildEdit(Child child){
		//method that builds the edit child page and populates the fields with the childs details.
		body.removeAll();
		buildChildPage();
		lblPageTitle.setText("Edit Child");
		this.idHolder = child.getID();
		cFNText.setText(child.getFirstname());
		cSNText.setText(child.getSurname());
		addressText.setText(child.getAddress());
		
		LocalDate dob = child.getBirthdate();
		String day = Integer.toString(dob.getDayOfMonth());
		String month = Integer.toString(dob.getMonthValue());
		String year = Integer.toString(dob.getYear());
		
		dayText.setText(day);
		monthText.setText(month);
		yearText.setText(year);
		allergyText.setText(child.getAllergyInfo());
		siblingBox.setSelected(child.getSibling());
		gTitleText.setText(child.getpTitle());
		gFNText.setText(child.getpFirstname());
		gSNText.setText(child.getpSurname());
		
		JButton sessionSelect = new JButton("Select Sessions");
		sessionSelect.setBounds(629, 317, 146, 26);
		sessionSelect.setActionCommand("sessionSelect");
		sessionSelect.addActionListener(this.controller);
		
		body.add(sessionSelect);
		body.remove(btnSubmit);
		
		confirmEdit = new JButton("Confirm Changes");
		confirmEdit.setBounds(629, 513, 146, 26);
		confirmEdit.addActionListener(this.controller);
		confirmEdit.setActionCommand(Integer.toString(child.getID()));
		

		ArrayList<Session> sessions = child.getSessions();
		
		JPanel childSessionHolder = new JPanel();
		childSessionHolder.setBounds(629, 345, 146, 165);
		childSessionHolder.setLayout(new GridLayout(0,1));
		body.add(childSessionHolder);
		
		String[] sessionArray = new String[sessions.size()];
		
		for(int i = 0; i < sessions.size(); i++){
			String selectedSession = sessions.get(i).getName();
			sessionArray[i] = selectedSession;
		}
		
		JList<String> sessionDisplay = new JList<String>(sessionArray);
		sessionDisplay.setEnabled(false);
		sessionDisplay.setSize(146,165);
		
		this.sessionDisplay = sessionDisplay;
		childSessionHolder.add(sessionDisplay);
		this.enrolled = sessions;
		childSessionHolder.repaint();
		body.add(confirmEdit);
		body.repaint();
	}
	public void invoicePaid(Child child){
		//method that closes the invoice frame.
		invoiceFrame.dispose();
	}
	public void buildAdminPage(){
		//method that builds the admin list page.
		int y = 42;
		
		adminUsernameRow = new JLabel[model.fetchAdmins().size()];
		adminIDRow = new JLabel[model.fetchAdmins().size()];
		adminDelete = new JButton[model.fetchAdmins().size()];
		this.adminUsernameRow = adminUsernameRow;
		this.adminIDRow = adminIDRow;
		this.adminDelete = adminDelete;
		
		JLabel lblUsername = new JLabel("New Username:");
		lblUsername.setBounds(50, y, 115, 20);
		body.add(lblUsername);
		
		txtUserField = new JTextField();
		txtUserField.setBounds(250, y, 146, 26);
		txtUserField.setColumns(10);
		body.add(txtUserField);
		
		y +=49;
		
		JLabel lblPassword = new JLabel("New Password:");
		lblPassword.setBounds(50, y, 115, 20);
		body.add(lblPassword);
		
		txtPWField = new JPasswordField();
		txtPWField.setBounds(250, y, 146, 26);
		txtPWField.setEchoChar('*');
		txtPWField.setColumns(10);
		body.add(txtPWField);
		
		JButton submitAdmin = new JButton("Submit");
		submitAdmin.setBounds(650, y, 115, 29);
		submitAdmin.addActionListener(this.controller);
		this.submitAdmin = submitAdmin;
		body.add(submitAdmin);
		
		y+= 49;
		
		JLabel lblUserRow = new JLabel("Username");
		lblUserRow.setBounds(50, y, 85, 20);
		body.add(lblUserRow);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(320, y, 69, 20);
		body.add(lblID);

		y += 49;
		ArrayList<Admin> admins = model.fetchAdmins();
		int size = admins.size();
		adminUsernameRow = new JLabel[size];
		adminIDRow = new JLabel[size];
		adminDelete = new JButton[size];
		JPanel[] viewBtnHolder = new JPanel[size];
		this.adminUsernameRow = adminUsernameRow;
		this.adminIDRow = adminIDRow;
		this.adminDelete = adminDelete;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, y, 805, 452);
		body.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		for(int i = 0; i<size;i++){
			Admin admin = admins.get(i);
			
			adminUsernameRow[i] = new JLabel(admin.getUsername());
			adminUsernameRow[i].setVerticalAlignment(SwingConstants.TOP);
			adminUsernameRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(adminUsernameRow[i]);
			
			adminIDRow[i] = new JLabel(Integer.toString(admin.getId()));
			adminIDRow[i].setVerticalAlignment(SwingConstants.TOP);
			adminIDRow[i].setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(adminIDRow[i]);

			viewBtnHolder[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			panel_1.add(viewBtnHolder[i]);
			if(size>1){
				adminDelete[i] = new JButton("Delete");
				adminDelete[i].addActionListener(controller);
				adminDelete[i].setActionCommand("a" + Integer.toString(admin.getId()));
				viewBtnHolder[i].add(adminDelete[i]);
				adminDelete[i].setVerticalAlignment(SwingConstants.TOP);
			}
		}
	}
	public void newAdmin(){
		//method that retrieves the details from the admin list textfields and uses them to create a new admin if they are valid.
		String username = txtUserField.getText();
		String password = txtPWField.getText();
		if (!txtUserField.getText().equals("") && !txtPWField.getText().equals("")){
			int newAdminId = model.generateAdminID();
			Admin admin = new Admin(username,password, newAdminId);
			model.newAdmin(admin);
			System.out.println(username + " " + password + " " + newAdminId);
			fillBody(4);
		}else{
			JOptionPane.showMessageDialog(null, "Error, one of the fields was invalid. Make sure both username and password fields contain something", "InfoBox: " + "Invalid entry", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void login(){
		//method used to log he user in.
		System.out.println("login called");
		String username = loginUsername.getText();
		String password = loginPassword.getText();
		if(model.login(username,password)){
			body.removeAll();
			JLabel lblYouAreNow = new JLabel("You are now logged in as: " + username);
			lblYouAreNow.setBounds(255, 249, 364, 84);
			lblYouAreNow.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(lblYouAreNow);
			body.repaint();
			loggedIn = true;
		}else{
			JOptionPane.showMessageDialog(null, "Incorrect username or password, please try again. Inputs are case sensitive.", "InfoBox: " + "Invalid Details", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void logOut(){
		//method to log out.
		loggedIn = false;
		fillBody(5);
	}
}
