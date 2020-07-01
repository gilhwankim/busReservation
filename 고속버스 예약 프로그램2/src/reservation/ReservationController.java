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
	
	private @FXML ComboBox<String> startingpointbtn; //����� �޺��ڽ�
	private @FXML ComboBox<String> destinationbtn; //������ �޺��ڽ�
	private @FXML ComboBox<String> startlocalterminalbtn; //����� �͹̳� �޺��ڽ�
	private @FXML ComboBox<String> destilocalterminalbtn; //������ �͹̳� �޺��ڽ�
	private @FXML ComboBox<String> ticketbtn; //�ż� �޺��ڽ�
	private @FXML RadioButton premiumbtn; //�����̾� ��ư
	private @FXML RadioButton udengbtn; //��� ��ư
	private @FXML RadioButton normalbtn;//�Ϲ� ��ư
	private @FXML DatePicker calendarbtn;//��¥
	private @FXML TableView<TableRowDataModel> tableview; //���̺� ��
	private @FXML Button reservationbtn; //�����ϱ� ��ư
	private @FXML Button preReservation; //���� ���ų��� ��ȸ ��ư
	private @FXML Button deletebtn; //�����ϱ� ��ư
	
	private DecimalFormat df = new DecimalFormat("###,###");
	//DatePicker ���� ������ StringŸ�� ��ȯ�� ���� �ڵ�
	private DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("MM-dd");
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
	private DAO dao = DAO.getinstance();
	private String gradeButton = "�Ϲ�";
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
		//���̺���� �⺻ �������� �� ObservableList�� �ִ´�.
		tableview.setItems(reservationOb);
		normalbtn.setSelected(true);
		//Ƽ�� �⺻ �� 1��
		ticketbtn.getSelectionModel().selectFirst();
		tableview.setPlaceholder(new Label(""));
		tableSetting();
	}
	
//	//����� ����
	public void startPoint(ActionEvent event) {
		//���� ���õ� �������
		String startP = startingpointbtn.getSelectionModel().getSelectedItem();

		//����� ���� �� ������ �´� �͹̳� �ҷ�����
			//�͹̳� �޺��ڽ��� ���� ������ �ʱ�ȭ.
			if(!(startlocalterminalbtn.getItems().size()==0)) {
				startlocalterminalbtn.getItems().clear();
			}
			
			terminallist(startP,startOb); //���õ� ���� �͹̳� �ҷ�����
			startlocalterminalbtn.setItems(startOb);
	}
	
	//������ ����
	public void destinationPoint(ActionEvent event) {
		//���� ���õ� ��������
		String destinationP = destinationbtn.getSelectionModel().getSelectedItem();
		//�͹̳� �޺��ڽ��� ���� ������ �ʱ�ȭ
		if(!(destilocalterminalbtn.getItems().size()==0)) {
			destilocalterminalbtn.getItems().clear();
		}
		terminallist(destinationP,destiOb);
		destilocalterminalbtn.setItems(destiOb);
	}
	//���õ� ���� �͹̳� �ҷ�����
	public void terminallist(String start,ObservableList<String> ob) {
		ob.clear();
		switch (start) {
		case "����":
			 terminal = tl.getSeoul();
			break;
		case "��õ���":
		     terminal = tl.getIncheonGyeonggi();
			break;
		case "����":
			terminal = tl.getGangwon();
			break;
		case "�����泲":
			terminal = tl.getDaejeonChungnam();
			break;
		case "���":
			terminal = tl.getChungbuk();
			break;
		case "��������":
			terminal = tl.getGwangjuJeonnam();
			break;
		case "����":
			terminal = tl.getJeonbuk();
			break;
		case "�λ�泲":
			terminal = tl.getBusanGyeongnam();
			break;
		case "�뱸���":
			terminal = tl.getDaeguGyeongbuk();
			break;
		}
		
		for(String str : terminal) {
			ob.add(str);
		}
	}
	
	//��� ��ư action
	public void lookup(ActionEvent event) {
		
		if(startlocalterminalbtn.getSelectionModel().getSelectedItem()==null||destilocalterminalbtn.getSelectionModel().getSelectedItem()==null
				||ticketbtn.getSelectionModel().getSelectedItem()==null||calendarbtn.getValue()==null) {
			System.out.println("��ĭ");
			return;
		}
		LocalDate date = calendarbtn.getValue();
		//���� ��¥�� ���� �Ұ� 
		if(calendarbtn.getValue().compareTo(LocalDate.now())<0) {
			System.out.println(formatterr.format(LocalDate.now())+" ��¥���� ���Ű� �����մϴ�.");
			return;
		}
		TableRowDataModel tr = new TableRowDataModel(formatterr.format(calendarbtn.getValue()), startlocalterminalbtn.getSelectionModel().getSelectedItem(),
				destilocalterminalbtn.getSelectionModel().getSelectedItem(), gradeButton, pricee, ticketbtn.getSelectionModel().getSelectedItem());
		//���̺� �信 �� �ִ� ObservableList�� add
		reservationOb.add(tr);
		//���̺� �� ���ΰ�ħ
		tableview.refresh();
	}
	//���̺� �� �÷� ���� ����
	public void tableSetting() {
	      TableColumn<TableRowDataModel, ?> a = tableview.getColumns().get(0);
	       a.setCellValueFactory(new PropertyValueFactory<>("date"));
	       a.setText("����Ͻ�");
	       
	       TableColumn<TableRowDataModel, ?> b = tableview.getColumns().get(1);
	       b.setCellValueFactory(new PropertyValueFactory<>("start"));
	       b.setText("�����");
	       
	       TableColumn<TableRowDataModel, ?> c = tableview.getColumns().get(2);
	       c.setCellValueFactory(new PropertyValueFactory<>("desti"));
	       c.setText("������");
	       
	       TableColumn<TableRowDataModel, ?> d = tableview.getColumns().get(3);
	       d.setCellValueFactory(new PropertyValueFactory<>("grade"));
	       d.setText("���");
	       
	       TableColumn<TableRowDataModel, Integer> e = (TableColumn<TableRowDataModel, Integer>) tableview.getColumns().get(4);
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
	       
	       TableColumn<TableRowDataModel, ?> f = tableview.getColumns().get(5);
	       f.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	       f.setText("�ο�");
	}

	public void buttonClick(ActionEvent event) {
		if(premiumbtn.isSelected()) {
			gradeButton = "�����̾�";
			pricee = 20000;
		} else if(udengbtn.isSelected()) {
			gradeButton = "���";
			pricee = 15000;
		} else {
			gradeButton = "�Ϲ�";
			pricee = 10000;
		}
	}
	
	//���õ� ���̺� �� ����
	public void columnDelete(ActionEvent event) {
		tableview.getItems().remove(tableview.getSelectionModel().getSelectedItem());
	}
	
	//�����ϱ� 
	public void reservation(ActionEvent event) throws Exception{
		if(reservationOb.isEmpty()) {
			System.out.println("���ų����� �߰����ּ���.");
			return;
		}
		System.out.println("������������ �Ѿ�ϴ�.");
		//���� ȭ�� �ݱ�
		Stage clo = (Stage)reservationbtn.getScene().getWindow();
		clo.close();
		
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/PaymentPage.fxml"));
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothi.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicBold.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicExtraBold.ttf"), 14);
		
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		stage.setTitle("����");
		stage.setScene(scene);
		stage.show();
	}
	
	//���� ���ų��� ��ȸ
	public void preReservationLookup(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Appointment_Lookup.fxml"));
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothi.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicBold.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicExtraBold.ttf"), 14);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		Stage stage = new Stage();
		
		//���ų��� â�� ���� �� 
		stage.setOnCloseRequest((e)->{
			//������ ������ �ִ� ���� �ʱ�ȭ
			list.clear();
			//���� ��� �� ���� �������� db�� ������
			for(TableRowDataModel ddd : Appointment_Lookup.data) {
				list.add(ddd);
			}
			dao.cancle(list);
		});
		stage.setTitle("���ų���");
		stage.setScene(scene);
		stage.show();
	}
	
}