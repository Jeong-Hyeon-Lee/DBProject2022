/*
* �������� (EUNSOO)
* 1) �ｺ��, Ʈ���̳�, ȸ������ ������� ���� ȸ���� '���� �����ϱ�' �޴��� ����� �� ����.
*  => �ش� ��ư�� ������ ȸ������ ������� �ʾҴٴ� �޼���â�� ���.
*/

package DB2022Team03.MemberInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DB2022Team03.Main.DeleteScreen;
import DB2022Team03.Main.StartScreen;
import DB2022Team03.Member_manageClass.M_manageClass;

public class M_MainScreen extends JFrame {
	public M_MainScreen(Connection conn,String ID) {
		setTitle("ȸ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("ȸ��MENU");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//�޴���
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(7,1));
		
		//�� �޴�
		//ȸ������Ȯ���ϱ� ��ư
		JPanel jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		JPanel Menu8 = new JPanel();
		JButton myPageBtn = new JButton("ȸ������Ȯ���ϱ�");
		Menu8.add(myPageBtn);
		jp4.add(Menu8);
		
		//�ｺ��ã�� ��ư
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JPanel Menu1 = new JPanel();
		JButton M_GScreen = new JButton("�ｺ�� ã��");
		Menu1.add(M_GScreen);
		jp1.add(Menu1);
		
		//Ʈ���̳�	ã�� ��ư
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel Menu2 = new JPanel();
		JButton M_TScreen = new JButton("Ʈ���̳� ã��");
		Menu2.add(M_TScreen);
		jp2.add(Menu2);
		
		//ȸ���ǵ��/���� ��ư
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		JPanel Menu3 = new JPanel();
		JButton enrollMembership = new JButton("ȸ���� ���/����");
		Menu3.add(enrollMembership);
		jp3.add(Menu3);
		
		
		/* *********************************************************************
		 * Eunsoo Part
		 ***********************************************************************/
		JPanel jp5 = new JPanel();
		jp5.setLayout(new FlowLayout());
		JPanel Menu5 = new JPanel();
		JButton M_mangeClass = new JButton("���� �����ϱ�");
		Menu5.add(M_mangeClass);
		jp5.add(Menu5);
		 /* *********************************************************************/
		
		//�α׾ƿ� ��ư
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�α׾ƿ�");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		//Ż���ư
		JPanel jp6 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu6 = new JPanel();
		JButton resign = new JButton("Ż���ϱ�");
		Menu6.add(resign);
		jp6.add(Menu6);

		//�޴��ǿ� �󼼸޴����̱�
		btnGroup.add(jp4);
		btnGroup.add(jp1);
		btnGroup.add(jp2);
		btnGroup.add(jp3);
		btnGroup.add(jp5);
		btnGroup.add(jp6);
		btnGroup.add(jp0);	
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,300,400);
		
		setResizable(false); //ȭ��ũ�����

		setVisible(true);
	
		//Btn event
		myPageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_myPage(conn,ID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				dispose(); // ������ frame ����
			}
		});
		M_GScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_searchGYM(conn,ID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				dispose(); 
			}
		});
		M_TScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_searchTrainer(conn,ID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				dispose(); 
			}
		});		
		undo.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//�α׾ƿ� : �� ó�� ���� ȭ������ �̵�
				new StartScreen(conn);
				dispose(); 
			}
		});
		enrollMembership.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					new M_enrollMembership(conn,ID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				dispose(); 

			}
		});
		
		
		/* *********************************************************************
		 * Eunsoo Part
		 ***********************************************************************/
		M_mangeClass.addActionListener(new ActionListener() {
			String query_test;
			PreparedStatement pstm_test;
			ResultSet rs_test;
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				query_test = "SELECT �Ҽ��ｺ��, ���Ʈ���̳�, ����ȸ���� FROM db2022_ȸ�� USE INDEX (ȸ����ȣ�ε���) WHERE ȸ����ȣ = ?";				
				try {
					pstm_test = conn.prepareStatement(query_test);
					pstm_test.setString(1, ID);
					rs_test = pstm_test.executeQuery(); 
					
					// Can manage classes only if the current member has a gym, a trainer, and membership.
					if(rs_test.next()) {
						if(rs_test.getString(1) != null && rs_test.getString(2) != null && rs_test.getString(3) != null) {
							new M_manageClass(conn,ID);
							dispose(); // ������ frame�� �����Ű�� �޼���.
						}
						else
							JOptionPane.showMessageDialog(null, "���� ȸ������ ������� �ʾҽ��ϴ�.");
					}
					else {
						JOptionPane.showMessageDialog(null, "��ϵ��� ���� ȸ���Դϴ�.");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});		
		 /* *********************************************************************/
		resign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new DeleteScreen(conn, "ȸ��", ID);
				dispose(); 

			}
		});
		
	}	
}