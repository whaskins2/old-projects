package Model;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.sql.PreparedStatement;

public class WUC_Event_M {
	//Event event = new Event();
	public String dateFormatter(LocalDateTime date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		String dateOutput = date.format(formatter);
		return dateOutput;
	}
	public String newEvent(Event event){
		String name = "";
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "INSERT INTO events(date, name, description, location, creator_id, creator_type)"
					+ "			VALUES(?, ?, ?, ?, ?, ?)";
			
			PreparedStatement studInsert = null;
			studInsert = db.prepareStatement(statement);
			studInsert.setString(1, event.getDate());
			studInsert.setString(2, event.getName());
			studInsert.setString(3, event.getDescription());
			studInsert.setString(4, event.getLocation());
			studInsert.setInt(5, event.getCreator_id());
			studInsert.setString(6, event.getCreator_type());
			studInsert.executeUpdate();
		
			Statement fetchIDstmt = db.createStatement();
			ResultSet getName = fetchIDstmt.executeQuery("SELECT name FROM events WHERE name = '" + event.getName() + "'");
			while(getName.next()){
				name = getName.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	public void amendEvent(Event event){
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "UPDATE events SET date = ?, name = ?, description = ?, location = ? WHERE event_id = " + event.getEvent_id();
			
			
			PreparedStatement studUpdate = null;
			studUpdate = db.prepareStatement(statement);
			studUpdate.setString(1, event.getDate());
			studUpdate.setString(2, event.getName());
			studUpdate.setString(3, event.getDescription());
			studUpdate.setString(4, event.getLocation());
			studUpdate.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteEvent(int id){
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "DELETE FROM events WHERE event_id = " + id;
			myStmt.execute(statement);
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void populateDropDown(JComboBox box, String tablename, String column){
		//populates a JComboBox with a certain table column.
		try {
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "SELECT " + column + " FROM " + tablename;
			ResultSet dropDownItems = myStmt.executeQuery(statement);
			
				while(dropDownItems.next()){
					box.addItem(dropDownItems.getString(column));
				}
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Event fetchEvent(int id){
		Event event = null;
		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from events where event_id = " + id);
			while (myRs.next()) {
				
				String datetime = myRs.getString("date");
				String name = myRs.getString("name");
				String description = myRs.getString("description");
				String location = myRs.getString("location");
				int creatorId = myRs.getInt("creator_id");
				String creatorType = myRs.getString("creator_type");
				event = new Event(datetime,name,description,location,creatorId, creatorType);
				event.setEvent_id(myRs.getInt("event_id"));
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event;
	}
	public ArrayList<Event> retrieveEvents(){
		Connection myConn;
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from events");
			while (myRs.next()) {
				String datetime = myRs.getString("date");
				String name = myRs.getString("name");
				String description = myRs.getString("description");
				String location = myRs.getString("location");
				int creatorId = myRs.getInt("creator_id");
				String creatorType = myRs.getString("creator_type");
				Event event = new Event(datetime,name,description,location,creatorId, creatorType);
				event.setEvent_id(myRs.getInt("event_id"));
				events.add(event);
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return events;
	}
	public ArrayList<Event> refineSearch(String ID, String name, String location){
		//identifies what criteria the user is trying to search with, returns the '%' wildcard if the user
		//has not selected anything.
		ArrayList<Event> events = new ArrayList<Event>();
		String searchCriteria[] = new String[3];
		if(!ID.equals("")&&!ID.equals("Search by ID")){
			searchCriteria[0] = ID;
		}else{
			searchCriteria[0] = "%";
		}
		if(!name.equals("")&&!name.equals("Search by Name")){
			searchCriteria[1] = name;
		}else{
			searchCriteria[1] = "%";
		}
		if(!location.equals("")&&!location.equals("Search by Location")){
			searchCriteria[2] = location;
		}else{
			searchCriteria[2] = "%";
		}
		ResultSet eventResults = null;
		try {
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String stmt = "SELECT * FROM events WHERE event_id LIKE ? AND name LIKE ? AND location LIKE ?";
			//students = myStmt.executeQuery(statement);
			PreparedStatement eventQuery = db.prepareStatement(stmt);
			
			eventQuery.setString(1,searchCriteria[0]);
			eventQuery.setString(2,searchCriteria[1]);
			eventQuery.setString(3,searchCriteria[2]);
			eventResults = eventQuery.executeQuery();
			
			while(eventResults.next()){
				Event event = new Event(eventResults.getString("date"),eventResults.getString("name"),eventResults.getString("description"),eventResults.getString("location"),eventResults.getInt("creator_id"), eventResults.getString("creator_type"));
				event.setEvent_id(eventResults.getInt("event_id"));
				events.add(event);
			}
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return events;
	}
}
