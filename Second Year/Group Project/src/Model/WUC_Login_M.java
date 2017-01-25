package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class WUC_Login_M {
	
	private Boolean status;

	public void login(int id, String password){
		
		System.out.println(id);
		System.out.println(password);
		
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://192.168.56.2/WUC","student","student");
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT staff_id, password FROM staff WHERE staff_id = " + id + " AND password = '" + password +"';");
			
			
			if( myRs.next() ){
		        //System.out.println("Authentication Successful");
		        status = true;
		      }else{
		    	 // System.out.println("Please Try Again");
		    	  status = false;
		      }	
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
	}
	
	public Boolean login_Status(){
		return this.status;
	}
	
}
