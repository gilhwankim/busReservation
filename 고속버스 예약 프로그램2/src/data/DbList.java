package data;

import java.util.List;
import reservation.TableRowDataModel;

//db에서 데이터를 받아오기 위한 클래스
public class DbList {

	public List<TableRowDataModel> dbList; 

	public void setDbList(List<TableRowDataModel> dbList) {
		this.dbList = dbList;
	}

	
	public List<TableRowDataModel> getDbList() {
		return dbList;
	}

	public DbList() {
		
	}
}