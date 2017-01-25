import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice implements Serializable{
	//Information about an invoice stored by a child object.
	private ArrayList<String> chargeNames;
	private double[] chargeCosts;
	private boolean siblingDiscount;
	private double cost;
	private int monthsAccountedFor;
	
	public Invoice(ArrayList<String> chargeNames, double[] chargeCosts, boolean siblingDiscount, double cost){
		this.chargeNames = chargeNames;
		this.chargeCosts = chargeCosts;
		this.siblingDiscount = siblingDiscount;
		this.cost = cost;
	}
	public int getMonthsAccountedFor(){
		return monthsAccountedFor;
	}
	public void setMonthsAccountedFor(int monthsAccountedFor){
		this.monthsAccountedFor=monthsAccountedFor;
	}
	public ArrayList<String> getChargeNames() {
		return chargeNames;
	}

	public void setChargeNames(ArrayList<String> chargeNames) {
		this.chargeNames = chargeNames;
	}

	public double[] getChargeCosts() {
		return chargeCosts;
	}

	public void setChargeCosts(double[] chargeCosts) {
		this.chargeCosts = chargeCosts;
	}

	public boolean isSiblingDiscount() {
		return siblingDiscount;
	}

	public void setSiblingDiscount(boolean siblingDiscount) {
		this.siblingDiscount = siblingDiscount;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
