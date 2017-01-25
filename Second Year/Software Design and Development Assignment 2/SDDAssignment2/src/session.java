import java.io.Serializable;

public class Session implements Serializable{
	//Stored information about sessions
	private String name;
	private int spotsLeft;
	private int id;
	private double zeroTo2Cost;
	private double twoTo3Cost;
	private double threePlusCost;
	
	public Session(int id, String name, int spotsLeft, double zeroTo2Cost, double twoTo3Cost, double threePlusCost){
		this.id = id;
		this.name = name;
		this.spotsLeft = spotsLeft;
		this.zeroTo2Cost = zeroTo2Cost;
		this.twoTo3Cost = twoTo3Cost;
		this.threePlusCost = threePlusCost;
	}
	public int getID(){
		return id;
	}
	public  void setID(int id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSpotsLeft() {
		return spotsLeft;
	}
	public void setSpotsLeft(int spotsLeft) {
		this.spotsLeft = spotsLeft;
	}
	public double getzeroTo2Cost(){
		return zeroTo2Cost;
	}
	public void setzeroTo2Cost(double zeroTo2Cost){
		this.zeroTo2Cost = zeroTo2Cost;
	}
	public double gettwoTo3Cost(){
		return twoTo3Cost;
	}
	public void settwoTo3Cost(double twoTo3Cost){
		this.twoTo3Cost = twoTo3Cost;
	}
	public double getthreePlusCost(){
		return zeroTo2Cost;
	}
	public void setthreePlusCost(double threePlusCost){
		this.threePlusCost = threePlusCost;
	}
}
