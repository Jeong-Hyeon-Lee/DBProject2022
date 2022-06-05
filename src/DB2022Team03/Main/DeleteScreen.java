package DB2022Team03.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;

import DB2022Team03.TrainerWithGUI.TrainerMenuJTable;
import DB2022Team03.Gym.G_selectMenu;
import DB2022Team03.MemberInfo.M_MainScreen;

public class DeleteScreen extends JFrame {
	
	String owner_name = null;

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
				if (userType.equals("ȸ��")) { // ȸ��������, ȸ�� ���̺�, ���� ���̺��� ���� �̷�������
					try {
						// transaction ����
						conn.setAutoCommit(false);

						// STEP0. �ʿ��� ���� ã�Ƽ� �����صα�
						String member_gym = null; // �α����� ȸ���� �Ҽ� �ｺ���ȣ
						String member_trainer = null; // �α����� ȸ���� ��� Ʈ���̳ʹ�ȣ

						String loginquery = " SELECT * "
								+ " FROM DB2022_ȸ�� USE INDEX (ȸ����ȣ�ε���)"
								+ " WHERE (ȸ����ȣ=?) ";

						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();

						while (rs.next()) {
							member_gym = rs.getString("�Ҽ��ｺ��");
							member_trainer = rs.getString("���Ʈ���̳�");
						}

						// STEP1. ���� ���̺��� �ش� ȸ���� ȸ����ȣ�� 000000���� ����
						// ������ ȸ���� ������. ȸ�� ���̺� �̹� Ż��ó���� ���� 000000�� tuple�� ����.
						String deleteQuery1 = " UPDATE DB2022_���� "
								+ " SET ȸ����ȣ = ? "
								+ " WHERE ȸ����ȣ = ? ";

						PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
						pst1.setString(1, "000000");
						pst1.setString(2, ID);
						pst1.executeUpdate();

						// STEP2. ȸ�� ���̺��� �ش� ȸ�� ����
						String deleteQuery2 = " DELETE FROM DB2022_ȸ�� "
								+ " WHERE ȸ����ȣ = ? ";

						PreparedStatement pst2 = conn.prepareStatement(deleteQuery2);
						pst2.setString(1, ID);
						pst2.executeUpdate();

						// STEP3. �ｺ�忡�� ��üȸ���� ����
						String deleteQuery3 = " UPDATE DB2022_�ｺ�� "
								+ " SET ��üȸ���� = ��üȸ���� - 1 "
								+ " WHERE �ｺ���ȣ = ? ";

						PreparedStatement pst3 = conn.prepareStatement(deleteQuery3);
						pst3.setString(1, member_gym);
						pst3.executeUpdate();

						// STEP4. ���Ʈ���̳��� ��� ȸ���� ����
						String deleteQuery4 = " UPDATE DB2022_Ʈ���̳� "
								+ " SET ���ȸ���� = ���ȸ���� - 1 "
								+ " WHERE �����ȣ = ? ";

						PreparedStatement pst4 = conn.prepareStatement(deleteQuery4);
						pst4.setString(1, member_trainer);
						pst4.executeUpdate();

						// STEP5. ��������, ȸ����ȣ Ʈ���̳ʹ�ȣ�� �Ѵ� 000000�� ��� �ش� ���� ����
						String deleteQuery5 = " DELETE FROM DB2022_���� "
								+ " WHERE ȸ����ȣ = ? and �����ȣ = ?";

						PreparedStatement pst5 = conn.prepareStatement(deleteQuery5);
						pst5.setString(1, "000000");
						pst5.setString(2, "000000");
						pst5.executeUpdate();

						conn.commit(); // transaction ��
						conn.setAutoCommit(true);

					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();

						System.out.println("Roll Back ����");

						try {
							if (conn != null)
								conn.rollback(); // ���� ������� �ʾ��� �� rollback();
						} catch (SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== Ʈ���̳� =======================
				else if (userType.equals("Ʈ���̳�")) {
					try {
						// transaction ����
						conn.setAutoCommit(false);

						// STEP0. �ʿ��� ���� ã�Ƽ� �����صα�
						String trainer_gym = null; // �α����� Ʈ���̳��� �Ҽ� �ｺ���ȣ

						String loginquery = " SELECT * "
								+ " FROM DB2022_Ʈ���̳� USE INDEX (�����ȣ�ε���)"
								+ " WHERE(�����ȣ=?) ";

						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();

						while (rs.next()) {
							trainer_gym = rs.getString("�ｺ���ȣ");
						}

						// STEP1. ���ȸ���� ���Ʈ���̳ʸ� 000000��
						// ȸ���� Ʈ���̳ʸ� �����ϰ� �����Ƿ�, ȸ������ ���� 000000�� ��������� ���� ����.
						String deleteQuery1 = " UPDATE DB2022_ȸ��"
								+ " SET ���Ʈ���̳� = ?"
								+ " WHERE(���Ʈ���̳� = ?)";

						PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
						pst1.setString(1, "000000");
						pst1.setString(2, ID);
						pst1.executeUpdate();

						// STEP3. �ｺ�忡�� ��üƮ���̳ʼ� ����
						String deleteQuery3 = " UPDATE DB2022_�ｺ��"
								+ " SET ��üƮ���̳ʼ� = ��üƮ���̳ʼ� - 1"
								+ " WHERE(�ｺ���ȣ=?)";

						PreparedStatement pst3 = conn.prepareStatement(deleteQuery3);
						pst3.setString(1, trainer_gym);
						pst3.executeUpdate();

						// STEP4. �ش� Ʈ���̳��� �������� �����ȣ�� 000000���� ����
						// ������ Ʈ���̳ʸ� ������. Ʈ���̳� ���̺� �̹� Ż��ó���� ���� 000000�� tuple�� ����.
						String deleteQuery4 = " UPDATE DB2022_���� "
								+ " SET �����ȣ = ? "
								+ " WHERE �����ȣ = ? ";

						PreparedStatement pst4 = conn.prepareStatement(deleteQuery4);
						pst4.setString(1, "000000");
						pst4.setString(2, ID);
						pst4.executeUpdate();

						// STEP5. ��������, ȸ����ȣ Ʈ���̳ʹ�ȣ�� �Ѵ� 000000�� ��� �ش� ���� ����
						String deleteQuery5 = " DELETE FROM DB2022_���� "
								+ " WHERE ȸ����ȣ = ? and �����ȣ = ?";

						PreparedStatement pst5 = conn.prepareStatement(deleteQuery5);
						pst5.setString(1, "000000");
						pst5.setString(2, "000000");
						pst5.executeUpdate();

						// STEP2. Ʈ���̳� ���̺��� �ش� Ʈ���̳� ����
						String deleteQuery2 = " DELETE FROM DB2022_Ʈ���̳�"
								+ " WHERE(�����ȣ=?)";

						PreparedStatement pst2 = conn.prepareStatement(deleteQuery2);
						pst2.setString(1, ID);
						pst2.executeUpdate();
						
						conn.commit(); // transaction ��
						conn.setAutoCommit(true);

					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();

						System.out.println("Roll Back ����");

						try {
							if (conn != null)
								conn.rollback(); // ���� ������� �ʾ��� �� rollback();
						} catch (SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== ���� =======================
				else if (userType.equals("����")) {
					try {
						// transaction ����
						conn.setAutoCommit(false);

						// STEP0. �ش� �ｺ�忡 �Ҽ��� Ʈ���̳�, ȸ���� 0���� ���� Ż�� �����
						int trainerNum = -1;
						int memberNum = -1;

						// �Ҽ�Ʈ���̳ʼ�, �Ҽ�ȸ���� ã��, �̵��� �ٽ� ����޴� �ҷ��� �� �ʿ��� owner_name�� ã�Ƶα�
						String loginquery = " SELECT *"
								+ " FROM DB2022_�ｺ��"
								+ " WHERE (�ｺ���ȣ=?) ";

						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();

						while (rs.next()) {
							trainerNum = rs.getInt("��üƮ���̳ʼ�");
							memberNum = rs.getInt("��üȸ����");
							owner_name = rs.getString("�̸�");
						}

						// �Ҽ� Ʈ���̳�, �Ҽ� ȸ�� ���� 0���� ���� Ż�� ���
						if (trainerNum == 0 && memberNum == 0) {
							// STEP1. �������� ���� >> ON DELETE CASCADE �Ǿ�����.
							// STEP2. �������� ���� >> �̹� ȸ���̳� Ʈ���̳� ������ �� ������.
							// STEP3. ȸ������ ���� >> �̹� 0���̶� ������ �� ����
							// STEP4. Ʈ���̳����� ���� >> �̹� 0���̶� ������ �� ����
							// STEP5. �ｺ�� ���� ����
							String deleteQuery1 
									= " DELETE FROM DB2022_�ｺ�� "
									+ " WHERE �ｺ���ȣ = ? ";

							PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
							pst1.setString(1, ID);
							pst1.executeUpdate();

							conn.commit(); // transaction ��
							conn.setAutoCommit(true);
						} else {
							deleteSuccess = false;
							JOptionPane.showMessageDialog(null, "�ش� �ｺ�忡 ���� Ʈ���̳ʿ� ȸ���� ��� Ż������ ���� Ż�𰡴��մϴ�."
									+ "\n"
									+ "���� ��ü Ʈ���̳� ��: " + trainerNum + "\n"
									+ "���� ��ü ȸ�� ��: " + memberNum + "\n"
									+ "�̹Ƿ� Ż�� �Ұ����մϴ�.");
						}

					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();

						System.out.println("Roll Back ����");

						try {
							if (conn != null)
								conn.rollback(); // ���� ������� �ʾ��� �� rollback();
						} catch (SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				if (deleteSuccess == true) {
					// Ż�𼺰��� �ٽ� startȭ������
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

				// �ٽ� ���� �޴� �������� ���ư���
				if (userType.equals("ȸ��")) {
					new M_MainScreen(conn, ID);
				} else if (userType.equals("Ʈ���̳�")) {
					new TrainerMenuJTable(ID);
				} else if (userType.equals("����")) {
					try {
						new G_selectMenu(ID, owner_name);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				dispose();

			}
		});

	}
}