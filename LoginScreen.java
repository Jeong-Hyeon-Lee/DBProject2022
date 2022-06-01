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

		setTitle("�α���");

		JPanel title = new JPanel();

		JLabel login = new JLabel(userType + " �α���");
		login.setForeground(new Color(5, 0, 153));
		login.setFont(new Font("���� ���", Font.BOLD, 20));
		title.add(login);

		// ���̵� text field
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(2, 2));

		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb1 = new JLabel("���̵� : ", JLabel.CENTER);

		idPanel.add(jlb1);

		JPanel idPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField jtf1 = new JTextField(10);

		idPanel2.add(jtf1);

		jp1.add(idPanel);
		jp1.add(idPanel2);

		// ��й�ȣ text field
		JPanel pwdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb2 = new JLabel("��й�ȣ : ", JLabel.CENTER);

		JPanel pwdPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPasswordField jtf2 = new JPasswordField(10);

		pwdPanel.add(jlb2);
		pwdPanel2.add(jtf2);
		jp1.add(pwdPanel);
		jp1.add(pwdPanel2);

		// ��ư
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel loginPanel = new JPanel(new FlowLayout());
		JButton jLogin = new JButton("�α���");

		JPanel joinPanel = new JPanel(new FlowLayout());
		JButton join = new JButton("ȸ������");

		JPanel backPanel = new JPanel(new FlowLayout());
		JButton back = new JButton("����ȭ��");

		loginPanel.add(jLogin);
		joinPanel.add(join);
		backPanel.add(back);
		jp2.add(loginPanel);
		jp2.add(joinPanel);
		jp2.add(backPanel);

		// jp3�� ��ġ
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		jp3.add(jp1);
		jp3.add(jp2);

		setLayout(new BorderLayout());

		add(title, BorderLayout.NORTH);
		add(jp1, BorderLayout.CENTER);
		add(jp2, BorderLayout.SOUTH);

		setBounds(200, 200, 400, 250);

		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// �̺�Ʈ ó��
		//�α��� ��ư Ŭ����
		jLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//�Էµ� �� �޾ƿ���
				String myId = jtf1.getText();
				String myPwd = new String(jtf2.getPassword());

				//�˾� â ��� Ȯ�� �����ֱ�
				JOptionPane.showMessageDialog(null, "���̵� : " + myId + ", ��й�ȣ : " + myPwd);
				
				//ȸ��MENUâ ����
				Connection conn;
				try {
					conn = DriverManager.getConnection(URL,USER,PASS);
					new M_MainScreen(conn,ID);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//DB���� (�̿ϼ�)
				/*
				if(userType.equals("ȸ��")) {
					//����
				}
				
				else if(userType.equals("Ʈ���̳�")) {
					boolean trainerLoginSuccess = false;
					String trainer_name = null; // ó�� Ʈ���̳ʰ� �α����� ���� Ʈ���̳��� �̸� ����
					String trainer_id = null; // ó�� Ʈ���̳ʰ� �α����� ���� Ʈ���̳��� ���̵� ����
					
					String loginquery = "SELECT ��й�ȣ, �̸�, �����ȣ, �ｺ���ȣ, ���ȸ����, �ѱٹ��ð� "
							+ "FROM DB2022_Ʈ���̳�" + " WHERE (�����ȣ=?)";

					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						Resultset rs = (Resultset) pst.executeQuery();
						
						if (rs.next().getString("��й�ȣ").equals(myPwd)) {
							trainerLoginSuccess = true; 
							JOptionPane.showMessageDialog(null, "�α��� ����");
						}
						else {
							JOptionPane.showMessageDialog(null, "���̵� ���ų�, �ùٸ��� ���� ��й�ȣ�Դϴ�. �ٽ� �α������ּ���.");
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
				
				else if(userType.equals("����")) {
					//����
				}
				*/
			}
		});

		//ȸ������ ��ư Ŭ����
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new JoinScreen(userType);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});

		//����ȭ�� ��ư Ŭ����
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new StartScreen();
				dispose();

			}
		});
	}

}
