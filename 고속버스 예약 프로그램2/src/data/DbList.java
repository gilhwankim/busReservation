package data;

import java.util.List;
import reservation.TableRowDataModel;

//db���� �����͸� �޾ƿ��� ���� Ŭ����
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