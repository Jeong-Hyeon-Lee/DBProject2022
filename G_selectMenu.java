package DB2022TEAM03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class G_selectMenu extends JFrame {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03";

	/*
	// Database user, password
	static final String USER = "root";
	static final String PASSWORD = "0000";
	*/

	//Database user, password
	static final String USER = "DB2022Team03";
	static final String PASSWORD= "DB2022Team03";

	static Connection conn;
	
	public G_selectMenu(String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		
		setTitle("헬스장 PT 예약 시스템 (관장)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫으면 프로그램 종료

		JPanel title = new JPanel();

		JLabel selectMenu = new JLabel("MENU");
		selectMenu.setForeground(new Color(5, 0, 153));
		selectMenu.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		title.add(selectMenu);

		JPanel btnpanel = new JPanel();
		btnpanel.setLayout(new GridLayout(4, 1));

		JButton menu1 = new JButton("가격 정보 수정하기");
		JButton menu2 = new JButton("프로모션 정보 수정하기");
		JButton menu3 = new JButton("트레이너 정보 출력하기");
		JButton menu4 = new JButton("회원 수 출력하기");

		btnpanel.add(menu1);
		btnpanel.add(menu2);
		btnpanel.add(menu3);
		btnpanel.add(menu4);

		add(title, BorderLayout.NORTH);
		add(btnpanel, BorderLayout.CENTER);

		setBounds(200, 200, 400, 250);

		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);

		// 이벤트 처리
		menu1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new G_changePriceInformation(conn, gymID);
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		menu2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new G_changePromotionInformation(conn, gymID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		menu3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new G_showTrainers(conn, gymID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		
		menu4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new G_countTrainees(conn, gymID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}