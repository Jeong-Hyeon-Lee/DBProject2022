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

//�ｺ�� ���θ�� ���� �����ϱ�
public class G_changePromotionInformation extends JFrame {
	public G_changePromotionInformation(Connection conn, String gymID) throws SQLException {
		// TODO Auto-generated constructor stub
		setTitle("���θ�� ���� �����ϱ�");
		
		//DB2022_���� ���̺��� �ｺ�� ��ȣ�� 'gymID'�� ���� tuple�� �ｺ�� ��ȣ�� rs�� ����
		//�ｺ�� ��ȣ�� DB2022_���� ���̺��� primary key �̹Ƿ� rs�� ����Ǵ� tuple�� 1��
		PreparedStatement pStmt1 = conn.prepareStatement("select �ｺ���ȣ from DB2022_���� use index (�ｺ���ȣ) where �ｺ���ȣ = ?");
		pStmt1.setString(1, gymID);
		ResultSet rs = pStmt1.executeQuery();

		while (rs.next()) { //�����ͺ��̽��� ���� �ｺ���̶�� ���� �ȵ�
			rs.getString(1); //ù��° column ��(�ｺ�� ��ȣ) String���� ��ȯ

			//DB2022_���� ���̺��� �ｺ�� ��ȣ�� ���ڷ� ���� �ｺ�� ��ȣ�� ���� tuple�� ��Ÿ���θ�Ǽ����� promoRS�� ����
			PreparedStatement pStmt0 = conn.prepareStatement("select ��Ÿ���θ�Ǽ��� from DB2022_���� use index (�ｺ���ȣ) where �ｺ���ȣ = ?");
			pStmt0.setString(1, gymID);
			ResultSet promoRS = pStmt0.executeQuery();

			//���θ�� ������ ����� ���� �����Ƿ� JTextArea ����ϰ� scroll ���, �ڵ� �ٹٲ� ��� ����
			JTextArea promo = new JTextArea(8,20);
			JScrollPane scroll = new JScrollPane(promo,
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			promo.setLineWrap(true); // �ڵ� �ٹٲ� ���
			
			//��Ÿ���θ�� ������ JTextArea�� �߰�
			while (promoRS.next()) {
				String s = promoRS.getString(1);
				promo.setText(s);
				promo.setForeground(new Color(5, 0, 153));
				promo.setFont(new Font("���� ���", Font.BOLD, 15));
				add(promo);
			}
			
			//�����ϱ� ��ư
			JButton button = new JButton("�����ϱ�");
			add(button);

			setLayout(new FlowLayout());

			setBounds(200, 200, 400, 250);

			setVisible(true);

			// �̺�Ʈ ó��
			button.addActionListener(new ActionListener() { //�����ϱ� ��ư ���� ��
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String newPromotion = JOptionPane.showInputDialog("���θ�� ����: ");
					
					if(newPromotion == null || newPromotion.equals("")) //�ƹ��͵� �� �Է��ϰų� ��� ��ư ���� ��� ���� ����
						return;
					
					try {
						//DB2022_���� ���̺��� �ｺ�� ��ȣ�� 'gymID'�� ���ٸ� ��Ÿ���θ�Ǽ��� ���� ������ �Է¹��� ������ update
						PreparedStatement pStmt2 = conn.prepareStatement("update DB2022_���� set ��Ÿ���θ�Ǽ��� = ? where �ｺ���ȣ = ?");
						pStmt2.setString(1, newPromotion);
						pStmt2.setString(2, gymID);
						pStmt2.executeUpdate();
						
						//ȭ�鿡 ���̴� ���� update�� ���� �°� ����
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