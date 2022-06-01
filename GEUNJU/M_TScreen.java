package DB2022TEAM03.GEUNJU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class M_TScreen extends JFrame {
	public M_TScreen(Connection conn, String ID) {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("ȸ��MENU-Ʈ���̳�");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//�޴���
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(3,1));
		
		//�� �޴�
		JPanel Menu1 = new JPanel();
		JButton searchTBtn = new JButton("Ʈ���̳� �˻��ϱ�(��ü�ｺ��)");
		Menu1.add(searchTBtn);

		JPanel Menu2 = new JPanel();
		JButton showTBtn = new JButton("Ʈ���̳� Ȯ���ϱ�(�Ҽ��ｺ��)");
		Menu2.add(showTBtn);

		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);

		//�޴��ǿ� �޴����̱�
		btnGroup.add(Menu1);
		btnGroup.add(Menu2);
		btnGroup.add(Menu9);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,400,250);
		
		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
		
		//Btn click �̺�Ʈ
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		searchTBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_searchTrainer(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		showTBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_searchGYM(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
	
	}	
}
