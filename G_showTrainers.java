package DB2022Team03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class G_showTrainers extends JFrame{
	public G_showTrainers(Connection conn, String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		
		PreparedStatement pStmt = conn.prepareStatement("SELECT count(*) FROM DB2022_Ʈ���̳� WHERE �ｺ���ȣ = ?"); //�� ����(=Ʈ���̳� �ο���) ���ϱ�
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		int trainerNum = 0;
		if (rs.next())
			trainerNum = rs.getInt(1);
		
		setTitle("Ʈ���̳� ����");
		
		String [] title = {"Ʈ���̳� �̸�", "�� �ٹ��ð�"};
		String [][] data = new String[trainerNum][2];
		
		PreparedStatement pStmt2 = conn.prepareStatement("SELECT �̸�, �ѱٹ��ð� FROM DB2022_Ʈ���̳� WHERE �ｺ���ȣ = ?");
		pStmt2.setString(1, gymID);
		ResultSet rs2 = pStmt2.executeQuery();
		
		int i=0;
		while (rs2.next()) {
			data[i][0] = rs2.getString(1);
			data[i][1] = rs2.getString(2);
			i++;
		}
		
		JTable table = new JTable(data, title);
		JScrollPane scroll = new JScrollPane(table);
		add(scroll);
		
		setBounds(200, 200, 400, 250);


		setVisible(true);
	}
}
