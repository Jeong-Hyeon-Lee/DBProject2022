package DB2022Team03.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//�ｺ�� ȸ�� �� ����
public class G_countTrainees extends JFrame {
	public G_countTrainees(Connection conn, String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		
		//DB2022_ȸ�� ���̺��� �ｺ�� ��ȣ�� 'gymID'�� ���� ȸ���� ���� rs�� ����
		PreparedStatement pStmt = conn.prepareStatement("SELECT count(ȸ����ȣ) FROM DB2022_ȸ�� WHERE �Ҽ��ｺ�� = ?");
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		int result = 0; //ȸ�� �� (sql query�� �����)
		if (rs.next()) // ���� �����Ѵٸ�
			result = rs.getInt(1); //ù��° column ��(count(ȸ����ȣ)) ������ ��ȯ
		JOptionPane.showMessageDialog(rootPane, "ȸ�� ��: " + result); //�˸�â���� �����ֱ�
	}
}