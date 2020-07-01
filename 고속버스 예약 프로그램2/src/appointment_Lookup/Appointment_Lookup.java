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
	
	private @FXML TableView<TableRowDataModel> LookupTable; //예약조회 테이블
	private @FXML TableColumn<TableRowDataModel, Integer> reservationnumber; //예약번호 컬럼 (지울 때 DB에서 사용)
	private @FXML TableColumn<TableRowDataModel, String> date; //날짜 컬럼
	private @FXML TableColumn<TableRowDataModel, String> startp; //출발지 컬럼
	private @FXML TableColumn<TableRowDataModel, String> destip; //도착지 컬럼
	private @FXML TableColumn<TableRowDataModel, String> grade; //등급 컬럼
	private @FXML TableColumn<TableRowDataModel, Integer> price; //가격 컬럼
	private @FXML TableColumn<TableRowDataModel, String> ticket; //인원 수 컬럼
	
	private DecimalFormat df = new DecimalFormat("###,###");
	
	//db에서 데이터 받아올 리스트
	private List<TableRowDataModel> reservationList; 
	private List<DbList> dbl;
	//테이블 뷰에 넣을 리스트
	public static ObservableList<TableRowDataModel> data = FXCollections.observableArrayList();
	DAO dao = DAO.getinstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableSetting();
		lookupTableDB();
		LookupTable.setPlaceholder(new Label("예매내역이 없습니다."));
		LookupTable.setItems(data);
	}
	
	//예매 완료 내역 DB에서 불러와 조회
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
	
	//테이블 뷰 컬럼 형식 지정
	public void tableSetting() {
	      TableColumn<TableRowDataModel, ?> a = LookupTable.getColumns().get(0);
	       a.setCellValueFactory(new PropertyValueFactory<>("date"));
	       a.setText("출발일시");
	       
	       TableColumn<TableRowDataModel, ?> b = LookupTable.getColumns().get(1);
	       b.setCellValueFactory(new PropertyValueFactory<>("start"));
	       b.setText("출발지");
	       
	       TableColumn<TableRowDataModel, ?> c = LookupTable.getColumns().get(2);
	       c.setCellValueFactory(new PropertyValueFactory<>("desti"));
	       c.setText("도착지");
	       
	       TableColumn<TableRowDataModel, ?> d = LookupTable.getColumns().get(3);
	       d.setCellValueFactory(new PropertyValueFactory<>("grade"));
	       d.setText("등급");
	       
	       TableColumn<TableRowDataModel, Integer> e = (TableColumn<TableRowDataModel, Integer>) LookupTable.getColumns().get(4);
	       e.setCellValueFactory(new PropertyValueFactory<>("price"));
	       //컬럼 아이템  형식 지정
	       e.setCellFactory(tc-> new TableCell<TableRowDataModel, Integer>(){
	    	   @Override
		          protected void updateItem(Integer item, boolean empty) {
		             super.updateItem(item, empty);
		             if(empty) {
		                setText(null);
		             }else {
		            	 //1000단위 구분해서 컬럼에 넣는다.
		                setText(df.format(item));
		             }
		          };
	       });
	       e.setText("가격");
	       
	       TableColumn<TableRowDataModel, ?> f = LookupTable.getColumns().get(5);
	       f.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	       f.setText("인원");
	}
	
	//예매 취소
	public void reservationCancle(ActionEvent event) {
		//예매내역 없으면 반응 안한다.
		if(LookupTable.getSelectionModel().getSelectedItem()==null) {
			System.out.println("예매내역이 없습니다.");
			return;
		}
		//선택된 테이블 행 삭제
		LookupTable.getItems().remove(LookupTable.getSelectionModel().getSelectedItem());}
}