package paymentPage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import data.Pop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import login.DAO;
import reservation.ReservationController;
import reservation.TableRowDataModel;

public class PaymentPageController implements Initializable {
	
	private @FXML TableView<TableRowDataModel> paymenttableview; // 예매내역 테이블 뷰
	private @FXML TableColumn<TableRowDataModel,String> date; // 날짜
	private @FXML TableColumn<TableRowDataModel,String> startP; // 출발지
	private @FXML TableColumn<TableRowDataModel,String> destiP; // 도착지
	private @FXML TableColumn<TableRowDataModel,String> grade; // 등급
	private @FXML TableColumn<TableRowDataModel,Integer> price; // 가격
	private @FXML TableColumn<TableRowDataModel,String> ticket; // 인원
	private @FXML TextField txt_total; // 총 결제금액
	private @FXML ComboBox<String> companybtn; // 카드사
	private @FXML TextField txt_cardnumber; // 카드번호
	private @FXML ComboBox<String> instalment_periodbtn; // 할부기간
	private @FXML PasswordField txt_cardpassword;// 카드 비밀번호
	private @FXML Button previousbtn; // 이전화면
	private @FXML Button paymentbtn; // 결제완료
	private int totalPrice =0;
	
	private DecimalFormat df = new DecimalFormat("###,###");
	private DAO dao = DAO.getinstance();
	private Pop pop = new Pop();
	
	ReservationController rc = new ReservationController();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("생성");
		
		tableSetting();
		//담아놓은 예약 내역을 받아온다
		paymenttableview.setItems(rc.reservationOb);
//		paymenttableview.refresh();
		
		for(int i=0; i<paymenttableview.getItems().size(); i++) {
			
			totalPrice += (price.getCellData(i)*Integer.parseInt(ticket.getCellData(i)));
			
		}		
		txt_total.setText(df.format(totalPrice));
		//입력제한
		keyLocked(txt_cardnumber,10);
		keyLocked(txt_cardpassword,4);
	}
	
	//테이블 뷰 컬럼 형식 지정
	public void tableSetting() {
	      TableColumn<TableRowDataModel, ?> a = paymenttableview.getColumns().get(0);
	       a.setCellValueFactory(new PropertyValueFactory<>("date"));
	       a.setText("출발일시");
	       
	       TableColumn<TableRowDataModel, ?> b = paymenttableview.getColumns().get(1);
	       b.setCellValueFactory(new PropertyValueFactory<>("start"));
	       b.setText("출발지");
	       
	       TableColumn<TableRowDataModel, ?> c = paymenttableview.getColumns().get(2);
	       c.setCellValueFactory(new PropertyValueFactory<>("desti"));
	       c.setText("도착지");
	       
	       TableColumn<TableRowDataModel, ?> d = paymenttableview.getColumns().get(3);
	       d.setCellValueFactory(new PropertyValueFactory<>("grade"));
	       d.setText("등급");
	       
	       TableColumn<TableRowDataModel, Integer> e = (TableColumn<TableRowDataModel, Integer>) paymenttableview.getColumns().get(4);
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
	       
	       TableColumn<TableRowDataModel, ?> f = paymenttableview.getColumns().get(5);
	       f.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	       f.setText("인원");
	}
	
	// 결제완료
	public void ConfirmReservation(ActionEvent event) {
		
		if(companybtn.getSelectionModel().getSelectedItem()==null) {
			System.out.println("카드사를 선택해주세요");
			return;
		} else if(txt_cardnumber.getText().equals("")) {
			System.out.println("카드번호를 입력해주세요");
			return;
		} else if(instalment_periodbtn.getSelectionModel().getSelectedItem()==null) {
			System.out.println("할부기간을 선택해주세요");
			return;
		} else if(txt_cardpassword.getText().equals("")) {
			System.out.println("카드 비밀번호를 입력해주세요");
			return;
		}
		//db에 넣을 리스트
		List<TableRowDataModel> list = new ArrayList<>();
		for(TableRowDataModel td : rc.reservationOb) {
			list.add(td);
		}
		dao.saveReservationList(list);
		
		pop.popupMsg("예매가 완료되었습니다.");
		Stage clo = (Stage)paymentbtn.getScene().getWindow();
		clo.close();
	}
	
	//텍스트 필드 입력 조건 제한
	public void keyLocked(TextField textField,int length) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			
			//텍스트 필드 길이 제한
			if(newValue.length()>length) {
				textField.setText(newValue.substring(0,length));
			}
			//텍스트필드 숫자만 입력가능하도록 제한.
			if (!newValue.matches("\\d*")) {
	        	textField.setText(newValue.replaceAll("[^\\d]", ""));
	        }
	        
	    });
	}
	
//	//이전화면(예매 화면)	
	public void preStage(ActionEvent event) throws Exception{
		
		Stage clo = (Stage)previousbtn.getScene().getWindow();
		clo.close();
		
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reservation.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		Stage stage = new Stage();
		stage.setTitle("예매");
		stage.setScene(scene);
		stage.show();
	}
}