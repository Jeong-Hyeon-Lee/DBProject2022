package DB2022Team03.EUNSOO;

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

import DB2022Team03.GEUNJU.M_MainScreen;

public class M_manageClass extends JFrame {
/*
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03"; 
	public static final String USER = "DB2022Team03";
	public static final String PASS = "DB2022Team03";
	public static final String ID = "M22380";
	public Connection conn;
*/
	/*
	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		new M_manageClass(conn,ID);
	}
	*/
	public M_manageClass(Connection conn, String ID) {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//Title
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("수업관리 MENU");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//메뉴판
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(5,1));
		
		//상세 메뉴
		// Cancel Class
		JPanel jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		JPanel Menu8 = new JPanel();
		JButton btn_cancel = new JButton("수업 취소하기");
		Menu8.add(btn_cancel);
		jp4.add(Menu8);
		
		// Reserve Class
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JPanel Menu1 = new JPanel();
		JButton btn_reserve = new JButton("수업 예약하기");
		Menu1.add(btn_reserve);
		jp1.add(Menu1);
		
		// See future classes		
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel Menu2 = new JPanel();
		JButton btn_seeReserved = new JButton("예약된 수업 조회");
		Menu2.add(btn_seeReserved);
		jp2.add(Menu2);
		
		// See past classes
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		
		JPanel Menu3 = new JPanel();
		JButton btn_seePast = new JButton("과거 수업 조회");
		Menu3.add(btn_seePast);
		jp3.add(Menu3);
		
		
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
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
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_cancelClass(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("마이페이지 로딩 실패:"+e1);
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		btn_reserve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_reserveClass(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		btn_seeReserved.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_seeFutureClasses(conn,ID);
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
				new M_MainScreen(conn, ID);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		btn_seePast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_seePastClasses(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		
	}	
}
