import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
	private View view;
	private Model model = new Model();
	
	public Controller(Model model){
		this.model = model;
	}
	
	public void addView(View view){
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		System.out.println(command);
		int id;
				if (command == "login"){
					//Displays the login screen.
					view.fillBody(0);
					
				}else if(e.getSource() == view.nav1){
					//Displays the add children page
					view.fillBody(1);
				}else if(e.getSource() == view.nav2){
					//Displays the sessions page
					view.fillBody(2);
				}else if(e.getSource() == view.nav3){
					//Displays the list of children
					view.fillBody(3);
				}else if(e.getSource() == view.nav4){
					//Displays the admin list
					view.fillBody(4);
				}else if(command == "newChild"){
					//Calls new child feature
					view.updateChild(true);
				}else if(command == "sessionSelect"){
					//opens the frame of sessions to select from
					view.buildSessionFrame();
				}else if(command == "newSession"){
					//Calls the add session feature
					view.newSession();
				}else if(command == "assignSessionsToChild"){
					//confirms selection of sessions
					view.selectedSessions();
					view.closeSessionFrame();
				}else if(e.getSource().equals(view.deleteChild)){
					//Calls delete child code
					id = Integer.parseInt(command);
					model.deleteChild(id);
					view.closeChildFrame();
				}else if(e.getSource().equals(view.showInvoice)){
					//Generates an invoice and displays it in a new frame.
					id = Integer.parseInt(command);
					model.moneyDue(id);
					view.buildInvoiceFrame(model.findChild(id));
				}else if(e.getSource().equals(view.editChild)){
					//Opens the edit child page
					id = Integer.parseInt(command);
					view.closeChildFrame();
					view.buildChildEdit(model.findChild(id));
				}else if(e.getSource().equals(view.confirmEdit)){
					//Confirms the changes made on the edit child page
					id = Integer.parseInt(command);
					view.updateChild(false);
				}else if(command.charAt(0) == 'c'){
					//Builds the individual child frame
					id = Integer.parseInt(command.substring(1));
					view.individualChild(id);
				}else if(command.charAt(0) == 's'){
					//Deletes a session
					id = Integer.parseInt(command.substring(1));
					model.deleteSession(id);
					view.fillBody(2);
				}else if(e.getSource().equals(view.paid)){
					//Pays an invoice off
					id = Integer.parseInt(command);
					model.invoicePaid(id);
					Child child = model.findChild(id);
					view.invoicePaid(child);
				}else if(command.charAt(0) == 'a'){
					//Deletes an admin
					id = Integer.parseInt(command.substring(1)); 
					model.deleteAdmin(id);
					view.fillBody(4);
				}else if(e.getSource().equals(view.submitAdmin)){
					//Creates an admin
					view.newAdmin();
				}else if(e.getSource().equals(view.loginBtn)){
					//logs in
					view.login();
				}else if(e.getSource().equals(view.btnLogOut)){
					//logs out
					view.logOut();
				}
	}

}
