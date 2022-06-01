package DB2022TEAM03;

import javax.swing.*;

import com.mysql.cj.protocol.Resultset;

import DB2022TEAM03.GEUNJU.M_MainScreen;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;

public class LoginScreen extends JFrame {

	public static final String URL = "jdbc:mysql://localhost/DB2022TEAM03";
	public static final String USER = "db2022team03";
	public static final String PASS = "db2022team03";
	public static String ID = "tttttt";
	public Connection conn;
	
	public LoginScreen(String userType) {

		setTitle("로그인");

		JPanel title = new JPanel();

		JLabel login = new JLabel(userType + " 로그인");
		login.setForeground(new Color(5, 0, 153));
		login.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		title.add(login);

		// 아이디 text field
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(2, 2));

		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb1 = new JLabel("아이디 : ", JLabel.CENTER);

		idPanel.add(jlb1);

		JPanel idPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField jtf1 = new JTextField(10);

		idPanel2.add(jtf1);

		jp1.add(idPanel);
		jp1.add(idPanel2);

		// 비밀번호 text field
		JPanel pwdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb2 = new JLabel("비밀번호 : ", JLabel.CENTER);

		JPanel pwdPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPasswordField jtf2 = new JPasswordField(10);

		pwdPanel.add(jlb2);
		pwdPanel2.add(jtf2);
		jp1.add(pwdPanel);
		jp1.add(pwdPanel2);

		// 버튼
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel loginPanel = new JPanel(new FlowLayout());
		JButton jLogin = new JButton("로그인");

		JPanel joinPanel = new JPanel(new FlowLayout());
		JButton join = new JButton("회원가입");

		JPanel backPanel = new JPanel(new FlowLayout());
		JButton back = new JButton("시작화면");

		loginPanel.add(jLogin);
		joinPanel.add(join);
		backPanel.add(back);
		jp2.add(loginPanel);
		jp2.add(joinPanel);
		jp2.add(backPanel);

		// jp3에 배치
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		jp3.add(jp1);
		jp3.add(jp2);

		setLayout(new BorderLayout());

		add(title, BorderLayout.NORTH);
		add(jp1, BorderLayout.CENTER);
		add(jp2, BorderLayout.SOUTH);

		setBounds(200, 200, 400, 250);

		setResizable(false); // 화면 크기 고정하는 작업

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// 이벤트 처리
		//로그인 버튼 클릭시
		jLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//입력된 값 받아오기
				String myId = jtf1.getText();
				String myPwd = new String(jtf2.getPassword());

				//팝업 창 띄워 확인 시켜주기
				JOptionPane.showMessageDialog(null, "아이디 : " + myId + ", 비밀번호 : " + myPwd);
				
				//회원MENU창 띄우기
				Connection conn;
				try {
					conn = DriverManager.getConnection(URL,USER,PASS);
					new M_MainScreen(conn,ID);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//DB연결 (미완성)
				/*
				if(userType.equals("회원")) {
					//아직
				}
				
				else if(userType.equals("트레이너")) {
					boolean trainerLoginSuccess = false;
					String trainer_name = null; // 처음 트레이너가 로그인할 때에 트레이너의 이름 저장
					String trainer_id = null; // 처음 트레이너가 로그인할 때에 트레이너의 아이디 저장
					
					String loginquery = "SELECT 비밀번호, 이름, 강사번호, 헬스장번호, 담당회원수, 총근무시간 "
							+ "FROM DB2022_트레이너" + " WHERE (강사번호=?)";

					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						Resultset rs = (Resultset) pst.executeQuery();
						
						if (rs.next().getString("비밀번호").equals(myPwd)) {
							trainerLoginSuccess = true; 
							JOptionPane.showMessageDialog(null, "로그인 성공");
						}
						else {
							JOptionPane.showMessageDialog(null, "아이디가 없거나, 올바르지 않은 비밀번호입니다. 다시 로그인해주세요.");
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
				
				else if(userType.equals("관장")) {
					//아직
				}
				*/
			}
		});

		//회원가입 버튼 클릭시
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new JoinScreen(userType);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});

		//시작화면 버튼 클릭시
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new StartScreen();
				dispose();

			}
		});
	}

}
