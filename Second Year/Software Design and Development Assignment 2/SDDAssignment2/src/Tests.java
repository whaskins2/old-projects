import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class Tests {
	private Model model = new Model();
	private Child testChild;
	public Tests(){
		model.openingState();
		//A test child.
		LocalDate testBD = LocalDate.of(2000,01,01);
		ArrayList<Session> testSessions = model.fetchSessions();
		String[] names = {testSessions.get(0).getName(), testSessions.get(1).getName(), testSessions.get(2).getName(), testSessions.get(3).getName(), testSessions.get(4).getName()};
		ArrayList<String> testnames = new ArrayList<String>(Arrays.asList(names));
		double[] testcosts = {testSessions.get(0).getthreePlusCost(),testSessions.get(1).getthreePlusCost(),testSessions.get(2).getthreePlusCost(),
				testSessions.get(3).getthreePlusCost(),testSessions.get(4).getthreePlusCost()};
		double cost = testSessions.get(0).getthreePlusCost() + testSessions.get(1).getthreePlusCost() + testSessions.get(2).getthreePlusCost() + 
				testSessions.get(3).getthreePlusCost() + testSessions.get(4).getthreePlusCost();
		Invoice testInvoice = new Invoice(testnames,testcosts,false,cost);
		Child testChild = new Child(999, "Walter", "Testman", "The Bronx", testBD, "Bees are fatal",
				"Dr", "Harold", "Testman", true, testSessions);
		testChild.setPaidFee(false);
		testChild.setPaymentDue(LocalDate.now().minusMonths(3));
		testChild.setInvoice(testInvoice);
		this.testChild = testChild;
	}
	@Test
	public void testDateConverter() {
		//Tests the dateConverter method
		LocalDate testDate1 = LocalDate.of(2000,01,01);
		LocalDate testDate2 = LocalDate.of(1999, 12, 31);
		String outputDate1 = model.formatDate(testDate1);
		String outputDate2 = model.formatDate(testDate2);
		assertEquals(outputDate1, "01/01/2000");
		assertEquals(outputDate2, "31/12/1999");
	}
	@Test
	public void testFetchChildren(){
		//tests the fetchChildren method.
		ArrayList children = model.fetchChildren();
		String className = children.get(0).getClass().getSimpleName();
		assertEquals(className, "Child");
	}
	@Test
	public void testFetchSession(){
		//tests the fetchSessions method.
		ArrayList sessions = model.fetchSessions();
		String className = sessions.get(0).getClass().getSimpleName();
		assertEquals(className, "Session");
	}
	@Test
	public void testFetchAdmin(){
		//tests the fetchAdmins method.
		ArrayList admins = model.fetchAdmins();
		String className = admins.get(0).getClass().getSimpleName();
		assertEquals(className, "Admin");
	}
	@Test
	public void testChildIDValidation(){
		//tests the compareChildID method.
		ArrayList<Child> children = model.fetchChildren();
		Child child = children.get(0);
		int usedID = child.getID();
		assertFalse(model.compareChildID(children, usedID));
		int newID = model.generateChildID();
		assertTrue(model.compareChildID(children, newID));
	}
	@Test
	public void testAdminIDValidation(){
		//tests the compareAdminId method.
		ArrayList<Admin> admins = model.fetchAdmins();
		Admin admin = admins.get(0);
		int usedID = admin.getId();
		assertFalse(model.compareAdminID(admins, usedID));
		int newID = model.generateAdminID();
		assertTrue(model.compareAdminID(admins, newID));
	}
	@Test
	public void testSessionIDValidation(){
		//tests the compareSessionID method.
		ArrayList<Session> sessions = model.fetchSessions();
		Session session = sessions.get(0);
		int usedID = session.getID();
		assertFalse(model.compareSessionID(sessions, usedID));
		int newID = model.generateSessionID();
		assertTrue(model.compareSessionID(sessions, newID));
	}
	@Test
	public void testChildIDGen(){
		//tests the child ID generating method.
		int newID = model.generateChildID();
		ArrayList<Child> children = model.fetchChildren();
		assertTrue(model.compareChildID(children, newID));
	}
	@Test
	public void testAdminIDGen(){
		//tests the admin id generating method.
		int newID = model.generateAdminID();
		ArrayList<Admin> admins = model.fetchAdmins();
		assertTrue(model.compareAdminID(admins, newID));
	}
	@Test
	public void testSessionIDGen(){
		//tests the session id generating method.
		int newID = model.generateSessionID();
		ArrayList<Session> sessions = model.fetchSessions();
		assertTrue(model.compareSessionID(sessions, newID));
	}
	@Test
	public void testFindChild(){
		//tests the findchild method.
		model.newChild(testChild);
		Child childFound = model.findChild(testChild.getID());
		boolean correctChild = false;
		if(testChild.getFirstname().equals(childFound.getFirstname()) && testChild.getSurname().equals(childFound.getSurname())
			&& testChild.getID() == childFound.getID()){
			correctChild = true;
		}
		assertTrue(correctChild);
		model.deleteChild(testChild.getID());
	}
	@Test
	public void testNewChild(){
		//tests the newChild method.
		model.newChild(testChild);
		Child child = model.findChild(testChild.getID());
		assertEquals(child.getID(), testChild.getID());
		model.deleteChild(testChild.getID());
	}
	@Test
	public void testFindSession(){
		//tests the findSession method
		ArrayList<Session> sessions = model.fetchSessions();
		Session session = sessions.get(0);
		Session sessionFound = model.findSession(session.getID());
		boolean correctSession = false;
		if(session.getName().equals(sessionFound.getName()) && session.getSpotsLeft() == sessionFound.getSpotsLeft()
			&& session.getID() == sessionFound.getID()){
			correctSession = true;
		}
		assertTrue(correctSession);
	}
	@Test
	public void testValidateDate(){
		//tests the validateDate method.
		String day = "01";
		String month = "01";
		String year = "2000";
		assertFalse(model.validateDate(day, month, year));
		day = "32";
		assertTrue(model.validateDate(day, month, year));
		day = "31";
		month = "13";
		assertTrue(model.validateDate(day, month, year));
		month = "12";
		year = "100";
		assertTrue(model.validateDate(day, month, year));
		year = "1999";
		assertFalse(model.validateDate(day, month, year));
	}
	@Test
	public void testLogin(){
		//tests the login method.
		ArrayList<Admin> admins = model.fetchAdmins();
		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<String> passwords = new ArrayList<String>();
		Admin admin = admins.get(0);
		
		assertTrue(model.login(admin.getUsername(), admin.getPassword()));
		
		String breakPW = admin.getPassword() + " madeInvalid";
		assertFalse(model.login(admin.getUsername(), breakPW));
		
		String testUsername = "asdajsdkljaiojwojw";
		boolean testUsable = true;
		for(int i = 0; i < admins.size(); i++){
			if(admins.get(i).getUsername().equals(testUsername) && admins.get(i).getPassword().equals("password")){
				testUsable = false;
			}
		}
		if(testUsable){
			assertFalse(model.login(testUsername, "password"));
		}
	}
	@Test
	public void testChangeChild(){
		//tests the changeChild method
		ArrayList<Child> children = model.fetchChildren();
		Child child = children.get(0);
		
		int ogChildID = child.getID();
		String ogFirstname = child.getFirstname();
		String ogSurname = child.getSurname();
		String ogAddress = child.getAddress();
		LocalDate ogBirthdate = child.getBirthdate();
		String ogAllergyInfo = child.getAllergyInfo();
		String ogpTitle = child.getpTitle();
		String ogpFirstname = child.getpFirstname();
		String ogpSurname = child.getpSurname();
		boolean ogSibling = child.getSibling();
		
		child.setFirstname("NewTestName");
		child.setSurname("NewTestSurname");
		child.setAddress("NewTestAddress");
		child.setBirthdate(child.getBirthdate().plusMonths(1));
		child.setAllergyInfo("NewTestAllergyInfo");
		child.setpTitle("NewTestPTitle");
		child.setpFirstname("NewTestpFirstName");
		child.setpSurname("NewTestpSurname");
		if(ogSibling){
			child.setSibling(false);
		}else{
			child.setSibling(true);
		}
		
		model.changeChild(child, true);
		Child fetchChild = model.findChild(ogChildID);
		assertEquals(fetchChild.getFirstname(), "NewTestName");
		assertEquals(fetchChild.getSurname(), "NewTestSurname");
		assertEquals(fetchChild.getAddress(), "NewTestAddress");
		assertEquals(fetchChild.getBirthdate(), ogBirthdate.plusMonths(1));
		assertEquals(fetchChild.getAllergyInfo(), "NewTestAllergyInfo");
		assertEquals(fetchChild.getpTitle(), "NewTestPTitle");
		assertEquals(fetchChild.getpFirstname(), "NewTestpFirstName");
		assertEquals(fetchChild.getpSurname(), "NewTestpSurname");
		
		fetchChild.setFirstname(ogFirstname);
		fetchChild.setSurname(ogSurname);
		fetchChild.setAddress(ogAddress);
		fetchChild.setBirthdate(ogBirthdate);
		fetchChild.setAllergyInfo(ogAllergyInfo);
		fetchChild.setpTitle(ogpTitle);
		fetchChild.setpFirstname(ogpFirstname);
		fetchChild.setpSurname(ogpSurname);
		fetchChild.setSibling(ogSibling);
		model.changeChild(fetchChild, true);
	}
	@Test
	public void testDeleteChild(){
		//tests the deleteChild method
		model.newChild(testChild);
		model.deleteChild(testChild.getID());
		assertNull(model.findChild(testChild.getID()));
	}
}
