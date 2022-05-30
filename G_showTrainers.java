package DB2022Team03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class G_showTrainers extends JFrame{
	public G_showTrainers(Connection conn, String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		PreparedStatement pStmt = conn.prepareStatement("SELECT 이름 FROM DB2022_트레이너 WHERE 헬스장번호 = ?");
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		
		StringBuilder sb = new StringBuilder();
		while (rs.next()) {
			String name = rs.getString("이름") + "\n";
			sb.append(name);
		}
		JOptionPane.showMessageDialog(rootPane, sb.toString());
	}
}
