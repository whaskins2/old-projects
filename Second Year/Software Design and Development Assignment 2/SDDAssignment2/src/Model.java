import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Model {
	private ObjectInputStream objectInputFile;
	private ObjectOutputStream objectOutputFile;
	private	DataInputStream inputDfile;
	private DataOutputStream outputDfile;
	
	public void newChild(Child child){
		//Adds a new child to the database by retrieving the current arraylist of children, appending the new child to it and then saving it over the existing data.
		System.out.println("newChild called");
		ArrayList<Child> childList = new ArrayList<Child>();
		if(new File("children.dat").isFile()){
			childList = fetchChildren();
		}
		childList.add(child);
		try {
			fileOutputConnect("children.dat", "object");
			objectOutputFile.writeObject(childList);
			System.out.println("New Child added");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(new File("children.dat").isFile()){
			sessionCapacityUpdate();
		}
	}
	public void newSession(Session session){
		//Adds a new session to the database in the same way as the newChild method.
		System.out.println("newSession called");
		ArrayList<Session> sessionList = new ArrayList<Session>();
		if(new File("sessions.dat").isFile()){
			sessionList = fetchSessions();
		}
		sessionList.add(session);
		FileOutputStream outStream;
		try {
			fileOutputConnect("sessions.dat", "object");
			objectOutputFile.writeObject(sessionList);
			System.out.println("New session added");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(new File("children.dat").exists()){
			sessionCapacityUpdate();
		}
	}
	public void newAdmin(Admin admin){
		//Adds a new admin to the database in the same way as the newChild and newSession methods.
		System.out.println("newAdmin called");
		ArrayList<Admin> adminList = new ArrayList<Admin>();
		if(new File("admin.dat").isFile()){
			adminList = fetchAdmins();
		}
		adminList.add(admin);
		try {
			fileOutputConnect("admin.dat", "object");
			objectOutputFile.writeObject(adminList);
			System.out.println("New Admin added");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Session> fetchSessions(){
		//Fetches the current list of sessions from the database.
		System.out.println("fetchSession Called");
		ArrayList<Session> sessionList = new ArrayList();
		try{
			fileInputConnect("sessions.dat", "object");
			boolean endOfFile = false;
			while (!endOfFile){
				try{
					sessionList = (ArrayList) objectInputFile.readObject();
				}catch(EOFException e){
					endOfFile = true;
				}
			}
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return sessionList;
	}
	public ArrayList<Child> fetchChildren(){
		//Fetches the current list of children from the database.
		ArrayList<Child> childList = new ArrayList();
		try{
			fileInputConnect("children.dat", "object");
			boolean endOfFile = false;
			while (!endOfFile){
				try{
					childList = (ArrayList) objectInputFile.readObject();
				}catch(EOFException e){
					endOfFile = true;
				}
			}
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return childList;
	}
	public ArrayList<Admin> fetchAdmins(){
		//Fetches the current list of admins from the database.
		ArrayList<Admin> adminList = new ArrayList();
		try{
			fileInputConnect("admin.dat", "object");
			boolean endOfFile = false;
			while (!endOfFile){
				try{
					adminList = (ArrayList) objectInputFile.readObject();
				}catch(EOFException e){
					endOfFile = true;
				}
			}
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return adminList;
	}
	public void fileInputConnect(String filename, String type){
		//Establishes a connection to file specified in the filename variable of the type specified in the type variable. Allows retrieving data. The data input is never actually used but was left in anyway as some
		//extra functionality.
		try{
			FileInputStream inStream = new FileInputStream(filename);
			if (type == "object"){
				ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
				this.objectInputFile = objectInputFile;
				
			}else{
				DataInputStream inputDfile = new DataInputStream(inStream);
				this.inputDfile = inputDfile;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void fileOutputConnect(String filename, String type){
		//Establishes a connection to a file specified by the filename variable. Allows outputting to the file.
		try{
			FileOutputStream outStream = new FileOutputStream(filename);
			if (type == "object"){
				ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
				this.objectOutputFile = objectOutputFile;
				
			}else{
				DataOutputStream outputDfile = new DataOutputStream(outStream);
				this.outputDfile = outputDfile;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String formatDate(LocalDate date){
		//Takes a date and formats it into a string.
		DecimalFormat df = new DecimalFormat("00"); 
		String day = df.format(date.getDayOfMonth());	
		String month = df.format(date.getMonthValue());
		int year = date.getYear();
		String dateOutput = day + "/" + month + "/" + year;
		return dateOutput;
	}
	public int generateChildID(){
		//generates a new child id that is not currently in use.
		int newID = 1;
		ArrayList<Child> children = fetchChildren();
		boolean idFound = compareChildID(children,newID);
		while (idFound == false){
			newID+=1;
			idFound = compareChildID(children,newID);
		}
		return newID;
	}
	public boolean compareChildID(ArrayList<Child> children, int id){
		//compares a given id to see if it exists in the arraylist passed, returns true if one is found.
		boolean usable = true;
		for(int i = 0; i<children.size(); i++){
			Child child = children.get(i);
			if(id == child.getID()){
				usable = false;
			}
		}
		return usable;
	}
	public Child findChild(int id){
		//retrieves a specific child from the database based on its id and returns it.
		Child returnMe = null;
		ArrayList<Child> children = fetchChildren();
		for (int i = 0; i<children.size(); i++){
			Child child = children.get(i);
			if (child.getID() == id){
				returnMe = child;
			}
		}
		if (returnMe == null){
			System.out.println("No child with that id found");
		}
		return returnMe;
	}
	public int generateSessionID(){
		//Generates a new session id.
		int newID = 1;
		ArrayList<Session> sessions = fetchSessions();
		boolean idFound = compareSessionID(sessions,newID);
		while (idFound == false){
			newID+=1;
			idFound = compareSessionID(sessions,newID);
		}
		return newID;
	}
	public int generateAdminID(){
		//generates a new admin id.
		int newID = 1;
		ArrayList<Admin> admins = fetchAdmins();
		boolean idFound = compareAdminID(admins,newID);
		while (idFound == false){
			newID+=1;
			idFound = compareAdminID(admins,newID);
		}
		return newID;
	}
	public boolean compareAdminID(ArrayList<Admin> admins, int id){
		//checks a passed arraylist to see if any of the values contain the passed id.
		boolean usable = true;
		for(int i = 0; i<admins.size(); i++){
			Admin admin = admins.get(i);
			if(id == admin.getId()){
				usable = false;
			}
		}
		return usable;
	}
	public boolean compareSessionID(ArrayList<Session> sessions, int id){
		//checks a passed arraylist to see if any of the values contain the passed id.
		boolean usable = true;
		for(int i = 0; i<sessions.size(); i++){
			Session session = sessions.get(i);
			if(id == session.getID()){
				usable = false;
			}
		}
		return usable;
	}
	public Session findSession(int id){
		//searches the database for a specific session then returns it.
		Session returnMe = null;
		ArrayList<Session> sessions = fetchSessions();
		for (int i = 0; i<sessions.size(); i++){
			Session session = sessions.get(i);
			if (session.getID() == id){
				returnMe = session;
			}
		}
		if (returnMe == null){
			System.out.println("No child with that id found");
		}
		return returnMe;
	}
	public void deleteSession(int id){
		//deletes a session from the database.
		ArrayList<Session> sessions = fetchSessions();
		for (int i = 0; i<sessions.size(); i++){
			Session session = sessions.get(i);
			if (session.getID() == id){
				sessions.remove(i);
			}
		}
		ArrayList<Child> children = fetchChildren();
		for(int i = 0; i < children.size(); i++){
			Child child = children.get(i);
			ArrayList<Session> childSessions = child.getSessions();
			for(int t = 0; t<childSessions.size();t++){
				Session session = childSessions.get(t);
				if(session.getID() == id){
					childSessions.remove(t);
				}
			}
			child.setSessions(childSessions);
			changeChild(child, false);
			moneyDue(child.getID());
		}
		
		
		FileOutputStream outStream;
		try {
			fileOutputConnect("sessions.dat", "object");
			objectOutputFile.writeObject(sessions);
			System.out.println("Session Deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionCapacityUpdate();
	}
	public void deleteAdmin(int id){
		//deletes an admin from the database.
		ArrayList<Admin> admins = fetchAdmins();
		for (int i = 0; i<admins.size(); i++){
			Admin admin = admins.get(i);
			if (admin.getId() == id){
				admins.remove(i);
			}
		}
		
		FileOutputStream outStream;
		try {
			fileOutputConnect("admin.dat", "object");
			objectOutputFile.writeObject(admins);
			System.out.println("Admin Deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deleteChild(int id){
		//deletes a child from the database.
		ArrayList<Child> children = fetchChildren();
		for (int i = 0; i<children.size(); i++){
			Child child = children.get(i);
			if (child.getID() == id){
				//updateSessionCapacity(child, "deleteChild");
				children.remove(i);
			}
		}
		FileOutputStream outStream;
		try {
			fileOutputConnect("children.dat", "object");
			objectOutputFile.writeObject(children);
			System.out.println("Child Deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionCapacityUpdate();
	}

	public void moneyDue(int id){
		//finds a child with the passed id and finds out how much money they owe before updating their invoice object and saving them to the database.
		Child child = findChild(id);
		double newPrice = 0;
		double oldPrice = 0;
		LocalDate now = LocalDate.now();
		LocalDate dob = child.getBirthdate();
		int age = now.compareTo(dob);
		ArrayList<Session> sessions = child.getSessions();
		int chargeCount = sessions.size();
		boolean late = false;
		
		if (now.isAfter(child.getPaymentDue())){
			//if payment is late.
			late = true;
		}
		if(!child.getPaidFee()){
			//if the child hasn't paid the fee
			chargeCount += 1;
		}
		
		String[] sessionNames = new String[chargeCount];
		double[] sessionCosts = new double[chargeCount];
		
		for (int i = 0; i < sessions.size(); i++){
			Session session = sessions.get(i);
			if (age < 2){
				newPrice+=session.getzeroTo2Cost();
				sessionNames[i] = session.getName();
				sessionCosts[i] = session.getzeroTo2Cost();
			}else if(age < 3){
				newPrice+=session.gettwoTo3Cost();
				sessionNames[i] = session.getName();
				sessionCosts[i] = session.gettwoTo3Cost();
			}else{
				newPrice+=session.getthreePlusCost();
				sessionNames[i] = session.getName();
				sessionCosts[i] = session.getthreePlusCost();
			}
		}
		if(!child.getPaidFee()){
			//fee is added.
			sessionNames[sessions.size()] = "Registration Fee";
			sessionCosts[sessions.size()] = 25;
			newPrice += 25;
		}
		if (child.getSibling()){
			//if the child has a registered sibling discount is applied.
			newPrice = newPrice*0.9;
		}
		if (late){
			//if the invoice is late.
			Invoice invoice = child.getInvoice();
			while(!child.getPaymentDue().plusMonths(invoice.getMonthsAccountedFor()).isAfter(now)){
				ArrayList<String> oldChargeNames = invoice.getChargeNames();
				double[] oldChargeCosts = invoice.getChargeCosts();
				for(int i = 0; i<oldChargeCosts.length; i++){
					oldPrice += oldChargeCosts[i];
				}
				for(int i = 0; i<oldChargeNames.size();i++){
					
					String name = oldChargeNames.get(i);
					if(name.equals("Registration Fee")){
						oldChargeNames.remove(i);
						oldChargeCosts = removeIndex(oldChargeCosts,i);
					}else{
						if(!oldChargeNames.get(i).endsWith("(Late)")){
							oldChargeNames.set(i, name += "(Late)");
							oldChargeCosts[i] = oldChargeCosts[i]*1.1;
						}
					}
				}
				ArrayList<String> appendedNames = new ArrayList<String>(Arrays.asList(sessionNames));
				appendedNames.addAll(oldChargeNames);
				double appendedCosts[] = concatDouble(sessionCosts, oldChargeCosts);
				invoice.setChargeNames(appendedNames);
				invoice.setChargeCosts(appendedCosts);
				invoice.setMonthsAccountedFor(invoice.getMonthsAccountedFor()+1);
			}
			
			//Checks whether any of the sessions on the invoice have been deleted before the child has started them.
			ArrayList<Session> compSessions = fetchSessions();
			ArrayList<String> compNames = invoice.getChargeNames();
			double[] charges = invoice.getChargeCosts();
			
			for(int i = 0; i< compNames.size();i++){
				boolean deletedSession = false;
				if(!compNames.get(i).endsWith("(Late)") && !compNames.get(i).equals("Registration Fee")){
					deletedSession = true;
					for(int t = 0; t<compSessions.size(); t++){
						if(compNames.get(i).equals(compSessions.get(t).getName())){
							deletedSession = false;
						}
					}
				}
				if(deletedSession){
					compNames.remove(i);
					charges = removeIndex(charges,i);
					invoice.setChargeNames(compNames);
					invoice.setChargeCosts(charges);
				}
			}
			
			double finalCost = 0;
			double[] y = invoice.getChargeCosts();
			for (int i = 0; i < y.length; i++){
				finalCost += y[i];
			}
			invoice.setCost(finalCost);
			child.setInvoice(invoice);
		}else{
			ArrayList<String> namesAsList = new ArrayList<String>(Arrays.asList(sessionNames));
			Invoice invoice = new Invoice(namesAsList,sessionCosts,child.getSibling(), newPrice);
			invoice.setMonthsAccountedFor(1);
			child.setInvoice(invoice);
		}
		//updates the child in the database
		changeChild(child, false);
	}
	public void changeChild(Child child, boolean manualEdit){
		//takes a child object and updates the child in the database with the same id with the new values.
		//boolean determines whether the update is occuring from the edit child method or if a child is being manually added by a developer for testing.
		Child oldChild = findChild(child.getID());
		oldChild.setFirstname(child.getFirstname());
		oldChild.setSurname(child.getSurname());
		oldChild.setAddress(child.getAddress());
		oldChild.setBirthdate(child.getBirthdate());
		oldChild.setAllergyInfo(child.getAllergyInfo());
		oldChild.setpTitle(child.getpTitle());
		oldChild.setpFirstname(child.getpFirstname());
		oldChild.setpSurname(child.getpSurname());
		oldChild.setSibling(child.getSibling());
		oldChild.setSessions(child.getSessions());
		if(!manualEdit){
			oldChild.setInvoice(child.getInvoice());
			oldChild.setPaymentDue(child.getPaymentDue());
			oldChild.setPaidFee(child.getPaidFee());
		}
		deleteChild(oldChild.getID());
		newChild(oldChild);
	}
	public void sessionCapacityUpdate(){
		//updates the remaining capacity of each session.
		ArrayList<Child> children = fetchChildren();
		ArrayList<Session> sessions = fetchSessions();
		
		for(int q = 0; q < sessions.size(); q++){
			Session session = sessions.get(q);
			session.setSpotsLeft(15);
		}
		
		for (int i = 0; i < children.size();i++){
			Child child = children.get(i);
			ArrayList<Session> childSessions = child.getSessions();
			for(int c = 0; c < childSessions.size(); c++){
				Session childSession = childSessions.get(c);
				int id = childSession.getID();
				for(int t = 0; t < sessions.size(); t++){
					Session compSession = sessions.get(t);
					if(id == compSession.getID()){
						int spots = compSession.getSpotsLeft();
						compSession.setSpotsLeft(spots-1);
					}
				}
				
			}
		}
		FileOutputStream outStream;
		try {
			fileOutputConnect("sessions.dat", "object");
			objectOutputFile.writeObject(sessions);
			System.out.println("Session capacity updated");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void invoicePaid(int id){
		//pays off an invoice
		Child child = findChild(id);
		LocalDate due = child.getPaymentDue();
		Invoice childInvoice = child.getInvoice();
		due=due.plusMonths(childInvoice.getMonthsAccountedFor());
		
		ArrayList<String> chargeNames = null;
		double[] chargeCosts = {};
		Invoice invoice = new Invoice(chargeNames, chargeCosts, child.getSibling(), 0.00);
		child.setInvoice(invoice);
		child.setPaidFee(true);
		child.setPaymentDue(due);
		changeChild(child, false);
	}
	public String[] concatString(String[] a, String[] b) {
		//concatenates two strings arrays together.
		   int aLen = a.length;
		   int bLen = b.length;
		   String[] c= new String[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
	}
	public double[] concatDouble(double[] sessionCosts, double[] oldChargeCosts) {
		//concatenates two double arrays together.
		   int aLen = sessionCosts.length;
		   int bLen = oldChargeCosts.length;
		   double[] c= new double[aLen+bLen];
		   System.arraycopy(sessionCosts, 0, c, 0, aLen);
		   System.arraycopy(oldChargeCosts, 0, c, aLen, bLen);
		   return c;
	}
	public double[] removeIndex(double[] array, int index){
		//removes a given index from a given double array.
		double[] newArray= new double[array.length-1];
		int i = 0;
		int c = 0;
		while( i<array.length){
			if (i != index){
				newArray[c] = array[i];
				c++;
			}
			i++;
		}
		return newArray;
	}
	public boolean login(String username, String password){
		//Finds whether the given details are in the admin list and returns true if they are.
		boolean valid = false;
		ArrayList<Admin> admins = fetchAdmins();
		for(int i = 0; i<admins.size();i++){
			Admin admin = admins.get(i);
			if(username.equals(admin.getUsername())){
				if(password.equals(admin.getPassword())){
					valid = true;
				}
			}
		}
		return valid;
	}
	public boolean openingState(){
		//works out which files the system is missing. Returns whether the admin file is missing so the system knows to tell the user the default login details.
		boolean missingFiles = false;
		boolean children = true;
		boolean admin=true;
		boolean sessions = true;
		if(new File("children.dat").isFile()){
			children = false;
		}
		if(new File("admin.dat").isFile()){
			admin = false;
		}
		if(new File("sessions.dat").isFile()){
			sessions = false; 
		}
		createFiles(children, admin, sessions);
		return admin;
	}
	public void createFiles(boolean children, boolean admin, boolean session){
		//for each true boolean passed the system creates and fills the corresponding data file.
		Session s1 = new Session(1,"All Day", 15, 35, 34, 33);
		Session s2 = new Session(2,"Morning", 15, 16.5, 15.5, 14.5);
		Session s3 = new Session(3,"Lunch", 15, 5, 5, 5);
		Session s4 = new Session(4,"Afternoon", 15, 18, 17, 16);
		Session s5 = new Session(5,"Pre School", 15, 25, 24.5, 23.5);
		if(admin){
			Admin exampleAdmin = new Admin("username","password", 1);
			newAdmin(exampleAdmin);
		}
		if(session){
			newSession(s1);
			newSession(s2);
			newSession(s3);
			newSession(s4);
			newSession(s5);
		}
		if(children){
			ArrayList<Session> exampleSessions = new ArrayList<Session>();
			if(session){
				exampleSessions.add(s1);
				exampleSessions.add(s2);
				exampleSessions.add(s3);
				exampleSessions.add(s4);
				exampleSessions.add(s5);
			}else{
				exampleSessions = fetchSessions();
			}
			Child exampleChild = new Child(9999, "Example", "Example", "Example", LocalDate.now(), "Example", "Example", "Example", "Example", false, exampleSessions);
			exampleChild.setPaidFee(false);
			exampleChild.setPaymentDue(LocalDate.now());
			newChild(exampleChild);
		}
	}
	public boolean validateDate(String day, String month, String year) {
		//Checks that the passed values are a valid date.
		boolean invalid = true;
		if(day.matches("[0-9]+") && day.length() < 3 && day != "" && Integer.parseInt(day) < 32){
			if(month.matches("[0-9]+") && month.length() < 3 && month != "" && Integer.parseInt(month) < 13){
				if(year.matches("[0-9]+") && year.length() < 5 && year.length() > 3 && year != "" ){
					invalid = false;
				}
			}
		}

		return invalid;
	}
}
