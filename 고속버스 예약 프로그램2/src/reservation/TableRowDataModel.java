package reservation;

import java.io.Serializable;

public class TableRowDataModel implements Serializable{
	private String date;
	private String start; 
	private String desti;
	private String grade;
	private int price;
	private String ticket;
	
	public TableRowDataModel() {
	}
	
	public TableRowDataModel(String date, String start, String desti, String grade, int price, String ticket) {
		this.date = date;
		this.start = start;
		this.desti = desti;
		this.grade = grade;
		this.price = price;
		this.ticket = ticket;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDesti() {
		return desti;
	}

	public void setDesti(String desti) {
		this.desti = desti;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}