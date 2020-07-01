package reservation;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import appointment_Lookup.Appointment_Lookup;
import data.TerminalList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import login.DAO;

public class ReservationController implements Initializable{
	
	private @FXML ComboBox<String> startingpointbtn; //출발지 콤보박스
	private @FXML ComboBox<String> destinationbtn; //도착지 콤보박스
	private @FXML ComboBox<String> startlocalterminalbtn; //출발지 터미널 콤보박스
	private @FXML ComboBox<String> destilocalterminalbtn; //도착지 터미널 콤보박스
	private @FXML ComboBox<String> ticketbtn; //매수 콤보박스
	private @FXML RadioButton premiumbtn; //프리미엄 버튼
	private @FXML RadioButton udengbtn; //우등 버튼
	private @FXML RadioButton normalbtn;//일반 버튼
	private @FXML DatePicker calendarbtn;//날짜
	private @FXML TableView<TableRowDataModel> tableview; //테이블 뷰
	private @FXML Button reservationbtn; //예매하기 버튼
	private @FXML Button preReservation; //이전 예매내역 조회 버튼
	private @FXML Button deletebtn; //삭제하기 버튼
	
	private DecimalFormat df = new DecimalFormat("###,###");
	//DatePicker 형식 지정후 String타입 반환을 위한 코드
	private DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("MM-dd");
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
	private DAO dao = DAO.getinstance();
	private String gradeButton = "일반";
	private Integer pricee=10000;
	
	private TerminalList tl = new TerminalList();
	private List<TableRowDataModel> list = new ArrayList<TableRowDataModel>();
	private String[] terminal;
	int pricenum = 1;
	private ObservableList<String> startOb = FXCollections.observableArrayList();
	private ObservableList<String> destiOb = FXCollections.observableArrayList();
	public static ObservableList<TableRowDataModel> reservationOb = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//테이블뷰의 기본 아이템이 될 ObservableList를 넣는다.
		tableview.setItems(reservationOb);
		normalbtn.setSelected(true);
		//티켓 기본 값 1매
		ticketbtn.getSelectionModel().selectFirst();
		tableview.setPlaceholder(new Label(""));
		tableSetting();
	}
	
//	//출발지 선택
	public void startPoint(ActionEvent event) {
		//현재 선택된 출발지역
		String startP = startingpointbtn.getSelectionModel().getSelectedItem();

		//출발지 선택 시 지역에 맞는 터미널 불러오기
			//터미널 콤보박스에 값이 있으면 초기화.
			if(!(startlocalterminalbtn.getItems().size()==0)) {
				startlocalterminalbtn.getItems().clear();
			}
			
			terminallist(startP,startOb); //선택된 지역 터미널 불러오기
			startlocalterminalbtn.setItems(startOb);
	}
	
	//도착지 선택
	public void destinationPoint(ActionEvent event) {
		//현재 선택된 도착지역
		String destinationP = destinationbtn.getSelectionModel().getSelectedItem();
		//터미널 콤보박스에 값이 있으면 초기화
		if(!(destilocalterminalbtn.getItems().size()==0)) {
			destilocalterminalbtn.getItems().clear();
		}
		terminallist(destinationP,destiOb);
		destilocalterminalbtn.setItems(destiOb);
	}
	//선택된 지역 터미널 불러오기
	public void terminallist(String start,ObservableList<String> ob) {
		ob.clear();
		switch (start) {
		case "서울":
			 terminal = tl.getSeoul();
			break;
		case "인천경기":
		     terminal = tl.getIncheonGyeonggi();
			break;
		case "강원":
			terminal = tl.getGangwon();
			break;
		case "대전충남":
			terminal = tl.getDaejeonChungnam();
			break;
		case "충북":
			terminal = tl.getChungbuk();
			break;
		case "광주전남":
			terminal = tl.getGwangjuJeonnam();
			break;
		case "전북":
			terminal = tl.getJeonbuk();
			break;
		case "부산경남":
			terminal = tl.getBusanGyeongnam();
			break;
		case "대구경북":
			terminal = tl.getDaeguGyeongbuk();
			break;
		}
		
		for(String str : terminal) {
			ob.add(str);
		}
	}
	
	//담기 버튼 action
	public void lookup(ActionEvent event) {
		
		if(startlocalterminalbtn.getSelectionModel().getSelectedItem()==null||destilocalterminalbtn.getSelectionModel().getSelectedItem()==null
				||ticketbtn.getSelectionModel().getSelectedItem()==null||calendarbtn.getValue()==null) {
			System.out.println("빈칸");
			return;
		}
		LocalDate date = calendarbtn.getValue();
		//지난 날짜는 예매 불가 
		if(calendarbtn.getValue().compareTo(LocalDate.now())<0) {
			System.out.println(formatterr.format(LocalDate.now())+" 날짜부터 예매가 가능합니다.");
			return;
		}
		TableRowDataModel tr = new TableRowDataModel(formatterr.format(calendarbtn.getValue()), startlocalterminalbtn.getSelectionModel().getSelectedItem(),
				destilocalterminalbtn.getSelectionModel().getSelectedItem(), gradeButton, pricee, ticketbtn.getSelectionModel().getSelectedItem());
		//테이블 뷰에 들어가 있는 ObservableList에 add
		reservationOb.add(tr);
		//테이블 뷰 새로고침
		tableview.refresh();
	}
	//테이블 뷰 컬럼 형식 지정
	public void tableSetting() {
	      TableColumn<TableRowDataModel, ?> a = tableview.getColumns().get(0);
	       a.setCellValueFactory(new PropertyValueFactory<>("date"));
	       a.setText("출발일시");
	       
	       TableColumn<TableRowDataModel, ?> b = tableview.getColumns().get(1);
	       b.setCellValueFactory(new PropertyValueFactory<>("start"));
	       b.setText("출발지");
	       
	       TableColumn<TableRowDataModel, ?> c = tableview.getColumns().get(2);
	       c.setCellValueFactory(new PropertyValueFactory<>("desti"));
	       c.setText("도착지");
	       
	       TableColumn<TableRowDataModel, ?> d = tableview.getColumns().get(3);
	       d.setCellValueFactory(new PropertyValueFactory<>("grade"));
	       d.setText("등급");
	       
	       TableColumn<TableRowDataModel, Integer> e = (TableColumn<TableRowDataModel, Integer>) tableview.getColumns().get(4);
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
	       
	       TableColumn<TableRowDataModel, ?> f = tableview.getColumns().get(5);
	       f.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	       f.setText("인원");
	}

	public void buttonClick(ActionEvent event) {
		if(premiumbtn.isSelected()) {
			gradeButton = "프리미엄";
			pricee = 20000;
		} else if(udengbtn.isSelected()) {
			gradeButton = "우등";
			pricee = 15000;
		} else {
			gradeButton = "일반";
			pricee = 10000;
		}
	}
	
	//선택된 테이블 행 제거
	public void columnDelete(ActionEvent event) {
		tableview.getItems().remove(tableview.getSelectionModel().getSelectedItem());
	}
	
	//예매하기 
	public void reservation(ActionEvent event) throws Exception{
		if(reservationOb.isEmpty()) {
			System.out.println("예매내역을 추가해주세요.");
			return;
		}
		System.out.println("결제페이지로 넘어갑니다.");
		//현재 화면 닫기
		Stage clo = (Stage)reservationbtn.getScene().getWindow();
		clo.close();
		
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/PaymentPage.fxml"));
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothi.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicBold.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicExtraBold.ttf"), 14);
		
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		stage.setTitle("결제");
		stage.setScene(scene);
		stage.show();
	}
	
	//이전 예매내역 조회
	public void preReservationLookup(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Appointment_Lookup.fxml"));
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothi.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicBold.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicExtraBold.ttf"), 14);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		Stage stage = new Stage();
		
		//에매내역 창이 닫힐 때 
		stage.setOnCloseRequest((e)->{
			//이전에 가지고 있던 내역 초기화
			list.clear();
			//예매 취소 후 남은 아이템을 db로 보낸다
			for(TableRowDataModel ddd : Appointment_Lookup.data) {
				list.add(ddd);
			}
			dao.cancle(list);
		});
		stage.setTitle("예매내역");
		stage.setScene(scene);
		stage.show();
	}
	
}