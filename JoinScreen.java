package DB2022TEAM03;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;
/*
 * 구현해야할 것들 정리
 * 1. ID 형식이 올바른지 확인해야함. 올바르지 않으면 가입 거부해야함.
 * 2. 그 외에도 입력값이 형식에 맞지않으면 가입 거부해야함. */

public class JoinScreen extends JFrame {

	public JoinScreen(String userType) {
		
		setTitle("회원가입");
		
		// 1. 컴포넌트들을 만들어 보자.
		JLabel title = new JLabel(userType + " 회원가입", JLabel.CENTER);
		
		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		
		JTextField id = new JTextField(10); 
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
		
		//공통1. 아이디
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("아이디 : "));
		idPanel.add(id);
		
		formPanel.add(idPanel);
		
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
				
				String myId = id.getText();
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
				
				if(userType.equals("회원")) JOptionPane.showMessageDialog(null, "아이디 : "+myId+", 비밀번호 : "+myPwd+", 이 름 : "+myName + ", 지 역 : "+address2);
				else if(userType.equals("트레이너")) JOptionPane.showMessageDialog(null, "아이디 : "+myId+", 비밀번호 : "+myPwd+", 이 름 : "+myName);
				else if(userType.equals("관장")) JOptionPane.showMessageDialog(null, "아이디 : "+myId+", 비밀번호 : "+myPwd+", 헬스장 이름 : "+myName 
						+ "\n주소 : "+ myAddress1 +" " + myAddress2 +" "+ myAddress3);
				
				//DB연결 (미완성)
				/*
				insert into DB2022_회원 values(헬스장번호, 회원번호, 이름, 지역, 전체횟수, 남은횟수, 담당트레이너, 현재회원권, 비밀번호);
				insert into DB2022_트레이너 values (강사번호, 헬스장번호, 이름, 담당회원수, 총근무시간, 비밀번호);
				insert into DB2022_헬스장 values (헬스장번호, 이름, 도시, 지역, 전체회원수, 전체트레이너수, 비밀번호); */
				
				/*
				//conn을 모든 class에서 불러오려면?
				if(userType.equals("회원")) {
					String JoinQuery = "insert into DB2022_회원 values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement pst = conn.prepareStatement(JoinQuery); //맞왜틀...
					pst.setString(1,  null); //소속 헬스장 번호 처음엔 null?
					pst.setString(2,  myId);
					pst.setString(3,  myName);
					pst.setString(4,  myAddress2);
					pst.setInt(5,  0);
					pst.setInt(6,  0);
					pst.setString(7,  null);
					pst.setString(8,  "없음");
					pst.setString(9,  myPwd);
					pst.executeUpdate();
				}
				
				else if(userType.equals("트레이너")) {
					String JoinQuery = "insert into DB2022_트레이너 values (?, ?, ?, ?, ?, ?);";
					PreparedStatement pst = conn.prepareStatement(JoinQuery); //맞왜틀...
					pst.setString(1,  null); //소속 헬스장 번호 처음엔 null?
					pst.setString(2,  myId);
					pst.setString(3,  myName);
					pst.setString(4,  myAddress2);
					pst.setInt(5,  0);
					pst.setInt(6,  0);
					pst.executeUpdate();
				}
				
				else if(userType.equals("관장")) {
					String JoinQuery = "insert into DB2022_헬스장 values (?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement pst = conn.prepareStatement(JoinQuery); //맞왜틀...
					pst.setString(1,  null); //소속 헬스장 번호 처음엔 null?
					pst.setString(2,  myId);
					pst.setString(3,  myName);
					pst.setString(4,  myAddress2);
					pst.setInt(5,  0);
					pst.setInt(6,  0);
					pst.setString(7,  null);
					pst.executeUpdate();
				}
				*/
			}
		});
		
		
		
		// 취소 버튼을 클릭했을 때 이벤트 처리
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				new LoginScreen(userType);
				dispose();
				
			}
		});
	}
}