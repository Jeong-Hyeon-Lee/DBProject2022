package DB2022TEAM03;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;
/*
 * 구현해야할 것들 정리
 * 1. [진행 중] 입력값이 형식에 맞지않으면 가입 거부해야함. 
 *    (가격등록안할려고 0원으로 넣는다던가, 전화번호에 4자리 숫자넣어야하는데 다른 값넣는다던가 등등)
 *    (잘못된 접근에도 error 뜨고 끝나는게 아니라, 계속해서 수행될 수 있도록 해줘야함.)
 * 
 * 2. [해결] ID말고 전화번호 뒷자리 넣어서 실행되어야함
 * 
 * 3. [해결] connection conn인자로 받든, 하나로 합쳐서 하든지... 
 * 
 * 4. [아직 시작안함] create.sql에 ID값 잘못된 것들 있음. 그거 고쳐서 commit해야함.
 * 
 * 5. [아직 시작안함] 회원가입 성공하면, 성공했다고 하고 아니면 아니라하고, 성공했으면 로그인하라고 로그인 창 띄워줘야함.
 * 
 * 6. [아직 시작안함] 탈퇴. 회원탈퇴 시 처리해야할 정보들 다 transaction으로 묶어서 탈퇴시키기. 
 */

public class JoinScreen extends JFrame {

	public JoinScreen(Connection conn, String userType) {
		
		setTitle("회원가입");
		
		// 1. 컴포넌트들을 만들어 보자.
		JLabel title = new JLabel(userType + " 회원가입", JLabel.CENTER);
		
		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		
		JTextField phone = new JTextField(10); 
		JPasswordField pwd = new JPasswordField(10);
		JTextField name = new JTextField(10);

		JTextField address1 = new JTextField(10); address1.setText("ex) 서울");
		JTextField address2 = new JTextField(10); address2.setText("ex) 서대문구");
		JTextField address3 = new JTextField(10); address3.setText("ex) 이화여대길 52");
		
		//가격은 1~500사이 정수값 가지도록 처리
		NumberFormatter 범위 = new NumberFormatter();
		범위.setValueClass(Integer.class);
		범위.setMinimum(new Integer(1));
		범위.setMaximum(new Integer(500));
		JFormattedTextField price1 = new JFormattedTextField(범위);
		JFormattedTextField price10 = new JFormattedTextField(범위);
		JFormattedTextField price20 = new JFormattedTextField(범위);
		price1.setColumns(10);
		price10.setColumns(10);
		price20.setColumns(10);
	
	
		// form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(9, 1)); //관장이 최대 9행 가져서 9,1로 둠
		
		//공통1. 아이디 (전화번호를 받아서 알아서 아이디 생성해줘야함)
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		phonePanel.add(new JLabel("전화번호 뒷4자리 : "));
		phonePanel.add(phone);
		
		formPanel.add(phonePanel);
		
		//공통2. 패스워드
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("비밀번호 : "));
		pwdPanel.add(pwd);
		
		formPanel.add(pwdPanel);
		
		//회원이면, 이름 , 지역 추가
		if(userType.equals("회원")) {
			//이름
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("이    름 : "));
			namePanel.add(name);
			
			formPanel.add(namePanel);
			
			//지역
			JPanel address2Panel = new JPanel();
			address2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address2Panel.add(new JLabel("지    역 : "));
			address2Panel.add(address2);
			
			formPanel.add(address2Panel);
		}
		
		//트레이너면, 이름 추가
		if(userType.equals("트레이너")) {
			//이름
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("이    름 : "));
			namePanel.add(name);
			
			formPanel.add(namePanel);
		}
		
		//관장이면, 헬스장이름, 도시, 지역, 도로명주소, 1회가격, 10회가격, 20회 가격 추가.
		if(userType.equals("관장")) {
			//헬스장이름
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("헬스장 이름 : "));
			namePanel.add(name);
			
			formPanel.add(namePanel);
			
			//도시, 지역, 도로명 주소
			JPanel address1Panel = new JPanel();
			address1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address1Panel.add(new JLabel("도    시 : "));
			address1Panel.add(address1);
			formPanel.add(address1Panel);
			
			JPanel address2Panel = new JPanel();
			address2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address2Panel.add(new JLabel("지    역 : "));
			address2Panel.add(address2);
			formPanel.add(address2Panel);
			
			JPanel address3Panel = new JPanel();
			address3Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address3Panel.add(new JLabel("도로명 주소 : "));
			address3Panel.add(address3);
			formPanel.add(address3Panel);
			
			JPanel price1Panel = new JPanel();
			price1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price1Panel.add(new JLabel("PT 1회 가격 : "));
			price1Panel.add(price1);
			formPanel.add(price1Panel);
			
			JPanel price10Panel = new JPanel();
			price10Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price10Panel.add(new JLabel("PT 10회 가격 : "));
			price10Panel.add(price10);
			formPanel.add(price10Panel);
			
			JPanel price20Panel = new JPanel();
			price20Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price20Panel.add(new JLabel("PT 20회 가격 : "));
			price20Panel.add(price20);
			formPanel.add(price20Panel);
		}
		
		// radio + form panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(formPanel);
		
		// button panel
		JPanel panel = new JPanel();
		panel.add(join);
		panel.add(cancel);
		
		add(title, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		
		setBounds(200, 200, 250, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// 회원가입 버튼 눌렀을 때 이벤트처리
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				String myId = "";
				String myPhoneNum = phone.getText();
				String myPwd = new String(pwd.getPassword());
				String myName = name.getText();
				String myAddress1 = address1.getText();
				String myAddress2 = address2.getText();
				String myAddress3 = address3.getText();
				
				/* price1.getText()에서 오류남
				try {
					int price1 = Integer.parseInt(price1.getText());
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "가격에 1~500사이의 정수만 입력해주세요.");
				}
				*/
					
				
				//=================================================
				//===================== 회원 =======================
				if(userType.equals("회원")) {
					//1. [전화번호로 아이디 생성하는 부분]
					/* [구현방법]
					 * 이미 만들어진 아이디 vs 입력받은걸로 후보만들어서 (x)
					 * 이미 만들어진 아이디를 잘라서 전화번호만 추출 vs 입력받은 전화번호 (o)
					 * 
					 * [설명]
					 * 2345라는 전화번호 입력시, 2345 번호가진 사람 수 세기. 23450, 23451 이 존재한다면 count는 2. 따라서 ID는 23452.
					 * */					
					String joinQuery
					="WITH phonenum as"
						+ "( SELECT SUBSTRING(회원번호, 2, 4) as substring"
						+ " FROM DB2022_회원)"
					+ " SELECT count(*)"
					+ " FROM phonenum"
					+ " WHERE substring LIKE ?;";
					
					try {
						//쿼리 실행
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();
						
						//아이디 만들기
						//맨처음 들어오는 전화번호에 대해 num은 0부터 시작될 것임. 
						while(rs.next()) {
							int num = rs.getInt(1);
							myId = "M"+ myPhoneNum + Integer.toString(num);
						}
					}
					catch(SQLException sqle){
						sqle.printStackTrace();
					}
					
					//2. 만들어진 아이디 확인시켜주는 부분.
					JOptionPane.showMessageDialog(null, "아이디 : "+myId+", 비밀번호 : "+myPwd+", 이 름 : "+myName + ", 지 역 : "+myAddress2 + "\n"
							+ "로그인 시 아이디가 필요하니 아이디를 꼭 기억해주세요.\n"
							+ "아이디는 (Member의 M) + (핸드폰 번호 뒷자리) + (숫자 하나)로 구성되어있습니다.");	
					
					//3. DB에 삽입
					try {
						//insert into DB2022_회원 values(헬스장번호, 회원번호, 이름, 지역, 전체횟수, 남은횟수, 담당트레이너, 현재회원권, 비밀번호);
						String JoinQuery = "insert into DB2022_회원 values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
						PreparedStatement pst = conn.prepareStatement(JoinQuery);
						pst.setString(1,  null); 
						pst.setString(2,  myId);
						pst.setString(3,  myName);
						pst.setString(4,  myAddress2);
						pst.setInt(5,  0);
						pst.setInt(6,  0);
						pst.setString(7,  null);
						pst.setString(8,  "없음");
						pst.setString(9,  myPwd);
						pst.executeUpdate();
					} catch(SQLException sqle){
						sqle.printStackTrace();
					}
				}
				
				
				
				//=================================================
				//===================== 트레이너 =======================
				else if(userType.equals("트레이너")) {
					//1. 전화번호로 아이디 만들기 START
					String joinQuery
					="WITH phonenum as"
						+ "( SELECT SUBSTRING(강사번호, 2, 4) as substring"
						+ " FROM DB2022_트레이너)"
					+ " SELECT count(*)"
					+ " FROM phonenum"
					+ " WHERE substring LIKE ?;";
					
					try {
						//쿼리 실행
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();
						
						//아이디 만들기
						while(rs.next()) {
							int num = rs.getInt(1);
							myId = "T"+ myPhoneNum + Integer.toString(num);
						}
					} catch(SQLException sqle){
						sqle.printStackTrace();
					}
					//전화번호로 아이디 만들기 END
					
					//2. 입력한 정보 확인 
					JOptionPane.showMessageDialog(null, "아이디 : "+myId+", 비밀번호 : "+myPwd+", 이 름 : "+myName+ "\n"
							+ "로그인 시 아이디가 필요하니 아이디를 꼭 기억해주세요.\n"
							+ "아이디는 (Trainer의 T) + (핸드폰 번호 뒷자리) + (숫자 하나)로 구성되어있습니다.");		
					
					//3. DB에 삽입
					try {
						//insert into DB2022_트레이너 values (헬스장번호, 강사번호, 이름, 담당회원수, 총근무시간, 비밀번호);
						String JoinQuery = "insert into DB2022_트레이너 values (?, ?, ?, ?, ?, ?);";
						PreparedStatement pst = conn.prepareStatement(JoinQuery);
						pst.setString(1,  null); 
						pst.setString(2,  myId);
						pst.setString(3,  myName);
						pst.setInt(4,  0);
						pst.setInt(5,  0);
						pst.setString(6,  myPwd);
						pst.executeUpdate();
					} catch(SQLException sqle){
						sqle.printStackTrace();
					}
				} 
				
				
				
				//=================================================
				//===================== 관장 =======================
				else if(userType.equals("관장")) {
					//1. 전화번호로 아이디 만들기 START
					String joinQuery
					="WITH phonenum as"
						+ "( SELECT SUBSTRING(헬스장번호, 2, 4) as substring"
						+ " FROM DB2022_헬스장)"
					+ " SELECT count(*)"
					+ " FROM phonenum"
					+ " WHERE substring LIKE ?;";
					
					try {
						//쿼리 실행
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();
						
						//아이디 만들기
						while(rs.next()) {
							int num = rs.getInt(1);
							myId = "G"+ myPhoneNum + Integer.toString(num);
						}
					} catch(SQLException sqle){
						sqle.printStackTrace();
					}
					//전화번호로 아이디 만들기 END
					
					//2. 입력한 정보 확인
					JOptionPane.showMessageDialog(null, "아이디 : "+myId+", 비밀번호 : "+myPwd+", 헬스장 이름 : "+myName 
							+ "\n주소 : "+ myAddress1 +" " + myAddress2 +" "+ myAddress3+ "\n"
							+ "로그인 시 아이디가 필요하니 아이디를 꼭 기억해주세요.\n"
							+ "아이디는 (Gym의 G) + (핸드폰 번호 뒷자리) + (숫자 하나)로 구성되어있습니다.");	
					
					//3. DB에 삽입
					try {
						//insert into DB2022_헬스장 values (헬스장번호, 이름, 도시, 지역, 도로명주소, 전체회원수, 전체트레이너수, 비밀번호);
						String JoinQuery = "insert into DB2022_헬스장 values (?, ?, ?, ?, ?, ?, ?);";
						PreparedStatement pst = conn.prepareStatement(JoinQuery);
						pst.setString(1,  myId); //소속 헬스장 번호 처음엔 null?
						pst.setString(2,  myName);
						pst.setString(3,  myAddress1);
						pst.setString(4,  myAddress2);
						pst.setString(5,  myAddress3);
						pst.setInt(6,  0);
						pst.setInt(7,  0);
						pst.setString(8, myPwd);
						pst.executeUpdate();
						
					} catch(SQLException sqle){
						sqle.printStackTrace();
					}
					
				} 
			
			}
		});
		
		
		
		// 취소 버튼을 클릭했을 때 이벤트 처리
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				new LoginScreen(conn, userType);
				dispose();
				
			}
		});
	}
}
