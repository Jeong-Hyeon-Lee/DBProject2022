package DB2022Team03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class G_changePriceInformation extends JFrame {

	public G_changePriceInformation(Connection conn, String gymID) throws SQLException, IOException {
		// TODO Auto-generated constructor stub
		// 가격 정보 수정하기

		setTitle("가격 정보 수정하기");

		PreparedStatement pStmt1 = conn.prepareStatement("select 헬스장번호 from DB2022_가격 where 헬스장번호 = ?");
		pStmt1.setString(1, gymID);
		ResultSet rs = pStmt1.executeQuery();

		while (rs.next()) { // 데이터베이스에 없는 헬스장이라면 실행 안됨
			rs.getString(1);
			PreparedStatement pStmt0 = conn
					.prepareStatement("select 1회가격, 10회가격, 20회가격 from DB2022_가격 where 헬스장번호 = ?");
			pStmt0.setString(1, gymID);
			ResultSet priceRS = pStmt0.executeQuery();

			JPanel text = new JPanel();

			while (priceRS.next()) {
				int a = priceRS.getInt(1);
				int b = priceRS.getInt(2);
				int c = priceRS.getInt(3);

				JLabel priceInfo = new JLabel("현재가격: 1회-" + a + " 10회-" + b + " 20회-" + c);
				priceInfo.setForeground(new Color(5, 0, 153));
				priceInfo.setFont(new Font("맑은 고딕", Font.BOLD, 10));
				text.add(priceInfo);
			}

			JPanel btnpanel = new JPanel();
			btnpanel.setLayout(new GridLayout(3, 1));

			JButton menu1 = new JButton("1회 가격 수정하기");
			JButton menu2 = new JButton("10회 가격 수정하기");
			JButton menu3 = new JButton("20회 가격 수정하기");

			btnpanel.add(menu1);
			btnpanel.add(menu2);
			btnpanel.add(menu3);

			add(text, BorderLayout.NORTH);
			add(btnpanel, BorderLayout.CENTER);

			setBounds(200, 200, 400, 250);

			setResizable(false); // 화면 크기 고정하는 작업

			setVisible(true);

			// 이벤트 처리
			menu1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int newPrice = Integer.parseInt(JOptionPane.showInputDialog("수정할 가격 입력하기"));
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_가격 set 1회가격 = ? where 헬스장번호 = ?");
						pStmt2.setInt(1, newPrice);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			menu2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int newPrice = Integer.parseInt(JOptionPane.showInputDialog("수정할 가격 입력하기"));
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_가격 set 10회가격 = ? where 헬스장번호 = ?");
						pStmt2.setInt(1, newPrice);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			menu3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int newPrice = Integer.parseInt(JOptionPane.showInputDialog("수정할 가격 입력하기"));
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_가격 set 20회가격 = ? where 헬스장번호 = ?");
						pStmt2.setInt(1, newPrice);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			

		}
	}
}