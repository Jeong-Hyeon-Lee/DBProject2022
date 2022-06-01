package DB2022Team03;

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

public class G_changePromotionInformation extends JFrame {
	public G_changePromotionInformation(Connection conn, String gymID) throws SQLException {
		// TODO Auto-generated constructor stub
		setTitle("프로모션 정보 수정하기");
		PreparedStatement pStmt1 = conn.prepareStatement("select 헬스장번호 from DB2022_가격 where 헬스장번호 = ?");
		pStmt1.setString(1, gymID);
		ResultSet rs = pStmt1.executeQuery();

		while (rs.next()) { //데이터베이스에 없는 헬스장이라면 실행 안됨
			rs.getString(1);

			PreparedStatement pStmt0 = conn.prepareStatement("select 기타프로모션설명 from DB2022_가격 where 헬스장번호 = ?");
			pStmt0.setString(1, gymID);
			ResultSet promoRS = pStmt0.executeQuery();

			JPanel text = new JPanel();
			JLabel promo = new JLabel();
			
			while (promoRS.next()) {
				String s = promoRS.getString(1);
				promo.setText(s);
				promo.setForeground(new Color(5, 0, 153));
				promo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
				text.add(promo);
			}

			JButton button = new JButton("수정하기");

			add(text);
			add(button);

			setLayout(new FlowLayout());

			setBounds(200, 200, 400, 250);

			setVisible(true);

			// 이벤트 처리
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String newPromotion = JOptionPane.showInputDialog("프로모션 수정: ");
					
					if(newPromotion == null || newPromotion.equals(""))
						return;
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_가격 set 기타프로모션설명 = ? where 헬스장번호 = ?");
						pStmt2.setString(1, newPromotion);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
						
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
