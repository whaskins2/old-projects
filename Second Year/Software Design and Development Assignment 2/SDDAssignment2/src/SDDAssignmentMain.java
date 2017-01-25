import java.io.IOException;

public class SDDAssignmentMain {

	public static void main(String[] args) throws Exception{
		//Main method that creates an instance of each of the other main classes.
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(model, controller);
	}

}
