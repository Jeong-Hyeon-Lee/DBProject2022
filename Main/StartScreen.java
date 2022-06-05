package DB2022Team03.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class StartScreen extends JFrame {

	public static String userType; // 회원, 트레이너, 관장 중 하나

	public StartScreen(Connection conn) {
		setTitle("헬스장 통합 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 윈도우를 닫으면 프로그램 종료

		JPanel title = new JPanel();

		// title 컨테이너에 들어갈 컴포넌트를 만들어 보자.
		JLabel login = new JLabel("헬스장 통합 관리 프로그램");
		login.setForeground(new Color(5, 0, 153));
		login.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		title.add(login);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());

		JPanel MemberPanel = new JPanel();
		JButton member = new JButton("회원");

		JPanel TrainerPanel = new JPanel();
		JButton trainer = new JButton("트레이너");

		JPanel OwnerPanel = new JPanel();
		JButton owner = new JButton("관장");

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

		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);

		// 이벤트 처리
		member.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				userType = "회원";
				new LoginScreen(conn, userType);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});

		trainer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				userType = "트레이너";
				new LoginScreen(conn, userType);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});

		owner.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				userType = "관장";
				new LoginScreen(conn, userType);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
	}
}
