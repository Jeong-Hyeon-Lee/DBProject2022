package DB2022TEAM03;

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
		// ���� ���� �����ϱ�

		setTitle("���� ���� �����ϱ�");

		PreparedStatement pStmt1 = conn.prepareStatement("select �ｺ���ȣ from DB2022_���� where �ｺ���ȣ = ?");
		pStmt1.setString(1, gymID);
		ResultSet rs = pStmt1.executeQuery();

		while (rs.next()) { // �����ͺ��̽��� ���� �ｺ���̶�� ���� �ȵ�
			rs.getString(1);
			PreparedStatement pStmt0 = conn
					.prepareStatement("select 1ȸ����, 10ȸ����, 20ȸ���� from DB2022_���� where �ｺ���ȣ = ?");
			pStmt0.setString(1, gymID);
			ResultSet priceRS = pStmt0.executeQuery();

			JPanel text = new JPanel();
			JLabel priceInfo = new JLabel();
			
			while (priceRS.next()) {
				int a = priceRS.getInt(1);
				int b = priceRS.getInt(2);
				int c = priceRS.getInt(3);

				priceInfo.setText("���簡��: 1ȸ-" + a + " 10ȸ-" + b + " 20ȸ-" + c);
				priceInfo.setForeground(new Color(5, 0, 153));
				priceInfo.setFont(new Font("���� ���", Font.BOLD, 10));
				text.add(priceInfo);
			}

			JPanel btnpanel = new JPanel();
			btnpanel.setLayout(new GridLayout(3, 1));

			JButton menu1 = new JButton("1ȸ ���� �����ϱ�");
			JButton menu2 = new JButton("10ȸ ���� �����ϱ�");
			JButton menu3 = new JButton("20ȸ ���� �����ϱ�");

			btnpanel.add(menu1);
			btnpanel.add(menu2);
			btnpanel.add(menu3);

			add(text, BorderLayout.NORTH);
			add(btnpanel, BorderLayout.CENTER);

			setBounds(200, 200, 400, 250);

			setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

			setVisible(true);

			// �̺�Ʈ ó��
			menu1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String s = JOptionPane.showInputDialog("������ ���� �Է��ϱ�"); //String ���·� ����, ���߿� Integer.parseInt �����
					
					if(s == null || s.equals(""))
						return;
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_���� set 1ȸ���� = ? where �ｺ���ȣ = ?");
						int newPrice = Integer.parseInt(s);
						pStmt2.setInt(1, newPrice);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
						
						ResultSet priceRS2 = pStmt0.executeQuery();
						while (priceRS2.next()) {
							int a = priceRS2.getInt(1);
							int b = priceRS2.getInt(2);
							int c = priceRS2.getInt(3);

							priceInfo.setText("���簡��: 1ȸ-" + a + " 10ȸ-" + b + " 20ȸ-" + c);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			menu2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String s = JOptionPane.showInputDialog("������ ���� �Է��ϱ�"); //String ���·� ����, ���߿� Integer.parseInt �����
					
					if(s == null || s.equals(""))
						return;
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_���� set 10ȸ���� = ? where �ｺ���ȣ = ?");
						int newPrice = Integer.parseInt(s);
						pStmt2.setInt(1, newPrice);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
						
						ResultSet priceRS2 = pStmt0.executeQuery();
						while (priceRS2.next()) {
							int a = priceRS2.getInt(1);
							int b = priceRS2.getInt(2);
							int c = priceRS2.getInt(3);

							priceInfo.setText("���簡��: 1ȸ-" + a + " 10ȸ-" + b + " 20ȸ-" + c);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			menu3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String s = JOptionPane.showInputDialog("������ ���� �Է��ϱ�"); //String ���·� ����, ���߿� Integer.parseInt �����
					
					if(s == null || s.equals(""))
						return;
					
					try {
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_���� set 20ȸ���� = ? where �ｺ���ȣ = ?");
						int newPrice = Integer.parseInt(s);
						pStmt2.setInt(1, newPrice);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
						
						ResultSet priceRS2 = pStmt0.executeQuery();
						while (priceRS2.next()) {
							int a = priceRS2.getInt(1);
							int b = priceRS2.getInt(2);
							int c = priceRS2.getInt(3);

							priceInfo.setText("���簡��: 1ȸ-" + a + " 10ȸ-" + b + " 20ȸ-" + c);
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