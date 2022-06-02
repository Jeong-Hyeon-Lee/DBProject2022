package DB2022Team03;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;

import DB2022Team03.GEUNJU.M_MainScreen;
import DB2022Team03.TrainerWithGUI.TrainerMenuJTable;
import DB2022Team03.Gym.G_selectMenu;

public class LoginScreen extends JFrame {
	
	public LoginScreen(Connection conn, String userType) {

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
		//1. 로그인 버튼 클릭시
		jLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//입력된 값 받아오기
				String myId = jtf1.getText();
				String myPwd = new String(jtf2.getPassword());

				//팝업 창 띄워 확인 시켜주기
				JOptionPane.showMessageDialog(null, "아이디 : " + myId + ", 비밀번호 : " + myPwd);
			
				//DB연결 
				// ================ 회원 로그인 =============== 
				if(userType.equals("회원")) {
					String member_id = null; // 로그인한 회원의 회원번호
					String member_name = null; // 로그인한 회원의 이름
					String member_gym = null; // 로그인한 회원의 소속 헬스장번호
					
					String loginquery = "SELECT * " + "FROM DB2022_회원" + " WHERE (회원번호=?)";
					//어차피 회원번호 primary key라 null아니면 tuple한개짜리 ResultSet 반환 >> while(rs.next()) 필요없음
					
					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						ResultSet rs = pst.executeQuery();
					
						//ResultSet이 비어있는 것은 if(!rs.next())으로 확인
						//ResultSet.getString();이 null인 것은 rs.null()로 확인
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다. 다시 로그인해주세요.");
						}
						else if (rs.getString("비밀번호").equals(myPwd)) {
							JOptionPane.showMessageDialog(null, "로그인 성공");
							member_id = rs.getString("회원번호");
							member_name = rs.getString("이름");
							member_gym = rs.getString("소속헬스장");
							
							//회원 페이지와 연결
							new M_MainScreen(conn,member_id);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "올바르지 않은 비밀번호입니다. 다시 로그인해주세요.");
						}
						
					}catch(SQLException sqle){
						sqle.printStackTrace();
					}
				}
				
				// ================ 트레이너 로그인 =============== 
				else if(userType.equals("트레이너")) {
					String trainer_id = null; // 로그인한 트레이너의 강사번호
					String trainer_name = null; // 로그인한 트레이너의 이름
					String trainer_gym = null; // 로그인한 트레이너의 소속 헬스장번호
					
					String loginquery = "SELECT * " + "FROM DB2022_트레이너" + " WHERE (강사번호=?)"; 
					//어차피 강사번호 primary key라 null아니면 tuple한개짜리 ResultSet 반환 >> while(rs.next()) 필요없음

					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						ResultSet rs = pst.executeQuery();
					
						//ResultSet이 비어있는 것은 if(!rs.next())으로 확인
						//ResultSet.getString();이 null인 것은 rs.null()로 확인
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다. 다시 로그인해주세요.");
						}
						else if (rs.getString("비밀번호").equals(myPwd)) { 
							JOptionPane.showMessageDialog(null, "로그인 성공");
							
							trainer_id = rs.getString("강사번호");
							trainer_name = rs.getString("이름");
							trainer_gym = rs.getString("헬스장번호");
							
							new TrainerMenuJTable(trainer_id);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "올바르지 않은 비밀번호입니다. 다시 로그인해주세요.");
						}
					}catch(SQLException sqle){
						sqle.printStackTrace();
					}
					
				}
				
				// ================ 관장 로그인 =============== 
				else if(userType.equals("관장")) {
					String owner_gym = null; // 로그인한 관장의 헬스장번호
					String owner_name = null; // 로그인한 관장의 헬스장이름
					
					String loginquery = "SELECT * " + "FROM DB2022_헬스장" + " WHERE (헬스장번호=?)";
					//헬스장번호 primary key라 null아니면 tuple한개짜리 ResultSet 반환 >> while(rs.next()) 필요없음
					
					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						ResultSet rs = pst.executeQuery();
						

						//ResultSet이 비어있는 것은 if(!rs.next())으로 확인
						//ResultSet.getString();이 null인 것은 rs.null()로 확인
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다. 다시 로그인해주세요.");
						}
						else if (rs.getString("비밀번호").equals(myPwd)) {
							JOptionPane.showMessageDialog(null, "로그인 성공");
							owner_gym = rs.getString("헬스장번호");
							owner_name = rs.getString("이름");
							
							new G_selectMenu(owner_gym, owner_name);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "올바르지 않은 비밀번호입니다. 다시 로그인해주세요.");
						}
					}catch(SQLException sqle){
						sqle.printStackTrace();
					}
				}
				
			}
		});

		//2. 회원가입 버튼 클릭시
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new JoinScreen(conn, userType);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});

		//3. 시작화면 버튼 클릭시
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new StartScreen(conn);
				dispose();

			}
		});
	}

}
