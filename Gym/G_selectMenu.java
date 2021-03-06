package DB2022Team03.Gym;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import DB2022Team03.Main.DeleteScreen;
import DB2022Team03.Main.StartScreen;

//관장으로 로그인했을 때 보이는 메뉴 화면
public class G_selectMenu extends JFrame {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03";

	//Database user, password
	static final String USER = "DB2022Team03";
	static final String PASSWORD= "DB2022Team03";
	
	static Connection conn;
	
	public G_selectMenu(String gymID) throws SQLException{
		// TODO Auto-generated constructor stub
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); //데이터베이스 접속
		
		setTitle("헬스장 통합 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//화면 상단
		JPanel top = new JPanel();
		
		//"MENU" 글씨 띄우기
		JLabel selectMenu = new JLabel("MENU");
		selectMenu.setForeground(new Color(5, 0, 153));
		selectMenu.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		top.add(selectMenu);
		
		//로그아웃, 탈퇴 버튼
		JPanel menu0 = new JPanel();
		JButton logout = new JButton("로그아웃");
		menu0.add(logout);
		JButton deleteAccount = new JButton("탈퇴하기");
		menu0.add(deleteAccount);
		menu0.setLayout(new GridLayout(2, 1));
		top.add(menu0);
		top.setLayout(new FlowLayout());

		JPanel btnpanel = new JPanel();
		btnpanel.setLayout(new GridLayout(4, 1));
		
		//메뉴 버튼
		JButton menu1 = new JButton("가격 정보 수정하기");
		JButton menu2 = new JButton("프로모션 정보 수정하기");
		JButton menu3 = new JButton("트레이너 정보 보기");
		JButton menu4 = new JButton("회원 수 보기");

		btnpanel.add(menu1);
		btnpanel.add(menu2);
		btnpanel.add(menu3);
		btnpanel.add(menu4);

		add(top, BorderLayout.NORTH);
		add(btnpanel, BorderLayout.CENTER);

		setBounds(200, 200, 400, 250);

		setResizable(false); // 화면 크기 고정

		setVisible(true);

		// 이벤트 처리
				menu1.addActionListener(new ActionListener() { //가격 정보 수정하기 버튼 누를 시
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

				menu2.addActionListener(new ActionListener() { //프로모션 정보 수정하기 버튼 누를 시

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

				menu3.addActionListener(new ActionListener() { //트레이너 정보 보기 버튼 누를 시

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
				
				menu4.addActionListener(new ActionListener() { //회원 수 보기 버튼 누를 시

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							new G_countTrainees(conn, gymID, rootPane);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				logout.addActionListener(new ActionListener() { //로그아웃 버튼 누를 시

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new StartScreen(conn);
						dispose(); //현재 창 닫기
					}
				});
				
				deleteAccount.addActionListener(new ActionListener() { //탈퇴하기 버튼 누를 시

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new DeleteScreen(conn, "관장", gymID);
						dispose(); //현재 창 닫기
					}
				});
			}
		}
