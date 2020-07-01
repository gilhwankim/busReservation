package data;

import java.util.ArrayList;
import java.util.List;

public class TerminalList {
	
	private String[] seoul = new String[] {
			"동서울","상봉","서울경부","센트럴시티"
			};
	
	private String[] incheonGyeonggi = new String[] {
			"광명","구리","부천","수원","안산","인천","인천공항","평택"
			};
	
	private String[] Gangwon= new String[] {
			"강릉","동해","삼척","속초","양양","원주"
			};
	
	private String[] DaejeonChungnam= new String[] {
			"청양","태안","홍성","공주","당진","논산"
			};
	
	private String[] Chungbuk= new String[] {
			"제천","청주","충주","청주공항"
			};
	
	private String[] GwangjuJeonnam= new String[] {
			"강진","나주","광주","목포","순천","여수"
			};
	
	private String[] Jeonbuk= new String[] {
			"전주","익산","순창","군산","김제"
			};
	
	private String[] BusanGyeongnam= new String[] {
			"김해","마산","부산","밀양","울산","창원"
			};
	
	private String[] DaeguGyeongbuk= new String[] {
			"경북","구미","김천","대구","동대구","포항"
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