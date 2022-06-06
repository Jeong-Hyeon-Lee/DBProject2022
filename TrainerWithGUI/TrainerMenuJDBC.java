package DB2022Team03.TrainerWithGUI;
// TrainerMenuJDBC.java
// trainer 관련 메뉴 핸들링을 위한 JDBC 연동 객체

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TrainerMenuJDBC {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // java-sql 연결 서버
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03"; // database url
	static final String USER = "DB2022Team03";     // 작성자 이름
	static final String PASS = "DB2022Team03"; // sql 연결 비밀번호
	
	// 필요한 변수 선언
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	
	// JDBC 연동을 위한 객체 생성자
	public TrainerMenuJDBC() {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("데이터베이스 연결 성공");
		}catch(SQLException e) {
			System.out.println(e + "=> 연결 안됨");
		}
	}


	// JDBC 연결을 끊기 위해서 사용
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

	// 트레이너가 로그인 했을 때 모든 수업 내용이 보이도록 강사번호에 따라 검색했을 때 모든 수업 정보를 보여 줍니다.
	public void classInfoAll(DefaultTableModel class_table, String trainer_id) {
		try {
			pst = con.prepareStatement("SELECT DB2022_회원.이름, DB2022_수업.수업시간, DB2022_수업.수업진행현황, DB2022_회원.회원번호 FROM DB2022_수업 INNER JOIN DB2022_회원 ON DB2022_회원.회원번호=DB2022_수업.회원번호 WHERE(DB2022_수업.강사번호=? AND DB2022_회원.담당트레이너=?) ORDER BY DB2022_수업.수업시간, DB2022_수업.회원번호"); // 수업 진행 현황이 같은 것 끼리뭉쳐서 반환
			// 수업 시간이 빠른 것 먼저 보여줌 -> 회원번호에 맞추어 정렬
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
	// 로그인한 trainer의 모든 정보를 보여줌
	public void trainerInfoAll(DefaultTableModel trainer_table, String trainer_id) {
		try {

			pst = con.prepareStatement("SELECT 강사번호, 이름, 헬스장번호, 담당회원수, 총근무시간 FROM DB2022_트레이너 USE INDEX (강사번호인덱스) WHERE(강사번호=?)");
			
			pst.setString(1, trainer_id);
			rs = pst.executeQuery();
			while (rs.next()) {
				Object info[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)};
				trainer_table.addRow(info); // DefaultTableModel에 row 추가 (데이터 보여주기 위해서)
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	// trainer 고유 번호 생성을 위해서 공통 뒷번호 4자리를 갖는 트레이너가 존재하는지 확인 (있다면 고유 pk 생성을 위해서 인원수 return)
	public int checkPhoneNum(String num) {
		int cnt = 0;
		try {
			pst = con.prepareStatement("SELECT COUNT (*) FROM (SELECT substring(화원번호, 2, 4) AS '뒷자리' FROM DB2022_회원) 뒷자리 WHERE(뒷자리=?)");
			pst.setString(1, num);
			rs = pst.executeQuery();
			if (rs.next()) {
				cnt += 1;
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return cnt+1; // 해당 번호 뒷 4자리에 해당하는 등록된 트레이너가 몇명인지 integer의 형태로 반환한다.
	}

	
	// trainer의 모든 예약 완료 수업 내역 조회 (불참 / 완료 로 바꾸기 위해)
	public void trainerClassAll(DefaultTableModel class_table, String tID) {
		String query1 = "SELECT DB2022_회원.이름, DB2022_수업.수업시간, DB2022_수업.수업진행현황 FROM DB2022_수업 INNER JOIN DB2022_회원 ON DB2022_회원.회원번호=DB2022_수업.회원번호 WHERE(DB2022_수업.강사번호=? AND DB2022_회원.담당트레이너=? AND DB2022_수업.수업진행현황='예약확인중')";

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
				class_table.addRow(info); // DefaultTableModel에 row 추가 (데이터 보여주기 위해서)
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			closeDB();
		}
	}
	// trainer의 모든 수입 계산
	public void calculateSalary(DefaultTableModel salary_table, String tID) {
		ResultSet students = null; // 담당하고 있는 모든 학생
		String q1 = "SELECT 이름, 현재회원권, 소속헬스장 FROM DB2022_회원 WHERE(담당트레이너=?)";
		int total = 0; // 총 금액 계산
		String results[][] = new String[100][3]; // 회원번호, 회원권, 소속헬스장 저장
		int cnt = 0;
		try {
			PreparedStatement st = con.prepareStatement(q1);
			st.setString(1, tID);
			ResultSet rs = st.executeQuery();
			if (rs.getRow() == -1) {
				System.out.println("담당하고 있는 학생이 없습니다.");
			}
			else {
				System.out.println("담당하고 있는 회원이 있습니다.");
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
		String q2 = "SELECT 1회가격, 10회가격, 20회가격 FROM DB2022_가격 USE INDEX (헬스장번호) WHERE(헬스장번호=?)";
		for (int i = 0;i<cnt;i++) {
			try {
				PreparedStatement st1 = con.prepareStatement(q2);
				st1.setString(1, results[i][2]);
				students = st1.executeQuery();
				int cost = 0;
				while (students.next()) {
					if (results[i][1].equals("1회권"))cost= students.getInt(1);
					else if (results[i][1].equals("10회권"))cost = students.getInt(1) * 10;
					else cost = students.getInt(2) * 20;
					total += cost;
					// System.out.println(results[i][0] + " 학생에게 " + Integer.toString(cost) + "원 만큼 받습니다.");
					String[] data = {results[i][0], Integer.toString(cost)};
					salary_table.addRow(data);
				}
			}catch(SQLException e) {
				e.getStackTrace();
			}
		}
		String[] data = {"모든 수입", Integer.toString(total)}; // 마지막으로 모든 총 수입 정보를 추가한다.
		salary_table.addRow(data);
	}
	// trainer의 예약 완료 수업 내역 중 선택한 수업의 진행 현황 수정
	

	public void rejectClass(JTable class_jt, String student_no, String class_t, String status, String trainer_pk) {
		// 예약 거절 (숫자 불변 / 수업 삭제)
		try {
			pst = con.prepareStatement("DELETE FROM DB2022_수업 WHERE(회원번호=? AND 수업시간=? AND 강사번호=?)");
			pst.setString(1, student_no);pst.setString(2, class_t);pst.setString(3, trainer_pk);
			pst.executeUpdate();
			
			
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			JOptionPane.showMessageDialog(class_jt, "수업 예약을 거절 완료했습니다.");
		}
	}
	public void acceptClass(JTable class_jt, String student_name, String student_no, String class_t, String status, String trainer_pk) {
		// 예약 수락 (회원 남은 수업 횟수 -1 : 예약 수락되는 시점에 무조건 회원 남은 횟수는 차감)
		PreparedStatement pstDetail = null;
		try {
			con.setAutoCommit(false); // transaction 시작
			pst = con.prepareStatement("UPDATE DB2022_수업 SET 수업진행현황='예약완료' WHERE(회원번호=? AND 수업시간=? AND 강사번호=?)");
			pst.setString(1,  student_no);
			pst.setString(2, class_t);
			pst.setString(3, trainer_pk);
			pst.executeUpdate();
			
			pstDetail = con.prepareStatement("UPDATE DB2022_회원 SET 남은횟수=남은횟수-1 WHERE(회원번호=?)");
			pstDetail.setString(1,  student_no);
			pstDetail.executeUpdate();
			
			con.commit();
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
			JOptionPane.showMessageDialog(class_jt,student_name + "과의 수업 예약을 수락 완료했습니다.");
		}
		
	}
	
	public void endClass(JTable class_jt, String student_no, String class_t, String status, String trainer_pk) {
		// 수업 완료 (강사 근무시간 +1)
		PreparedStatement pstDetail = null;
		try {
			con.setAutoCommit(false); // transaction 시작
			pst = con.prepareStatement("UPDATE DB2022_수업 SET 수업진행현황='완료' WHERE(회원번호=? AND 수업시간=? AND 강사번호=?)");
			pst.setString(1,  student_no);
			pst.setString(2, class_t);
			pst.setString(3, trainer_pk);
			pst.executeUpdate();
			
			pstDetail = con.prepareStatement("UPDATE DB2022_트레이너 SET 총근무시간=총근무시간+1 WHERE(강사번호=?)");
			pstDetail.setString(1,  trainer_pk);
			pstDetail.executeUpdate();
			
			con.commit();
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
			JOptionPane.showMessageDialog(class_jt, class_t+"에 진행된 수업을 완료했습니다.");
		}
		
	}
	

	public void noshowClass(JTable class_jt, String student_no, String class_t, String status, String trainer_pk) {
		// 수업 불참 (강사 근무시간 +1)
		PreparedStatement pstDetail = null;
		boolean noshow_valid = true;
		if (status.equals("예약완료")==false) { 
			JOptionPane.showMessageDialog(class_jt, "<예약완료>인 수업만 <불참>으로 변경이 가능합니다.");
			return;
		}
		try {
			PreparedStatement hdiff = con.prepareStatement("SELECT TIMESTAMPDIFF(HOUR, (SELECT 수업시간 FROM DB2022_수업 WHERE(수업시간=? AND 강사번호=?)), now())"); // now() - 수업 시간
			hdiff.setString(1, class_t);
			hdiff.setString(2, trainer_pk);
			ResultSet trs = hdiff.executeQuery();
			if (trs.next()) {
				System.out.println(trs.getInt(1));
				if (trs.getInt(1) <= 1) { // 수업 시작 시간 + 수업 진행 시간 만큼이 지나지 않은 경우
					noshow_valid = false;
					JOptionPane.showMessageDialog(class_jt, "수업 시간이 끝나지 않았기 때문에 <불참>으로 변경할 수 없습니다.");
					return;
			}}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		if (noshow_valid) { // 불참으로 현황을 변경할 수 있는 경우에
			try {
				con.setAutoCommit(false); // transaction 시작
				pst = con.prepareStatement("UPDATE DB2022_수업 SET 수업진행현황='불참' WHERE(회원번호=? AND 수업시간=? AND 강사번호=?)");
				pst.setString(1,  student_no);
				pst.setString(2, class_t);
				pst.setString(3, trainer_pk);
				pst.executeUpdate();
			
				pstDetail = con.prepareStatement("UPDATE DB2022_트레이너 SET 총근무시간=총근무시간+1 WHERE(강사번호=?)");
				pstDetail.setString(1,  trainer_pk);
				pstDetail.executeUpdate();
			
				con.commit(); // transaction 수행
				con.setAutoCommit(true); // 다시 auto commit 가능하게 변경
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
				JOptionPane.showMessageDialog(class_jt, class_t+"에 진행된 수업에 회원이 불참하였습니다.");
			}
		}
		
	}
	
	
}
