package DB2022Team03.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;

import DB2022Team03.TrainerWithGUI.TrainerMenuJTable;
import DB2022Team03.Gym.G_selectMenu;
import DB2022Team03.MemberInfo.M_MainScreen;

public class LoginScreen extends JFrame {
	
	public LoginScreen(Connection conn, String userType) {

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
		//1. �α��� ��ư Ŭ����
		jLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//�Էµ� �� �޾ƿ���
				String myId = jtf1.getText();
				String myPwd = new String(jtf2.getPassword());

				//�˾� â ��� Ȯ�� �����ֱ�
				JOptionPane.showMessageDialog(null, "���̵� : " + myId + ", ��й�ȣ : " + myPwd);
			
				//DB���� 
				// ================ ȸ�� �α��� =============== 
				if(userType.equals("ȸ��")) {
					String member_id = null; // �α����� ȸ���� ȸ����ȣ
					String member_name = null; // �α����� ȸ���� �̸�
					String member_gym = null; // �α����� ȸ���� �Ҽ� �ｺ���ȣ
					
					String loginquery = "SELECT * " + "FROM DB2022_ȸ�� USE INDEX (ȸ����ȣ�ε���)" + " WHERE (ȸ����ȣ=?)";
					//������ ȸ����ȣ primary key�� null�ƴϸ� tuple�Ѱ�¥�� ResultSet ��ȯ >> while(rs.next()) �ʿ����
					
					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						ResultSet rs = pst.executeQuery();
					
						//ResultSet�� ����ִ� ���� if(!rs.next())���� Ȯ��
						//ResultSet.getString();�� null�� ���� rs.null()�� Ȯ��
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵��Դϴ�. �ٽ� �α������ּ���.");
						}
						else if(myId.equals("000000")) { //"000000"���δ� �α��� ���ϰ� ���� (Ż���� ȸ���� ǥ���ϱ����� ���� tuple���� ��¥ ȸ���� �ƴ�)
							JOptionPane.showMessageDialog(null, "�� ���̵�� �α����� �� ���� ���̵��Դϴ�.");
						}
						else if (rs.getString("��й�ȣ").equals(myPwd)) {
							JOptionPane.showMessageDialog(null, "�α��� ����");
							member_id = rs.getString("ȸ����ȣ");
							member_name = rs.getString("�̸�");
							member_gym = rs.getString("�Ҽ��ｺ��");
							
							//ȸ�� �޴� ������
							new M_MainScreen(conn,member_id);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "�ùٸ��� ���� ��й�ȣ�Դϴ�. �ٽ� �α������ּ���.");
						}
						
					}catch(SQLException sqle){
						sqle.printStackTrace();
					}
				}
				
				// ================ Ʈ���̳� �α��� =============== 
				else if(userType.equals("Ʈ���̳�")) {
					String trainer_id = null; // �α����� Ʈ���̳��� �����ȣ
					String trainer_name = null; // �α����� Ʈ���̳��� �̸�
					String trainer_gym = null; // �α����� Ʈ���̳��� �Ҽ� �ｺ���ȣ
					
					String loginquery = "SELECT * " + "FROM DB2022_Ʈ���̳� USE INDEX (�����ȣ�ε���)" + " WHERE (�����ȣ=?)"; 
					//������ �����ȣ primary key�� null�ƴϸ� tuple�Ѱ�¥�� ResultSet ��ȯ >> while(rs.next()) �ʿ����

					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						ResultSet rs = pst.executeQuery();
					
						//ResultSet�� ����ִ� ���� if(!rs.next())���� Ȯ��
						//ResultSet.getString();�� null�� ���� rs.null()�� Ȯ��
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵��Դϴ�. �ٽ� �α������ּ���.");
						}
						else if(myId.equals("000000")) { //"000000"���δ� �α��� ���ϰ� ���� (Ż���� ȸ���� ǥ���ϱ����� ���� tuple���� ��¥ ȸ���� �ƴ�)
							JOptionPane.showMessageDialog(null, "�� ���̵�� �α����� �� ���� ���̵��Դϴ�.");
						}
						else if (rs.getString("��й�ȣ").equals(myPwd)) { 
							JOptionPane.showMessageDialog(null, "�α��� ����");
							
							trainer_id = rs.getString("�����ȣ");
							trainer_name = rs.getString("�̸�");
							trainer_gym = rs.getString("�ｺ���ȣ");
							
							//Ʈ���̳� �޴� ������
							new TrainerMenuJTable(trainer_id);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "�ùٸ��� ���� ��й�ȣ�Դϴ�. �ٽ� �α������ּ���.");
						}
					}catch(SQLException sqle){
						sqle.printStackTrace();
					}
					
				}
				
				// ================ ���� �α��� =============== 
				else if(userType.equals("����")) {
					String owner_gym = null; // �α����� ������ �ｺ���ȣ
					String owner_name = null; // �α����� ������ �ｺ���̸�
					
					String loginquery = "SELECT * " + "FROM DB2022_�ｺ��" + " WHERE (�ｺ���ȣ=?)";
					//�ｺ���ȣ primary key�� null�ƴϸ� tuple�Ѱ�¥�� ResultSet ��ȯ >> while(rs.next()) �ʿ����
					
					try {
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1,  myId);
						ResultSet rs = pst.executeQuery();
						

						//ResultSet�� ����ִ� ���� if(!rs.next())���� Ȯ��
						//ResultSet.getString();�� null�� ���� rs.null()�� Ȯ��
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵��Դϴ�. �ٽ� �α������ּ���.");
						}
						else if(myId.equals("000000")) { //"000000"���δ� �α��� ���ϰ� ���� (Ż���� ȸ���� ǥ���ϱ����� ���� tuple���� ��¥ ȸ���� �ƴ�)
							JOptionPane.showMessageDialog(null, "�� ���̵�� �α����� �� ���� ���̵��Դϴ�.");
						}
						else if (rs.getString("��й�ȣ").equals(myPwd)) {
							JOptionPane.showMessageDialog(null, "�α��� ����");
							owner_gym = rs.getString("�ｺ���ȣ");
							owner_name = rs.getString("�̸�");
							
							//���� �޴� ������ 
							new G_selectMenu(owner_gym, owner_name);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "�ùٸ��� ���� ��й�ȣ�Դϴ�. �ٽ� �α������ּ���.");
						}
					}catch(SQLException sqle){
						sqle.printStackTrace();
					}
				}
				
			}
		});

		//2. ȸ������ ��ư Ŭ����
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new JoinScreen(conn, userType);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});

		//3. ����ȭ�� ��ư Ŭ����
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new StartScreen(conn);
				dispose();

			}
		});
	}

}