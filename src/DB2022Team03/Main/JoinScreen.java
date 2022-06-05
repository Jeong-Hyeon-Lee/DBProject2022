package DB2022Team03.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;
/*
 * �߰��� �����ؾ��� �͵� ����
 * 1. [�Ϸ�] �Է°��� ���Ŀ� ���������� ���� �ź��ؾ���. 
 *    (��ȭ��ȣ�� 4�ڸ� ���ڳ־���ϴµ� �ٸ� ���ִ´ٴ��� ���)
 *    (�߸��� ���ٿ��� error �߰� �����°� �ƴ϶�, ����ؼ� ����� �� �ֵ��� �������.)
 * 
 * 2. [�Ϸ�] ID���� ��ȭ��ȣ ���ڸ� �־ ����Ǿ����
 * 
 * 3. [�Ϸ�] connection conn���ڷ� �޵�, �ϳ��� ���ļ� �ϵ���... 
 * 
 * 4. [�Ϸ�] create.sql�� ID�� �߸��� �͵� ����. �װ� ���ļ� commit�ؾ���.
 * 
 * 5. [�Ϸ�] ȸ������ �����ϸ�, �����ߴٰ� �ϰ� �ƴϸ� �ƴ϶��ϰ�, ���������� �α����϶�� �α��� â ��������.
 * 
 * 6. [���� ��] Ż��. ȸ��Ż�� �� ó���ؾ��� ������ �� transaction���� ��� Ż���Ű��. 
 */

public class JoinScreen extends JFrame {

	public JoinScreen(Connection conn, String userType) {

		setTitle("ȸ������");

		// 1. ������Ʈ���� ����� ����.
		JLabel title = new JLabel(userType + " ȸ������", JLabel.CENTER);

		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("���� ���", Font.BOLD, 20));

		JButton join = new JButton("ȸ������");
		JButton cancel = new JButton("���");

		JTextField phone = new JTextField(10);
		JPasswordField pwd = new JPasswordField(10);
		JTextField name = new JTextField(10);
		JTextField Tgym = new JTextField(10);

	
		JTextField address1 = new JTextField(10); // ����
		address1.setText("ex) ���빮��");
		JTextField address2 = new JTextField(10); // �ּ�
		address2.setText("ex) ��ȭ����� 52");

		JTextField price1 = new JTextField(10);
		JTextField price10 = new JTextField(10);
		JTextField price20 = new JTextField(10);

		// form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(10, 1)); // ������ �ִ� 10�� ������ 10,1�� ��

		// ����1. ���̵� (��ȭ��ȣ�� �޾Ƽ� �˾Ƽ� ���̵� �����������)
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		phonePanel.add(new JLabel("��ȭ��ȣ ��4�ڸ� : "));
		phonePanel.add(phone);

		formPanel.add(phonePanel);

		// ����2. �н�����
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("��й�ȣ : "));
		pwdPanel.add(pwd);

		formPanel.add(pwdPanel);

		// ȸ���̸�, �̸� , ���� �߰�
		if (userType.equals("ȸ��")) {
			// �̸�
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("��    �� : "));
			namePanel.add(name);

			formPanel.add(namePanel);

			// ����
			JPanel address1Panel = new JPanel();
			address1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address1Panel.add(new JLabel("��    �� : "));
			address1Panel.add(address1);

			formPanel.add(address1Panel);
		}

		// Ʈ���̳ʸ�, �̸��� �Ҽ� �ｺ�� �߰�
		if (userType.equals("Ʈ���̳�")) {
			// �̸�
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("��    �� : "));
			namePanel.add(name);

			formPanel.add(namePanel);

			// �Ҽ� �ｺ��
			JPanel gymPanel = new JPanel();
			gymPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			gymPanel.add(new JLabel("�Ҽ� �ｺ�� :  "));
			gymPanel.add(Tgym);
			
			formPanel.add(gymPanel);
		}

		// �����̸�, �ｺ���̸�, ����, ����, ���θ��ּ�, 1ȸ����, 10ȸ����, 20ȸ ���� �߰�.
		if (userType.equals("����")) {
			// �ｺ���̸�
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("�ｺ�� �̸� : "));
			namePanel.add(name);

			formPanel.add(namePanel);

			// ����, ����, ���θ� �ּ�


			JPanel address1Panel = new JPanel();
			address1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address1Panel.add(new JLabel("��    �� : "));
			address1Panel.add(address1);
			formPanel.add(address1Panel);

			JPanel address2Panel = new JPanel();
			address2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address2Panel.add(new JLabel("���θ� �ּ� : "));
			address2Panel.add(address2);
			formPanel.add(address2Panel);

			JPanel price1Panel = new JPanel();
			price1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price1Panel.add(new JLabel("PT 1ȸ ���� : "));
			price1Panel.add(price1);
			formPanel.add(price1Panel);

			JPanel price10Panel = new JPanel();
			price10Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price10Panel.add(new JLabel("PT 10ȸ ���� : "));
			price10Panel.add(price10);
			formPanel.add(price10Panel);

			JPanel price20Panel = new JPanel();
			price20Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price20Panel.add(new JLabel("PT 20ȸ ���� : "));
			price20Panel.add(price20);
			formPanel.add(price20Panel);
		}

		// �Է����� ó������ �ؽ�Ʈ �ʵ�
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

		// ȸ������ ��ư ������ �� �̺�Ʈó��
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				String myId = "";
				String myGym = Tgym.getText();
				String myPhoneNum = phone.getText();
				String myPwd = new String(pwd.getPassword());
				String myName = name.getText();
				String myAddress1 = address1.getText(); //����
				String myAddress2 = address2.getText(); //���θ��ּ�
				String myPrice1 = price1.getText();
				String myPrice10 = price10.getText();
				String myPrice20 = price20.getText();

				// 0. �Է��� ���� ���Ŀ� �°� ���� ó��.
				boolean JoinSuccess = true; // ��ü �α��� ���� ����.
				boolean correctPhoneNum = true;

				// �Է��� ��ȭ��ȣ ���ڿ��� ���ڷθ� �����ƴ��� Ȯ��.
				for (int i = 0; i < myPhoneNum.length(); i++) {
					char temp = myPhoneNum.charAt(i);
					if (!Character.isDigit(temp)) {
						correctPhoneNum = false;
						break;
					}
				}

				// [���̵�]
				if (myPhoneNum.length() != 4) {
					checkCondition.setText("��ȭ��ȣ ��4�ڸ��� 4�ڸ� ���ڸ� �Է����ּ���.");
					JoinSuccess = false;
				} else if (!correctPhoneNum) {
					checkCondition.setText("��ȭ��ȣ ��4�ڸ����� ���ڸ� �Է����ּ���.");
					JoinSuccess = false;
				}
				// [���]
				else if (myPwd.length() > 10) {
					checkCondition.setText("��й�ȣ�� �ʹ� ��ϴ�. (10�� �̳�)");
					JoinSuccess = false;
				}
				// [�̸�]
				else if (userType.equals("����") && !(0 < myName.length() && myName.length() <= 25)) {
					checkCondition.setText("�ｺ�� �̸��� �ʹ� ª�ų� �ʹ� ��ϴ�. (1�� �̻� 25�� �̳�)");
					JoinSuccess = false;
				} else if ((userType.equals("ȸ��") || userType.equals("Ʈ���̳�"))
						&& !(0 < myName.length() && myName.length() <= 10)) {
					checkCondition.setText("�̸��� �ʹ� ª�ų� �ʹ� ��ϴ�. (1�� �̻� 10�� �̳�)");
					JoinSuccess = false;
				}

				// =================================================
				// ===================== ȸ�� =======================
				if (userType.equals("ȸ��")) {
					// 0. �Է��� ���� ���Ŀ� �°� ���� ó��.
					if (!(myAddress1.length()>0 && myAddress1.length() <= 6)) {
						checkCondition.setText("�������� �ʹ� ª�ų� ��ϴ�. (1�� �̻� 6�� �̳�)");
						JoinSuccess = false;
					}

					// 1. [��ȭ��ȣ�� ���̵� �����ϴ� �κ�] START
					/*
					 * [�������]
					 * �̹� ������� ���̵� vs �Է¹����ɷ� �ĺ����� (x)
					 * �̹� ������� ���̵� �߶� ��ȭ��ȣ�� ���� vs �Է¹��� ��ȭ��ȣ (o)
					 * 
					 * [����]
					 * 2345��� ��ȭ��ȣ �Է½�, 2345 ��ȣ���� ��� �� ����. 23450, 23451 �� �����Ѵٸ� count�� 2. ���� ID��
					 * 23452.
					 */
					String joinQuery = "WITH phonenum as"
							+ "( SELECT SUBSTRING(ȸ����ȣ, 2, 4) as substring"
							+ " FROM DB2022_ȸ��)"
							+ " SELECT count(*)"
							+ " FROM phonenum"
							+ " WHERE substring LIKE ?;";

					try {
						// ���� ����
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();

						// ���̵� �����
						// ��ó�� ������ ��ȭ��ȣ�� ���� num�� 0���� ���۵� ����.
						while (rs.next()) {
							int num = rs.getInt(1);
							if (!(0 <= num && num <= 9)) {
								JOptionPane.showMessageDialog(null,
										"�˼��մϴ�. �Է��Ͻ� �ڵ�����ȣ ���ڸ��� �̹� �ʹ� ���� ȸ���� ��� ���Դϴ�." + "\n"
												+ "�ٸ� ������ 4�ڸ� ���� �Է����ּ���.");
								JoinSuccess = false;
							} else {
								myId = "M" + myPhoneNum + Integer.toString(num);
							}
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
					// 1. [��ȭ��ȣ�� ���̵� �����ϴ� �κ�] END

					if (JoinSuccess == true) {
						// 2. ������� ���̵� Ȯ�ν����ִ� �κ�.
						JOptionPane.showMessageDialog(null,
								"���̵� : " + myId + ", ��й�ȣ : " + myPwd + ", �� �� : " + myName + ", �� �� : " + myAddress1
										+ "\n"
										+ "�α��� �� ���̵� �ʿ��ϴ� ���̵� �� ������ּ���.\n"
										+ "���̵�� (Member�� M) + (�ڵ��� ��ȣ ���ڸ�) + (���� �ϳ�)�� �����Ǿ��ֽ��ϴ�.");

						// 3. DB�� ����
						try {
							// insert into DB2022_ȸ�� values(�ｺ���ȣ, ȸ����ȣ, �̸�, ����, ��üȽ��, ����Ƚ��, ���Ʈ���̳�, ����ȸ����,
							// ��й�ȣ);
							String JoinQuery = "insert into DB2022_ȸ�� values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
							PreparedStatement pst = conn.prepareStatement(JoinQuery);
							pst.setString(1, null);
							pst.setString(2, myId);
							pst.setString(3, myName);
							pst.setString(4, myAddress1);
							pst.setInt(5, 0);
							pst.setInt(6, 0);
							pst.setString(7, null);
							pst.setString(8, "����");
							pst.setString(9, myPwd);
							pst.executeUpdate();
						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== Ʈ���̳� =======================
				else if (userType.equals("Ʈ���̳�")) {
					// 1. ��ȭ��ȣ�� ���̵� ����� START
					String joinQuery = "WITH phonenum as"
							+ "( SELECT SUBSTRING(�����ȣ, 2, 4) as substring"
							+ " FROM DB2022_Ʈ���̳�)"
							+ " SELECT count(*)"
							+ " FROM phonenum"
							+ " WHERE substring LIKE ?;";

					try {
						// ���� ����
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();

						// ���̵� �����
						while (rs.next()) {
							int num = rs.getInt(1);
							if (!(0 <= num && num <= 9)) {
								JOptionPane.showMessageDialog(null,
										"�˼��մϴ�. �Է��Ͻ� �ڵ�����ȣ ���ڸ��� �̹� �ʹ� ���� ȸ���� ��� ���Դϴ�." + "\n"
												+ "�ٸ� ������ 4�ڸ� ���� �Է����ּ���.");
								JoinSuccess = false;
							} else {
								myId = "T" + myPhoneNum + Integer.toString(num);
							}
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
					// ��ȭ��ȣ�� ���̵� ����� END
					try{
						PreparedStatement pst = conn.prepareStatement("SELECT * FROM DB2022_�ｺ�� WHERE(�ｺ���ȣ=?)");
						pst.setString(1, myGym);
						ResultSet rs = pst.executeQuery();
						if(rs.next() == false){
							checkCondition.setText("�Է��Ͻ� �ｺ���� �������� �ʽ��ϴ�. �ٽ� �Է��� �ּ���");
							JoinSuccess = false;
						}
					}catch (SQLException ex){
						ex.printStackTrace();
					}
					if (JoinSuccess == true) {
						// 2. �Է��� ���� Ȯ��
						JOptionPane.showMessageDialog(null,
								"���̵� : " + myId + ", ��й�ȣ : " + myPwd + ", �� �� : " + myName +  "�Ҽ� �ｺ�� : " + myGym + "\n"
										+ "�α��� �� ���̵� �ʿ��ϴ� ���̵� �� ������ּ���.\n"
										+ "���̵�� (Trainer�� T) + (�ڵ��� ��ȣ ���ڸ�) + (���� �ϳ�)�� �����Ǿ��ֽ��ϴ�.");

						// 3. DB�� ����
						try {
							// insert into DB2022_Ʈ���̳� values (�ｺ���ȣ, �����ȣ, �̸�, ���ȸ����, �ѱٹ��ð�, ��й�ȣ);
							String JoinQuery = "insert into DB2022_Ʈ���̳� values (?, ?, ?, ?, ?, ?);";
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
				// ===================== ���� =======================
				else if (userType.equals("����")) {
					// 0. �Է��� ���� ���Ŀ� �°� ���� ó��.
					boolean isInt = true;
					boolean isNotNull = true;

					if (myPrice1.length() == 0 || myPrice10.length() == 0 || myPrice20.length() == 0) {
						checkCondition.setText("������ �Է����ּ���");
						JoinSuccess = false;
						isNotNull = false;
					}

					// �Է��� ���� ���ڿ��� ���ڷθ� �����ƴ��� Ȯ��.
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

					// [�ּ�]
					if (!(0 < myAddress1.length() && myAddress1.length() <= 6)) {
						checkCondition.setText("wldur���� �ʹ� ª�ų� ��ϴ�. (1�� �̻� 6�� �̳�)");
						JoinSuccess = false;
					} else if (!(0 < myAddress2.length() && myAddress2.length() <= 6)) {
						checkCondition.setText("���θ��ּҰ� �ʹ� ª�ų� ��ϴ�. (1�� �̻� 20�� �̳�)");
						JoinSuccess = false;
					} 
					// [����]
					else if (!isInt) {
						checkCondition.setText("������������ ���ڸ� �Է����ּ���.");
						JoinSuccess = false;
					} else if (!(0 < myPrice1Int && myPrice1Int <= 300) || !(0 < myPrice10Int && myPrice10Int <= 300)
							|| !(0 < myPrice20Int && myPrice20Int <= 300)) {
						checkCondition.setText("������ 1���� �̻� 300�������Ϸ� �ۼ����ּ���.");
						JoinSuccess = false;
					}

					// 1. ��ȭ��ȣ�� ���̵� ����� START
					String joinQuery = "WITH phonenum as"
							+ "( SELECT SUBSTRING(�ｺ���ȣ, 2, 4) as substring"
							+ " FROM DB2022_�ｺ��)"
							+ " SELECT count(*)"
							+ " FROM phonenum"
							+ " WHERE substring LIKE ?;";

					try {
						// ���� ����
						PreparedStatement pst = conn.prepareStatement(joinQuery);
						pst.setString(1, myPhoneNum);
						ResultSet rs = pst.executeQuery();

						// ���̵� �����
						while (rs.next()) {
							int num = rs.getInt(1);
							myId = "G" + myPhoneNum + Integer.toString(num);
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
					// ��ȭ��ȣ�� ���̵� ����� END

					if (JoinSuccess == true) {
						// 2. �Է��� ���� Ȯ��
						JOptionPane.showMessageDialog(null,
								"���̵� : " + myId + ", ��й�ȣ : " + myPwd + ", �ｺ�� �̸� : " + myName
										+ "\n�ּ� : " + myAddress1 + " " + myAddress2 + " " + "\n"
										+ "�α��� �� ���̵� �ʿ��ϴ� ���̵� �� ������ּ���.\n"
										+ "���̵�� (Gym�� G) + (�ڵ��� ��ȣ ���ڸ�) + (���� �ϳ�)�� �����Ǿ��ֽ��ϴ�.");

						// 3. DB�� ����
						try {
							// insert into DB2022_�ｺ�� values (�ｺ���ȣ, �̸�, ����, ���θ��ּ�, ��üȸ����, ��üƮ���̳ʼ�,
							// ��й�ȣ);
							String JoinQuery = "insert into DB2022_�ｺ�� values (?, ?, ?, ?, ?, ?, ?);";
							PreparedStatement pst = conn.prepareStatement(JoinQuery);
							pst.setString(1, myId);
							pst.setString(2, myName);
							pst.setString(3, myAddress1);
							pst.setString(4, myAddress2);
							pst.setInt(6, 0);
							pst.setInt(7, 0);
							pst.setString(8, myPwd);
							pst.executeUpdate();

							// insert into DB2022_���� values (�ｺ���ȣ, 1ȸ����, 10ȸ����, 20ȸ����, ��Ÿ���θ�Ǽ���);
							String JoinQuery2 = "insert into DB2022_���� values (?, ?, ?, ?, ?);";
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
					// ȸ������ ������ �ٽ� �α��� ȭ������.
					JOptionPane.showMessageDialog(null, "ȸ������ ����! �α��� ���ּ���.");
					new LoginScreen(conn, userType);
					dispose(); // ������ frame�� �����Ű�� �޼���.
				}
			}
		});

		// ��� ��ư�� Ŭ������ �� �̺�Ʈ ó��
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new LoginScreen(conn, userType);
				dispose();

			}
		});
	}
}