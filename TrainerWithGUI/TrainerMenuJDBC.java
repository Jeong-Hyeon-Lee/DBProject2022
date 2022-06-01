package TrainerWithGUI;
// TrainerMenuJDBC.java
// trainer ���� �޴� �ڵ鸵�� ���� JDBC ���� ��ü

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TrainerMenuJDBC {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // java-sql ���� ����
	static final String DB_URL = "jdbc:mysql://localhost/DB2022Team03"; // database url
	static final String USER = "root";     // �ۼ��� �̸�
	static final String PASS = "penguin1109"; // sql ���� ��й�ȣ
	
	// �ʿ��� ���� ����
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	
	// JDBC ������ ���� ��ü ������
	public TrainerMenuJDBC() {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("�����ͺ��̽� ���� ����");
		}catch(SQLException e) {
			System.out.println(e + "=> ���� �ȵ�");
		}
	}

	public int checkLogin(String tID, String tPW) {
		String loginquery = "SELECT ��й�ȣ, �̸�, �����ȣ, �ｺ���ȣ, ���ȸ����, �ѱٹ��ð� "
				+ "FROM DB2022_Ʈ���̳�" + " WHERE (�����ȣ=?)";
		boolean login_success = false;
		try {
			pst = con.prepareStatement(loginquery);
			pst.setString(1,  tID);
			rs = pst.executeQuery();
			if (rs.getRow() == -1) {
				JOptionPane.showMessageDialog(null, "���̵� ���ų�, �ùٸ��� ���� ��й�ȣ�Դϴ�. �ٽ� �α������ּ���.");
			}
			while (rs.next()) {
				if (rs.getString(1).equals(tPW)) {
					login_success = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "���̵� ���ų�, �ùٸ��� ���� ��й�ȣ�Դϴ�. �ٽ� �α������ּ���.");
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if (login_success)return 1;
		else return 0;
	}
	// JDBC ������ ���� ���ؼ� ���
	public void closeDB() {
		try {
			if (rs!=null)rs.close();
			if (st != null)st.close();
			if (rs != null)rs.close();
			if (pst != null)pst.close();
		}catch(Exception e) {
			System.out.println("Failed to close the database");
		}
	}
	public void classInfoAll(DefaultTableModel class_table, String trainer_id) {
		try {
			pst = con.prepareStatement("SELECT ȸ����ȣ, �����ð�, ����������Ȳ FROM DB2022_���� WHERE(�����ȣ=?) ORDER BY ����������Ȳ, �����ð�"); // ���� ���� ��Ȳ�� ���� �� �������ļ� ��ȯ
			// ���� �ð��� ���� �� ���� ������
			pst.setString(1, trainer_id);
			rs = pst.executeQuery();
		
			while (rs.next()) {
				Object info[] = {rs.getString(1), rs.getString(2), rs.getString(3)};
				class_table.addRow(info);
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	// �α����� trainer�� ��� ������ ������
	public void trainerInfoAll(DefaultTableModel trainer_table, String trainer_id) {
		try {
			pst = con.prepareStatement("SELECT �����ȣ, �̸�, �ｺ���ȣ, ���ȸ����, �ѱٹ��ð� FROM DB2022_Ʈ���̳� WHERE(�����ȣ=?)");
			
			pst.setString(1, trainer_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				Object info[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)};
				trainer_table.addRow(info); // DefaultTableModel�� row �߰� (������ �����ֱ� ���ؼ�)
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	// trainer ���� ��ȣ ������ ���ؼ� ���� �޹�ȣ 4�ڸ��� ���� Ʈ���̳ʰ� �����ϴ��� Ȯ��
	public int checkPhoneNum(String num) {
		int cnt = 0;
		try {
			pst = con.prepareStatement("SELECT COUNT (*) FROM (SELECT substring(ȭ����ȣ, 2, 4) AS '���ڸ�' FROM DB2022_ȸ��) ���ڸ� WHERE(���ڸ�=?)");
			pst.setString(1, num);
			rs = pst.executeQuery();
			if (rs.next()) {
				cnt += 1;
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return cnt+1; // �ش� ��ȣ �� 4�ڸ��� �ش��ϴ� ��ϵ� Ʈ���̳ʰ� ������� integer�� ���·� ��ȯ�Ѵ�.
	}
	// ȸ������
	public int Join(String myGym, String id, String name, String pwd) {
		String q = "INSERT INTO DB2022_Ʈ���̳�(�ｺ���ȣ, �����ȣ, �̸�, ��й�ȣ) VALUES (?,?,?,?)";
		boolean success = false;
		try {
			pst = con.prepareStatement(q);
			pst.setString(1, myGym);
			pst.setString(2, id);
			pst.setString(3, name);
			pst.setString(4, pwd);
			pst.executeUpdate();
			success = true;
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		if (success)return 1;
		else return 0;
	}
	
	
	
	// trainer�� ��� ���� �Ϸ� ���� ���� ��ȸ (���� / �Ϸ� �� �ٲٱ� ����)
	public void trainerClassAll(DefaultTableModel class_table, String tID) {
		String query1 = "SELECT ȸ����ȣ, �����ð�, ����������Ȳ FROM DB2022_���� " + 
				"WHERE(�����ȣ=? AND ����������Ȳ='����Ȯ����')";
		String query2 = "UPDATE DB2022_���� SET ����������Ȳ='����Ϸ�'";
		try {
			pst = con.prepareStatement(query1);
			pst.setString(1, tID);
			rs = pst.executeQuery();
			while (rs.next()) {
				Object info[] = {rs.getString(1), rs.getString(2), rs.getString(3),};
				class_table.addRow(info); // DefaultTableModel�� row �߰� (������ �����ֱ� ���ؼ�)
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	// trainer�� ��� ���� ���
	public void calculateSalary(DefaultTableModel salary_table, String tID) {
		ResultSet students = null; // ����ϰ� �ִ� ��� �л�
		String q1 = "SELECT ȸ����ȣ, ����ȸ����, �Ҽ��ｺ�� FROM DB2022_ȸ�� WHERE(���Ʈ���̳�=?)";
		int total = 0; // �� �ݾ� ���
		String results[][] = new String[100][3]; // ȸ����ȣ, ȸ����, �Ҽ��ｺ�� ����
		int cnt = 0;
		try {
			PreparedStatement st = con.prepareStatement(q1);
			st.setString(1, tID);
			ResultSet rs = st.executeQuery();
			if (rs.getRow() == -1) {
				System.out.println("����ϰ� �ִ� �л��� �����ϴ�.");
			}
			else {
				System.out.println("����ϰ� �ִ� ȸ���� �ֽ��ϴ�.");
			}
			while (rs.next()) {
				String stu_id = rs.getString(1); 
				String stu_ms = rs.getString(2);
				String stu_gym = rs.getString(3);
				results[cnt][0] = stu_id;results[cnt][1] = stu_ms;results[cnt][2] = stu_gym;
				cnt += 1;
			}
			rs.close();
			st.close();
		}catch(SQLException e) {
			e.getStackTrace();
		}
		String q2 = "SELECT 1ȸ����, 10ȸ����, 20ȸ���� FROM DB2022_���� WHERE(�ｺ���ȣ=?)";
		for (int i = 0;i<cnt;i++) {
			try {
				PreparedStatement st1 = con.prepareStatement(q2);
				st1.setString(1, results[i][2]);
				students = st1.executeQuery();
				int cost = 0;
				while (students.next()) {
					if (results[i][1].equals("1ȸ��"))cost= students.getInt(1);
					else if (results[i][1].equals("10ȸ��"))cost = students.getInt(1) * 10;
					else cost = students.getInt(2) * 20;
					total += cost;
					// System.out.println(results[i][0] + " �л����� " + Integer.toString(cost) + "�� ��ŭ �޽��ϴ�.");
					String[] data = {results[i][0], Integer.toString(cost)};
					salary_table.addRow(data);
				}
			}catch(SQLException e) {
				e.getStackTrace();
			}
		}
		String[] data = {"��� ����", Integer.toString(total)}; // ���������� ��� �� ���� ������ �߰��Ѵ�.
		salary_table.addRow(data);
	}
	// trainer�� ���� �Ϸ� ���� ���� �� ������ ������ ���� ��Ȳ ����
	public void changeClassStatus(String student_no, String class_t, String status, String trainer_pk, String fieldName) {
		String q = null;
		System.out.println(fieldName);
		if (fieldName.equals("����") || fieldName.equals("���")) {
			q = "DELETE FROM DB2022_���� WHERE(ȸ����ȣ=? AND �����ð�=? AND �����ȣ=?)";
		}
		else {
			q = "UPDATE DB2022_���� SET ����������Ȳ=? WHERE(ȸ����ȣ=? AND �����ð�=? AND �����ȣ=?)";
		}
		try {
			pst = con.prepareStatement(q);
			if (fieldName.equals("����") || fieldName.equals("���")) {
				pst.setString(1, student_no);
				pst.setString(2, class_t);
				pst.setString(3,  trainer_pk);
			}
			else {
				pst.setString(1,  fieldName);
				pst.setString(2, student_no);
				pst.setString(3, class_t);
				pst.setString(4, trainer_pk);
			}
			pst.executeUpdate();
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
	}
	// trainer�� ��� ���� ��� �� ���� ���� ��ȸ (���� / ���� �ϱ� ����)
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
