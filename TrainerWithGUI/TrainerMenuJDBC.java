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
	static final String DB_URL = "jdbc:mysql://localhost/DB2022Team03"; // database url
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

	public int checkLogin(String tID, String tPW) {
		String loginquery = "SELECT 비밀번호, 이름, 강사번호, 헬스장번호, 담당회원수, 총근무시간 "
				+ "FROM DB2022_트레이너" + " WHERE (강사번호=?)";
		boolean login_success = false;
		try {
			pst = con.prepareStatement(loginquery);
			pst.setString(1,  tID);
			rs = pst.executeQuery();
			if (rs.getRow() == -1) {
				JOptionPane.showMessageDialog(null, "아이디가 없거나, 올바르지 않은 비밀번호입니다. 다시 로그인해주세요.");
			}
			while (rs.next()) {
				if (rs.getString(1).equals(tPW)) {
					login_success = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "아이디가 없거나, 올바르지 않은 비밀번호입니다. 다시 로그인해주세요.");
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if (login_success)return 1;
		else return 0;
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
	public void classInfoAll(DefaultTableModel class_table, String trainer_id) {
		try {
			pst = con.prepareStatement("SELECT DB2022_회원.이름, DB2022_수업.수업시간, DB2022_수업.수업진행현황, DB2022_회원.회원번호 FROM DB2022_수업 INNER JOIN DB2022_회원 ON DB2022_회원.회원번호=DB2022_수업.회원번호 WHERE(DB2022_수업.강사번호=? AND DB2022_회원.담당트레이너=?)"); // 수업 진행 현황이 같은 것 끼리뭉쳐서 반환
			// 수업 시간이 빠른 것 먼저 보여줌
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
			pst = con.prepareStatement("SELECT 강사번호, 이름, 헬스장번호, 담당회원수, 총근무시간 FROM DB2022_트레이너 WHERE(강사번호=?)");
			
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
	// trainer 고유 번호 생성을 위해서 공통 뒷번호 4자리를 갖는 트레이너가 존재하는지 확인
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
	// 회원가입
	public int Join(String myGym, String id, String name, String pwd) {
		String q = "INSERT INTO DB2022_트레이너(헬스장번호, 강사번호, 이름, 비밀번호) VALUES (?,?,?,?)";
		boolean success = false;
		try {
			pst = con.prepareStatement(q);
			pst.setString(1, myGym);
			pst.setString(2, id);
			pst.setString(3, name);
			pst.setString(4, pwd);
			pst.executeUpdate();
			success = true;
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		if (success)return 1;
		else return 0;
	}
	
	
	
	// trainer의 모든 예약 완료 수업 내역 조회 (불참 / 완료 로 바꾸기 위해)
	public void trainerClassAll(DefaultTableModel class_table, String tID) {
		String query1 = "SELECT DB2022_회원.이름, DB2022_수업.수업시간, DB2022_수업.수업진행현황 FROM DB2022_수업 INNER JOIN DB2022_회원 ON DB2022_회원.회원번호=DB2022_수업.회원번호 WHERE(DB2022_수업.강사번호=? AND DB2022_회원.담당트레이너=? AND DB2022_수업.수업진행현황='예약확인중')";
		String query2 = "UPDATE DB2022_수업 SET 수업진행현황='예약완료'";
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
		String q2 = "SELECT 1회가격, 10회가격, 20회가격 FROM DB2022_가격 WHERE(헬스장번호=?)";
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
	public void changeClassStatus(String student_no, String class_t, String status, String trainer_pk, String fieldName) {
		String q = null;
		System.out.println(fieldName);
		if (fieldName.equals("거절") || fieldName.equals("취소")) {
			q = "DELETE FROM DB2022_수업 WHERE(회원번호=? AND 수업시간=? AND 강사번호=?)";
		}
		else {
			q = "UPDATE DB2022_수업 SET 수업진행현황=? WHERE(회원번호=? AND 수업시간=? AND 강사번호=?)";
		}
		try {
			pst = con.prepareStatement(q);
			if (fieldName.equals("거절") || fieldName.equals("취소")) {
				pst.setString(1, student_no);
				pst.setString(2, class_t);
				pst.setString(3,  trainer_pk);
			}
			else {
				pst.setString(1,  fieldName);
				pst.setString(2, student_no);
				pst.setString(3, class_t);
				pst.setString(4, trainer_pk);
			}
			pst.executeUpdate();
			
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			closeDB();
		}
		
	}
	// trainer의 모든 예약 대기 중 수업 내역 조회 (수락 / 거절 하기 위해)
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
