package DB2022Team03.YeonWoo;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;

public class DeleteScreen extends JFrame {

	public DeleteScreen(Connection conn, String userType, String ID) { 
		setTitle("Ż��");

		// title
		JPanel titlePanel = new JPanel();
		JLabel delete = new JLabel(userType + " Ż��");
		delete.setForeground(new Color(5, 0, 153));
		delete.setFont(new Font("���� ���", Font.BOLD, 20));
		titlePanel.add(delete);

		// check ����
		JPanel checkPanel = new JPanel(new FlowLayout());
		JLabel check = new JLabel(" ", JLabel.CENTER);
		check.setText(ID + "��, ���� Ż���Ͻðڽ��ϱ�?");
		checkPanel.add(check);
		
		JLabel check2 = new JLabel(" ", JLabel.CENTER);
		check2.setText("Ż�� �� ���õ� ��� ������ �����˴ϴ�.");
		checkPanel.add(check2);

		// ��ư
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		JPanel deletePanel = new JPanel(new FlowLayout());
		JButton deleteButton = new JButton("Ż��");
		deletePanel.add(deleteButton);
		
		JPanel backPanel = new JPanel(new FlowLayout());
		JButton backButton = new JButton("���");
		backPanel.add(backButton);

		buttonPanel.add(deletePanel);
		buttonPanel.add(backPanel);

		// Panel�� ��ġ
		JPanel Panel = new JPanel();
		Panel.setLayout(new FlowLayout());
		Panel.add(buttonPanel);

		setLayout(new BorderLayout());

		add(titlePanel, BorderLayout.NORTH);
		add(checkPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setBounds(200, 200, 400, 250);

		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		
		// Ż�� ��ư ������ �� �̺�Ʈó��
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				boolean deleteSuccess = true;

				// =================================================
				// ===================== ȸ�� =======================
				if (userType.equals("ȸ��")) { //ȸ��������, ȸ�� ���̺�, ���� ���̺��� ���� �̷�������
					try {
						//transaction ����
						conn.setAutoCommit(false);
						
						//STEP0. �ʿ��� ���� ã�Ƽ� �����صα�
						String member_gym = null; // �α����� ȸ���� �Ҽ� �ｺ���ȣ
						String member_trainer = null; //�α����� ȸ���� ��� Ʈ���̳ʹ�ȣ
						
						String loginquery 
						= " SELECT * " 
						+ " FROM DB2022_ȸ��" 
						+ " WHERE (ȸ����ȣ=?) ";
						
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();
						
						while(rs.next()) {
							member_gym = rs.getString("�Ҽ��ｺ��");
							member_trainer = rs.getString("���Ʈ���̳�");
						}
						
						//STEP1. ���� ���̺��� �ش� ȸ���� ���� ����
						//������ ȸ���� �����ϹǷ�, ���� ���� ����
						String deleteQuery1
						= " DELETE FROM DB2022_���� "
						+ " WHERE ȸ����ȣ = ? ";
						
						PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
						pst1.setString(1, ID);
						pst1.executeUpdate();
						
						//STEP2. ȸ�� ���̺��� �ش� ȸ�� ����
						String deleteQuery2
						= " DELETE FROM DB2022_ȸ�� "
						+ " WHERE ȸ����ȣ = ? ";
						
						PreparedStatement pst2 = conn.prepareStatement(deleteQuery2);
						pst2.setString(1, ID);
						pst2.executeUpdate();
						
						//STEP3. �ｺ�忡�� ��üȸ���� ����
						String deleteQuery3
						= " UPDATE DB2022_�ｺ�� "
						+ " SET ��üȸ���� = ��üȸ���� - 1 "
						+ " WHERE �ｺ�� = ? ";
						
						PreparedStatement pst3 = conn.prepareStatement(deleteQuery3);
						pst3.setString(1, member_gym);
						pst3.executeUpdate();
						
						//STEP4. ���Ʈ���̳��� ��� ȸ���� ����
						String deleteQuery4
						= " UPDATE DB2022_Ʈ���̳� "
						+ " SET ���ȸ���� = ���ȸ���� - 1 "
						+ " WHERE �����ȣ = ? ";
						
						PreparedStatement pst4 = conn.prepareStatement(deleteQuery4);
						pst4.setString(1, member_trainer);
						pst4.executeUpdate();
						
						conn.commit(); //transaction ��, ������ؿ����� �� commit();
						conn.setAutoCommit(true);
					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();
						
						System.out.println("Roll Back ����");
						
						try {
							if(conn!=null)
								conn.rollback(); //���� ������� �ʾ��� �� rollback();
						} catch(SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== Ʈ���̳� =======================
				else if (userType.equals("Ʈ���̳�")) { 
					try {
						//transaction ����
						conn.setAutoCommit(false);
						
						//STEP0. �ʿ��� ���� ã�Ƽ� �����صα�
						String trainer_gym = null; // �α����� Ʈ���̳��� �Ҽ� �ｺ���ȣ
						
						String loginquery 
						= " SELECT * " 
						+ " FROM DB2022_Ʈ���̳�" 
						+ " WHERE (�����ȣ=?) ";
						
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();
						
						while(rs.next()) {
							trainer_gym = rs.getString("�ｺ���ȣ");
						}
						
						//STEP1. ���� ���̺��� �ش� Ʈ���̳��� ���� ����
						//������ Ʈ���̳ʸ� �����ϹǷ�, ���� ���� ����
						String deleteQuery1
						= " DELETE FROM DB2022_���� "
						+ " WHERE �����ȣ = ? ";
						
						PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
						pst1.setString(1, ID);
						pst1.executeUpdate();
						
						//STEP2. Ʈ���̳� ���̺��� �ش� Ʈ���̳� ����
						String deleteQuery2
						= " DELETE FROM DB2022_Ʈ���̳� "
						+ " WHERE �����ȣ = ? ";
						
						PreparedStatement pst2 = conn.prepareStatement(deleteQuery2);
						pst2.setString(1, ID);
						pst2.executeUpdate();
						
						//STEP3. �ｺ�忡�� ��üƮ���̳ʼ� ����
						String deleteQuery3
						= " UPDATE DB2022_�ｺ�� "
						+ " SET ��üƮ���̳ʼ� = ��üƮ���̳ʼ� - 1 "
						+ " WHERE �ｺ�� = ? ";
						
						PreparedStatement pst3 = conn.prepareStatement(deleteQuery3);
						pst3.setString(1, trainer_gym);
						pst3.executeUpdate();
						
						//STEP4. ���ȸ���� ���Ʈ���̳ʸ� null��
						String deleteQuery4
						= " UPDATE DB2022_ȸ�� "
						+ " SET ���Ʈ���̳� = null "
						+ " WHERE �����ȣ = ? ";
						
						PreparedStatement pst4 = conn.prepareStatement(deleteQuery4);
						pst4.setString(1, ID);
						pst4.executeUpdate();
						
						conn.commit(); //transaction ��
						conn.setAutoCommit(true);
						
					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();
						
						System.out.println("Roll Back ����");
						
						try {
							if(conn!=null)
								conn.rollback(); //���� ������� �ʾ��� �� rollback();
						} catch(SQLException se2) {
							se2.printStackTrace();
						}
					}	
				}

				// =================================================
				// ===================== ���� =======================
				else if (userType.equals("����")) { 
					try {
						//transaction ����
						conn.setAutoCommit(false);
						
						//STEP0. �ش� �ｺ�忡 �Ҽ��� Ʈ���̳�, ȸ�� ���� ����
						// [����] create table�� �����صΰ� �̰ſ� ���� ����� �� ������ �� table�� ���ְ� ������, JDBC������ table�����Ǵ���, �ƴϸ� ��� �ϴ���.
						// [����] �Ҽ�Ʈ���̳�, �Ҽ�ȸ�� �Ѵ� �ｺ���ȣ�� �����ϰ� ������ ���δ� �� �´���.
						// [����] on delete cascade�����ϱ� ������ �����ϸ� �� ���� ��� ȸ��, Ʈ���̳� ���� ���� �������ص��ǳ�? �ٵ�, ������ �� cascade�� ����..?
						
						String trainer_gym = null; // �α����� ȸ���� �Ҽ� �ｺ���ȣ
						
						String loginquery 
						= " SELECT �����ȣ " 
						+ " FROM DB2022_Ʈ���̳�" 
						+ " WHERE (�ｺ���ȣ=?) ";
						
						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs1 = pst.executeQuery();
						
						while(rs1.next()) {
							
						}
						
						// STEP1. �������� ����
						// STEP1. �������� ����
						// STEP1. ȸ���������� �ｺ���ȣ null��
						// STEP1. Ʈ���̳��������� �ｺ���ȣ null��
						// STEP1. �ｺ�� ���� ����
						
					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();
						
						System.out.println("Roll Back ����");
						
						try {
							if(conn!=null)
								conn.rollback(); //���� ������� �ʾ��� �� rollback();
						} catch(SQLException se2) {
							se2.printStackTrace();
						}
					}
				}
		
				
				if (deleteSuccess == true) {
					//Ż�𼺰��� �ٽ� startȭ������
					JOptionPane.showMessageDialog(null, "Ż��ó�� �Ǿ����ϴ�.");
					new StartScreen(conn);
					dispose(); // ������ frame�� �����Ű�� �޼���.
				}
			}
		});
				
		// ��� ��ư�� Ŭ������ �� �̺�Ʈ ó��
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new LoginScreen(conn, userType);
				dispose();

			}
		});

	}
}
