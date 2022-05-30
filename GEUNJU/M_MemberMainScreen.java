package DB2022TEAM03.GEUNJU;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JFrame;

public class M_MemberMainScreen extends JFrame {
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
	
	public static void main(String[] args) {
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
						//recommendGYM(conn);
						break;
						
					case 2: //지역 입력받아 헬스장 검색 결과보여주기
						//searchGYM(conn);
						break;
						
					case 3: //헬스장 이름 입력받아 등록하기 -> case1,2번에서 클릭하면 넘어가도록 해도 괜찮을 듯
						//enrollGYM(conn);						
						break;
					
					case 4: //헬스장 이름 입력받아 트레이너 보여주기
						//searchTrainer(conn);
						break;
						
					case 5: //등록한 헬스장 트레이너 확인하기
						//showTrainer(conn);
						break;
						
					case 6: //트레이너 이름으로 담당트레이너 등록하기 (동명이인 트레이너가 없다고 가정)
						//enrollTrainer(conn);
						break;
						
					case 7: //회원권 등록하기
						//enrollMembership(conn);
						break;
						
					case 8: //회원정보 확인하기
						//myPage(conn);
						break;
				}
			}
		}
		
		catch (SQLException sqle) {
			System.out.println("SQLException:"+sqle);
		}
		
	}
}
