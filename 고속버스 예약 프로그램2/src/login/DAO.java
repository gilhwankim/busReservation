package login;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DbList;
import reservation.TableRowDataModel;

public class DAO {
	
   private static final String ID = "root";
   private static final String PW = "1234";
   private static final String URL = "jdbc:mysql://localhost:3306/busdb";
   public Connection conn;
   public static Login loginn = new Login();
   
   public static DAO instance;
   
   public DAO() {
      try {
            Class.forName("com.mysql.jdbc.Driver"); 
            conn = DriverManager.getConnection(URL, ID, PW);
            System.out.println("드라이버 로딩 성공!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("드라이버 로드 실패!!");
        }
   }
    //dao가 비어있는 경우에만 새로 생성한다.
    public static DAO getinstance() {
          if(instance == null) {
             instance = new DAO();
          }
          return instance;
    }
    
    //회원 정보 저장
    public void insertGuestInfo(String id, String password, String name, String phoneNum) {
    	String sql = "insert into guestInfo values(?,?,?,?);";
        PreparedStatement pstmt = null;
         try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, id);  
             pstmt.setString(2, password);  
             pstmt.setString(3, name);  
             pstmt.setString(4, phoneNum);  
             pstmt.executeUpdate();
             System.out.println("데이터 삽입 성공!");
         } catch (Exception e) {
        	 System.out.println("데이터 삽입 실패!");
         }
    }
    
	//로그인 id, password 검증
	public int GoLogin(String id, String password) {
		String sql = "select id,password from guestinfo where id = ? and password = ?;";
//		String sql = "select * from guestinfo where guestid = ? and guestpassword = ?;";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			ResultSet resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				if(resultSet.getString("id").equals(id)&&resultSet.getString("password").equals(password)) {
					return 1; //아이디 패스워드 일치
				}else if(!resultSet.getString("id").equals(id)||!resultSet.getString("password").equals(password)) {
					return 0;//아이디 또는 패스워드 불일치
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return -1;// 아이디 또는 패스워드 불일치
	}
	
//	//회원가입 id 검증
	public String alreadyId(String id) throws Exception{
		String sql = "select id from guestInfo where id = ?;";
		PreparedStatement pstmt = null;
		String idd = "";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet resultSet = pstmt.executeQuery();
			
		if(resultSet.next()) {
			idd = resultSet.getString("id");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		//DB에 같은 아이디 존재하지 않으면 "" 리턴.
		return idd;
	}
	
	public void saveReservationList(List<TableRowDataModel> list) {
	   	String sql = "insert into reservationList values(?,?);";
        PreparedStatement pstmt = null;
         try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, loginn.getId());  
             pstmt.setObject(2, list);  
             pstmt.executeUpdate();
             System.out.println("데이터 삽입 성공!");
         } catch (Exception e) {
        	 e.printStackTrace();
        	 System.out.println("데이터 삽입 실패!");
         }
	}
	
	public List<DbList> loadReservationList() {
			      String sql = "select list from reservationList where id = "+loginn.getId()+";";
			       PreparedStatement pstmt = null;
			       List<DbList> listt = new ArrayList<DbList>();
			       
			          try {
			             pstmt = conn.prepareStatement(sql);
			               ResultSet rs = pstmt.executeQuery();
			               
			               while(rs.next()) {
			            	   DbList dl = new DbList();
			            	   //리스트를 바이트로 받아와 다시 배열타입으로 변경해 받는다.
			            	   ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes("list")));
			            	   dl.setDbList((List<TableRowDataModel>)ois.readObject());
			            	   listt.add(dl);
			               }
			               System.out.println("데이터 로드 성공!");
			               return listt;
			           } catch (Exception e) {            
			              System.out.println("데이터 로드 실패!");
			              e.printStackTrace();
			              return null;
			           } 
			   }
	
	
	public void cancle(List<TableRowDataModel> list) {
		 String sql = "delete from reservationList where id = ?;";
	       PreparedStatement pstmt = null;
	       try {
	             pstmt = conn.prepareStatement(sql);
	             pstmt.setString(1, loginn.getId());
	             pstmt.executeUpdate();
	             System.out.println("데이터 삭제 성공!");
	             
	             for(TableRowDataModel tr1 : list) {
	            	 System.out.println("!!!!!삭제 성공 후 : " + tr1.getDate());
	             }
	         } catch (Exception e) {
	        	 e.printStackTrace();
	        	 System.out.println("데이터 삭제 실패!");
	         }
	       //예매 취소 후에도 남은 예매내역이 있을 경우 다시 db에 저장
	    if(list.size()!=0) {
	    String sql2 = "insert into reservationList values(?,?);";
	    PreparedStatement pstmt2 = null;
        try {
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, loginn.getId());  
            pstmt2.setObject(2, list);  
            pstmt2.executeUpdate();
            System.out.println("데이터 삽입 성공!");
            for(TableRowDataModel tr1 : list) {
           	 System.out.println("!!!!!삽입 성공 후 : " + tr1.getDate());
            }
        } catch (Exception e) {
       	 e.printStackTrace();
       	 System.out.println("데이터 삽입 실패!");
        }
	       }
        
	}
}
