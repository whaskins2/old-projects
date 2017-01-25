package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import Model.WUC_Diary_M;
import Model.WUC_Event_M;
import Model.WUC_Login_M;
import Model.WUC_Student_M;
import View.WUC_Diary_V;
import View.WUC_Event_V;
import View.WUC_Homepage_V;
import View.WUC_Login_V;
import View.WUC_Student_V;

public class WUC_Controller implements ActionListener,ItemListener,MouseListener{
	
	private WUC_Login_V viewLogin;
	private WUC_Homepage_V viewHome;
	private WUC_Student_V viewStudent;
	private WUC_Event_V viewEvent;
	private WUC_Diary_V viewDiary;
	
	private WUC_Login_M model;
	private WUC_Student_M studModel;
	private WUC_Event_M eventModel;
	private WUC_Diary_M diaryModel;
	
	public WUC_Controller(WUC_Login_M model){
		this.model = model;
	}
	
	public void addLoginView (WUC_Login_V view){
		this.viewLogin = view;
	}
	
	public void addHomeView (WUC_Homepage_V view){
		this.viewHome = view;
	}
	
	//Student
	public void addStudentView(WUC_Student_V view){
		this.viewStudent = view;
	}
	public void addStudentModel(WUC_Student_M model){
		this.studModel = model;
	}
	
	//Event
	public void addEventView(WUC_Event_V view){
		this.viewEvent = view;
	}
	public void addEventModel(WUC_Event_M model){
		this.eventModel = model;
	}
	
	//Diary
	public void addDiaryView(WUC_Diary_V view){
		this.viewDiary = view;
	}
	public void addDiaryModel(WUC_Diary_M model){
		this.diaryModel = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Login_Submit")){
			model.login(viewLogin.getID(), viewLogin.getPassword());
			
			if (model.login_Status() == true ){
				viewLogin.getPanel().setVisible(false);
				viewHome.getPanel().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Please Try Again...");
			}
			
		//Student action listener code
		}else if(e.getSource().equals(viewStudent.delete)){
			System.out.println("delete pressed");
			String selected = viewStudent.studentComboBox.getSelectedItem().toString();
			if (selected.equals("Select a Student")){
				viewStudent.alert("You must select a student first", "Trying to delete without a user selected.");
			}else{
				int id = Integer.parseInt(selected);
				studModel.deleteStudent(id);
				viewStudent.studentComboBox.removeItem(viewStudent.studentComboBox.getSelectedItem());
				viewStudent.alert("The student you selected has been deleted.", "Deleted Student");
			}
			
		}else if(e.getSource().equals(viewStudent.btnSaveAndContinue)){
			System.out.println("save and continue pressed");
			int id = Integer.parseInt(viewStudent.studentComboBox.getSelectedItem().toString());
			viewStudent.studentID = id;
			viewStudent.studentMaker(false);
			
		}else if(e.getActionCommand().equals("Create Student")){
			System.out.println("createStud pressed");
			viewStudent.studentMaker(true);
			
		}else if(e.getActionCommand().charAt(0) == 's'){
			int id = Integer.parseInt(e.getActionCommand().substring(1));
			System.out.println("trying to view student: " + id);
			viewStudent.viewStudent(id);
			
		}else if(e.getSource() == viewStudent.btnSearch){
			System.out.println("trying to narrow down results");
			String id = viewStudent.txtID.getText();
			String firstname = viewStudent.txtFN.getText();
			String surname = viewStudent.txtSN.getText();
			String status = viewStudent.cBStatus.getSelectedItem().toString();
			String course = viewStudent.cBCourse.getSelectedItem().toString();
			viewStudent.statement = studModel.refineSearch(id, firstname, surname, status, studModel.courseCode(course));
			viewStudent.jPCentre.removeAll();
			viewStudent.jPCentre.repaint();
			viewStudent.fillMain(3);
			
		}else if(e.getSource().equals(viewStudent.deleteStud)){
			int id = Integer.parseInt(e.getActionCommand());
			studModel.deleteStudent(id);
			viewStudent.alert("The student you selected has been deleted.", "Deleted Student");
			viewStudent.jPCentre.removeAll();
			viewStudent.jPCentre.repaint();
			viewStudent.fillMain(3);
			viewStudent.closeStudFrame();
			
		}else if(e.getSource().equals(viewStudent.editStud)){
			viewStudent.jPCentre.removeAll();
			viewStudent.jPCentre.repaint();
			viewStudent.fillMain(2);
			viewStudent.selectedStudent(Integer.parseInt(e.getActionCommand()));
			int index = studModel.getComboIndex(viewStudent.studentComboBox,Integer.parseInt(e.getActionCommand()));
			viewStudent.studentComboBox.setSelectedIndex(index);
			viewStudent.closeStudFrame();
			
		//Event Action Listener Code
		}else if(e.getSource().equals(viewEvent.btnSubmit)){
			viewEvent.eventMaker(true, 10000001/*viewLogin.getID()*/); //TODO remove comments and integer.
		}else if(e.getSource().equals(viewEvent.btnSaveAndContinue)){
			viewEvent.eventMaker(false, 10000001/*viewLogin.getID()*/);//TODO remove comments and integer.
		}else if(e.getSource().equals(viewEvent.delete)){
			String selected = viewEvent.eventComboBox.getSelectedItem().toString();
			if(!selected.equals("Select an event")){
				int event_id = Integer.parseInt(selected);
				eventModel.deleteEvent(event_id);
				viewEvent.contentPane.removeAll();
				viewEvent.contentPane.repaint();
				viewEvent.fillMain(2);
			}else{
				viewEvent.alert("You must select an event first", "No Event Selected");
			}
		}else if(e.getActionCommand().charAt(0) == 'e'){
			int id = Integer.parseInt(e.getActionCommand().substring(1));
			System.out.println("trying to view event: " + id);
			viewEvent.viewEvent(id);
		}else if(e.getSource().equals(viewEvent.deleteEvent)){
			int id = Integer.parseInt(e.getActionCommand());
			eventModel.deleteEvent(id);
			viewEvent.alert("The event you selected has been deleted.", "Deleted Event");
			viewEvent.contentPane.removeAll();
			viewEvent.contentPane.repaint();
			viewEvent.fillMain(3);
			viewEvent.closeEventFrame();
		}else if(e.getSource() == viewEvent.btnSearch){
			System.out.println("trying to narrow down results");
			String id = viewEvent.txtID.getText();
			String name = viewEvent.txtN.getText();
			String location = viewEvent.txtLoc.getText();
			viewEvent.events = eventModel.refineSearch(id, name, location);
			viewEvent.contentPane.removeAll();
			viewEvent.contentPane.repaint();
			viewEvent.fillMain(3);
		}else if(e.getSource().equals(viewEvent.editEvent)){
			viewEvent.contentPane.removeAll();
			viewEvent.contentPane.repaint();
			viewEvent.fillMain(2);
			viewEvent.selectedEvent(Integer.parseInt(e.getActionCommand()));
			int index = studModel.getComboIndex(viewEvent.eventComboBox,Integer.parseInt(e.getActionCommand()));
			viewEvent.eventComboBox.setSelectedIndex(index);
			viewEvent.closeEventFrame();
			
		//Diary Action Listener Code
		}else if(e.getSource().equals(viewDiary.btnSubmit)){
			viewDiary.entryMaker(true, 10000001/*viewLogin.getID()*/); //TODO remove comments and integer.
		}else if(e.getSource().equals(viewDiary.btnSaveAndContinue)){
			viewDiary.entryMaker(false, 10000001/*viewLogin.getID()*/);//TODO remove comments and integer.
		}else if(e.getSource().equals(viewDiary.btnDelete)){
			String selected = viewDiary.entryComboBox.getSelectedItem().toString();
			if(!selected.equals("Select a diary entry")){
				int entry_id = Integer.parseInt(selected);
				System.out.println("Entry ID: " + entry_id);
				diaryModel.deleteEntry(entry_id);
				viewDiary.contentPane.removeAll();
				viewDiary.contentPane.repaint();
				viewDiary.fillMain(2);
			}else{
				viewDiary.alert("You must select a diary entry first", "No Entry Selected");
			}
		}else if(e.getActionCommand().charAt(0) == 'd'){
			int id = Integer.parseInt(e.getActionCommand().substring(1));
			System.out.println("trying to view diary entry: " + id);
			viewDiary.viewEvent(id);
		}else if(e.getSource().equals(viewDiary.deleteEntry)){
			int id = Integer.parseInt(e.getActionCommand());
			diaryModel.deleteEntry(id);
			viewDiary.contentPane.removeAll();
			viewDiary.contentPane.repaint();
			viewDiary.fillMain(3);
			viewDiary.closeEntryFrame();
			
		}else if(e.getSource() == viewDiary.btnSearch){
			System.out.println("trying to narrow down results");
			String id = viewDiary.txtID.getText();
			String cType = viewDiary.cType.getSelectedItem().toString();
			viewDiary.entries = diaryModel.refineSearch(id,cType);
			viewDiary.contentPane.removeAll();
			viewDiary.contentPane.repaint();
			viewDiary.fillMain(3);
		}else if(e.getSource().equals(viewDiary.editEntry)){
			viewDiary.contentPane.removeAll();
			viewDiary.contentPane.repaint();
			viewDiary.fillMain(2);
			viewDiary.selectedEntry(Integer.parseInt(e.getActionCommand()));
			int index = studModel.getComboIndex(viewDiary.entryComboBox,Integer.parseInt(e.getActionCommand()));
			viewDiary.entryComboBox.setSelectedIndex(index);
			viewDiary.closeEntryFrame();
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			if(e.getSource().equals(viewStudent.studentComboBox)){
				String selected = viewStudent.studentComboBox.getSelectedItem().toString();
				System.out.println(selected);
				if(!selected.equals("Select a Student")){
					viewStudent.selectedStudent(Integer.parseInt(selected));
				}
				
			}else if(e.getSource().equals(viewEvent.eventComboBox)){
				String selected = viewEvent.eventComboBox.getSelectedItem().toString();
				System.out.println(selected);
				if(!selected.equals("Select an event")){
					viewEvent.selectedEvent(Integer.parseInt(selected));
				}
			}else if(e.getSource().equals(viewDiary.entryComboBox)){
				String selected = viewDiary.entryComboBox.getSelectedItem().toString();
				System.out.println(selected);
				if(!selected.equals("Select a diary entry")){
					viewDiary.selectedEntry(Integer.parseInt(selected));
				}
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Used to automatically remove contents of text fields when they are clicked on.
		if (e.getSource().equals(viewStudent.txtID)){
			viewStudent.txtID.setText("");
		}else if(e.getSource().equals(viewStudent.txtFN)){
			viewStudent.txtFN.setText("");
		}else if(e.getSource().equals(viewStudent.txtSN)){
			viewStudent.txtSN.setText("");
		}else if(e.getSource().equals(viewEvent.dayTxt)){
			viewEvent.dayTxt.setText("");
		}else if(e.getSource().equals(viewEvent.yearTxt)){
			viewEvent.yearTxt.setText("");
		}else if(e.getSource().equals(viewEvent.monthTxt)){
			viewEvent.monthTxt.setText("");
		}else if(e.getSource().equals(viewEvent.minsTxt)){
			viewEvent.minsTxt.setText("");
		}else if(e.getSource().equals(viewEvent.hrsTxt)){
			viewEvent.hrsTxt.setText("");
		}else if(e.getSource().equals(viewEvent.txtID)){
			viewEvent.txtID.setText("");
		}else if(e.getSource().equals(viewEvent.txtN)){
			viewEvent.txtN.setText("");
		}else if(e.getSource().equals(viewEvent.txtLoc)){
			viewEvent.txtLoc.setText("");
		}else if(e.getSource().equals(viewDiary.txtID)){
			viewDiary.txtID.setText("");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}