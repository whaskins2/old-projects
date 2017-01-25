package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Controller.WUC_Controller;
import Model.WUC_Student_M;

public class WUC_Student_V{
	public JPanel jPCentre;
	private JButton jBSubmit;
	private JTextField jTUser, jTPass;
	private JLabel jlLogo, jLUser, jLPassword, jLHead;
	ImageIcon img = new ImageIcon("img/logo.jpg");
	public JButton btnSaveAndContinue;
	public JButton delete;
	public JButton createStud;
	public JButton btnSearch;
	public JButton editStud;
	public JButton deleteStud;
	private WUC_Controller controller;
	private WUC_Student_M model;
	private JTextField fnText;
	private JTextField mnText;
	private JTextField snText;
	private JTextField haText;
	private JTextField phoneText;
	private JTextField emailText;
	private JTextField qualText;
	private JTextField pwText;
	private JTextField taText;
	private JTextField jTASearch;
	public JTextField txtID;
	public JTextField txtFN;
	public JTextField txtSN;
	public JComboBox courseComboBox;
	public JComboBox stComboBox;
	private JComboBox genderComboBox;
	public JComboBox cBStatus;
	public JComboBox cBCourse;
	public JComboBox studentComboBox;
	private JLabel taLabel;
	private JLabel courseLabel;
	private JComboBox tutorComboBox;
	private ResultSet myRs;
	public int studentID;
	public String statement[] = new String[5];
	private JFrame studFrame;
	
	public WUC_Student_V(WUC_Controller controller, WUC_Student_M model, int state){
		this.controller = controller;
		this.model = model;
		jPCentre = new JPanel();
		jPCentre.setBounds(0, 0, 955, 415);
		jPCentre.setLayout(null);
		this.jPCentre = jPCentre;
		initStmt();
		fillMain(state);
	}
	
	public void initStmt(){
		//Sets the default search parameters for the query page
		this.statement = model.refineSearch("", "", "", "", "");
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void fillMain(int state){
		//State variable corresponds to which mode the view is being used in. 1 - Create, 2 - Amend, 3 - Query.
		if(state == 1|| state == 2){
			JLabel lblImagegoesHere = new JLabel();
			lblImagegoesHere.setIcon(img);
			lblImagegoesHere.setBounds(55, 60, 220, 210);
			jPCentre.add(lblImagegoesHere);
			
			JButton btnNewButton = new JButton("New button");
			btnNewButton.setBounds(160, 273, 115, 29);
			jPCentre.add(btnNewButton);
			
			createStud = new JButton("Create Student");
			createStud.setBounds(771, 377, 150, 29);
			createStud.addActionListener(controller);
			createStud.setActionCommand("Create Student");
			jPCentre.add(createStud);
			this.createStud = createStud;
			
			JLabel fnLabel = new JLabel("Firstname");
			fnLabel.setBounds(290, 60, 110, 20);
			jPCentre.add(fnLabel);
			
			fnText = new JTextField();
			fnText.setColumns(10);
			fnText.setBounds(415, 60, 146, 26);
			jPCentre.add(fnText);
			
			JLabel mnLabel = new JLabel("Middlename");
			mnLabel.setBounds(290, 110, 110, 20);
			jPCentre.add(mnLabel);
			
			mnText = new JTextField();
			mnText.setColumns(10);
			mnText.setBounds(415, 110, 146, 26);
			jPCentre.add(mnText);
			
			JLabel snLabel = new JLabel("Surname");
			snLabel.setBounds(290, 160, 110, 20);
			jPCentre.add(snLabel);
			
			snText = new JTextField();
			snText.setColumns(10);
			snText.setBounds(415, 160, 146, 26);
			jPCentre.add(snText);
			
			JLabel genLabel = new JLabel("Gender");
			genLabel.setBounds(290, 210, 110, 20);
			jPCentre.add(genLabel);
			
			genderComboBox = new JComboBox();
			genderComboBox.setBounds(415, 207, 146, 26);
			genderComboBox.addItem("Male");
			genderComboBox.addItem("Female");
			genderComboBox.addItem("Other");
			this.genderComboBox = genderComboBox;
			jPCentre.add(genderComboBox);
			
			JLabel haLabel = new JLabel("Home Address");
			haLabel.setBounds(290, 259, 110, 20);
			jPCentre.add(haLabel);
			
			haText = new JTextField();
			haText.setColumns(10);
			haText.setBounds(415, 260, 146, 26);
			jPCentre.add(haText);
			
			taLabel = new JLabel("Term Address");
			taLabel.setBounds(290, 310, 110, 20);
			jPCentre.add(taLabel);
			
			taText = new JTextField();
			taText.setColumns(10);
			taText.setBounds(415, 310, 146, 26);
			jPCentre.add(taText);
			
			stComboBox = new JComboBox();
			stComboBox.setBounds(415, 360, 146, 26);
			stComboBox.addItem("Provisional");
			stComboBox.addItem("Studying");
			stComboBox.addItem("Graduated");
			stComboBox.addItem("Withdrew");
			stComboBox.addItem("Terminated");
			this.stComboBox = stComboBox;
			jPCentre.add(stComboBox);
			
			JLabel stLabel = new JLabel("Status");
			stLabel.setBounds(290, 363, 110, 20);
			jPCentre.add(stLabel);
			
			JLabel phoneLabel = new JLabel("Phone Number");
			phoneLabel.setBounds(614, 60, 146, 20);
			jPCentre.add(phoneLabel);
			
			phoneText = new JTextField();
			phoneText.setColumns(10);
			phoneText.setBounds(775, 60, 146, 26);
			jPCentre.add(phoneText);
			
			JLabel emailLabel = new JLabel("Email");
			emailLabel.setBounds(614, 110, 146, 20);
			jPCentre.add(emailLabel);
			
			emailText = new JTextField();
			emailText.setColumns(10);
			emailText.setBounds(775, 110, 146, 26);
			jPCentre.add(emailText);
			
			JLabel qualLabel = new JLabel("Entry Qualifications");
			qualLabel.setBounds(614, 160, 140, 20);
			jPCentre.add(qualLabel);
			
			qualText = new JTextField();
			qualText.setColumns(10);
			qualText.setBounds(775, 160, 146, 26);
			jPCentre.add(qualText);
			
			JLabel pwLabel = new JLabel("System Password");
			pwLabel.setBounds(614, 210, 146, 20);
			jPCentre.add(pwLabel);
			
			pwText = new JTextField();
			pwText.setColumns(10);
			pwText.setBounds(775, 210, 146, 26);
			jPCentre.add(pwText);
			
			courseLabel = new JLabel("Course");
			courseLabel.setBounds(614, 259, 146, 20);
			jPCentre.add(courseLabel);
			
			courseComboBox = new JComboBox();
			courseComboBox.setBounds(775, 260, 146, 26);
			model.populateDropDown(courseComboBox, "courses", "title");
			jPCentre.add(courseComboBox);
			
			tutorComboBox = new JComboBox();
			tutorComboBox.setBounds(775, 310, 146, 26);
			model.populateDropDown(tutorComboBox, "staff", "staff_id");
			jPCentre.add(tutorComboBox);
			
			JLabel tutorLabel = new JLabel("Personal Tutor ID");
			tutorLabel.setBounds(614, 310, 146, 20);
			jPCentre.add(tutorLabel);
			
			
			if (state == 2){
				try {
					Connection myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
					//SQL Statement
					Statement myStmt = myConn.createStatement();
					//Execute SQL Statement
					
					JComboBox studentComboBox = new JComboBox();
					studentComboBox.setBounds(415, 18, 146, 26);
					studentComboBox.addItem("Select a Student");
					model.populateDropDown(studentComboBox, "students", "student_id");
					studentComboBox.addItemListener(controller);
					this.studentComboBox = studentComboBox;
					jPCentre.add(studentComboBox);
					
					JLabel studentLbl = new JLabel("Select a student");
					studentLbl.setBounds(244, 24, 115, 20);
					jPCentre.add(studentLbl);
					
					delete = new JButton("Delete Student");
					delete.setBounds(614, 377, 150, 29);
					delete.addActionListener(controller);
					jPCentre.add(delete);
					this.delete = delete;
					
					jPCentre.remove(createStud);
					btnSaveAndContinue = new JButton("Save and Continue");
					btnSaveAndContinue.setBounds(771, 377, 150, 29);
					btnSaveAndContinue.addActionListener(controller);
					jPCentre.add(btnSaveAndContinue);
					this.btnSaveAndContinue = btnSaveAndContinue;
					myConn.close();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else if(state == 3){
			int size = model.resultsSize();
			System.out.println(size);
			JLabel[] studID = new JLabel[size];
			JLabel[] studFN = new JLabel[size];
			JLabel[] studSN = new JLabel[size];
			JLabel[] studGender = new JLabel[size];
			JLabel[] studStatus = new JLabel[size];
			JLabel[] studEnrollDate = new JLabel[size];
			JLabel[] studCourse = new JLabel[size];
			JLabel[] studTutor = new JLabel[size];
			JButton[] studViewBtn = new JButton[size];
			JPanel[] viewBtnHolder = new JPanel[size];
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(123, 37, 817, 362);
			jPCentre.add(scrollPane);
			
			JPanel panel_1 = new JPanel();
			scrollPane.setViewportView(panel_1);
			panel_1.setLayout(new GridLayout(0, 9, 0, 0));
			
			ResultSet students = null;
			try {
				Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
				Statement myStmt = db.createStatement();
				String stmt = "SELECT * FROM students WHERE student_id LIKE ? AND firstname LIKE ? AND surname LIKE ? AND status LIKE ? AND course_code LIKE ? ";
				//students = myStmt.executeQuery(statement);
				PreparedStatement studQuery = null;
				studQuery = db.prepareStatement(stmt);
				
				studQuery.setString(1,statement[0]);
				studQuery.setString(2,statement[1]);
				studQuery.setString(3,statement[2]);
				studQuery.setString(4,statement[3]);
				studQuery.setString(5,statement[4]);
				students = studQuery.executeQuery();
				
				int i = 0;
				while(students.next()){
					studID[i] = new JLabel(students.getString("student_id"));
					studID[i].setVerticalAlignment(SwingConstants.TOP);
					studID[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studID[i]);
					
					studFN[i] = new JLabel(students.getString("firstname"));
					studFN[i].setVerticalAlignment(SwingConstants.TOP);
					studFN[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studFN[i]);
					
					studSN[i] = new JLabel(students.getString("surname"));
					studSN[i].setVerticalAlignment(SwingConstants.TOP);
					studSN[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studSN[i]);
					
					studGender[i] = new JLabel(students.getString("gender"));
					studGender[i].setVerticalAlignment(SwingConstants.TOP);
					studGender[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studGender[i]);
					
					studStatus[i] = new JLabel(students.getString("status"));
					studStatus[i].setVerticalAlignment(SwingConstants.TOP);
					studStatus[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studStatus[i]);
					
					studEnrollDate[i] = new JLabel(students.getString("enrollment_date"));
					studEnrollDate[i].setVerticalAlignment(SwingConstants.TOP);
					studEnrollDate[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studEnrollDate[i]);
					
					studCourse[i] = new JLabel(students.getString("course_code"));
					studCourse[i].setVerticalAlignment(SwingConstants.TOP);
					studCourse[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studCourse[i]);
					
					studTutor[i] = new JLabel(students.getString("personal_tutor_id"));
					studTutor[i].setVerticalAlignment(SwingConstants.TOP);
					studTutor[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(studTutor[i]);
					
					viewBtnHolder[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
					panel_1.add(viewBtnHolder[i]);
					
					studViewBtn[i] = new JButton("View");
					studViewBtn[i].addActionListener(controller);
					studViewBtn[i].setActionCommand("s" + students.getString("student_id"));
					viewBtnHolder[i].add(studViewBtn[i]);
					studViewBtn[i].setVerticalAlignment(SwingConstants.TOP);
					i++;
				}
				db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cBStatus = new JComboBox();
			cBStatus.setBounds(17, 170, 91, 26);
			cBStatus.addItem("Select Status");
			cBStatus.addItem("Provisional");
			cBStatus.addItem("Studying");
			cBStatus.addItem("Graduated");
			cBStatus.addItem("Withdrew");
			cBStatus.addItem("Terminated");
			this.cBStatus = cBStatus;
			jPCentre.add(cBStatus);
			
			cBCourse = new JComboBox();
			cBCourse.setBounds(17, 210, 91, 26);
			cBCourse.addItem("Select Course");
			model.populateDropDown(cBCourse, "courses", "title");
			this.cBCourse = cBCourse;
			jPCentre.add(cBCourse);
			
			txtID = new JTextField("Search by ID");
			txtID.setBounds(17, 50, 91, 26);
			txtID.addMouseListener(controller);
			this.txtID = txtID;
			jPCentre.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblSearch = new JLabel("Refine Search");
			lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
			lblSearch.setBounds(17, 13, 107, 20);
			jPCentre.add(lblSearch);
			
			txtFN = new JTextField("Search by Firstname");
			txtFN.setColumns(10);
			txtFN.setBounds(17, 90, 91, 26);
			txtFN.addMouseListener(controller);
			this.txtFN = txtFN;
			jPCentre.add(txtFN);
			
			txtSN = new JTextField("Search by Surname");
			txtSN.setColumns(10);
			txtSN.setBounds(17, 130, 91, 26);
			txtSN.addMouseListener(controller);
			this.txtSN = txtSN;
			jPCentre.add(txtSN);
			
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel.setBounds(125, 13, 57, 20);
			jPCentre.add(lblNewLabel);
			
			JLabel lblFirstname = new JLabel("Firstname");
			lblFirstname.setHorizontalAlignment(SwingConstants.LEFT);
			lblFirstname.setBounds(217, 13, 78, 20);
			jPCentre.add(lblFirstname);
			
			JLabel lblMiddlename = new JLabel("Surname");
			lblMiddlename.setHorizontalAlignment(SwingConstants.LEFT);
			lblMiddlename.setBounds(307, 13, 69, 20);
			jPCentre.add(lblMiddlename);
			
			JLabel lblSurname = new JLabel("Gender");
			lblSurname.setHorizontalAlignment(SwingConstants.LEFT);
			lblSurname.setBounds(391, 13, 51, 20);
			jPCentre.add(lblSurname);
			
			JLabel lblGender = new JLabel("Status");
			lblGender.setHorizontalAlignment(SwingConstants.LEFT);
			lblGender.setBounds(490, 13, 69, 20);
			jPCentre.add(lblGender);
			
			JLabel lblTermAddress = new JLabel("Enrol Date");
			lblTermAddress.setHorizontalAlignment(SwingConstants.LEFT);
			lblTermAddress.setBounds(574, 13, 91, 20);
			jPCentre.add(lblTermAddress);
			
			JLabel lblHomeAddress = new JLabel("Course");
			lblHomeAddress.setHorizontalAlignment(SwingConstants.LEFT);
			lblHomeAddress.setBounds(667, 13, 69, 20);
			jPCentre.add(lblHomeAddress);
			
			JLabel lblTelephoneNumber = new JLabel("Tutor");
			lblTelephoneNumber.setHorizontalAlignment(SwingConstants.LEFT);
			lblTelephoneNumber.setBounds(756, 13, 69, 20);
			jPCentre.add(lblTelephoneNumber);
			
			btnSearch = new JButton("Search");
			btnSearch.setBounds(15, 250, 93, 29);
			btnSearch.addActionListener(controller);
			this.btnSearch = btnSearch;
			jPCentre.add(btnSearch);
		}
	}
	public void selectedStudent(int id){
		//This method populates the text fields when altering a student.
		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from students where student_id = " + id);
			
			int staff_id = 0;
			String course_code = "";
			while (myRs.next()) {
				System.out.println(myRs.getString("firstname") + ", " + myRs.getString(("surname")));
				
				fnText.setText(myRs.getString("firstname"));
				mnText.setText(myRs.getString("middlename"));
				snText.setText(myRs.getString("surname"));
				haText.setText(myRs.getString("home_address"));
				taText.setText(myRs.getString("term_address"));
				phoneText.setText(myRs.getString("telephone_number"));
				emailText.setText(myRs.getString("email"));
				qualText.setText(myRs.getString("entry_qualifications"));
				pwText.setText(myRs.getString("password"));
				staff_id = myRs.getInt("personal_tutor_id"); 
				course_code = myRs.getString("course_code");
			}
			ResultSet course = myStmt.executeQuery("SELECT * FROM courses WHERE course_code = '" + course_code + "'");
			
			while(course.next()){
				courseComboBox.addItem(course.getString("title"));
			}
			
			ResultSet tutor = myStmt.executeQuery("SELECT * FROM staff WHERE staff_id = " + staff_id);
	
			while(tutor.next()){
				tutorComboBox.addItem(tutor.getString("firstname") + " " + tutor.getString("surname"));
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void studentMaker(boolean newStudent){
		//This method adds a new student to the database
		Connection myConn;
		System.out.println("studentMaker called");
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = myConn.createStatement();
			String courseTitle = courseComboBox.getSelectedItem().toString();
			ResultSet findCourseCode = myStmt.executeQuery("select course_code from courses where title = '" + courseTitle + "'");
			String courseCode = "";
			while(findCourseCode.next()){
				courseCode = findCourseCode.getString("course_code");
			}
			
			String firstname = fnText.getText();
			System.out.println(firstname + " can you see me?");
			String surname = snText.getText();
			String middlename = mnText.getText();
			String homeAddress = haText.getText();
			String termAddress = taText.getText();
			String phone = phoneText.getText();
			String email = emailText.getText();
			String qualifications = qualText.getText();
			String password = pwText.getText();
			String status = stComboBox.getSelectedItem().toString();
			String gender = genderComboBox.getSelectedItem().toString();
			if(gender == "Male"){
				gender = "M";
			}else if(gender == "Female"){
				gender = "F";
			}
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String imageFilepath = "example/file/path.jpg";
			int personal_tutor_id = Integer.parseInt(tutorComboBox.getSelectedItem().toString());
			
			if(newStudent){
				System.out.println("trying to add a new student");
				System.out.println("FN:" + firstname + surname + middlename + homeAddress + termAddress + phone);
				String statement = "INSERT INTO students(password, status, firstname, surname, middlename, gender, term_address, home_address, telephone_number, email, entry_qualifications, course_code, enrollment_date, personal_tutor_id, image_filepath)"
						+ "			VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement studInsert = null;
				studInsert = myConn.prepareStatement(statement);
				studInsert.setString(1, password);
				studInsert.setString(2, status);
				studInsert.setString(3, firstname);
				studInsert.setString(4, surname);
				studInsert.setString(5, middlename);
				studInsert.setString(6, gender);
				studInsert.setString(7, termAddress);
				studInsert.setString(8, homeAddress);
				studInsert.setString(9, phone);
				studInsert.setString(10, email);
				studInsert.setString(11, qualifications);
				studInsert.setString(12, courseCode);
				studInsert.setString(13, date);
				studInsert.setInt(14, 10000000);
				studInsert.setString(15, imageFilepath);
				studInsert.executeUpdate();
			
				Statement fetchIDstmt = myConn.createStatement();
				ResultSet ID = fetchIDstmt.executeQuery("SELECT student_id FROM students WHERE email = '" + email + "'");
				while(ID.next()){
					studentID = ID.getInt("student_id");
				}
				alert("Student successfully added to the database. New student's ID is: " + studentID, "Student Added");
			}else{
				System.out.println("trying to update a student");
				System.out.println("FN:" + firstname + surname + middlename + homeAddress + termAddress + phone);
				String statement = "UPDATE students SET password = ?, status = ?, firstname = ?, surname = ?, middlename = ?,"
						+ "			gender = ?, term_address = ?, home_address = ?, telephone_number = ?, email = ?, entry_qualifications = ?,"
						+ "			course_code = ?, enrollment_date = ?, personal_tutor_id = ?, image_filepath = ? WHERE student_id = " + studentID;
				
				
				PreparedStatement studUpdate = null;
				studUpdate = myConn.prepareStatement(statement);
				studUpdate.setString(1, password);
				studUpdate.setString(2, status);
				studUpdate.setString(3, firstname);
				studUpdate.setString(4, surname);
				studUpdate.setString(5, middlename);
				studUpdate.setString(6, gender);
				studUpdate.setString(7, termAddress);
				studUpdate.setString(8, homeAddress);
				studUpdate.setString(9, phone);
				studUpdate.setString(10, email);
				studUpdate.setString(11, qualifications);
				studUpdate.setString(12, courseCode);
				studUpdate.setString(13, date);
				studUpdate.setInt(14, 10000000);
				studUpdate.setString(15, imageFilepath);
				studUpdate.executeUpdate();
				alert("Student " + studentID + " successfully updated.", "Student Updated");
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alert(String infoMessage, String titleBar){
			JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	public JPanel getPanel(){
		return jPCentre;
	}

	public void viewStudent(int id) {
		//Method for displaying individual student details in more detail than can be displayed on the query page.
		ResultSet student = null;
		try {
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "SELECT * FROM students WHERE student_id = " + id;
			student = myStmt.executeQuery(statement);
			
			studFrame = new JFrame();
			studFrame.setVisible(true);
			studFrame.setResizable(false);
			studFrame.setBounds(100, 100, 600, 800);
			studFrame.setLocationRelativeTo(null);
			JPanel studPanel = new JPanel();
			studPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			//childPanel.setBackground(Color.CYAN);
			studFrame.setContentPane(studPanel);
			studPanel.setLayout(new BorderLayout());
			//this.childFrame = childFrame;
			
			while(student.next()){
				JPanel namesAndDob = new JPanel();
				studPanel.add(namesAndDob, BorderLayout.NORTH);
				namesAndDob.setLayout(new GridLayout(0,2, 50,40));
				
				JLabel studNameLbl = new JLabel("Name:");
				namesAndDob.add(studNameLbl);
				JLabel studHead = new JLabel(student.getString("firstname") + " " + student.getString("middlename") + " " + student.getString("surname"), SwingConstants.LEFT);
				namesAndDob.add(studHead);
				
				JLabel studIDlbl = new JLabel("Student ID:");
				namesAndDob.add(studIDlbl);
				JLabel studID = new JLabel(student.getString("student_id"), SwingConstants.LEFT);
				namesAndDob.add(studID);
				
				JLabel studEnrDateLbl = new JLabel("Enrollment Date:");
				namesAndDob.add(studEnrDateLbl);
				JLabel studEnrDate = new JLabel(student.getString("enrollment_date"), SwingConstants.LEFT);
				namesAndDob.add(studEnrDate);
				
				JLabel studStatusLbl = new JLabel("Status:");
				namesAndDob.add(studStatusLbl);
				JLabel studStatus = new JLabel(student.getString("status"), SwingConstants.LEFT);
				namesAndDob.add(studStatus);
				
				JLabel studGenderLbl = new JLabel("Gender:");
				namesAndDob.add(studGenderLbl);
				JLabel studGender = new JLabel(student.getString("gender")); 
				namesAndDob.add(studGender);
				
				JLabel termAddressLbl = new JLabel("Term Address: ");
				namesAndDob.add(termAddressLbl);
				JLabel termAdd = new JLabel(student.getString("term_address"));
				namesAndDob.add(termAdd);
				
				JLabel homeAddressLbl = new JLabel("Home Address: ");
				namesAndDob.add(homeAddressLbl);
				JLabel homeAddress = new JLabel(student.getString("home_address"));
				namesAndDob.add(homeAddress);
				
				JLabel telephoneLbl = new JLabel("Telephone: ");
				namesAndDob.add(telephoneLbl);
				JLabel studPhone = new JLabel(student.getString("telephone_number"));
				namesAndDob.add(studPhone);
				
				JLabel studEmailLbl = new JLabel("Email: ");
				namesAndDob.add(studEmailLbl);
				JLabel emailLbl = new JLabel(student.getString("email"));
				namesAndDob.add(emailLbl);
				
				JLabel qualificationsLbl = new JLabel("Entry Qualifications: ");
				namesAndDob.add(qualificationsLbl);
				JLabel studQuals = new JLabel(student.getString("entry_qualifications"));
				namesAndDob.add(studQuals);
				
				JLabel courseCodeLbl = new JLabel("Course Code: ");
				namesAndDob.add(courseCodeLbl);
				JLabel studCourse = new JLabel(student.getString("course_code"));
				namesAndDob.add(studCourse);
				
				JLabel tutorIdLbl = new JLabel("Tutor ID: ");
				namesAndDob.add(tutorIdLbl);
				JLabel tutorId = new JLabel(student.getString("personal_tutor_id"));
				namesAndDob.add(tutorId);
				
				JPanel buttons = new JPanel();
				studPanel.add(buttons, BorderLayout.SOUTH);
				
				editStud = new JButton("edit");
				editStud.addActionListener(this.controller);
				editStud.setActionCommand(student.getString("student_id"));
				buttons.add(editStud);
				this.editStud = editStud;
				
				deleteStud = new JButton("delete");
				deleteStud.addActionListener(this.controller);
				deleteStud.setActionCommand(student.getString("student_id"));
				buttons.add(deleteStud);
				this.deleteStud = deleteStud;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeStudFrame(){
		studFrame.dispose();
	}
}