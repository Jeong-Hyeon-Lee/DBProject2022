package DB2022Team03.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;
/*
 * 추가로 구현해야할 것들 정리
 * 1. [완료] 입력값이 형식에 맞지않으면 가입 거부해야함. 
 *    (전화번호에 4자리 숫자넣어야하는데 다른 값넣는다던가 등등)
 *    (잘못된 접근에도 error 뜨고 끝나는게 아니라, 계속해서 수행될 수 있도록 해줘야함.)
 * 
 * 2. [완료] ID말고 전화번호 뒷자리 넣어서 실행되어야함
 * 
 * 3. [완료] connection conn인자로 받든, 하나로 합쳐서 하든지... 
 * 
 * 4. [완료] create.sql에 ID값 잘못된 것들 있음. 그거 고쳐서 commit해야함.
 * 
 * 5. [완료] 회원가입 성공하면, 성공했다고 하고 아니면 아니라하고, 성공했으면 로그인하라고 로그인 창 띄워줘야함.
 * 
 * 6. [완료] 탈퇴. 회원탈퇴 시 처리해야할 정보들 다 transaction으로 묶어서 탈퇴시키기. 
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
		JTextField Tgym = new JTextField(10);

		JTextField address1 = new JTextField(10); // 지역
		address1.setText("ex) 서대문구");
		JTextField address2 = new JTextField(10); // 주소
		address2.setText("ex) 이화여대길 52");

		JTextField price1 = new JTextField(10);
		JTextField price10 = new JTextField(10);
		JTextField price20 = new JTextField(10);

		// form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(10, 1)); // 관장이 최대 10행 가져서 10,1로 둠

		// 공통1. 아이디 (전화번호를 받아서 알아서 아이디 생성해줘야함)
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		phonePanel.add(new JLabel("전화번호 뒷4자리 : "));
		phonePanel.add(phone);

		formPanel.add(phonePanel);

		// 공통2. 패스워드
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("비밀번호 : "));
		pwdPanel.add(pwd);

		formPanel.add(pwdPanel);

		// 회원이면, 이름 , 지역 추가
		if (userType.equals("회원")) {
			// 이름
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("이    름 : "));
			namePanel.add(name);

			formPanel.add(namePanel);

			// 지역
			JPanel address1Panel = new JPanel();
			address1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address1Panel.add(new JLabel("지    역 : "));
			address1Panel.add(address1);

			formPanel.add(address1Panel);
		}

		// 트레이너면, 이름과 소속 헬스장 추가
		if (userType.equals("트레이너")) {
			// 이름
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("이    름 : "));
			namePanel.add(name);

			formPanel.add(namePanel);

			// 소속 헬스장
			JPanel gymPanel = new JPanel();
			gymPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			gymPanel.add(new JLabel("소속 헬스장 :  "));
			gymPanel.add(Tgym);

			formPanel.add(gymPanel);
		}

		// 관장이면, 헬스장이름, 도시, 지역, 도로명주소, 1회가격, 10회가격, 20회 가격 추가.
		if (userType.equals("관장")) {
			// 헬스장이름
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("헬스장 이름 : "));
			namePanel.add(name);

			formPanel.add(namePanel);

			// 도시, 지역, 도로명 주소

			JPanel address1Panel = new JPanel();
			address1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address1Panel.add(new JLabel("지    역 : "));
			address1Panel.add(address1);
			formPanel.add(address1Panel);

			JPanel address2Panel = new JPanel();
			address2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address2Panel.add(new JLabel("도로명 주소 : "));
			address2Panel.add(address2);
			formPanel.add(address2Panel);

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

		// 입력형식 처리해줄 텍스트 필드
		JLabel checkCondition = new JLabel("");
		formPanel.add(checkCondition);

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

		setBounds(200, 200, 400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// 회원가입 버튼 눌렀을 때 이벤트처리
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				String myId = "";
				String myGym = Tgym.getText();
				String myPhoneNum = phone.getText();
				String myPwd = new String(pwd.getPassword());
				String myName = name.getText();
				String myAddress1 = address1.getText(); // 지역
				String myAddress2 = address2.getText(); // 도로명주소
				String myPrice1 = price1.getText();
				String myPrice10 = price10.getText();
				String myPrice20 = price20.getText();

				// 0. 입력한 값들 형식에 맞게 예외 처리.
				boolean JoinSuccess = true; // 전체 로그인 성공 여부.
				boolean correctPhoneNum = true;

				// 입력한 전화번호 문자열이 숫자로만 구성됐는지 확인.
				for (int i = 0; i < myPhoneNum.length(); i++) {
					char temp = myPhoneNum.charAt(i);
					if (!Character.isDigit(temp)) {
						correctPhoneNum = false;
						break;
					}
				}

				// [아이디]
				if (myPhoneNum.length() != 4) {
					checkCondition.setText("전화번호 뒷4자리에 4자리 숫자를 입력해주세요.");
					JoinSuccess = false;
				} else if (!correctPhoneNum) {
					checkCondition.setText("전화번호 뒷4자리에는 숫자만 입력해주세요.");
					JoinSuccess = false;
				}
				// [비번]
				else if (myPwd.length() > 10) {
					checkCondition.setText("비밀번호가 너무 깁니다. (10자 이내)");
					JoinSuccess = false;
				}
				// [이름]
				else if (userType.equals("관장") && !(0 < myName.length() && myName.length() <= 25)) {
					checkCondition.setText("헬스장 이름이 너무 짧거나 너무 깁니다. (1자 이상 25자 이내)");
					JoinSuccess = false;
				} else if ((userType.equals("회원") || userType.equals("트레이너"))
						&& !(0 < myName.length() && myName.length() <= 10)) {
					checkCondition.setText("이름이 너무 짧거나 너무 깁니다. (1자 이상 10자 이내)");
					JoinSuccess = false;
				}

				// =================================================
				// ===================== 회원 =======================
				if (userType.equals("회원")) {
					// 0. 입력한 값들 형식에 맞게 예외 처리.
					if (!(myAddress1.length() > 0 && myAddress1.length() <= 6)) {
						checkCondition.setText("지역명이 너무 짧거나 깁니다. (1자 이상 6자 이내)");
						JoinSuccess = false;
					}

					// 1. [전화번호로 아이디 생성하는 부분] START
					/*
					 * [구현방법]
					 * 이미 만들어진 아이디 vs 입력받은걸로 후보만들어서 (x)
					 * 이미 만들어진 아이디를 잘라서 전화번호만 추출 vs 입력받은 전화번호 (o)
					 * 
					 * [설명]
					 * 2345라는 전화번호 입력시, 2345 번호가진 사람 수 세기. 23450, 23451 이 존재한다면 count는 2. 따라서 ID는
					 * 23452.
					 */
					String joinQuery = "WITH phonenum as"
							+ "( SELECT SUBSTRING(회원번호, 2, 4) as substring"
							+ " FROM DB2022_회원)"
							+ " SELECT count(*)"
							+ " FROM phonenum"
							+ " WHERE substring LIKE ?;";

					try {
						// 쿼리 실행
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();

						// 아이디 만들기
						// 맨처음 들어오는 전화번호에 대해 num은 0부터 시작될 것임.
						while (rs.next()) {
							int num = rs.getInt(1);
							if (!(0 <= num && num <= 9)) {
								JOptionPane.showMessageDialog(formPanel,
										"죄송합니다. 입력하신 핸드폰번호 뒷자리는 이미 너무 많은 회원이 사용 중입니다." + "\n"
												+ "다른 임의의 4자리 수를 입력해주세요.");
								JoinSuccess = false;
							} else {
								myId = "M" + myPhoneNum + Integer.toString(num);
							}
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
					// 1. [전화번호로 아이디 생성하는 부분] END

					if (JoinSuccess == true) {
						// 2. 만들어진 아이디 확인시켜주는 부분.
						JOptionPane.showMessageDialog(formPanel,
								"아이디 : " + myId + ", 비밀번호 : " + myPwd + ", 이 름 : " + myName + ", 지 역 : " + myAddress1
										+ "\n"
										+ "로그인 시 아이디가 필요하니 아이디를 꼭 기억해주세요.\n"
										+ "아이디는 (Member의 M) + (핸드폰 번호 뒷자리) + (숫자 하나)로 구성되어있습니다.");

						// 3. DB에 삽입
						try {
							// insert into DB2022_회원 values(헬스장번호, 회원번호, 이름, 지역, 전체횟수, 남은횟수, 담당트레이너, 현재회원권,
							// 비밀번호);
							String JoinQuery = "insert into DB2022_회원 values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
							PreparedStatement pst = conn.prepareStatement(JoinQuery);
							pst.setString(1, null);
							pst.setString(2, myId);
							pst.setString(3, myName);
							pst.setString(4, myAddress1);
							pst.setInt(5, 0);
							pst.setInt(6, 0);
							pst.setString(7, null);
							pst.setString(8, "없음");
							pst.setString(9, myPwd);
							pst.executeUpdate();
						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== 트레이너 =======================
				else if (userType.equals("트레이너")) {
					// 1. 전화번호로 아이디 만들기 START
					String joinQuery = "WITH phonenum as"
							+ "( SELECT SUBSTRING(강사번호, 2, 4) as substring"
							+ " FROM DB2022_트레이너)"
							+ " SELECT count(*)"
							+ " FROM phonenum"
							+ " WHERE substring LIKE ?;";

					try {
						// 쿼리 실행
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();

						// 아이디 만들기
						while (rs.next()) {
							int num = rs.getInt(1);
							if (!(0 <= num && num <= 9)) {
								JOptionPane.showMessageDialog(formPanel,
										"죄송합니다. 입력하신 핸드폰번호 뒷자리는 이미 너무 많은 회원이 사용 중입니다." + "\n"
												+ "다른 임의의 4자리 수를 입력해주세요.");
								JoinSuccess = false;
							} else {
								myId = "T" + myPhoneNum + Integer.toString(num);
							}
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
					// 전화번호로 아이디 만들기 END
					try {
						PreparedStatement pst = conn.prepareStatement("SELECT * FROM DB2022_헬스장 WHERE(헬스장번호=?)");
						pst.setString(1, myGym);
						ResultSet rs = pst.executeQuery();
						if (rs.next() == false) {
							checkCondition.setText("입력하신 헬스장은 존재하지 않습니다. 다시 입력해 주세요");
							JoinSuccess = false;
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					if (JoinSuccess == true) {
						// 2. 입력한 정보 확인
						JOptionPane.showMessageDialog(formPanel,
								"아이디 : " + myId + ", 비밀번호 : " + myPwd + ", 이 름 : " + myName + "소속 헬스장 : " + myGym + "\n"
										+ "로그인 시 아이디가 필요하니 아이디를 꼭 기억해주세요.\n"
										+ "아이디는 (Trainer의 T) + (핸드폰 번호 뒷자리) + (숫자 하나)로 구성되어있습니다.");

						// 3. DB에 삽입
						try {
							// insert into DB2022_트레이너 values (헬스장번호, 강사번호, 이름, 담당회원수, 총근무시간, 비밀번호);
							String JoinQuery = "insert into DB2022_트레이너 values (?, ?, ?, ?, ?, ?);";
							PreparedStatement pst = conn.prepareStatement(JoinQuery);
							pst.setString(1, myGym);
							pst.setString(2, myId);
							pst.setString(3, myName);
							pst.setInt(4, 0);
							pst.setInt(5, 0);
							pst.setString(6, myPwd);
							pst.executeUpdate();
						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== 관장 =======================
				else if (userType.equals("관장")) {
					// 0. 입력한 값들 형식에 맞게 예외 처리.
					boolean isInt = true;
					boolean isNotNull = true;

					if (myPrice1.length() == 0 || myPrice10.length() == 0 || myPrice20.length() == 0) {
						checkCondition.setText("가격을 입력해주세요");
						JoinSuccess = false;
						isNotNull = false;
					}

					// 입력한 가격 문자열이 숫자로만 구성됐는지 확인.
					for (int i = 0; i < myPrice1.length(); i++) {
						char temp = myPrice1.charAt(i);
						if (!Character.isDigit(temp)) {
							isInt = false;
							JoinSuccess = false;
							break;
						}
					}
					for (int i = 0; i < myPrice10.length(); i++) {
						char temp = myPrice10.charAt(i);
						if (!Character.isDigit(temp)) {
							isInt = false;
							JoinSuccess = false;
							break;
						}
					}
					for (int i = 0; i < myPrice20.length(); i++) {
						char temp = myPrice20.charAt(i);
						if (!Character.isDigit(temp)) {
							isInt = false;
							JoinSuccess = false;
							break;
						}
					}

					int myPrice1Int = 10;
					int myPrice10Int = 10;
					int myPrice20Int = 10;

					if (isInt && isNotNull) {
						myPrice1Int = Integer.parseInt(myPrice1);
						myPrice10Int = Integer.parseInt(myPrice10);
						myPrice20Int = Integer.parseInt(myPrice20);
					}

					// [주소]
					if (!(0 < myAddress1.length() && myAddress1.length() <= 6)) {
						checkCondition.setText("지역명이 너무 짧거나 깁니다. (1자 이상 6자 이내)");
						JoinSuccess = false;
					} else if (!(0 < myAddress2.length() && myAddress2.length() <= 6)) {
						checkCondition.setText("도로명주소가 너무 짧거나 깁니다. (1자 이상 20자 이내)");
						JoinSuccess = false;
					}
					// [가격]
					else if (!isInt) {
						checkCondition.setText("가격정보에는 숫자만 입력해주세요.");
						JoinSuccess = false;
					} else if (!(0 < myPrice1Int && myPrice1Int <= 300) || !(0 < myPrice10Int && myPrice10Int <= 300)
							|| !(0 < myPrice20Int && myPrice20Int <= 300)) {
						checkCondition.setText("가격은 1만원 이상 300만원이하로 작성해주세요.");
						JoinSuccess = false;
					}

					// 1. 전화번호로 아이디 만들기 START
					String joinQuery = "WITH phonenum as"
							+ "( SELECT SUBSTRING(헬스장번호, 2, 4) as substring"
							+ " FROM DB2022_헬스장)"
							+ " SELECT count(*)"
							+ " FROM phonenum"
							+ " WHERE substring LIKE ?;";

					try {
						// 쿼리 실행
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();

						// 아이디 만들기
						while (rs.next()) {
							int num = rs.getInt(1);
							myId = "G" + myPhoneNum + Integer.toString(num);
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
					// 전화번호로 아이디 만들기 END

					if (JoinSuccess == true) {
						// 2. 입력한 정보 확인
						JOptionPane.showMessageDialog(formPanel,
								"아이디 : " + myId + ", 비밀번호 : " + myPwd + ", 헬스장 이름 : " + myName
										+ "\n주소 : " + myAddress1 + " " + myAddress2 + " " + "\n"
										+ "로그인 시 아이디가 필요하니 아이디를 꼭 기억해주세요.\n"
										+ "아이디는 (Gym의 G) + (핸드폰 번호 뒷자리) + (숫자 하나)로 구성되어있습니다.");

						// 3. DB에 삽입
						try {
							// insert into DB2022_헬스장 values (헬스장번호, 이름, 지역, 도로명주소, 전체회원수, 전체트레이너수,
							// 비밀번호);
							String JoinQuery = "insert into DB2022_헬스장 values (?, ?, ?, ?, ?, ?, ?);";
							PreparedStatement pst = conn.prepareStatement(JoinQuery);
							pst.setString(1, myId);
							pst.setString(2, myName);
							pst.setString(3, myAddress1);
							pst.setString(4, myAddress2);
							pst.setInt(6, 0);
							pst.setInt(7, 0);
							pst.setString(8, myPwd);
							pst.executeUpdate();

							// insert into DB2022_가격 values (헬스장번호, 1회가격, 10회가격, 20회가격, 기타프로모션설명);
							String JoinQuery2 = "insert into DB2022_가격 values (?, ?, ?, ?, ?);";
							PreparedStatement pst2 = conn.prepareStatement(JoinQuery2);
							pst2.setString(1, myId);
							pst2.setInt(2, myPrice1Int);
							pst2.setInt(3, myPrice10Int);
							pst2.setInt(4, myPrice20Int);
							pst2.setString(5, null);
							pst2.executeUpdate();

						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
				}

				if (JoinSuccess == true) {
					// 회원가입 성공시 다시 로그인 화면으로.
					JOptionPane.showMessageDialog(formPanel, "회원가입 성공! 로그인 해주세요.");
					new LoginScreen(conn, userType);
					dispose(); // 현재의 frame을 종료시키는 메서드.
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