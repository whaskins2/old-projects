package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.WUC_Controller;
import Model.Diary;
import Model.WUC_Diary_M;
	
public class WUC_Diary_V {
	public JPanel contentPane;
	private WUC_Controller controller;
	private WUC_Diary_M model;
	private JLabel lblLocation;
	private JTextField locTxt;
	private JTextArea descriptionTxt;
	private JLabel lblDescription;
	private JLabel creatorID;
	private JLabel idLbl;
	private JLabel dateLbl;
	private JLabel timeLbl;
	private JLabel creatorName;
	public JTextField txtID;
	public JButton btnSearch;
	public JButton btnSubmit;
	public JButton btnSaveAndContinue;
	public JButton btnDelete;
	public JButton editEntry;
	public JButton deleteEntry;
	public JComboBox entryComboBox;
	public JComboBox cType;
	public String statement[] = new String[5];
	private JFrame entryFrame;
	public ArrayList<Diary> entries;
	
	public WUC_Diary_V(WUC_Controller controller, WUC_Diary_M model, int state){
		this.controller = controller;
		this.model = model;
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 955, 415);
		contentPane.setBackground(Color.GRAY);
		this.contentPane = contentPane;
		fillMain(state);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void fillMain(int state){
		//State variable corresponds to which mode the view is being used in. 1 - Create, 2 - Amend, 3 - Query.
		entries = model.retrieveEntries();
		if(state == 1){
			descriptionTxt = new JTextArea();
			descriptionTxt.setBounds(180, 160, 300, 200);
			descriptionTxt.setLineWrap(true);
			this.descriptionTxt = descriptionTxt;
			JScrollPane scrollDesc = new JScrollPane(descriptionTxt);
			scrollDesc.setBounds(180, 60, 645, 221);
			contentPane.add(scrollDesc);
			
			lblDescription = new JLabel("Diary Message:");
			lblDescription.setBounds(55, 60, 115, 26);
			contentPane.add(lblDescription);
			
			btnSubmit = new JButton("Submit");
			btnSubmit.setBounds(635, 331, 190, 29);
			btnSubmit.addActionListener(controller);
			contentPane.add(btnSubmit);
		}else if (state == 2){
			
			entryComboBox = new JComboBox();
			entryComboBox.setBounds(180, 10, 146, 26);
			entryComboBox.addItem("Select a diary entry");
			model.populateDropDown(entryComboBox, "diary_items", "diary_item_id");
			entryComboBox.addItemListener(controller);
			this.entryComboBox = entryComboBox;
			contentPane.add(entryComboBox);
			
			JLabel eventLbl = new JLabel("Select an event:");
			eventLbl.setBounds(55, 10, 146, 26);
			contentPane.add(eventLbl);
			
			JLabel cIdLbl = new JLabel("Creator ID:");
			cIdLbl.setBounds(55, 60, 115, 26);
			contentPane.add(cIdLbl);
			
			creatorID = new JLabel("");
			creatorID.setBounds(180, 60, 300, 26);
			this.creatorID = creatorID;
			contentPane.add(creatorID);
			
			creatorName = new JLabel("");
			creatorName.setBounds(300, 60, 300, 26);
			this.creatorName = creatorName;
			contentPane.add(creatorName);
			
			JLabel lblEntry = new JLabel("Entry ID:");
			lblEntry.setBounds(55, 110, 115, 26);
			contentPane.add(lblEntry);
			
			idLbl = new JLabel("");
			idLbl.setBounds(180, 110, 300, 26);
			this.idLbl = idLbl;
			contentPane.add(idLbl);
			
			descriptionTxt = new JTextArea();
			descriptionTxt.setBounds(180, 160, 300, 200);
			descriptionTxt.setLineWrap(true);
			this.descriptionTxt = descriptionTxt;
			JScrollPane scrollDesc = new JScrollPane(descriptionTxt);
			scrollDesc.setBounds(180, 160, 300, 200);
			contentPane.add(scrollDesc);
			
			lblDescription = new JLabel("Message:");
			lblDescription.setBounds(55, 160, 115, 20);
			contentPane.add(lblDescription);
			
			JLabel lblDate = new JLabel("Date:");
			lblDate.setBounds(510, 60, 115, 26);
			contentPane.add(lblDate);
			
			dateLbl = new JLabel("");
			dateLbl.setBounds(635, 60, 300, 26);
			contentPane.add(dateLbl);
			
			JLabel lblTime = new JLabel("Time:");
			lblTime.setBounds(510, 110, 115, 26);
			contentPane.add(lblTime);
			
			timeLbl = new JLabel("");
			timeLbl.setBounds(635, 110, 300, 26);
			contentPane.add(timeLbl);
			
			btnSaveAndContinue = new JButton("Save and Continue");
			btnSaveAndContinue.setBounds(635, 331, 190, 29);
			btnSaveAndContinue.addActionListener(controller);
			contentPane.add(btnSaveAndContinue);
			
			btnDelete = new JButton("Delete");
			btnDelete.setBounds(635, 281, 190, 29);
			btnDelete.addActionListener(controller);
			contentPane.add(btnDelete);
			
		}else if(state == 3){
			int size = entries.size();
			JLabel[] entryID = new JLabel[size];
			JLabel[] entryDate = new JLabel[size];
			JLabel[] creatorID = new JLabel[size];
			JLabel[] creatorType = new JLabel[size];
			JPanel[] viewBtnHolder = new JPanel[size];
			JButton[] entryViewBtn = new JButton[size];
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(123, 37, 817, 362);
			contentPane.add(scrollPane);
			
			JPanel panel_1 = new JPanel();
			scrollPane.setViewportView(panel_1);
			panel_1.setLayout(new GridLayout(0, 5, 0, 0));
			
			for(int i = 0; i<size;i++){
				Diary entry = entries.get(i);
				entryID[i] = new JLabel(Integer.toString(entry.getDiary_item_id()));
				entryID[i].setVerticalAlignment(SwingConstants.TOP);
				entryID[i].setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(entryID[i]);
				
				entryDate[i] = new JLabel(entry.getDate());
				entryDate[i].setVerticalAlignment(SwingConstants.TOP);
				entryDate[i].setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(entryDate[i]);
				
				creatorID[i] = new JLabel(Integer.toString(entry.getCreator_id()));
				creatorID[i].setVerticalAlignment(SwingConstants.TOP);
				creatorID[i].setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(creatorID[i]);
				
				creatorType[i] = new JLabel(entry.getCreatorType());
				creatorType[i].setVerticalAlignment(SwingConstants.TOP);
				creatorType[i].setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(creatorType[i]);
				
				viewBtnHolder[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
				panel_1.add(viewBtnHolder[i]);
				
				entryViewBtn[i] = new JButton("View");
				entryViewBtn[i].addActionListener(controller);
				entryViewBtn[i].setActionCommand("d" + entry.getDiary_item_id());
				viewBtnHolder[i].add(entryViewBtn[i]);
				entryViewBtn[i].setVerticalAlignment(SwingConstants.TOP);
			}
			
			txtID = new JTextField("Search by Entry ID");
			txtID.setBounds(17, 50, 91, 26);
			txtID.addMouseListener(controller);
			this.txtID = txtID;
			contentPane.add(txtID);
			txtID.setColumns(10);
			
			cType = new JComboBox();
			cType.setBounds(15, 90, 93, 29);
			cType.addItem("Search by Creator Type");
			cType.addItem("Student");
			cType.addItem("Admin");
			cType.addItem("Tutor");
			this.cType = cType;
			contentPane.add(cType);
			
			JLabel lblSearch = new JLabel("Refine Search");
			lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
			lblSearch.setBounds(17, 13, 107, 20);
			contentPane.add(lblSearch);
			
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel.setBounds(125, 13, 57, 20);
			contentPane.add(lblNewLabel);
			
			JLabel lblName = new JLabel("Date");
			lblName.setHorizontalAlignment(SwingConstants.LEFT);
			lblName.setBounds(288, 13, 78, 20);
			contentPane.add(lblName);
			
			JLabel lblLoc = new JLabel("creator_id");
			lblLoc.setHorizontalAlignment(SwingConstants.LEFT);
			lblLoc.setBounds(451, 13, 69, 20);
			contentPane.add(lblLoc);
			
			JLabel lblDate = new JLabel("creator_type");
			lblDate.setHorizontalAlignment(SwingConstants.LEFT);
			lblDate.setBounds(614, 13, 51, 20);
			contentPane.add(lblDate);
			
			btnSearch = new JButton("Search");
			btnSearch.setBounds(15, 130, 93, 29);
			btnSearch.addActionListener(controller);
			this.btnSearch = btnSearch;
			contentPane.add(btnSearch);
		}
	}
	public void selectedEntry(int id){
		//This method populates the text fields when altering an event.
		Diary entry = model.fetchEntry(id);
		creatorID.setText(Integer.toString(entry.getCreator_id()));
		creatorName.setText(entry.getCreatorType());
		idLbl.setText(Integer.toString(entry.getDiary_item_id()));
		descriptionTxt.setText(entry.getItem_contents());
		String date = entry.getDate().substring(0, 10);
		String time = entry.getDate().substring(11, 19);
		dateLbl.setText(date);
		timeLbl.setText(time);
	}
	public void entryMaker(boolean newEntry, int creatorId){
		//This method adds a new diary entry to the database or amends an existing one

		String contents = descriptionTxt.getText();
		LocalDateTime datetime = LocalDateTime.now();
		String date = model.dateFormatter(datetime);
		String creatorType = "admin";
		Diary entry = new Diary(date,contents,creatorId,creatorType);
		
		if(newEntry){
			model.newEntry(entry);
			alert("Diary entry successfully added to the database.", "Entry Added");
		}else{
			entry.setDiary_item_id(Integer.parseInt(entryComboBox.getSelectedItem().toString()));
			model.amendEntry(entry);
			alert("Diary entry " + entry.getDiary_item_id() + " successfully modified.", "Entry Altered");
		}
	}

	public void alert(String infoMessage, String titleBar){
			JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	public JPanel getPanel(){
		return contentPane;
	}

	public void viewEvent(int id) {
		//Method for displaying individual Event details in more detail than can be displayed on the query page.
		Diary entry = model.fetchEntry(id);
		
		entryFrame = new JFrame();
		entryFrame.setVisible(true);
		entryFrame.setResizable(false);
		entryFrame.setBounds(100, 100, 600, 800);
		entryFrame.setLocationRelativeTo(null);
		JPanel entryPanel = new JPanel();
		entryPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		entryFrame.setContentPane(entryPanel);
		entryPanel.setLayout(new BorderLayout());
			
		JPanel details = new JPanel();
		entryPanel.add(details, BorderLayout.NORTH);
		details.setLayout(new GridLayout(0,2, 50,40));

		
		JLabel eventIdlbl = new JLabel("Event ID:");
		details.add(eventIdlbl);
		JLabel eventId = new JLabel(Integer.toString(entry.getDiary_item_id()), SwingConstants.LEFT);
		details.add(eventId);
		
		JLabel entryDateLbl = new JLabel("Date:");
		details.add(entryDateLbl);
		JLabel entryDate = new JLabel(entry.getDate(), SwingConstants.LEFT);
		details.add(entryDate);
		
		JLabel eventCreatorIdlbl = new JLabel("Creator ID:");
		details.add(eventCreatorIdlbl);
		JLabel eventCreatorId = new JLabel(Integer.toString(entry.getCreator_id())); 
		details.add(eventCreatorId);
		
		JLabel eventCreatorTypelbl = new JLabel("Creator Role: ");
		details.add(eventCreatorTypelbl);
		JLabel eventCreatorType = new JLabel(entry.getCreatorType());
		details.add(eventCreatorType);
		
		JLabel eventDesclbl = new JLabel("Description:");
		details.add(eventDesclbl);
		JLabel eventDesc = new JLabel(entry.getItem_contents(), SwingConstants.LEFT);
		details.add(eventDesc);
		
		JPanel buttons = new JPanel();
		details.add(buttons, BorderLayout.SOUTH);
		
		editEntry = new JButton("edit");
		editEntry.addActionListener(controller);
		editEntry.setActionCommand(Integer.toString(entry.getDiary_item_id()));
		buttons.add(editEntry);
		this.editEntry = editEntry;
		
		deleteEntry = new JButton("delete");
		deleteEntry.addActionListener(controller);
		deleteEntry.setActionCommand(Integer.toString(entry.getDiary_item_id()));
		buttons.add(deleteEntry);
		this.deleteEntry = deleteEntry;
	}
	public void closeEntryFrame(){
		entryFrame.dispose();
	}


}
