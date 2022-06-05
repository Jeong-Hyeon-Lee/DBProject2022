package DB2022Team03.Member_manageClass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DB2022Team03.MemberInfo.M_MainScreen;
import DB2022Team03.Main.StartScreen;

public class M_manageClass extends JFrame {
/*
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03"; 
	public static final String USER = "DB2022Team03";
	public static final String PASS = "DB2022Team03";
	public static final String ID = "M22380";
	public Connection conn;
*/
	/*
	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		new M_manageClass(conn,ID);
	}
	*/
	public M_manageClass(Connection conn, String ID) {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//Title
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("�������� MENU");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//�޴���
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(5,1));
		
		//�� �޴�
		// Cancel Class
		JPanel jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		JPanel Menu8 = new JPanel();
		JButton btn_cancel = new JButton("���� ����ϱ�");
		Menu8.add(btn_cancel);
		jp4.add(Menu8);
		
		// Reserve Class
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JPanel Menu1 = new JPanel();
		JButton btn_reserve = new JButton("���� �����ϱ�");
		Menu1.add(btn_reserve);
		jp1.add(Menu1);
		
		// See future classes		
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel Menu2 = new JPanel();
		JButton btn_seeReserved = new JButton("����� ���� ��ȸ");
		Menu2.add(btn_seeReserved);
		jp2.add(Menu2);
		
		// See past classes
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		
		JPanel Menu3 = new JPanel();
		JButton btn_seePast = new JButton("���� ���� ��ȸ");
		Menu3.add(btn_seePast);
		jp3.add(Menu3);
		
		
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);
		jp0.add(Menu9);

		//�޴��ǿ� �޴����̱�
		btnGroup.add(jp4);
		btnGroup.add(jp1);
		btnGroup.add(jp2);
		btnGroup.add(jp3);
		btnGroup.add(jp0);
				
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,300,400);
		
		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
	
		//Btn click �̺�Ʈ
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_cancelClass(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("���������� �ε� ����:"+e1);
				}
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		btn_reserve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_reserveClass(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		btn_seeReserved.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_seeFutureClasses(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});		
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn, ID);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		btn_seePast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_seePastClasses(conn,ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		
	}	
}