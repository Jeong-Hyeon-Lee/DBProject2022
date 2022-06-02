package DB2022TEAM03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class G_countTrainees extends JFrame {
	public G_countTrainees(Connection conn, String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		PreparedStatement pStmt = conn.prepareStatement("SELECT count(회원번호) FROM DB2022_회원 WHERE 소속헬스장 = ?");
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		int result = 0;
		if (rs.next())
			result = rs.getInt(1);
		JOptionPane.showMessageDialog(rootPane,"회원 수: "+ result);
	}
}
