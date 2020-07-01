package data;

import java.util.ArrayList;
import java.util.List;

public class TerminalList {
	
	private String[] seoul = new String[] {
			"������","���","������","��Ʈ����Ƽ"
			};
	
	private String[] incheonGyeonggi = new String[] {
			"����","����","��õ","����","�Ȼ�","��õ","��õ����","����"
			};
	
	private String[] Gangwon= new String[] {
			"����","����","��ô","����","���","����"
			};
	
	private String[] DaejeonChungnam= new String[] {
			"û��","�¾�","ȫ��","����","����","���"
			};
	
	private String[] Chungbuk= new String[] {
			"��õ","û��","����","û�ְ���"
			};
	
	private String[] GwangjuJeonnam= new String[] {
			"����","����","����","����","��õ","����"
			};
	
	private String[] Jeonbuk= new String[] {
			"����","�ͻ�","��â","����","����"
			};
	
	private String[] BusanGyeongnam= new String[] {
			"����","����","�λ�","�о�","���","â��"
			};
	
	private String[] DaeguGyeongbuk= new String[] {
			"���","����","��õ","�뱸","���뱸","����"
			};
	
	public TerminalList() {
		String str = "seoul";
		List<String[]> list = new ArrayList<>();
		terminalList(list);
		for(String[] strr : list) {
			for(int i=0; i<strr.length; i++ ) {
				System.out.println("@@@@"+strr[i]);
			}
		}
	}
	
	public List<String[]> terminalList(List<String[]> list) {
		list.add(seoul);
		list.add(incheonGyeonggi);
		
		return list;
	}

	public String[] getSeoul() {
		return seoul;
	}

	public String[] getIncheonGyeonggi() {
		return incheonGyeonggi;
	}

	public String[] getGangwon() {
		return Gangwon;
	}

	public String[] getDaejeonChungnam() {
		return DaejeonChungnam;
	}

	public String[] getChungbuk() {
		return Chungbuk;
	}

	public String[] getGwangjuJeonnam() {
		return GwangjuJeonnam;
	}

	public String[] getJeonbuk() {
		return Jeonbuk;
	}

	public String[] getBusanGyeongnam() {
		return BusanGyeongnam;
	}

	public String[] getDaeguGyeongbuk() {
		return DaeguGyeongbuk;
	}

	
}