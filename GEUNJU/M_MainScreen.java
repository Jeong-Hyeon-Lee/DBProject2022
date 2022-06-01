package DB2022TEAM03.GEUNJU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DB2022TEAM03.StartScreen;

public class M_MainScreen extends JFrame {
	
	public static final String URL = "jdbc:mysql://localhost/DB2022TEAM03";
	public static final String USER = "db2022team03";
	public static final String PASS = "db2022team03";
	public static String ID = "tttttt";
	public Connection conn;
	
	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection(URL,USER,PASS);
		new M_MainScreen(conn,ID);
	}
	
	public M_MainScreen(Connection conn,String ID) {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("회원MENU");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//메뉴판
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(5,1));
		
		//상세 메뉴
		//회원정보
		JPanel jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		JPanel Menu8 = new JPanel();
		JButton myPageBtn = new JButton("회원정보확인하기");
		Menu8.add(myPageBtn);
		jp4.add(Menu8);
		
		//헬스장
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JPanel Menu1 = new JPanel();
		JButton M_GScreen = new JButton("헬스장 찾기");
		Menu1.add(M_GScreen);
		jp1.add(Menu1);
		
		//트레이너		
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel Menu2 = new JPanel();
		JButton M_TScreen = new JButton("트레이너 찾기");
		Menu2.add(M_TScreen);
		jp2.add(Menu2);
		
		//회원권
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		
		JPanel Menu3 = new JPanel();
		JButton enrollMembership = new JButton("회원권 등록/변경");
		Menu3.add(enrollMembership);
		jp3.add(Menu3);
		
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("로그아웃");
		Menu9.add(undo);
		jp0.add(Menu9);

		//메뉴판에 메뉴붙이기
		btnGroup.add(jp4);
		btnGroup.add(jp1);
		btnGroup.add(jp2);
		btnGroup.add(jp3);
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,300,400);
		
		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
	
		//Btn click 이벤트
		myPageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_myPage(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("마이페이지 로딩 실패:"+e1);
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		M_GScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_searchGYM(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		M_TScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_searchTrainer(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});		
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new StartScreen();
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		enrollMembership.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_enrollMembership(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
	}	
}
