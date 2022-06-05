package DB2022Team03.Gym;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DB2022Team03.Main.DeleteScreen;
import DB2022Team03.Main.StartScreen;

//�������� �α������� �� ���̴� �޴� ȭ��
public class G_selectMenu extends JFrame {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03";

	//Database user, password
	static final String USER = "DB2022Team03";
	static final String PASSWORD= "DB2022Team03";
	
	static Connection conn;
	
	public G_selectMenu(String gymID, String gymName) throws SQLException{
		// TODO Auto-generated constructor stub
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); //�����ͺ��̽� ����
		
		setTitle("�ｺ�� PT ���� �ý��� - " + gymName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//ȭ�� ���
		JPanel top = new JPanel();
		
		//"MENU" �۾� ����
		JLabel selectMenu = new JLabel("MENU");
		selectMenu.setForeground(new Color(5, 0, 153));
		selectMenu.setFont(new Font("���� ���", Font.BOLD, 25));
		top.add(selectMenu);
		
		//�α׾ƿ�, Ż�� ��ư
		JPanel menu0 = new JPanel();
		JButton logout = new JButton("�α׾ƿ�");
		menu0.add(logout);
		JButton deleteAccount = new JButton("Ż���ϱ�");
		menu0.add(deleteAccount);
		menu0.setLayout(new GridLayout(2, 1));
		top.add(menu0);
		top.setLayout(new FlowLayout());

		JPanel btnpanel = new JPanel();
		btnpanel.setLayout(new GridLayout(4, 1));
		
		//�޴� ��ư
		JButton menu1 = new JButton("���� ���� �����ϱ�");
		JButton menu2 = new JButton("���θ�� ���� �����ϱ�");
		JButton menu3 = new JButton("Ʈ���̳� ���� ����");
		JButton menu4 = new JButton("ȸ�� �� ����");

		btnpanel.add(menu1);
		btnpanel.add(menu2);
		btnpanel.add(menu3);
		btnpanel.add(menu4);

		add(top, BorderLayout.NORTH);
		add(btnpanel, BorderLayout.CENTER);

		setBounds(200, 200, 400, 250);

		setResizable(false); // ȭ�� ũ�� ����

		setVisible(true);

		// �̺�Ʈ ó��
				menu1.addActionListener(new ActionListener() { //���� ���� �����ϱ� ��ư ���� ��
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							new G_changePriceInformation(conn, gymID);
						} catch (SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				menu2.addActionListener(new ActionListener() { //���θ�� ���� �����ϱ� ��ư ���� ��

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							new G_changePromotionInformation(conn, gymID);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				menu3.addActionListener(new ActionListener() { //Ʈ���̳� ���� ���� ��ư ���� ��

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							new G_showTrainers(conn, gymID);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
				
				menu4.addActionListener(new ActionListener() { //ȸ�� �� ���� ��ư ���� ��

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							new G_countTrainees(conn, gymID);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				logout.addActionListener(new ActionListener() { //�α׾ƿ� ��ư ���� ��

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new StartScreen(conn);
						dispose(); //���� â �ݱ�
					}
				});
				
				deleteAccount.addActionListener(new ActionListener() { //Ż���ϱ� ��ư ���� ��

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new DeleteScreen(conn, "����", gymID);
						dispose(); //���� â �ݱ�
					}
				});
			}
		}