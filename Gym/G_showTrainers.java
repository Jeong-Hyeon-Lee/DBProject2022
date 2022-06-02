package DB2022Team03.Gym;

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
		
		PreparedStatement pStmt = conn.prepareStatement("SELECT count(*) FROM DB2022_트레이너 WHERE 헬스장번호 = ?"); //행 개수(=트레이너 인원수) 구하기
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		int trainerNum = 0;
		if (rs.next())
			trainerNum = rs.getInt(1);
		
		setTitle("트레이너 정보");
		
		String [] title = {"트레이너 이름", "총 근무시간"};
		String [][] data = new String[trainerNum][2];
		
		PreparedStatement pStmt2 = conn.prepareStatement("SELECT 이름, 총근무시간 FROM DB2022_트레이너 WHERE 헬스장번호 = ?");
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