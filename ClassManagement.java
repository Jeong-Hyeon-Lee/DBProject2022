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


/* ������ 1: ������ �ð��� ���� ������ �ϴ� ��츦 ���ƾ� �� 
 * ������ 2: ���� ��Ұ� �Ұ����� ��� �Ұ����ϴٰ� �˷���� ��
 * */


public class ClassManagement{

	//JDBC driver name�� database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03"; 
	
	//Database user�� password
	static final String  USER = "root";
	static final String PASSWORD="sharon98";
	
	//Connection ��ü
	static Connection conn;
	
	// �޴�
	static int menu;
	
	
	public static void showClasses(String memberId, int menu) throws SQLException {
				
		// SQL ������ �����ϱ� ���� PreparedStatement ��ü�� ����� �����ϴ� ResultSet ��ü ����
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1=null;
		ResultSet rs2=null;

		// ����1: ���� �ð�(DateTime)�� ���
		String query1 = "SELECT NOW()";
		
		// ���� 2: ������ �޴��� ���� ���� ����� ���
		String query2 = null;
		/*
		 * ���� ���� �޴�  => showClasses �޼ҵ��� ����
		 * 1. ���� �����ϱ�          => ����: X
		 * 2. ���� ����ϱ�          => ����: ��� ������ ���� ���
		 * 3. ���� ���� ���� ����  => ����: ����Ȯ����/����Ϸ� ���� ���
		 * 4. ���� �Ϸ� ���� ����  => ����: �Ϸ�/����/��� ���� ���
		 */
		switch(menu) {
		case 2: 
			query2 = "SELECT �����ð�, ����������Ȳ " + "FROM DB2022_���� " +
					"WHERE ȸ����ȣ = ? AND ����������Ȳ IN ('����Ȯ����', '����Ϸ�') AND �����ð� < DATE_ADD(NOW(), INTERVAL 5 HOUR)";
			break;
		case 3:
			query2 = "SELECT �����ð�, ����������Ȳ " + "FROM DB2022_���� " +
					"WHERE ȸ����ȣ = ? AND ����������Ȳ IN ('����Ȯ����', '����Ϸ�')";
			break;
		case 4:
			query2 = "SELECT �����ð�, ����������Ȳ " + "FROM DB2022_���� " +
					"WHERE ȸ����ȣ = ? AND ����������Ȳ IN ('�Ϸ�', '����', '���')";
			break;
		default:
			System.out.println("���� ����� �����ϴ�.");
		}
		
		//conn.setAutoCommit(false);

		try {		
			// DB query string preparation
			pStmt1 = conn.prepareStatement(query1);
			pStmt2 = conn.prepareStatement(query2);
			
			pStmt2.setString(1, memberId);  // ȸ����ȣ

			// ���� ����
			rs1 = pStmt1.executeQuery();
			rs2 = pStmt2.executeQuery();
			
			/*
			// ���� ���� ��� ���
			// ����1: ���� �ð�(DateTime)�� ���
			System.out.println("...........................................................................................................");
			while(rs1.next()) {
				LocalDateTime currentDateTime = rs1.getTimestamp(1).toLocalDateTime();
				System.out.println("����ð�: " + currentDateTime);
				System.out.println("ȸ�� ID: " + memberId);  // ȸ�� ���� ���
			}
			//System.out.println("...........................................................................................................");
			 */
			
			
			// ���� 2: ������ �޴��� ���� ���� ����� ���
			System.out.println("[���� ���]");
			System.out.println("----------------------------------------");
			while(rs2.next()) {
				LocalDateTime classDatetime = rs2.getTimestamp(1).toLocalDateTime();
				String classState = rs2.getString(2);
				System.out.println("�����ð�: " + classDatetime + " ����������Ȳ: " + classState);
			}
			System.out.println("----------------------------------------");
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle);
		}
	}
	
	
	public static void reserveClass(String memberId, String classDateTime) throws SQLException {
		String trainerId = null;  // ȸ���� ��� Ʈ���̳� ID

		// SQL ������ �����ϱ� ���� PreparedStatement ��ü�� ����� �����ϴ� ResultSet ��ü ����
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1=null;
		
		// ȸ���� Ʈ���̳ʸ� ���ϴ� SQL ����
		String query = "SELECT DISTINCT �����ȣ FROM DB2022_���� WHERE ȸ����ȣ = ?";
		
		// ������ �����ϴ� SQL ����
		String update_reserve = "INSERT INTO DB2022_���� " + "VALUES(?, ?, ?, ?)";
		
		
		try {		
			// DB query string preparation
			pStmt1 = conn.prepareStatement(query);
			pStmt1.setString(1, memberId);
			
			rs1 = pStmt1.executeQuery();
			
			while(rs1.next()) {
				trainerId = rs1.getString(1);
				//System.out.println("��� Ʈ���̳� ID: " + trainerId);
			}
		
			
			pStmt2 = conn.prepareStatement(update_reserve);

			pStmt2.setString(1, memberId);
			pStmt2.setString(2, trainerId);
			pStmt2.setTimestamp(3, Timestamp.valueOf(classDateTime));  // �����ð�
			pStmt2.setString(4, "����Ȯ����"); 
						
			// DB ������Ʈ
			pStmt2.executeUpdate();
	
			showClasses(memberId, 3);  // ������Ʈ �� ����Ȯ����/����Ϸ� ���� ���

		}
		catch (SQLException sqle) {
			System.out.println("SQLException_: " + sqle);
		}
	}
	
	
	public static void cancelClass(String memberId, String classDateTime) throws SQLException {
				
		// SQL ������ �����ϱ� ���� PreparedStatement ��ü�� ����� �����ϴ� ResultSet ��ü ����
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		
		// ������ ����ϴ� SQL update �����
		// 1) ����Ȯ������ ���� => ���ڵ� ����
		String update_requested = "DELETE FROM DB2022_���� " + "WHERE ȸ����ȣ = ? AND �����ð�  = ? AND ����������Ȳ = '����Ȯ����' AND �����ð� > DATE_ADD(NOW(), INTERVAL 5 HOUR)";
		// 2) ����Ϸ�� ���� => '���'�� ���� �ٲ�
		String update_reserved = "UPDATE DB2022_���� " + "SET ����������Ȳ = '���' " + "WHERE ȸ����ȣ = ? AND �����ð� = ? AND ����������Ȳ = '����Ϸ�' AND �����ð� > DATE_ADD(NOW(), INTERVAL 5 HOUR)";
		
		try {		
			// DB query string preparation
			pStmt1 = conn.prepareStatement(update_requested);
			pStmt2 = conn.prepareStatement(update_reserved);
			
			pStmt1.setString(1, memberId);
			pStmt1.setTimestamp(2, Timestamp.valueOf(classDateTime));  // �����ð�
			pStmt2.setString(1, memberId);  
			pStmt2.setTimestamp(2, Timestamp.valueOf(classDateTime));  // �����ð�
						
			// DB ������Ʈ
			pStmt1.executeUpdate();
			pStmt2.executeUpdate();
			
			showClasses(memberId, 3);  // ������Ʈ �� ����Ȯ����/����Ϸ� ���� ���

		}
		catch (SQLException sqle) {
			System.out.println("SQLException_: " + sqle);
		}
	}
	
	
	
	public static void printMenu() {
		/*
		 * ���� ���� �޴�  => showClasses �޼ҵ��� ����
		 * 1. ���� �����ϱ�          => ����: X
		 * 2. ���� ����ϱ�          => ����: ��� ������ ���� ���
		 * 3. ���� ���� ���� ����  => ����: ����Ȯ����/����Ϸ� ���� ���
		 * 4. ���� �Ϸ� ���� ����  => ����: �Ϸ�/����/��� ���� ���
		 */
		System.out.println("<�޴�>"); 
		System.out.println("(1) ���� �����ϱ� "); 
		System.out.println("(2) ���� ����ϱ�");
		System.out.println("(3) ���� ���� ���� ����");
		System.out.println("(4) ���� �Ϸ� ���� ����");
		System.out.println("(0) : ������");
		System.out.println("...........................................................................................................");
	}
		
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int menu = 1;
		String memberId = "M09560";
		String classDatetime = null;
		
		// DB�� ����
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);			
		} catch(SQLException sqle) {
			System.out.println("SQLException_Connect: " + sqle);
		}
		
		
		// Menu ����
		while(menu != 0) {
			printMenu();
			System.out.print("=> ���Ͻô� �۾��� �����ϼ���: ");
			try {
				menu = scanner.nextInt();
				
			} catch(InputMismatchException e) {
				System.out.println("**������ �Է��� �ּ���.**");
				System.out.println("...........................................................................................................");
				scanner.next();
				continue;
			}
			switch(menu) {
			case 0: 
				System.out.println("�����մϴ�.");
				break;
			case 1:
				System.out.println("������ �����մϴ�.");
				System.out.println("...........................................................................................................");
				try {
					showClasses(memberId, 3);  // ������Ʈ �� ����Ȯ����/����Ϸ� ���� ���
					scanner.nextLine();
					System.out.print("���Ͻô� ��¥�� �ð��� �Է��ϼ���.\n  ex) 2000-06-15 20:00:00\n: ");
					classDatetime = scanner.nextLine();
					reserveClass(memberId, classDatetime);
				} catch(SQLException sqle) {
					System.out.println("SQLException_Execute: " + sqle);
				}
				break;
				
			case 2:
				System.out.println("������ ����մϴ�.");
				System.out.println("...........................................................................................................");
				try {
					showClasses(memberId, 3);  // ������Ʈ �� ����Ȯ����/����Ϸ� ���� ���
					scanner.nextLine();
					System.out.print("����Ͻ� ������ ��¥�� �ð��� �Է��ϼ���.\n  ex) 2000-06-15 20:00:00\n: ");
					classDatetime = scanner.nextLine();
					cancelClass(memberId, classDatetime);
				} catch(SQLException sqle) {
					System.out.println("SQLException_Execute: " + sqle);
				}
				break;
				
			case 3:				
			case 4:
				System.out.println("���� ������ Ȯ���մϴ�.");
				System.out.println("...........................................................................................................");
				try {
					showClasses(memberId, menu);
				} catch(SQLException sqle) {
					System.out.println("SQLException_Execute: " + sqle);
				}
				break;
				
			default:
				System.out.println("**1~4�� ������ �Է��� �ּ���.**");
				System.out.println("...........................................................................................................");
				break;
			}
			System.out.println();

		}
		scanner.close();
	}
}