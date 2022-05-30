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
		System.out.println("1. �ｺ�� ��õ�ޱ�"); 
		System.out.println("2. �ｺ�� �˻��ϱ�");
		System.out.println("3. �ｺ�� ����ϱ�");
		System.out.println("4. �ｺ�� Ʈ���̳� Ȯ���ϱ�");
		System.out.println("5. �Ҽ��ｺ�� Ʈ���̳� Ȯ���ϱ�");
		System.out.println("6. ���Ʈ���̳� ����ϱ�");
		System.out.println("7. ȸ���� ����ϱ�");
		System.out.println("8. ȸ������ Ȯ���ϱ�");
		System.out.println("0. �α׾ƿ� �� ���α׷� ����");
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
			System.out.println("�α��� ����");
			
			boolean working = true;			
			while(working) {
				MENU();
				int select = scan.nextInt();
				switch(select){
					case 0: //���α׷� ����
						working=false; 
						System.out.println("�α׾ƿ�");
						break; 
					
					case 1: //���� ������ �ִ� �ｺ�� ��õ�ޱ�
						//recommendGYM(conn);
						break;
						
					case 2: //���� �Է¹޾� �ｺ�� �˻� ��������ֱ�
						//searchGYM(conn);
						break;
						
					case 3: //�ｺ�� �̸� �Է¹޾� ����ϱ� -> case1,2������ Ŭ���ϸ� �Ѿ���� �ص� ������ ��
						//enrollGYM(conn);						
						break;
					
					case 4: //�ｺ�� �̸� �Է¹޾� Ʈ���̳� �����ֱ�
						//searchTrainer(conn);
						break;
						
					case 5: //����� �ｺ�� Ʈ���̳� Ȯ���ϱ�
						//showTrainer(conn);
						break;
						
					case 6: //Ʈ���̳� �̸����� ���Ʈ���̳� ����ϱ� (�������� Ʈ���̳ʰ� ���ٰ� ����)
						//enrollTrainer(conn);
						break;
						
					case 7: //ȸ���� ����ϱ�
						//enrollMembership(conn);
						break;
						
					case 8: //ȸ������ Ȯ���ϱ�
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
