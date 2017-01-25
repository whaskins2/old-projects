import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


import Controller.WUC_Controller;
import Model.WUC_Diary_M;
import Model.WUC_Event_M;
import Model.WUC_Login_M;
import Model.WUC_Student_M;
import View.WUC_Diary_V;
import View.WUC_Event_V;
import View.WUC_Homepage_V;
import View.WUC_Login_V;
import View.WUC_Student_V;

	public class WUC_Run {
		public static void main(String[] args) {
		//	try {
			UIManager.put("MenuBar.background", Color.WHITE);        
		        UIManager.put("MenuItem.background", Color.CYAN);
		        UIManager.put("Menu.selectionBackground", Color.CYAN);
				//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				
				
			JFrame WUC_Frame = new JFrame();
			WUC_Frame.setSize(1024, 768);
			WUC_Frame.setLayout(null);
			WUC_Frame.setResizable(false); 
			WUC_Frame.setLocationRelativeTo(null);
			WUC_Frame.setTitle("Woodlands University College | Administration");
			WUC_Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
			
			//Declare the controller/model
			WUC_Login_M model = new WUC_Login_M();
			WUC_Controller controller = new WUC_Controller(model);
			WUC_Student_M studModel = new WUC_Student_M();
			WUC_Event_M eventModel = new WUC_Event_M();
			WUC_Diary_M diaryModel = new WUC_Diary_M();
			//WUC_Homepage_C controller_h = new WUC_Homepage_C();
			
			//All views go here - Also keep them setVisible to false after adding to frame EXCEPT the first one as its login
			
			WUC_Login_V login = new WUC_Login_V(controller, model);
			WUC_Frame.add(login.getPanel());
			login.getPanel().setVisible(false);//remove later
			
			WUC_Homepage_V homepage = new WUC_Homepage_V(controller);
			WUC_Frame.add(homepage.getPanel());
			homepage.getPanel().setVisible(false);
			
			
			WUC_Student_V studentCreate = new WUC_Student_V(controller,studModel, 1);
			controller.addStudentView(studentCreate);
			controller.addStudentModel(studModel);
			WUC_Frame.add(studentCreate.getPanel());
			studentCreate.getPanel().setVisible(true);
			
			WUC_Student_V studentAlter = new WUC_Student_V(controller,studModel, 2);
			//controller.addStudentView(studentAlter);
			WUC_Frame.add(studentAlter.getPanel());
			studentAlter.getPanel().setVisible(false);
			
			WUC_Student_V studentQuery = new WUC_Student_V(controller,studModel, 3);
			//controller.addStudentView(studentQuery);
			WUC_Frame.add(studentQuery.getPanel());
			studentQuery.getPanel().setVisible(false);
			
			WUC_Event_V eventCreate = new WUC_Event_V(controller, eventModel, 1);
			controller.addEventView(eventCreate);
			controller.addEventModel(eventModel);
			WUC_Frame.add(eventCreate.getPanel());
			eventCreate.getPanel().setVisible(false);
			
			WUC_Event_V eventAmend = new WUC_Event_V(controller, eventModel, 2);
			//controller.addEventView(eventAmend);
			WUC_Frame.add(eventAmend.getPanel());
			eventAmend.getPanel().setVisible(false);
			
			WUC_Event_V eventQuery = new WUC_Event_V(controller, eventModel, 3);
			//controller.addEventView(eventQuery);
			WUC_Frame.add(eventQuery.getPanel());
			eventQuery.getPanel().setVisible(false);
			
			WUC_Diary_V diaryCreate = new WUC_Diary_V(controller, diaryModel,1);
			controller.addDiaryModel(diaryModel);
			controller.addDiaryView(diaryCreate);
			WUC_Frame.add(diaryCreate.getPanel());
			diaryCreate.getPanel().setVisible(false);
			
			WUC_Diary_V diaryAmend = new WUC_Diary_V(controller, diaryModel,2);
			WUC_Frame.add(diaryAmend.getPanel());
			//controller.addDiaryView(diaryAmend);
			diaryAmend.getPanel().setVisible(false);
			
			WUC_Diary_V diaryQuery = new WUC_Diary_V(controller, diaryModel,3);
			WUC_Frame.add(diaryQuery.getPanel());
			//controller.addDiaryView(diaryQuery);
			diaryQuery.getPanel().setVisible(false);
			
			//Then set frame to visible
			WUC_Frame.setSize(1024, 800);
			WUC_Frame.setVisible(true);
			
			/*} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}	*/
		}
	}
