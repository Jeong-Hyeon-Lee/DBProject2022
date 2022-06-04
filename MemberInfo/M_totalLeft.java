package DB2022Team03.MemberInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class M_totalLeft{
	public static int[] M_totalLeft(Connection conn, String ID) throws SQLException {
		int total, left;
		String str = "SELECT 전체횟수,남은횟수 FROM db2022_회원 USE INDEX (회원번호인덱스) WHERE 회원번호=?";
		PreparedStatement pstmt = conn.prepareStatement(str);
		pstmt.setString(1,ID);
		ResultSet rset = pstmt.executeQuery();
		
		rset.next();
		total=rset.getInt(1);
		left=rset.getInt(2);
		
		return new int[] {total,left};
	}
}
