package Model;

import javax.swing.JComboBox;
import java.sql.*;

public class WUC_Student_M {
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

	public void deleteStudent(int id) {
		//Deletes the student with the id passed to the method.
		try{
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "DELETE FROM students WHERE student_id = " + id;
			myStmt.execute(statement);
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public int resultsSize(){
		//returns the amount of rows in the student table.
		int size = 0;
		try {
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "SELECT student_id FROM students";
			ResultSet students = myStmt.executeQuery(statement);
			while(students.next()){
				size++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}
	public String[] refineSearch(String ID, String firstname, String surname, String status, String course){
		//identifies what criteria the user is trying to search with, returns the '%' wildcard if the user
		//has not selected anything.
		String searchCriteria[] = new String[5];
		if(!ID.equals("")&&!ID.equals("Search by ID")){
			searchCriteria[0] = ID;
		}else{
			searchCriteria[0] = "%";
		}
		if(!firstname.equals("")&&!firstname.equals("Search by Firstname")){
			searchCriteria[1] = firstname;
		}else{
			searchCriteria[1] = "%";
		}
		if(!surname.equals("")&&!surname.equals("Search by Surname")){
			searchCriteria[2] = surname;
		}else{
			searchCriteria[2] = "%";
		}
		if(!status.equals("Select Status")&&!status.equals("")){
			searchCriteria[3] = status;
		}else{
			searchCriteria[3] = "%";
		}
		if(!course.equals("Select Course")&&!course.equals("")){
			searchCriteria[4] = course;
		}else{
			searchCriteria[4] = "%";
		}
		return searchCriteria;
		
	}
	public String courseCode(String name){
		//fetches the course code of a course when provided the title of the course.
		String cCode = "";
		try {
			Connection db = DriverManager.getConnection("jdbc:mysql://46.32.240.35/wucdb-7pa-u-040356","wucdb-7pa-u-040356","computingstudent");
			Statement myStmt = db.createStatement();
			String statement = "SELECT course_code FROM courses WHERE title = '" + name + "'";
			ResultSet code = myStmt.executeQuery(statement);
			
			while(code.next()){
				cCode = code.getString("course_code");
			}
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cCode;
	}

	public int getComboIndex(JComboBox combo, int id) {
		int index = 0;
		for(int i = 0; i < combo.getItemCount(); i++){
			if(combo.getItemAt(i).toString().equals(Integer.toString(id))){
				index = i;
			}
		}
		return index;
	}
}
