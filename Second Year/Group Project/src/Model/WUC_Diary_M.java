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
public class WUC_Diary_M {
	public String dateFormatter(LocalDateTime date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		String dateOutput = date.format(formatter);
		return dateOutput;
	}
	public void newEntry(Diary entry){
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "INSERT INTO diary_items(date, item_contents, creator_id, creator_type)"
					+ "			VALUES(?, ?, ?, ?)";
			
			PreparedStatement studInsert = null;
			studInsert = db.prepareStatement(statement);
			studInsert.setString(1, entry.getDate());
			studInsert.setString(2, entry.getItem_contents());
			studInsert.setInt(3, entry.getCreator_id());
			studInsert.setString(4, entry.getCreatorType());
			studInsert.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void amendEntry(Diary entry){
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "UPDATE diary_items SET creator_type = ?, date = ?, item_contents = ?, creator_id = ? WHERE diary_item_id = " + entry.getDiary_item_id();
			
			PreparedStatement studUpdate = null;
			studUpdate = db.prepareStatement(statement);
			studUpdate.setString(1, entry.getCreatorType());
			studUpdate.setString(2, entry.getDate());
			studUpdate.setString(3, entry.getItem_contents());
			studUpdate.setString(4, Integer.toString(entry.getCreator_id()));
			studUpdate.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteEntry(int id){
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "DELETE FROM diary_items WHERE diary_item_id = " + id;
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
	public Diary fetchEntry(int id){
		Diary entry = null;
		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from diary_items where diary_item_id = " + id);
			while (myRs.next()) {
				int entryId = myRs.getInt("diary_item_id");
				String date = myRs.getString("date");
				String item_contents = myRs.getString("item_contents");
				int creatorId = myRs.getInt("creator_id");
				String creatorType = myRs.getString("creator_type");
				entry = new Diary(entryId,date,item_contents,creatorId, creatorType);
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}
	public ArrayList<Diary> retrieveEntries(){
		Connection myConn;
		ArrayList<Diary> entries = new ArrayList<Diary>();
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from diary_items");
			while (myRs.next()) {
				int entryId = myRs.getInt("diary_item_id");
				String date = myRs.getString("date");
				String item_contents = myRs.getString("item_contents");
				int creatorId = myRs.getInt("creator_id");
				String creatorType = myRs.getString("creator_type");
				Diary entry = new Diary(entryId,date,item_contents,creatorId, creatorType);
				entries.add(entry);
			}
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entries;
	}
	public ArrayList<Diary> refineSearch(String ID, String creatorType){
		//identifies what criteria the user is trying to search with, returns the '%' wildcard if the user
		//has not selected anything.
		ArrayList<Diary> entries = new ArrayList<Diary>();
		String searchCriteria[] = new String[2];
		if(!ID.equals("")&&!ID.equals("Search by Entry ID")){
			searchCriteria[0] = ID;
		}else{
			searchCriteria[0] = "%";
		}
		if(!creatorType.equals("Search by Creator Type")){
			searchCriteria[1] = creatorType.toLowerCase();
		}else{
			searchCriteria[1] = "%";
		}
		ResultSet entryResults = null;
		try {
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String stmt = "SELECT * FROM diary_items WHERE diary_item_id LIKE ? AND creator_type LIKE ?";
			//students = myStmt.executeQuery(statement);
			PreparedStatement entryQuery = db.prepareStatement(stmt);
			
			entryQuery.setString(1,searchCriteria[0]);
			entryQuery.setString(2,searchCriteria[1]);
			entryResults = entryQuery.executeQuery();
			
			while(entryResults.next()){
				System.out.println("result");
				Diary entry = new Diary(entryResults.getInt("diary_item_id"),entryResults.getString("date"),entryResults.getString("item_contents"),
				entryResults.getInt("creator_id"), entryResults.getString("creator_type"));
				entries.add(entry);
			}
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entries;
	}

}
