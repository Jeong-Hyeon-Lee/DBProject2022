package DB2022TEAM03.GEUNJU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class M_GScreen extends JFrame {
	public M_GScreen() {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("회원MENU-헬스장");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//메뉴판
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//상세 메뉴
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JPanel Menu1 = new JPanel();
		JButton recommendGYMBtn = new JButton("헬스장 추천받기");
		Menu1.add(recommendGYMBtn);
		jp1.add(Menu1);
		
		//JPanel jp2 = new JPanel();
		//jp2.setLayout(new FlowLayout());
		JPanel Menu2 = new JPanel();
		JButton searchGYMBtn = new JButton("헬스장 검색하기");
		Menu2.add(searchGYMBtn);
		jp1.add(Menu2);
		
		//JPanel jp3 = new JPanel();
		//jp3.setLayout(new FlowLayout());
		JPanel Menu3 = new JPanel();
		JButton enrollGYMBtn = new JButton("헬스장 등록하기");
		Menu3.add(enrollGYMBtn);
		jp1.add(Menu3);
		
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
		Menu9.add(undo);
		jp0.add(Menu9);

		//메뉴판에 메뉴붙이기
		btnGroup.add(jp1);
		//btnGroup.add(jp2);
		//btnGroup.add(jp3);
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,600,200);
		
		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
		
		//Btn click 이벤트
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen();
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
	
	}	
}
