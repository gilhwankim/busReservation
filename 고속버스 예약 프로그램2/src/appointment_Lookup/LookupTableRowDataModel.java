package appointment_Lookup;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class LookupTableRowDataModel {
	private IntegerProperty number;
	private StringProperty date;
	private StringProperty start; 
	private StringProperty desti;
	private StringProperty grade;
	private IntegerProperty price;
	private StringProperty ticket;
	
	public LookupTableRowDataModel(IntegerProperty number,StringProperty date, StringProperty start, StringProperty desti, StringProperty grade,
			IntegerProperty price, StringProperty ticket) {
		this.number = number;
		this.date = date;
		this.start = start;
		this.desti = desti;
		this.grade = grade;
		this.price = price;
		this.ticket = ticket;
	}

	public IntegerProperty getNumber() {
		return number;
	}

	public StringProperty getDate() {
		return date;
	}

	public StringProperty getStart() {
		return start;
	}

	public StringProperty getDesti() {
		return desti;
	}

	public StringProperty getGrade() {
		return grade;
	}

	public IntegerProperty getPrice() {
		return price;
	}

	public StringProperty getTicket() {
		return ticket;
	}

	
	
}