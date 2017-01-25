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
import Model.Event;
import Model.WUC_Event_M;
public class WUC_Event_V {

		public JPanel contentPane;
		ImageIcon img = new ImageIcon("img/logo.jpg");
		private WUC_Controller controller;
		private WUC_Event_M model;
		private JTextField eNTxt;
		public JTextField dayTxt;
		public JTextField monthTxt;
		public JTextField yearTxt;
		public JTextField hrsTxt;
		public JTextField minsTxt;
		private JLabel lblLocation;
		private JTextField locTxt;
		private JTextArea descriptionTxt;
		private JLabel lblDescription;
		private JLabel lblDate;
		private JLabel lblTime;
		public JTextField txtID;
		public JTextField txtLoc;
		public JTextField txtN;
		public JButton btnSearch;
		public JButton btnSubmit;
		public JButton btnSaveAndContinue;
		public JButton delete;
		public JButton editEvent;
		public JButton deleteEvent;
		public JComboBox eventComboBox;
		public String statement[] = new String[5];
		private JFrame eventFrame;
		public ArrayList<Event> events;
		
		public WUC_Event_V(WUC_Controller controller, WUC_Event_M model, int state){
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
			events = model.retrieveEvents();
			if(state == 1|| state == 2){
				JLabel nameLbl = new JLabel("Event Name:");
				nameLbl.setBounds(55, 60, 115, 26);
				contentPane.add(nameLbl);
				
				eNTxt = new JTextField();
				eNTxt.setBounds(180, 60, 300, 26);
				contentPane.add(eNTxt);
				eNTxt.setColumns(10);
				
				lblLocation = new JLabel("Location:");
				lblLocation.setBounds(55, 110, 115, 26);
				contentPane.add(lblLocation);
				
				locTxt = new JTextField();
				locTxt.setBounds(180, 110, 300, 26);
				contentPane.add(locTxt);
				
				descriptionTxt = new JTextArea();
				descriptionTxt.setBounds(180, 160, 300, 200);
				descriptionTxt.setLineWrap(true);
				JScrollPane scrollDesc = new JScrollPane(descriptionTxt);
				scrollDesc.setBounds(180, 160, 300, 200);
				contentPane.add(scrollDesc);
				
				lblDescription = new JLabel("Description:");
				lblDescription.setBounds(55, 160, 115, 20);
				contentPane.add(lblDescription);
				
				lblDate = new JLabel("Date:");
				lblDate.setBounds(510, 60, 115, 26);
				contentPane.add(lblDate);
				
				dayTxt = new JTextField("DD");
				dayTxt.setBounds(635, 60, 40, 26);
				dayTxt.addMouseListener(controller);
				contentPane.add(dayTxt);
				
				monthTxt = new JTextField("MM");
				monthTxt.setBounds(690, 60, 40, 26);
				monthTxt.addMouseListener(controller);
				contentPane.add(monthTxt);
				
				yearTxt = new JTextField("YYYY");
				yearTxt.setBounds(745, 60, 80, 26);
				yearTxt.addMouseListener(controller);
				contentPane.add(yearTxt);
				
				lblTime = new JLabel("Time:");
				lblTime.setBounds(510, 110, 115, 26);
				contentPane.add(lblTime);
				
				hrsTxt = new JTextField("Hour");
				hrsTxt.setBounds(635, 110, 40, 26);
				hrsTxt.addMouseListener(controller);
				contentPane.add(hrsTxt);
				
				minsTxt = new JTextField("Min");
				minsTxt.setBounds(690, 110, 40, 26);
				minsTxt.addMouseListener(controller);
				contentPane.add(minsTxt);
				
				btnSubmit = new JButton("Submit");
				btnSubmit.setBounds(635, 331, 190, 29);
				btnSubmit.addActionListener(controller);
				contentPane.add(btnSubmit);
			}
				
			if (state == 2){
					eventComboBox = new JComboBox();
					eventComboBox.setBounds(180, 10, 146, 26);
					eventComboBox.addItem("Select an event");
					model.populateDropDown(eventComboBox, "events", "event_id");
					eventComboBox.addItemListener(controller);
					this.eventComboBox = eventComboBox;
					contentPane.add(eventComboBox);
					
					JLabel eventLbl = new JLabel("Select an event:");
					eventLbl.setBounds(55, 10, 146, 26);
					contentPane.add(eventLbl);
					
					delete = new JButton("Delete Event");
					delete.setBounds(635, 281, 190, 29);
					delete.addActionListener(controller);
					contentPane.add(delete);
					this.delete = delete;
					
					contentPane.remove(btnSubmit);
					btnSaveAndContinue = new JButton("Save and Continue");
					btnSaveAndContinue.setBounds(635, 331, 190, 29);
					btnSaveAndContinue.addActionListener(controller);
					contentPane.add(btnSaveAndContinue);
					this.btnSaveAndContinue = btnSaveAndContinue;
					
			}else if(state == 3){
				int size = events.size();
				JLabel[] eventID = new JLabel[size];
				JLabel[] eventName = new JLabel[size];
				JLabel[] eventLocation = new JLabel[size];
				JLabel[] eventDate = new JLabel[size];
				JPanel[] viewBtnHolder = new JPanel[size];
				JButton[] eventViewBtn = new JButton[size];
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(123, 37, 817, 362);
				contentPane.add(scrollPane);
				
				JPanel panel_1 = new JPanel();
				scrollPane.setViewportView(panel_1);
				panel_1.setLayout(new GridLayout(0, 5, 0, 0));
				
				for(int i = 0; i<size;i++){
					Event event = events.get(i);
					eventID[i] = new JLabel(Integer.toString(event.getEvent_id()));
					eventID[i].setVerticalAlignment(SwingConstants.TOP);
					eventID[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(eventID[i]);
					
					eventName[i] = new JLabel(event.getName());
					eventName[i].setVerticalAlignment(SwingConstants.TOP);
					eventName[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(eventName[i]);
					
					eventLocation[i] = new JLabel(event.getLocation());
					eventLocation[i].setVerticalAlignment(SwingConstants.TOP);
					eventLocation[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(eventLocation[i]);
					
					eventDate[i] = new JLabel(event.getDate());
					eventDate[i].setVerticalAlignment(SwingConstants.TOP);
					eventDate[i].setHorizontalAlignment(SwingConstants.LEFT);
					panel_1.add(eventDate[i]);
					
					viewBtnHolder[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
					panel_1.add(viewBtnHolder[i]);
					
					eventViewBtn[i] = new JButton("View");
					eventViewBtn[i].addActionListener(controller);
					eventViewBtn[i].setActionCommand("e" + event.getEvent_id());
					viewBtnHolder[i].add(eventViewBtn[i]);
					eventViewBtn[i].setVerticalAlignment(SwingConstants.TOP);
					}
				
				txtID = new JTextField("Search by ID");
				txtID.setBounds(17, 50, 91, 26);
				txtID.addMouseListener(controller);
				this.txtID = txtID;
				contentPane.add(txtID);
				txtID.setColumns(10);
				
				JLabel lblSearch = new JLabel("Refine Search");
				lblSearch.setHorizontalAlignment(SwingConstants.LEFT);
				lblSearch.setBounds(17, 13, 107, 20);
				contentPane.add(lblSearch);
				
				txtN = new JTextField("Search by Name");
				txtN.setColumns(10);
				txtN.setBounds(17, 90, 91, 26);
				txtN.addMouseListener(controller);
				this.txtN = txtN;
				contentPane.add(txtN);
				
				txtLoc = new JTextField("Search by Location");
				txtLoc.setColumns(10);
				txtLoc.setBounds(17, 130, 91, 26);
				txtLoc.addMouseListener(controller);
				this.txtLoc = txtLoc;
				contentPane.add(txtLoc);
				
				JLabel lblNewLabel = new JLabel("ID");
				lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel.setBounds(125, 13, 57, 20);
				contentPane.add(lblNewLabel);
				
				JLabel lblName = new JLabel("Name");
				lblName.setHorizontalAlignment(SwingConstants.LEFT);
				lblName.setBounds(288, 13, 78, 20);
				contentPane.add(lblName);
				
				JLabel lblLoc = new JLabel("Location");
				lblLoc.setHorizontalAlignment(SwingConstants.LEFT);
				lblLoc.setBounds(451, 13, 69, 20);
				contentPane.add(lblLoc);
				
				JLabel lblDate = new JLabel("Date");
				lblDate.setHorizontalAlignment(SwingConstants.LEFT);
				lblDate.setBounds(614, 13, 51, 20);
				contentPane.add(lblDate);
				
				btnSearch = new JButton("Search");
				btnSearch.setBounds(15, 170, 93, 29);
				btnSearch.addActionListener(controller);
				this.btnSearch = btnSearch;
				contentPane.add(btnSearch);
			}
		}
		public void selectedEvent(int id){
			//This method populates the text fields when altering an event.
			Event event = model.fetchEvent(id);
			eNTxt.setText(event.getName());
			descriptionTxt.setText(event.getDescription());
			locTxt.setText(event.getLocation());
			String datetime = event.getDate();
			dayTxt.setText(datetime.substring(8,10));
			monthTxt.setText(datetime.substring(5,7));
			yearTxt.setText(datetime.substring(0,4));
			hrsTxt.setText(datetime.substring(11,13));
			minsTxt.setText(datetime.substring(14,16));
		}
		public void eventMaker(boolean newEvent, int creatorId){
			//This method adds a new student to the database
			System.out.println("eventMaker called");
			String name = eNTxt.getText();
			String location = locTxt.getText();
			String desc = descriptionTxt.getText();
			int day = Integer.parseInt(dayTxt.getText());
			int month = Integer.parseInt(monthTxt.getText());
			int year = Integer.parseInt(yearTxt.getText());
			int hour= Integer.parseInt(hrsTxt.getText());
			int min = Integer.parseInt(minsTxt.getText());
			LocalDateTime datetime = LocalDateTime.of(year,month,day,hour,min);
			String date = model.dateFormatter(datetime);
			String creatorType = "admin";
			Event event = new Event(date,name,desc,location,creatorId,creatorType);
			
			if(newEvent){
				String eName = model.newEvent(event);
				alert("Event successfully added to the database. New event's name is: " + eName, "Event Added");
			}else{
				String selected = eventComboBox.getSelectedItem().toString();
				int event_id = Integer.parseInt(selected);
				event.setEvent_id(event_id);
				model.amendEvent(event);
				alert("Event " + event_id + " successfully modified.", "Event Altered");
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
			Event event = model.fetchEvent(id);
			
			eventFrame = new JFrame();
			eventFrame.setVisible(true);
			eventFrame.setResizable(false);
			eventFrame.setBounds(100, 100, 600, 800);
			eventFrame.setLocationRelativeTo(null);
			JPanel eventPanel = new JPanel();
			eventPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			eventFrame.setContentPane(eventPanel);
			eventPanel.setLayout(new BorderLayout());
				
			JPanel details = new JPanel();
			eventPanel.add(details, BorderLayout.NORTH);
			details.setLayout(new GridLayout(0,2, 50,40));
			
			JLabel eventNamelbl = new JLabel("Name:");
			details.add(eventNamelbl);
			JLabel eventName = new JLabel(event.getName(), SwingConstants.LEFT);
			details.add(eventName);
			
			JLabel eventIdlbl = new JLabel("Event ID:");
			details.add(eventIdlbl);
			JLabel eventId = new JLabel(Integer.toString(event.getEvent_id()), SwingConstants.LEFT);
			details.add(eventId);
			
			JLabel eventDesclbl = new JLabel("Description:");
			details.add(eventDesclbl);
			JLabel eventDesc = new JLabel(event.getDescription(), SwingConstants.LEFT);
			details.add(eventDesc);
			
			JLabel eventLoclbl = new JLabel("Location:");
			details.add(eventLoclbl);
			JLabel eventLoc = new JLabel(event.getLocation(), SwingConstants.LEFT);
			details.add(eventLoc);
			
			JLabel eventCreatorIdlbl = new JLabel("Creator ID:");
			details.add(eventCreatorIdlbl);
			JLabel eventCreatorId = new JLabel(Integer.toString(event.getCreator_id())); 
			details.add(eventCreatorId);
			
			JLabel eventCreatorTypelbl = new JLabel("Creator Role: ");
			details.add(eventCreatorTypelbl);
			JLabel eventCreatorType = new JLabel(event.getCreator_type());
			details.add(eventCreatorType);
			
			
			JPanel buttons = new JPanel();
			details.add(buttons, BorderLayout.SOUTH);
			
			editEvent = new JButton("edit");
			editEvent.addActionListener(controller);
			editEvent.setActionCommand(Integer.toString(event.getEvent_id()));
			buttons.add(editEvent);
			this.editEvent = editEvent;
			
			deleteEvent = new JButton("delete");
			deleteEvent.addActionListener(controller);
			deleteEvent.setActionCommand(Integer.toString(event.getEvent_id()));
			buttons.add(deleteEvent);
			this.deleteEvent = deleteEvent;
		}
		public void closeEventFrame(){
			eventFrame.dispose();
		}
	}

