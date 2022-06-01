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
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("회원MENU-트레이너");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//메뉴판
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(3,1));
		
		//상세 메뉴
		JPanel Menu1 = new JPanel();
		JButton searchTBtn = new JButton("트레이너 검색하기(전체헬스장)");
		Menu1.add(searchTBtn);

		JPanel Menu2 = new JPanel();
		JButton showTBtn = new JButton("트레이너 확인하기(소속헬스장)");
		Menu2.add(showTBtn);

		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
		Menu9.add(undo);

		//메뉴판에 메뉴붙이기
		btnGroup.add(Menu1);
		btnGroup.add(Menu2);
		btnGroup.add(Menu9);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,400,250);
		
		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
		
		//Btn click 이벤트
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); // 현재의 frame을 종료시키는 메서드.

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
				dispose(); // 현재의 frame을 종료시키는 메서드.

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
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
	
	}	
}
