package DB2022Team03.TrainerWithGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// LoginScreen으로 봐도 무방

public class TrainerLoginScreen extends JFrame {
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC 연동 객체 생성
	
	public TrainerLoginScreen(String userType) {

		setTitle("로그인");

		JPanel title = new JPanel();
		TrainerMenuJDBC tmdb = new TrainerMenuJDBC();
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
			
				if(userType.equals("회원")) {
					// 회원 로그인 함수 호출
				}
				else if(userType.equals("관장")){
					// 관장 로그인 함수 호출
				}
				else if(userType.equals("트레이너")) {
					int success = tmdb.checkLogin(myId, myPwd);
					if (success == 1) {
						new TrainerMenuJTable(myId);
					}
					else {
						jtf1.setText("");
						jtf2.setText("");
					}
				}
				
			}
		});
		
		//회원가입 버튼 클릭시
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new TrainerJoinScreen(userType); // JoinScreen(userType);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});

		//시작화면 버튼 클릭시
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new ServiceStartScreen(); // 처음 관장, 트레이너, 회원 선택하는 부분
				dispose();

			}
		});
	}
	
}
