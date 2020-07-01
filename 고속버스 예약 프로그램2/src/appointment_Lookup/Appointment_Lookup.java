package appointment_Lookup;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import data.DbList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.DAO;
import reservation.TableRowDataModel;
public class Appointment_Lookup implements Initializable{
	
	private @FXML TableView<TableRowDataModel> LookupTable; //������ȸ ���̺�
	private @FXML TableColumn<TableRowDataModel, Integer> reservationnumber; //�����ȣ �÷� (���� �� DB���� ���)
	private @FXML TableColumn<TableRowDataModel, String> date; //��¥ �÷�
	private @FXML TableColumn<TableRowDataModel, String> startp; //����� �÷�
	private @FXML TableColumn<TableRowDataModel, String> destip; //������ �÷�
	private @FXML TableColumn<TableRowDataModel, String> grade; //��� �÷�
	private @FXML TableColumn<TableRowDataModel, Integer> price; //���� �÷�
	private @FXML TableColumn<TableRowDataModel, String> ticket; //�ο� �� �÷�
	
	private DecimalFormat df = new DecimalFormat("###,###");
	
	//db���� ������ �޾ƿ� ����Ʈ
	private List<TableRowDataModel> reservationList; 
	private List<DbList> dbl;
	//���̺� �信 ���� ����Ʈ
	public static ObservableList<TableRowDataModel> data = FXCollections.observableArrayList();
	DAO dao = DAO.getinstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableSetting();
		lookupTableDB();
		LookupTable.setPlaceholder(new Label("���ų����� �����ϴ�."));
		LookupTable.setItems(data);
	}
	
	//���� �Ϸ� ���� DB���� �ҷ��� ��ȸ
	public void lookupTableDB() {
		dbl = dao.loadReservationList();
		data.clear();
		for(DbList d : dbl) {
			reservationList = d.getDbList();
			for(TableRowDataModel dd : reservationList) {
				data.add(dd);
			}
		}
		LookupTable.refresh();
	}
	
	//���̺� �� �÷� ���� ����
	public void tableSetting() {
	      TableColumn<TableRowDataModel, ?> a = LookupTable.getColumns().get(0);
	       a.setCellValueFactory(new PropertyValueFactory<>("date"));
	       a.setText("����Ͻ�");
	       
	       TableColumn<TableRowDataModel, ?> b = LookupTable.getColumns().get(1);
	       b.setCellValueFactory(new PropertyValueFactory<>("start"));
	       b.setText("�����");
	       
	       TableColumn<TableRowDataModel, ?> c = LookupTable.getColumns().get(2);
	       c.setCellValueFactory(new PropertyValueFactory<>("desti"));
	       c.setText("������");
	       
	       TableColumn<TableRowDataModel, ?> d = LookupTable.getColumns().get(3);
	       d.setCellValueFactory(new PropertyValueFactory<>("grade"));
	       d.setText("���");
	       
	       TableColumn<TableRowDataModel, Integer> e = (TableColumn<TableRowDataModel, Integer>) LookupTable.getColumns().get(4);
	       e.setCellValueFactory(new PropertyValueFactory<>("price"));
	       //�÷� ������  ���� ����
	       e.setCellFactory(tc-> new TableCell<TableRowDataModel, Integer>(){
	    	   @Override
		          protected void updateItem(Integer item, boolean empty) {
		             super.updateItem(item, empty);
		             if(empty) {
		                setText(null);
		             }else {
		            	 //1000���� �����ؼ� �÷��� �ִ´�.
		                setText(df.format(item));
		             }
		          };
	       });
	       e.setText("����");
	       
	       TableColumn<TableRowDataModel, ?> f = LookupTable.getColumns().get(5);
	       f.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	       f.setText("�ο�");
	}
	
	//���� ���
	public void reservationCancle(ActionEvent event) {
		//���ų��� ������ ���� ���Ѵ�.
		if(LookupTable.getSelectionModel().getSelectedItem()==null) {
			System.out.println("���ų����� �����ϴ�.");
			return;
		}
		//���õ� ���̺� �� ����
		LookupTable.getItems().remove(LookupTable.getSelectionModel().getSelectedItem());}
}