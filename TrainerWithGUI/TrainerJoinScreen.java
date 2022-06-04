package DB2022Team03.TrainerWithGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

// JoinScreen (회원가입 화면)
public class TrainerJoinScreen extends JFrame {
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC 연동 객체 생성
	public TrainerJoinScreen(String userType) {
		
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
		JTextField gym = new JTextField(6);
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
		
		//공통1. 전화번호 뒷자리 4자리
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("전회번호 뒤4자리 : "));
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
			// 소속 헬스장
			JPanel gymPanel = new JPanel();
			gymPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			gymPanel.add(new JLabel("소속 헬스장 : "));
			gymPanel.add(gym);
			
			formPanel.add(gymPanel);
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
				String myGym = gym.getText();
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
				
				if(userType.equals("회원")) JOptionPane.showMessageDialog(null, "전화번호 뒤4자리 : "+myId+", 비밀번호 : "+myPwd+", 이 름 : "+myName + ", 지 역 : "+address2);
				else if(userType.equals("트레이너")) JOptionPane.showMessageDialog(null, "전회번호 뒤4자리 : "+myId+", 비밀번호 : "+myPwd+", 이 름 : "+myName+ "소속 헬스장 : " +myGym);
				else if(userType.equals("관장")) JOptionPane.showMessageDialog(null, "전화번호 뒤4자리 : "+myId+", 비밀번호 : "+myPwd+", 헬스장 이름 : "+myName 
						+ "\n주소 : "+ myAddress1 +" " + myAddress2 +" "+ myAddress3);
				
				if (userType.equals("트레이너")) {
					int idNo = tmdb.checkPhoneNum(myId);
					String newpk = "T" + myId + Integer.toString(idNo);
					System.out.println(newpk);
					int joinSuccess = tmdb.Join(myGym, newpk, myName, myPwd);
					if (joinSuccess == 1) {
						int success = tmdb.checkLogin(newpk, myPwd);
						if (success == 1) {
							new TrainerMenuJTable(newpk);
						}
					}
					
				}
				/*
				gymId와 member_ID의 경우에는 회원 가입하는 과정에서 폰 뒷 4자리를 입력하면 동일 번호 개수 검색하면서 생성을 해서 저장하면 메뉴 화면으로 이동 가능
			
				*/
				else if (userType.equals("관장")){
					// 관장 회원 가입 함수 호출 
					// 회원 가입 마무리 되면 자동 로그인
					/*
					new G_selectMenu(gymId);
					*/
					// 이후 관장용 메뉴 화면으로 넘어감 (G_selectMenu.java)
				}
				else if (userType.equals("회원")){
					// 멤버 회원 가입 함수 호출 
					// 회원 가입 마무리 되면 자동 로그인
					// 이후 멤버용 메뉴 화면으로 넘어감 (M_MainScreen.java)
					/*
					Connection conn = DriverManager.getConnection(URL,USER,PASS);
					new M_MainScreen(conn,member_ID);
					*/
				};
			
				   
				
			}
		});
		
		
		
		// 취소 버튼을 클릭했을 때 이벤트 처리
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				new TrainerJoinScreen(userType);
				dispose();
				
			}
		});
	}
}
