package DB2022Team03.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class StartScreen extends JFrame {

	public static String userType; //ȸ��, Ʈ���̳�, ���� �� �ϳ�
	
	public StartScreen(Connection conn) {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����

		JPanel title = new JPanel();

		// title �����̳ʿ� �� ������Ʈ�� ����� ����.
		JLabel login = new JLabel("�ｺ�� PT ���� �ý���");
		login.setForeground(new Color(5, 0, 153));
		login.setFont(new Font("���� ���", Font.BOLD, 25));
		title.add(login);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());


		JPanel MemberPanel = new JPanel();
		JButton member = new JButton("ȸ��");

		JPanel TrainerPanel = new JPanel();
		JButton trainer = new JButton("Ʈ���̳�");

		JPanel OwnerPanel = new JPanel();
		JButton owner = new JButton("����");
		
		MemberPanel.add(member);
		TrainerPanel.add(trainer);
		OwnerPanel.add(owner);

		jp1.add(MemberPanel);
		jp1.add(TrainerPanel);
		jp1.add(OwnerPanel);

		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		jp2.add(jp1);

		setLayout(new BorderLayout());

		add(title, BorderLayout.NORTH);
		add(jp2, BorderLayout.CENTER);

		setBounds(200, 200, 400, 250);

		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);

		// �̺�Ʈ ó��
		member.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				userType = "ȸ��";
				new LoginScreen(conn, userType);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		
		trainer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				userType = "Ʈ���̳�";
				new LoginScreen(conn, userType);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		
		owner.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				userType = "����";
				new LoginScreen(conn, userType);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
	}
}