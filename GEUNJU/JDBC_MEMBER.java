package DB2022TEAM03.GEUNJU;
import java.sql.*;
import java.util.Scanner;

public class JDBC_MEMBER {
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost/DB2022TEAM03";
	static final String USER = "db2022team03";
	static final String PASS = "db2022team03";
	
	static PreparedStatement pstmt = null;
	static ResultSet rset = null;
	static String str = null;
	static ResultSetMetaData rsmd = null;
	static int columnCnt = 0;
	
	static String ID,PW;
	static String GYMname,GYMid;

	static Scanner scan = new Scanner(System.in);
	
	public static void MENU() {
		System.out.println("======================================");
		System.out.println("1. 헬스장 추천받기"); 
		System.out.println("2. 헬스장 검색하기");
		System.out.println("3. 헬스장 등록하기");
		System.out.println("4. 헬스장 트레이너 확인하기");
		System.out.println("5. 소속헬스장 트레이너 확인하기");
		System.out.println("6. 담당트레이너 등록하기");
		System.out.println("7. 회원권 등록하기");
		System.out.println("8. 회원정보 확인하기");
		System.out.println("0. 로그아웃 및 프로그램 종료");
		System.out.println("======================================");
		System.out.println("Selet number : ");
	}
		
	public static int[] totalLeft(Connection conn) throws SQLException { //전체수업횟수,남은수업횟수 확인(처음부터 접근막으려고->UPDATE SET CASE문도 가능하긴 함
		int total, left;
		str = "SELECT 전체횟수,남은횟수 FROM DB2022_회원 WHERE 회원번호=?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1,ID);
		rset = pstmt.executeQuery();
		
		rset.next();
		total=rset.getInt(1);
		left=rset.getInt(2);
		
		return new int[] {total,left};
	}
	
	public static void recommendGYM(Connection conn) throws SQLException {
		str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격 WHERE 지역 IN (SELECT 지역 FROM db2022_회원 WHERE 회원번호=?)";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();
		
		if(!rset.isBeforeFirst()) System.out.println("가까운 헬스장을 찾지 못했습니다."); 
		else {
			System.out.println("=================헬스장추천=================");
			for(int i=1;i<=columnCnt;i++) System.out.print(rsmd.getColumnName(i)+" ");
			System.out.print("\n-----------------------------------------\n");
			while(rset.next()) {
				for(int i=1;i<=6;i++) { 
					System.out.print(rset.getString(i)+" ");
				}
				System.out.println();
			}
		}
	}
	
	public static void searchGYM(Connection conn) throws SQLException {
		System.out.println("검색을 원하는 지역을 입력해주세요. : ");
		String location = scan.next();
		
		str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격 WHERE 지역=?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, location);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();
		
		if(!rset.isBeforeFirst()) System.out.println("입력한 지역에서 헬스장을 찾지 못했습니다."); 
		else {
			System.out.println("=================헬스장검색=================");
			for(int i=1;i<=columnCnt;i++) System.out.print(rsmd.getColumnName(i)+" ");
			System.out.print("\n-----------------------------------------\n");
			while(rset.next()) {
				for(int i=1;i<=6;i++) {
					System.out.print(rset.getString(i)+" ");
				}
				System.out.println();
			}
		}
	}
	
	public static void enrollGYM(Connection conn) throws SQLException {
		//현재 회원의 남은수업횟수가 0인지 확인
		int check[] = totalLeft(conn);
		
		if(check[1]==0) {
			System.out.println("헬스장 이름을 입력해주세요. : ");
			scan.nextLine();//버퍼비우기
			GYMname = scan.nextLine();
			//헬스장 이름으로 번호찾기
			str = "SELECT 헬스장번호 FROM DB2022_헬스장 WHERE 이름=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, GYMname);
			rset = pstmt.executeQuery();
			
			if(!rset.isBeforeFirst()) System.out.println("입력한 이름을 가진 헬스장을 찾지 못했습니다."); 
			else {
				rset.next();
				GYMid = rset.getString(1);						
				
				//헬스장 등록하기
				//str = "UPDATE DB2022_회원 SET 소속헬스장=CASE WHEN 남은횟수==0 THEN ? END WHERE 회원번호=?";
				str = "UPDATE DB2022_회원 SET 소속헬스장=? WHERE 회원번호=?";
				pstmt = conn.prepareStatement(str);
				pstmt.setString(1, GYMid);
				pstmt.setString(2, ID);
				pstmt.executeUpdate();
				//헬스장 : 전체회원수 코드 증가도 여기서 ..? 
				System.out.println(GYMname+"에 회원으로 등록되었습니다.");
			}
		} else {
			System.out.println("아직 수업횟수가 남아서 헬스장을 변경할 수 없습니다. (남은 수업횟수:"+check[1]+")");
		}
	}
	
	public static void enrollMembership(Connection conn) throws SQLException {
		//현재 회원의 남은수업횟수가 0인지 확인
		int check[] = totalLeft(conn);
		
		if(check[1]==0) { 
			System.out.println("원하는 회원권을 숫자로 입력해주세요. : ");
			int membership = scan.nextInt();
			
			
			//str = "UPDATE DB2022_회원 SET (현재회원권, 남은횟수, 전체횟수) = CASE WHEN 남은횟수==0 THEN (?, ?, ?) END WHERE 회원번호=?";
			str = "UPDATE DB2022_회원 SET 현재회원권=?,전체횟수=?,남은횟수=? WHERE 회원번호=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, membership+"회권");
			pstmt.setInt(2, membership+check[0]);
			pstmt.setInt(3, membership);
			pstmt.setString(4, ID);
			pstmt.executeUpdate();
			System.out.println(membership+"회원권을 등록했습니다.");
		} else {
			System.out.println("아직 수업횟수가 남아서 회원권을 변경할 수 없습니다. (남은 수업횟수:"+check[1]+")");
		}
	}
	
	public static void showTrainer(Connection conn) throws SQLException {
		//그냥 소속헬스장 트레이너보여주기
		str = "SELECT 이름,담당회원수 FROM DB2022_트레이너 WHERE 헬스장번호 IN (SELECT 소속헬스장 FROM DB2022_회원 WHERE 회원번호=?)";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();	
		
		//System.out.println("회원정보를 불러오는데 실패했습니다."); 
		System.out.println("=================트레이너목록=================");
		if(!rset.isBeforeFirst()) System.out.println("등록된 트레이너가 없습니다.");
		else {
			for(int i=1;i<=columnCnt;i++) System.out.print(rsmd.getColumnName(i)+" ");
			System.out.print("\n-----------------------------------------\n");
			while(rset.next()) {
				for(int i=1;i<=2;i++) {
					System.out.print(rset.getString(i)+" ");
				}
				System.out.println();
			}	
		}
	}
	
	public static void searchTrainer(Connection conn) throws SQLException {
		//헬스장 이름 검색해서 찾기
		System.out.println("헬스장 이름을 입력해주세요. : ");
		scan.nextLine();//버퍼비우기
		GYMname = scan.nextLine();
		
		//헬스장 이름으로 번호찾기
		str = "SELECT 헬스장번호 FROM DB2022_헬스장 WHERE 이름=?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, GYMname);
		rset = pstmt.executeQuery();
		
		if(!rset.isBeforeFirst()) System.out.println("입력한 이름을 가진 헬스장을 찾지 못했습니다."); 
		else {
			rset.next();
			String GYMid = rset.getString(1);
			
			//헬스장 번호로 트레이너 보여주기 
			str = "SELECT 이름, 담당회원수 FROM DB2022_트레이너 WHERE 헬스장번호=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, GYMid);
			rset = pstmt.executeQuery();
			
			rsmd = rset.getMetaData();
			columnCnt = rsmd.getColumnCount();	
			
			System.out.println("=================트레이너 in "+GYMname+"=================");
			
			if(!rset.isBeforeFirst()) System.out.println("아직 트레이너가 등록되지 않았습니다.");
			else {
				for(int i=1;i<=columnCnt;i++) System.out.print(rsmd.getColumnName(i)+" ");
				System.out.print("\n-----------------------------------------\n");
				while(rset.next()) {
					for(int i=1;i<=2;i++) {
						System.out.print(rset.getString(i)+" ");
					}
					System.out.println();
				}	
			}
		}
	}
	
	public static void enrollTrainer(Connection conn) throws SQLException {
		//현재 회원의 남은수업횟수가 0인지 확인
		int check[] = totalLeft(conn);
				
		if(check[1]==0) {
			System.out.println("트레이너 이름을 입력해주세요. : ");
			String Tname = scan.next();
			
			//트레이너 이름으로 번호찾기
			str = "SELECT 강사번호 FROM DB2022_트레이너 WHERE 이름=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, Tname);
			rset = pstmt.executeQuery();
			
			if(!rset.isBeforeFirst()) System.out.println("입력한 이름을 가진 트레이너를 찾지 못했습니다."); 
			else {
				rset.next();
				String Tid = rset.getString(1);						
				
				//담당 트레이너 등록하기
				//str = "UPDATE DB2022_회원 SET 담당트레이너=CASE WHEN 남은횟수==0 THEN ? END WHERE 회원번호=?";
				str = "UPDATE DB2022_회원 SET 담당트레이너=? WHERE 회원번호=?";
				pstmt = conn.prepareStatement(str);
				pstmt.setString(1, Tid);
				pstmt.setString(2, ID);
				pstmt.executeUpdate();
				//트레이너 : 담당회원수 추가하는 거, 헬스장 : 전체회원수 코드 필요하나? count로 할 건데? 필요하면 transaction으로 같이 묶어야 함
				System.out.println("담당트레이너("+Tname+")가 등록되었습니다.");
			}
		} else {
			System.out.println("아직 수업횟수가 남아서 담당트레이너를 변경할 수 없습니다. (남은 수업횟수:"+check[1]+")");
		} 
		
	}
	
	public static void myPage(Connection conn) throws SQLException { 
		str = "SELECT G.이름 as 헬스장, M.이름, M.지역, M.전체횟수, M.남은횟수,T.이름 as 트레이너, M.현재회원권 FROM db2022_회원 as M, db2022_트레이너 as T, db2022_헬스장 as G WHERE M.담당트레이너=T.강사번호 and M.소속헬스장=G.헬스장번호 and M.회원번호 = ?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();
		

		System.out.println("=================내정보=================");
		for(int i=1;i<=columnCnt;i++) System.out.print(rsmd.getColumnName(i)+" "); //select에서 as문 쓴 게 적용 안 되는 듯?
		System.out.print("\n-----------------------------------------\n");
		if(!rset.isBeforeFirst()) System.out.println("회원정보를 불러오는데 실패했습니다.");
		else {
			while(rset.next()) {
				for(int i=1;i<=7;i++) {
					System.out.print(rset.getString(i)+" ");
				}
				System.out.println();
			}		
		}				
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		//connectDB
		try (
			Connection conn = DriverManager.getConnection(URL,USER,PASS);
			Statement stmt = conn.createStatement();
				
		)
		{	
			//login
			System.out.println("ID:");
			ID = scan.nextLine();
			System.out.println("PW:");
			PW = scan.nextLine();
			System.out.println("로그인 성공");
			
			boolean working = true;			
			while(working) {
				MENU();
				int select = scan.nextInt();
				switch(select){
					case 0: //프로그램 종료
						working=false; 
						System.out.println("로그아웃");
						break; 
					
					case 1: //같은 지역에 있는 헬스장 추천받기
						recommendGYM(conn);
						break;
						
					case 2: //지역 입력받아 헬스장 검색 결과보여주기
						searchGYM(conn);
						break;
						
					case 3: //헬스장 이름 입력받아 등록하기 -> case1,2번에서 클릭하면 넘어가도록 해도 괜찮을 듯
						enrollGYM(conn);						
						break;
					
					case 4: //헬스장 이름 입력받아 트레이너 보여주기
						searchTrainer(conn);
						break;
						
					case 5: //등록한 헬스장 트레이너 확인하기
						showTrainer(conn);
						break;
						
					case 6: //트레이너 이름으로 담당트레이너 등록하기 (동명이인 트레이너가 없다고 가정)
						enrollTrainer(conn);
						break;
						
					case 7: //회원권 등록하기
						enrollMembership(conn);
						break;
						
					case 8: //회원정보 확인하기
						myPage(conn);
						break;
						

				}
			}
		}
		
		catch (SQLException sqle) {
			System.out.println("SQLException:"+sqle);
		}
		
	}
}
