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
	
	private @FXML TableView<TableRowDataModel> paymenttableview; // ���ų��� ���̺� ��
	private @FXML TableColumn<TableRowDataModel,String> date; // ��¥
	private @FXML TableColumn<TableRowDataModel,String> startP; // �����
	private @FXML TableColumn<TableRowDataModel,String> destiP; // ������
	private @FXML TableColumn<TableRowDataModel,String> grade; // ���
	private @FXML TableColumn<TableRowDataModel,Integer> price; // ����
	private @FXML TableColumn<TableRowDataModel,String> ticket; // �ο�
	private @FXML TextField txt_total; // �� �����ݾ�
	private @FXML ComboBox<String> companybtn; // ī���
	private @FXML TextField txt_cardnumber; // ī���ȣ
	private @FXML ComboBox<String> instalment_periodbtn; // �ҺαⰣ
	private @FXML PasswordField txt_cardpassword;// ī�� ��й�ȣ
	private @FXML Button previousbtn; // ����ȭ��
	private @FXML Button paymentbtn; // �����Ϸ�
	private int totalPrice =0;
	
	private DecimalFormat df = new DecimalFormat("###,###");
	private DAO dao = DAO.getinstance();
	private Pop pop = new Pop();
	
	ReservationController rc = new ReservationController();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("����");
		
		tableSetting();
		//��Ƴ��� ���� ������ �޾ƿ´�
		paymenttableview.setItems(rc.reservationOb);
//		paymenttableview.refresh();
		
		for(int i=0; i<paymenttableview.getItems().size(); i++) {
			
			totalPrice += (price.getCellData(i)*Integer.parseInt(ticket.getCellData(i)));
			
		}		
		txt_total.setText(df.format(totalPrice));
		//�Է�����
		keyLocked(txt_cardnumber,10);
		keyLocked(txt_cardpassword,4);
	}
	
	//���̺� �� �÷� ���� ����
	public void tableSetting() {
	      TableColumn<TableRowDataModel, ?> a = paymenttableview.getColumns().get(0);
	       a.setCellValueFactory(new PropertyValueFactory<>("date"));
	       a.setText("����Ͻ�");
	       
	       TableColumn<TableRowDataModel, ?> b = paymenttableview.getColumns().get(1);
	       b.setCellValueFactory(new PropertyValueFactory<>("start"));
	       b.setText("�����");
	       
	       TableColumn<TableRowDataModel, ?> c = paymenttableview.getColumns().get(2);
	       c.setCellValueFactory(new PropertyValueFactory<>("desti"));
	       c.setText("������");
	       
	       TableColumn<TableRowDataModel, ?> d = paymenttableview.getColumns().get(3);
	       d.setCellValueFactory(new PropertyValueFactory<>("grade"));
	       d.setText("���");
	       
	       TableColumn<TableRowDataModel, Integer> e = (TableColumn<TableRowDataModel, Integer>) paymenttableview.getColumns().get(4);
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
	       
	       TableColumn<TableRowDataModel, ?> f = paymenttableview.getColumns().get(5);
	       f.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	       f.setText("�ο�");
	}
	
	// �����Ϸ�
	public void ConfirmReservation(ActionEvent event) {
		
		if(companybtn.getSelectionModel().getSelectedItem()==null) {
			System.out.println("ī��縦 �������ּ���");
			return;
		} else if(txt_cardnumber.getText().equals("")) {
			System.out.println("ī���ȣ�� �Է����ּ���");
			return;
		} else if(instalment_periodbtn.getSelectionModel().getSelectedItem()==null) {
			System.out.println("�ҺαⰣ�� �������ּ���");
			return;
		} else if(txt_cardpassword.getText().equals("")) {
			System.out.println("ī�� ��й�ȣ�� �Է����ּ���");
			return;
		}
		//db�� ���� ����Ʈ
		List<TableRowDataModel> list = new ArrayList<>();
		for(TableRowDataModel td : rc.reservationOb) {
			list.add(td);
		}
		dao.saveReservationList(list);
		
		pop.popupMsg("���Ű� �Ϸ�Ǿ����ϴ�.");
		Stage clo = (Stage)paymentbtn.getScene().getWindow();
		clo.close();
	}
	
	//�ؽ�Ʈ �ʵ� �Է� ���� ����
	public void keyLocked(TextField textField,int length) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			
			//�ؽ�Ʈ �ʵ� ���� ����
			if(newValue.length()>length) {
				textField.setText(newValue.substring(0,length));
			}
			//�ؽ�Ʈ�ʵ� ���ڸ� �Է°����ϵ��� ����.
			if (!newValue.matches("\\d*")) {
	        	textField.setText(newValue.replaceAll("[^\\d]", ""));
	        }
	        
	    });
	}
	
//	//����ȭ��(���� ȭ��)	
	public void preStage(ActionEvent event) throws Exception{
		
		Stage clo = (Stage)previousbtn.getScene().getWindow();
		clo.close();
		
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reservation.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		Stage stage = new Stage();
		stage.setTitle("����");
		stage.setScene(scene);
		stage.show();
	}
}