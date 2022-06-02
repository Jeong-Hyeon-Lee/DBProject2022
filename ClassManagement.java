package DB2022TEAM03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;


/* 개선점 1: 과거의 시간에 수업 예약을 하는 경우를 막아야 함 
 * 개선점 2: 수업 취소가 불가능한 경우 불가능하다고 알려줘야 함
 * */


public class ClassManagement{

	//JDBC driver name과 database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03"; 
	
	//Database user와 password
	static final String  USER = "root";
	static final String PASSWORD="sharon98";
	
	//Connection 객체
	static Connection conn;
	
	// 메뉴
	static int menu;
	
	
	public static void showClasses(String memberId, int menu) throws SQLException {
				
		// SQL 문장을 실행하기 위한 PreparedStatement 객체와 결과를 저장하는 ResultSet 객체 생성
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1=null;
		ResultSet rs2=null;

		// 쿼리1: 현재 시각(DateTime)을 출력
		String query1 = "SELECT NOW()";
		
		// 쿼리 2: 선택한 메뉴에 따라 수업 목록을 출력
		String query2 = null;
		/*
		 * 수업 관리 메뉴  => showClasses 메소드의 쿼리
		 * 1. 수업 예약하기          => 쿼리: X
		 * 2. 수업 취소하기          => 쿼리: 취소 가능한 수업 출력
		 * 3. 수업 예약 내역 보기  => 쿼리: 예약확인중/예약완료 수업 출력
		 * 4. 수업 완료 내역 보기  => 쿼리: 완료/불참/취소 수업 출력
		 */
		switch(menu) {
		case 2: 
			query2 = "SELECT 수업시간, 수업진행현황 " + "FROM DB2022_수업 " +
					"WHERE 회원번호 = ? AND 수업진행현황 IN ('예약확인중', '예약완료') AND 수업시간 < DATE_ADD(NOW(), INTERVAL 5 HOUR)";
			break;
		case 3:
			query2 = "SELECT 수업시간, 수업진행현황 " + "FROM DB2022_수업 " +
					"WHERE 회원번호 = ? AND 수업진행현황 IN ('예약확인중', '예약완료')";
			break;
		case 4:
			query2 = "SELECT 수업시간, 수업진행현황 " + "FROM DB2022_수업 " +
					"WHERE 회원번호 = ? AND 수업진행현황 IN ('완료', '불참', '취소')";
			break;
		default:
			System.out.println("수업 목록이 없습니다.");
		}
		
		//conn.setAutoCommit(false);

		try {		
			// DB query string preparation
			pStmt1 = conn.prepareStatement(query1);
			pStmt2 = conn.prepareStatement(query2);
			
			pStmt2.setString(1, memberId);  // 회원번호

			// 쿼리 실행
			rs1 = pStmt1.executeQuery();
			rs2 = pStmt2.executeQuery();
			
			/*
			// 쿼리 실행 결과 출력
			// 쿼리1: 현재 시각(DateTime)을 출력
			System.out.println("...........................................................................................................");
			while(rs1.next()) {
				LocalDateTime currentDateTime = rs1.getTimestamp(1).toLocalDateTime();
				System.out.println("현재시각: " + currentDateTime);
				System.out.println("회원 ID: " + memberId);  // 회원 정보 출력
			}
			//System.out.println("...........................................................................................................");
			 */
			
			
			// 쿼리 2: 선택한 메뉴에 따라 수업 목록을 출력
			System.out.println("[수업 목록]");
			System.out.println("----------------------------------------");
			while(rs2.next()) {
				LocalDateTime classDatetime = rs2.getTimestamp(1).toLocalDateTime();
				String classState = rs2.getString(2);
				System.out.println("수업시간: " + classDatetime + " 수업진행현황: " + classState);
			}
			System.out.println("----------------------------------------");
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle);
		}
	}
	
	
	public static void reserveClass(String memberId, String classDateTime) throws SQLException {
		String trainerId = null;  // 회원의 담당 트레이너 ID

		// SQL 문장을 실행하기 위한 PreparedStatement 객체와 결과를 저장하는 ResultSet 객체 생성
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1=null;
		
		// 회원의 트레이너를 구하는 SQL 쿼리
		String query = "SELECT DISTINCT 강사번호 FROM DB2022_수업 WHERE 회원번호 = ?";
		
		// 수업을 예약하는 SQL 문장
		String update_reserve = "INSERT INTO DB2022_수업 " + "VALUES(?, ?, ?, ?)";
		
		
		try {		
			// DB query string preparation
			pStmt1 = conn.prepareStatement(query);
			pStmt1.setString(1, memberId);
			
			rs1 = pStmt1.executeQuery();
			
			while(rs1.next()) {
				trainerId = rs1.getString(1);
				//System.out.println("담당 트레이너 ID: " + trainerId);
			}
		
			
			pStmt2 = conn.prepareStatement(update_reserve);

			pStmt2.setString(1, memberId);
			pStmt2.setString(2, trainerId);
			pStmt2.setTimestamp(3, Timestamp.valueOf(classDateTime));  // 수업시간
			pStmt2.setString(4, "예약확인중"); 
						
			// DB 업데이트
			pStmt2.executeUpdate();
	
			showClasses(memberId, 3);  // 업데이트 후 예약확인중/예약완료 수업 출력

		}
		catch (SQLException sqle) {
			System.out.println("SQLException_: " + sqle);
		}
	}
	
	
	public static void cancelClass(String memberId, String classDateTime) throws SQLException {
				
		// SQL 문장을 실행하기 위한 PreparedStatement 객체와 결과를 저장하는 ResultSet 객체 생성
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		
		// 수업을 취소하는 SQL update 문장들
		// 1) 예약확인중인 수업 => 레코드 삭제
		String update_requested = "DELETE FROM DB2022_수업 " + "WHERE 회원번호 = ? AND 수업시간  = ? AND 수업진행현황 = '예약확인중' AND 수업시간 > DATE_ADD(NOW(), INTERVAL 5 HOUR)";
		// 2) 예약완료된 수업 => '취소'로 상태 바꿈
		String update_reserved = "UPDATE DB2022_수업 " + "SET 수업진행현황 = '취소' " + "WHERE 회원번호 = ? AND 수업시간 = ? AND 수업진행현황 = '예약완료' AND 수업시간 > DATE_ADD(NOW(), INTERVAL 5 HOUR)";
		
		try {		
			// DB query string preparation
			pStmt1 = conn.prepareStatement(update_requested);
			pStmt2 = conn.prepareStatement(update_reserved);
			
			pStmt1.setString(1, memberId);
			pStmt1.setTimestamp(2, Timestamp.valueOf(classDateTime));  // 수업시간
			pStmt2.setString(1, memberId);  
			pStmt2.setTimestamp(2, Timestamp.valueOf(classDateTime));  // 수업시간
						
			// DB 업데이트
			pStmt1.executeUpdate();
			pStmt2.executeUpdate();
			
			showClasses(memberId, 3);  // 업데이트 후 예약확인중/예약완료 수업 출력

		}
		catch (SQLException sqle) {
			System.out.println("SQLException_: " + sqle);
		}
	}
	
	
	
	public static void printMenu() {
		/*
		 * 수업 관리 메뉴  => showClasses 메소드의 쿼리
		 * 1. 수업 예약하기          => 쿼리: X
		 * 2. 수업 취소하기          => 쿼리: 취소 가능한 수업 출력
		 * 3. 수업 예약 내역 보기  => 쿼리: 예약확인중/예약완료 수업 출력
		 * 4. 수업 완료 내역 보기  => 쿼리: 완료/불참/취소 수업 출력
		 */
		System.out.println("<메뉴>"); 
		System.out.println("(1) 수업 예약하기 "); 
		System.out.println("(2) 수업 취소하기");
		System.out.println("(3) 수업 예약 내역 보기");
		System.out.println("(4) 수업 완료 내역 보기");
		System.out.println("(0) : 나가기");
		System.out.println("...........................................................................................................");
	}
		
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int menu = 1;
		String memberId = "M09560";
		String classDatetime = null;
		
		// DB에 접속
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);			
		} catch(SQLException sqle) {
			System.out.println("SQLException_Connect: " + sqle);
		}
		
		
		// Menu 선택
		while(menu != 0) {
			printMenu();
			System.out.print("=> 원하시는 작업을 선택하세요: ");
			try {
				menu = scanner.nextInt();
				
			} catch(InputMismatchException e) {
				System.out.println("**정수를 입력해 주세요.**");
				System.out.println("...........................................................................................................");
				scanner.next();
				continue;
			}
			switch(menu) {
			case 0: 
				System.out.println("종료합니다.");
				break;
			case 1:
				System.out.println("수업을 예약합니다.");
				System.out.println("...........................................................................................................");
				try {
					showClasses(memberId, 3);  // 업데이트 전 예약확인중/예약완료 수업 출력
					scanner.nextLine();
					System.out.print("원하시는 날짜와 시간을 입력하세요.\n  ex) 2000-06-15 20:00:00\n: ");
					classDatetime = scanner.nextLine();
					reserveClass(memberId, classDatetime);
				} catch(SQLException sqle) {
					System.out.println("SQLException_Execute: " + sqle);
				}
				break;
				
			case 2:
				System.out.println("수업을 취소합니다.");
				System.out.println("...........................................................................................................");
				try {
					showClasses(memberId, 3);  // 업데이트 전 예약확인중/예약완료 수업 출력
					scanner.nextLine();
					System.out.print("취소하실 수업의 날짜와 시간을 입력하세요.\n  ex) 2000-06-15 20:00:00\n: ");
					classDatetime = scanner.nextLine();
					cancelClass(memberId, classDatetime);
				} catch(SQLException sqle) {
					System.out.println("SQLException_Execute: " + sqle);
				}
				break;
				
			case 3:				
			case 4:
				System.out.println("수업 내역을 확인합니다.");
				System.out.println("...........................................................................................................");
				try {
					showClasses(memberId, menu);
				} catch(SQLException sqle) {
					System.out.println("SQLException_Execute: " + sqle);
				}
				break;
				
			default:
				System.out.println("**1~4의 정수를 입력해 주세요.**");
				System.out.println("...........................................................................................................");
				break;
			}
			System.out.println();

		}
		scanner.close();
	}
}