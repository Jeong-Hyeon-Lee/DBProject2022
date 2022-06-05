package DB2022Team03.MemberInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class M_totalLeft{
	public static int[] M_totalLeft(Connection conn, String ID) throws SQLException {
		int total, left;
		String str = "SELECT ��üȽ��,����Ƚ�� FROM db2022_ȸ�� USE INDEX (ȸ����ȣ�ε���) WHERE ȸ����ȣ=?";
		PreparedStatement pstmt = conn.prepareStatement(str);
		pstmt.setString(1,ID);
		ResultSet rset = pstmt.executeQuery();
		
		rset.next();
		total=rset.getInt(1);
		left=rset.getInt(2);
		
		return new int[] {total,left};
	}
}