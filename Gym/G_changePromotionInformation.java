package DB2022Team03.Gym;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

//헬스장 프로모션 정보 수정하기
public class G_changePromotionInformation extends JFrame {
	public G_changePromotionInformation(Connection conn, String gymID) throws SQLException {
		// TODO Auto-generated constructor stub
		setTitle("헬스장 통합 관리 프로그램");
		
		//DB2022_가격 테이블에서 헬스장 번호가 'gymID'와 같은 tuple의 헬스장 번호를 rs에 저장
		//헬스장 번호가 DB2022_가격 테이블의 primary key 이므로 rs에 저장되는 tuple은 1개
		PreparedStatement pStmt1 = conn.prepareStatement("select 헬스장번호 from DB2022_가격 use index (헬스장번호) where 헬스장번호 = ?");
		pStmt1.setString(1, gymID);
		ResultSet rs = pStmt1.executeQuery();

		while (rs.next()) { //데이터베이스에 없는 헬스장이라면 실행 안됨
			rs.getString(1); //첫번째 column 값(헬스장 번호) String으로 반환

			//DB2022_가격 테이블에서 헬스장 번호가 인자로 받은 헬스장 번호와 같은 tuple의 기타프로모션설명을 promoRS에 저장
			PreparedStatement pStmt0 = conn.prepareStatement("select 기타프로모션설명 from DB2022_가격 use index (헬스장번호) where 헬스장번호 = ?");
			pStmt0.setString(1, gymID);
			ResultSet promoRS = pStmt0.executeQuery();

			//프로모션 정보가 길어질 수도 있으므로 JTextArea 사용하고 scroll 기능, 자동 줄바꿈 기능 설정
			JTextArea promo = new JTextArea(8,20);
			JScrollPane scroll = new JScrollPane(promo,
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			promo.setLineWrap(true); // 자동 줄바꿈 기능
			
			//기타프로모션 설명을 JTextArea에 추가
			while (promoRS.next()) {
				String s = promoRS.getString(1);
				promo.setText(s);
				promo.setForeground(new Color(5, 0, 153));
				promo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
				add(promo);
			}
			
			//수정하기 버튼
			JButton button = new JButton("수정하기");
			add(button);

			setLayout(new FlowLayout());

			setBounds(200, 200, 400, 250);

			setVisible(true);

			// 이벤트 처리
			button.addActionListener(new ActionListener() { //수정하기 버튼 누를 시
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String newPromotion = JOptionPane.showInputDialog("프로모션 수정: ");
					
					if(newPromotion == null || newPromotion.equals("")) //아무것도 안 입력하거나 취소 버튼 누를 경우 오류 방지
						return;
					
					try {
						//DB2022_가격 테이블에서 헬스장 번호가 'gymID'와 같다면 기타프로모션설명 값을 위에서 입력받은 값으로 update
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_가격 set 기타프로모션설명 = ? where 헬스장번호 = ?");
						pStmt2.setString(1, newPromotion);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
						
						//화면에 보이는 정보 update된 값에 맞게 수정
						ResultSet promoRS2 = pStmt0.executeQuery();
						while (promoRS2.next()) {
							String s = promoRS2.getString(1);
							promo.setText(s);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

		}
	}
}