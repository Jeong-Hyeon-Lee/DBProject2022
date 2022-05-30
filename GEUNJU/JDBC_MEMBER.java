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
		
	public static int[] totalLeft(Connection conn) throws SQLException { //��ü����Ƚ��,��������Ƚ�� Ȯ��(ó������ ���ٸ�������->UPDATE SET CASE���� �����ϱ� ��
		int total, left;
		str = "SELECT ��üȽ��,����Ƚ�� FROM DB2022_ȸ�� WHERE ȸ����ȣ=?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1,ID);
		rset = pstmt.executeQuery();
		
		rset.next();
		total=rset.getInt(1);
		left=rset.getInt(2);
		
		return new int[] {total,left};
	}
	
	public static void recommendGYM(Connection conn) throws SQLException {
		str = "select �̸�,����,1ȸ����,10ȸ����,20ȸ����,��Ÿ���θ�Ǽ��� from db2022_�ｺ�� natural join db2022_���� WHERE ���� IN (SELECT ���� FROM db2022_ȸ�� WHERE ȸ����ȣ=?)";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();
		
		if(!rset.isBeforeFirst()) System.out.println("����� �ｺ���� ã�� ���߽��ϴ�."); 
		else {
			System.out.println("=================�ｺ����õ=================");
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
		System.out.println("�˻��� ���ϴ� ������ �Է����ּ���. : ");
		String location = scan.next();
		
		str = "select �̸�,����,1ȸ����,10ȸ����,20ȸ����,��Ÿ���θ�Ǽ��� from db2022_�ｺ�� natural join db2022_���� WHERE ����=?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, location);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();
		
		if(!rset.isBeforeFirst()) System.out.println("�Է��� �������� �ｺ���� ã�� ���߽��ϴ�."); 
		else {
			System.out.println("=================�ｺ��˻�=================");
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
		//���� ȸ���� ��������Ƚ���� 0���� Ȯ��
		int check[] = totalLeft(conn);
		
		if(check[1]==0) {
			System.out.println("�ｺ�� �̸��� �Է����ּ���. : ");
			scan.nextLine();//���ۺ���
			GYMname = scan.nextLine();
			//�ｺ�� �̸����� ��ȣã��
			str = "SELECT �ｺ���ȣ FROM DB2022_�ｺ�� WHERE �̸�=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, GYMname);
			rset = pstmt.executeQuery();
			
			if(!rset.isBeforeFirst()) System.out.println("�Է��� �̸��� ���� �ｺ���� ã�� ���߽��ϴ�."); 
			else {
				rset.next();
				GYMid = rset.getString(1);						
				
				//�ｺ�� ����ϱ�
				//str = "UPDATE DB2022_ȸ�� SET �Ҽ��ｺ��=CASE WHEN ����Ƚ��==0 THEN ? END WHERE ȸ����ȣ=?";
				str = "UPDATE DB2022_ȸ�� SET �Ҽ��ｺ��=? WHERE ȸ����ȣ=?";
				pstmt = conn.prepareStatement(str);
				pstmt.setString(1, GYMid);
				pstmt.setString(2, ID);
				pstmt.executeUpdate();
				//�ｺ�� : ��üȸ���� �ڵ� ������ ���⼭ ..? 
				System.out.println(GYMname+"�� ȸ������ ��ϵǾ����ϴ�.");
			}
		} else {
			System.out.println("���� ����Ƚ���� ���Ƽ� �ｺ���� ������ �� �����ϴ�. (���� ����Ƚ��:"+check[1]+")");
		}
	}
	
	public static void enrollMembership(Connection conn) throws SQLException {
		//���� ȸ���� ��������Ƚ���� 0���� Ȯ��
		int check[] = totalLeft(conn);
		
		if(check[1]==0) { 
			System.out.println("���ϴ� ȸ������ ���ڷ� �Է����ּ���. : ");
			int membership = scan.nextInt();
			
			
			//str = "UPDATE DB2022_ȸ�� SET (����ȸ����, ����Ƚ��, ��üȽ��) = CASE WHEN ����Ƚ��==0 THEN (?, ?, ?) END WHERE ȸ����ȣ=?";
			str = "UPDATE DB2022_ȸ�� SET ����ȸ����=?,��üȽ��=?,����Ƚ��=? WHERE ȸ����ȣ=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, membership+"ȸ��");
			pstmt.setInt(2, membership+check[0]);
			pstmt.setInt(3, membership);
			pstmt.setString(4, ID);
			pstmt.executeUpdate();
			System.out.println(membership+"ȸ������ ����߽��ϴ�.");
		} else {
			System.out.println("���� ����Ƚ���� ���Ƽ� ȸ������ ������ �� �����ϴ�. (���� ����Ƚ��:"+check[1]+")");
		}
	}
	
	public static void showTrainer(Connection conn) throws SQLException {
		//�׳� �Ҽ��ｺ�� Ʈ���̳ʺ����ֱ�
		str = "SELECT �̸�,���ȸ���� FROM DB2022_Ʈ���̳� WHERE �ｺ���ȣ IN (SELECT �Ҽ��ｺ�� FROM DB2022_ȸ�� WHERE ȸ����ȣ=?)";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();	
		
		//System.out.println("ȸ�������� �ҷ����µ� �����߽��ϴ�."); 
		System.out.println("=================Ʈ���̳ʸ��=================");
		if(!rset.isBeforeFirst()) System.out.println("��ϵ� Ʈ���̳ʰ� �����ϴ�.");
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
		//�ｺ�� �̸� �˻��ؼ� ã��
		System.out.println("�ｺ�� �̸��� �Է����ּ���. : ");
		scan.nextLine();//���ۺ���
		GYMname = scan.nextLine();
		
		//�ｺ�� �̸����� ��ȣã��
		str = "SELECT �ｺ���ȣ FROM DB2022_�ｺ�� WHERE �̸�=?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, GYMname);
		rset = pstmt.executeQuery();
		
		if(!rset.isBeforeFirst()) System.out.println("�Է��� �̸��� ���� �ｺ���� ã�� ���߽��ϴ�."); 
		else {
			rset.next();
			String GYMid = rset.getString(1);
			
			//�ｺ�� ��ȣ�� Ʈ���̳� �����ֱ� 
			str = "SELECT �̸�, ���ȸ���� FROM DB2022_Ʈ���̳� WHERE �ｺ���ȣ=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, GYMid);
			rset = pstmt.executeQuery();
			
			rsmd = rset.getMetaData();
			columnCnt = rsmd.getColumnCount();	
			
			System.out.println("=================Ʈ���̳� in "+GYMname+"=================");
			
			if(!rset.isBeforeFirst()) System.out.println("���� Ʈ���̳ʰ� ��ϵ��� �ʾҽ��ϴ�.");
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
		//���� ȸ���� ��������Ƚ���� 0���� Ȯ��
		int check[] = totalLeft(conn);
				
		if(check[1]==0) {
			System.out.println("Ʈ���̳� �̸��� �Է����ּ���. : ");
			String Tname = scan.next();
			
			//Ʈ���̳� �̸����� ��ȣã��
			str = "SELECT �����ȣ FROM DB2022_Ʈ���̳� WHERE �̸�=?";
			pstmt = conn.prepareStatement(str);
			pstmt.setString(1, Tname);
			rset = pstmt.executeQuery();
			
			if(!rset.isBeforeFirst()) System.out.println("�Է��� �̸��� ���� Ʈ���̳ʸ� ã�� ���߽��ϴ�."); 
			else {
				rset.next();
				String Tid = rset.getString(1);						
				
				//��� Ʈ���̳� ����ϱ�
				//str = "UPDATE DB2022_ȸ�� SET ���Ʈ���̳�=CASE WHEN ����Ƚ��==0 THEN ? END WHERE ȸ����ȣ=?";
				str = "UPDATE DB2022_ȸ�� SET ���Ʈ���̳�=? WHERE ȸ����ȣ=?";
				pstmt = conn.prepareStatement(str);
				pstmt.setString(1, Tid);
				pstmt.setString(2, ID);
				pstmt.executeUpdate();
				//Ʈ���̳� : ���ȸ���� �߰��ϴ� ��, �ｺ�� : ��üȸ���� �ڵ� �ʿ��ϳ�? count�� �� �ǵ�? �ʿ��ϸ� transaction���� ���� ����� ��
				System.out.println("���Ʈ���̳�("+Tname+")�� ��ϵǾ����ϴ�.");
			}
		} else {
			System.out.println("���� ����Ƚ���� ���Ƽ� ���Ʈ���̳ʸ� ������ �� �����ϴ�. (���� ����Ƚ��:"+check[1]+")");
		} 
		
	}
	
	public static void myPage(Connection conn) throws SQLException { 
		str = "SELECT G.�̸� as �ｺ��, M.�̸�, M.����, M.��üȽ��, M.����Ƚ��,T.�̸� as Ʈ���̳�, M.����ȸ���� FROM db2022_ȸ�� as M, db2022_Ʈ���̳� as T, db2022_�ｺ�� as G WHERE M.���Ʈ���̳�=T.�����ȣ and M.�Ҽ��ｺ��=G.�ｺ���ȣ and M.ȸ����ȣ = ?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		rsmd = rset.getMetaData();
		columnCnt = rsmd.getColumnCount();
		

		System.out.println("=================������=================");
		for(int i=1;i<=columnCnt;i++) System.out.print(rsmd.getColumnName(i)+" "); //select���� as�� �� �� ���� �� �Ǵ� ��?
		System.out.print("\n-----------------------------------------\n");
		if(!rset.isBeforeFirst()) System.out.println("ȸ�������� �ҷ����µ� �����߽��ϴ�.");
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
						recommendGYM(conn);
						break;
						
					case 2: //���� �Է¹޾� �ｺ�� �˻� ��������ֱ�
						searchGYM(conn);
						break;
						
					case 3: //�ｺ�� �̸� �Է¹޾� ����ϱ� -> case1,2������ Ŭ���ϸ� �Ѿ���� �ص� ������ ��
						enrollGYM(conn);						
						break;
					
					case 4: //�ｺ�� �̸� �Է¹޾� Ʈ���̳� �����ֱ�
						searchTrainer(conn);
						break;
						
					case 5: //����� �ｺ�� Ʈ���̳� Ȯ���ϱ�
						showTrainer(conn);
						break;
						
					case 6: //Ʈ���̳� �̸����� ���Ʈ���̳� ����ϱ� (�������� Ʈ���̳ʰ� ���ٰ� ����)
						enrollTrainer(conn);
						break;
						
					case 7: //ȸ���� ����ϱ�
						enrollMembership(conn);
						break;
						
					case 8: //ȸ������ Ȯ���ϱ�
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
