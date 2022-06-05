package DB2022Team03.TrainerWithGUI;
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
	static final String USER = "DB2022Team03";     // �ۼ��� �̸�
	static final String PASS = "DB2022Team03"; // sql ���� ��й�ȣ
	
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

	// Ʈ���̳ʰ� �α��� ���� �� ��� ���� ������ ���̵��� �����ȣ�� ���� �˻����� �� ��� ���� ������ ���� �ݴϴ�.
	public void classInfoAll(DefaultTableModel class_table, String trainer_id) {
		try {
			pst = con.prepareStatement("SELECT DB2022_ȸ��.�̸�, DB2022_����.�����ð�, DB2022_����.����������Ȳ, DB2022_ȸ��.ȸ����ȣ FROM DB2022_���� INNER JOIN DB2022_ȸ�� ON DB2022_ȸ��.ȸ����ȣ=DB2022_����.ȸ����ȣ WHERE(DB2022_����.�����ȣ=? AND DB2022_ȸ��.���Ʈ���̳�=?) ORDER BY DB2022_����.�����ð�, DB2022_����.ȸ����ȣ"); // ���� ���� ��Ȳ�� ���� �� �������ļ� ��ȯ
			// ���� �ð��� ���� �� ���� ������ -> ȸ����ȣ�� ���߾� ����
			pst.setString(1, trainer_id);
			pst.setString(2,  trainer_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				Object info[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
				class_table.addRow(info);
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	// �α����� trainer�� ��� ������ ������
	public void trainerInfoAll(DefaultTableModel trainer_table, String trainer_id) {
		try {

			pst = con.prepareStatement("SELECT �����ȣ, �̸�, �ｺ���ȣ, ���ȸ����, �ѱٹ��ð� FROM DB2022_Ʈ���̳� USE INDEX (�����ȣ�ε���) WHERE(�����ȣ=?)");
			
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
	// trainer ���� ��ȣ ������ ���ؼ� ���� �޹�ȣ 4�ڸ��� ���� Ʈ���̳ʰ� �����ϴ��� Ȯ�� (�ִٸ� ���� pk ������ ���ؼ� �ο��� return)
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

	
	// trainer�� ��� ���� �Ϸ� ���� ���� ��ȸ (���� / �Ϸ� �� �ٲٱ� ����)
	public void trainerClassAll(DefaultTableModel class_table, String tID) {
		String query1 = "SELECT DB2022_ȸ��.�̸�, DB2022_����.�����ð�, DB2022_����.����������Ȳ FROM DB2022_���� INNER JOIN DB2022_ȸ�� ON DB2022_ȸ��.ȸ����ȣ=DB2022_����.ȸ����ȣ WHERE(DB2022_����.�����ȣ=? AND DB2022_ȸ��.���Ʈ���̳�=? AND DB2022_����.����������Ȳ='����Ȯ����')";

		for (int i = 0;i<class_table.getRowCount();i++) {
			class_table.removeRow(0);
		}
		try {
			pst = con.prepareStatement(query1);
			pst.setString(1, tID);
			pst.setString(2,  tID);
			rs = pst.executeQuery();
			while (rs.next()) {
				Object info[] = {rs.getString(1), rs.getString(2), rs.getString(3),};
				class_table.addRow(info); // DefaultTableModel�� row �߰� (������ �����ֱ� ���ؼ�)
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			closeDB();
		}
	}
	// trainer�� ��� ���� ���
	public void calculateSalary(DefaultTableModel salary_table, String tID) {
		ResultSet students = null; // ����ϰ� �ִ� ��� �л�
		String q1 = "SELECT �̸�, ����ȸ����, �Ҽ��ｺ�� FROM DB2022_ȸ�� WHERE(���Ʈ���̳�=?)";
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
		String q2 = "SELECT 1ȸ����, 10ȸ����, 20ȸ���� FROM DB2022_���� USE INDEX (�ｺ���ȣ) WHERE(�ｺ���ȣ=?)";
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
	

	public void rejectClass(String student_no, String class_t, String status, String trainer_pk) {
		// ���� ���� (���� �Һ� / ���� ����)
		try {
			pst = con.prepareStatement("DELETE FROM DB2022_���� WHERE(ȸ����ȣ=? AND �����ð�=? AND �����ȣ=?)");
			pst.setString(1, student_no);pst.setString(2, class_t);pst.setString(3, trainer_pk);
			pst.executeUpdate();
			
			
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			JOptionPane.showMessageDialog(null, "���� ������ ���� �Ϸ��߽��ϴ�.");
		}
	}
	public void acceptClass(String student_name, String student_no, String class_t, String status, String trainer_pk) {
		// ���� ���� (ȸ�� ���� ���� Ƚ�� -1 : ���� �����Ǵ� ������ ������ ȸ�� ���� Ƚ���� ����)
		PreparedStatement pstDetail = null;
		try {
			con.setAutoCommit(false); // transaction ����
			pst = con.prepareStatement("UPDATE DB2022_���� SET ����������Ȳ='����Ϸ�' WHERE(ȸ����ȣ=? AND �����ð�=? AND �����ȣ=?)");
			pst.setString(1,  student_no);
			pst.setString(2, class_t);
			pst.setString(3, trainer_pk);
			pst.executeUpdate();
			
			pstDetail = con.prepareStatement("UPDATE DB2022_ȸ�� SET ����Ƚ��=����Ƚ��-1 WHERE(ȸ����ȣ=?)");
			pstDetail.setString(1,  student_no);
			pstDetail.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
		}catch(Throwable e) {
			if (con!=null) {
				try {
					con.rollback();
				}catch(SQLException ex) {}
			}
		}finally {
			if (pst!=null)
				try {pst.close();}catch(SQLException e) {}
			if (pstDetail !=null)
				try {pstDetail.close();}catch(SQLException e) {}
			JOptionPane.showMessageDialog(null,student_name + "���� ���� ������ ���� �Ϸ��߽��ϴ�.");
		}
		
	}
	
	public void endClass(String student_no, String class_t, String status, String trainer_pk) {
		// ���� �Ϸ� (���� �ٹ��ð� +1)
		PreparedStatement pstDetail = null;
		try {
			con.setAutoCommit(false); // transaction ����
			pst = con.prepareStatement("UPDATE DB2022_���� SET ����������Ȳ='�Ϸ�' WHERE(ȸ����ȣ=? AND �����ð�=? AND �����ȣ=?)");
			pst.setString(1,  student_no);
			pst.setString(2, class_t);
			pst.setString(3, trainer_pk);
			pst.executeUpdate();
			
			pstDetail = con.prepareStatement("UPDATE DB2022_Ʈ���̳� SET �ѱٹ��ð�=�ѱٹ��ð�+1 WHERE(�����ȣ=?)");
			pstDetail.setString(1,  trainer_pk);
			pstDetail.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
		}catch(Throwable e) {
			if (con!=null) {
				try {
					con.rollback();
				}catch(SQLException ex) {}
			}
		}finally {
			if (pst!=null)
				try {pst.close();}catch(SQLException e) {}
			if (pstDetail !=null)
				try {pstDetail.close();}catch(SQLException e) {}
			JOptionPane.showMessageDialog(null, class_t+"�� ����� ������ �Ϸ��߽��ϴ�.");
		}
		
	}
	
	public void noshowClass(String student_no, String class_t, String status, String trainer_pk) {
		// ���� ���� (���� �ٹ��ð� +1)
		PreparedStatement pstDetail = null;
		boolean noshow_valid = true;
		if (status.equals("����Ϸ�")==false) { 
			JOptionPane.showMessageDialog(null, "<����Ϸ�>�� ������ <����>���� ������ �����մϴ�.");
			return;
		}
		try {
			PreparedStatement hdiff = con.prepareStatement("SELECT TIMESTAMPDIFF(HOUR, (SELECT �����ð� FROM DB2022_���� WHERE(�����ð�=? AND �����ȣ=?)), now())"); // now() - ���� �ð�
			hdiff.setString(1, class_t);
			hdiff.setString(2, trainer_pk);
			ResultSet trs = hdiff.executeQuery();
			if (trs.next()) {
				System.out.println(trs.getInt(1));
				if (trs.getInt(1) <= 1) { // ���� ���� �ð� + ���� ���� �ð� ��ŭ�� ������ ���� ���
					noshow_valid = false;
					JOptionPane.showMessageDialog(null, "���� �ð��� ������ �ʾұ� ������ <����>���� ������ �� �����ϴ�.");
					return;
			}}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		if (noshow_valid) { // �������� ��Ȳ�� ������ �� �ִ� ��쿡
			try {
				con.setAutoCommit(false); // transaction ����
				pst = con.prepareStatement("UPDATE DB2022_���� SET ����������Ȳ='����' WHERE(ȸ����ȣ=? AND �����ð�=? AND �����ȣ=?)");
				pst.setString(1,  student_no);
				pst.setString(2, class_t);
				pst.setString(3, trainer_pk);
				pst.executeUpdate();
			
				pstDetail = con.prepareStatement("UPDATE DB2022_Ʈ���̳� SET �ѱٹ��ð�=�ѱٹ��ð�+1 WHERE(�����ȣ=?)");
				pstDetail.setString(1,  trainer_pk);
				pstDetail.executeUpdate();
			
				con.commit(); // transaction ����
				con.setAutoCommit(true); // �ٽ� auto commit �����ϰ� ����
			}catch(Throwable e) {
				if (con!=null) {
					try {
						con.rollback();
					}catch(SQLException ex) {}
				}
			}finally {
				if (pst!=null)
					try {pst.close();}catch(SQLException e) {}
				if (pstDetail !=null)
					try {pstDetail.close();}catch(SQLException e) {}
				JOptionPane.showMessageDialog(null, class_t+"�� ����� ������ ȸ���� �����Ͽ����ϴ�.");
			}
		}
		
	}
	
	
}