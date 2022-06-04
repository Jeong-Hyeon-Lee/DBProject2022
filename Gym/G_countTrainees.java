package DB2022Team03.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//헬스장 회원 수 보기
public class G_countTrainees extends JFrame {
	public G_countTrainees(Connection conn, String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		
		//DB2022_회원 테이블에서 헬스장 번호가 'gymID'와 같은 회원의 수를 rs에 저장
		PreparedStatement pStmt = conn.prepareStatement("SELECT count(회원번호) FROM DB2022_회원 WHERE 소속헬스장 = ?");
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		int result = 0; //회원 수 (sql query의 결과값)
		if (rs.next()) // 값이 존재한다면
			result = rs.getInt(1); //첫번째 column 값(count(회원번호)) 정수로 반환
		JOptionPane.showMessageDialog(rootPane, "회원 수: " + result); //알림창으로 보여주기
	}
}