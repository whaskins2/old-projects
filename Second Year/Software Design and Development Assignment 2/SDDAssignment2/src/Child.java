import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Child implements Serializable{
	//Stored information about children
	private String firstname;
	private String surname;
	private String address;
	private LocalDate birthdate;
	private String allergyInfo;
	private String pTitle;
	private String pFirstname;
	private String pSurname;
	private boolean sibling;
	private int id;
	private boolean paidFee = false;
	private Invoice invoice;
	private ArrayList sessions = new ArrayList();
	private LocalDate paymentDue;
	
	public Child(int id, String firstname, String surname, String address, LocalDate birthdate, String allergyInfo,
			String pTitle, String pFirstname, String pSurname, boolean sibling, ArrayList sessions){
		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.address = address;
		this.birthdate = birthdate;
		this.allergyInfo = allergyInfo;
		this.pTitle = pTitle;
		this.pFirstname = pFirstname;
		this.pSurname = pSurname;
		this.sibling = sibling;
		this.sessions = sessions;
	}
	public void setPaymentDue(LocalDate paymentDue){
		this.paymentDue = paymentDue;
	}
	public LocalDate getPaymentDue(){
		return paymentDue;
	}
	public void setInvoice(Invoice invoice){
		this.invoice = invoice;
	}
	public Invoice getInvoice(){
		return invoice;
	}
	public boolean getPaidFee(){
		return paidFee;
	}
	public void setPaidFee(boolean paidFee){
		this.paidFee = paidFee;
	}
	public void setSibling(boolean sibling){
		this.sibling = sibling;
	}
	public boolean getSibling(){
		return sibling;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname){
		this.surname = surname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	public String getAllergyInfo() {
		return allergyInfo;
	}
	public void setAllergyInfo(String allergyInfo) {
		this.allergyInfo = allergyInfo;
	}
	public String getpTitle() {
		return pTitle;
	}
	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}
	public String getpFirstname() {
		return pFirstname;
	}
	public void setpFirstname(String pFirstname) {
		this.pFirstname = pFirstname;
	}
	public String getpSurname() {
		return pSurname;
	}
	public void setpSurname(String pSurname) {
		this.pSurname = pSurname;
	}
	public ArrayList getSessions() {
		return sessions;
	}
	public void setSessions(ArrayList sessions) {
		this.sessions = sessions;
	}
	public int getID(){
		return id;
	}
}
