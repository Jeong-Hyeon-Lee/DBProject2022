package DB2022Team03.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

//헬스장 트레이너 정보 보기
public class G_showTrainers extends JFrame{
	public G_showTrainers(Connection conn, String gymID) throws SQLException{

		//DB2022_트레이너 테이블에서 헬스장번호가 'gymID'와 같은 행 개수(=해당 헬스장의 트레이너 인원 수) 구하기
		PreparedStatement pStmt = conn.prepareStatement("SELECT count(*) FROM DB2022_트레이너 WHERE 헬스장번호 = ?");
		pStmt.setString(1, gymID);
		ResultSet rs = pStmt.executeQuery();
		int trainerNum = 0; //트레이너 인원 수
		if (rs.next()) //값이 존재한다면
			trainerNum = rs.getInt(1); //첫번째 column 값(count(*)) 정수로 반환
		
		setTitle("헬스장 통합 관리 프로그램");
		
		//JTable을 만들기 위해 (표 형식으로 보여주기)
		String [] title = {"트레이너 이름", "총 근무시간"};
		String [][] data = new String[trainerNum][2];
		
		//DB2022_트레이너 테이블에서 헬스장번호가 'gymID'와 같은 트레이너의 이름, 총 근무 시간 구하기
		PreparedStatement pStmt2 = conn.prepareStatement("SELECT 이름, 총근무시간 FROM DB2022_트레이너 WHERE 헬스장번호 = ?");
		pStmt2.setString(1, gymID);
		ResultSet rs2 = pStmt2.executeQuery();
		
		int i=0;
		while (rs2.next()) { //끝날 때까지 다음 레코드로 이동
			data[i][0] = rs2.getString(1); //이름 저장
			data[i][1] = rs2.getString(2); //총 근무 시간 저장
			i++;
		}
		
		//표 형식, 만일 길어질 경우 스크롤 가능하게
		JTable table = new JTable(data, title);
		JScrollPane scroll = new JScrollPane(table);
		add(scroll);
		
		setBounds(200, 200, 400, 250);


		setVisible(true);
	}
}