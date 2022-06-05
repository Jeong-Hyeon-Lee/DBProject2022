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
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class M_searchGYM extends JFrame {
	public static JTable jt;
	public static DefaultTableModel tableModel;
	static PreparedStatement pstmt = null;
	static ResultSet rset = null;
	static String str = null;
	static JLabel infoText;
	static JPanel btnGroup;
	static String columnNames[]= {"�ｺ��","����","1ȸ����","10ȸ����","20ȸ����","��Ÿ���θ��"};
	static Statement stmt; 
	
	public M_searchGYM(Connection conn, String ID) throws SQLException {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("�ｺ�� ã��");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//search - �ｺ�� �������� ã��
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		
		JPanel i1 = new JPanel();
		JLabel inputDesc = new JLabel("�˻��� : ");
		i1.add(inputDesc);
		input.add(i1);
		
		JPanel i2 = new JPanel();
		JTextField inputText = new JTextField(25);
		i2.add(inputText);
		input.add(i2);
		
		JPanel i3 = new JPanel();
		JButton searchGYMBtn = new JButton("���� �˻�"); //btnŬ���� ���ϴ� ������ ��ȸ�ϵ���
		i3.add(searchGYMBtn);
		input.add(i3);
		
		JPanel i4 = new JPanel();
		JButton searchGYMBtn2 = new JButton("�̸� �˻�"); //btnŬ���� ���ϴ� ������ ��ȸ�ϵ���
		i4.add(searchGYMBtn2);
		input.add(i4);
	
		//Table
		JPanel table = new JPanel();
		table.setLayout(new GridLayout(1,1));
		tableModel = new DefaultTableModel(columnNames,0);
		jt = new JTable(tableModel);
		
		//query for table
		stmt = conn.createStatement();
		str = "select * from DB2022_searchGYM";
		rset = stmt.executeQuery(str);
		
		//for err & undo 
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//table data
		if(!rset.isBeforeFirst()) {
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			jpErr.add(new JLabel("�ｺ�������� �ҷ����µ� �����߽��ϴ�."));
			btnGroup.add(jpErr);
		}
		else {
			while(rset.next()) {
				String gym = rset.getString(1);
				String location = rset.getString(2);
				int price1 = rset.getInt(3);
				int price10 = rset.getInt(4);
				int price20 = rset.getInt(5);
				String promotion = rset.getString(6);
				
				String[] data = {gym,location,String.valueOf(price1),String.valueOf(price10),String.valueOf(price20),promotion};
				
				tableModel.addRow(data);
			}
			jt = new JTable(tableModel);
			
			//��ũ��&column���� ���� JScrollPane ����
			JScrollPane scrollpane=new JScrollPane(jt);
			scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
		
			table.add(scrollpane);
		}
		
		//�ȳ�����
		JPanel info = new JPanel();
		infoText = new JLabel("�ｺ�� ����� ���Ͻø� ���ϴ� �ｺ���� Ŭ���� ��, �v���ϱ� ��ư�� �����ּ���.");
		info.add(infoText);
		btnGroup.add(info);
		
		//undo
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		//recommend
		JPanel recommend = new JPanel();
		JButton recommendBtn = new JButton("��õ�ޱ�");
		recommend.add(recommendBtn);
		jp0.add(recommend);
		
		//enroll
		JPanel enroll = new JPanel();
		JButton enrollBtn = new JButton("����ϱ�");
		enroll.add(enrollBtn);
		jp0.add(enroll);
		
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		JPanel center = new JPanel(new BorderLayout());
		center.add(input,BorderLayout.NORTH);
		center.add(table,BorderLayout.CENTER);
		
		add(M_main,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
		add(btnGroup,BorderLayout.SOUTH);
		setBounds(200,200,700,400);
		
		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
		
		searchGYMBtn.addActionListener(new ActionListener() { //�������� �˻�
			@Override //btnŬ���� ���ϴ� ������ ��ȸ�ϵ���
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();

				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from DB2022_searchGYM WHERE ���� like ?";
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("�ｺ�������� �ҷ����µ� �����߽��ϴ�.")); //�Է��� �������� �ｺ���� ã�� ���߽��ϴ�.
						btnGroup.add(jpErr);
					}
					else {
						while(rset.next()) {
							String gym = rset.getString(1);
							String location = rset.getString(2);
							String price1 = rset.getString(3);
							String price10 = rset.getString(4);
							String price20 = rset.getString(5);
							String promotion = rset.getString(6);
							
							String[] data = {gym,location,price1,price10,price20,promotion};
							
							tableModel.addRow(data);
						}
						jt.setModel(tableModel);					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		searchGYMBtn2.addActionListener(new ActionListener() { //�̸����� �˻�
			@Override //btnŬ���� ���ϴ� ������ ��ȸ�ϵ���
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();

				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from DB2022_searchGYM WHERE �̸� like ?";
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("�ｺ�������� �ҷ����µ� �����߽��ϴ�."));
						btnGroup.add(jpErr);
					}
					else {
						while(rset.next()) {
							String gym = rset.getString(1);
							String location = rset.getString(2);
							String price1 = rset.getString(3);
							String price10 = rset.getString(4);
							String price20 = rset.getString(5);
							String promotion = rset.getString(6);
							
							String[] data = {gym,location,price1,price10,price20,promotion};
							
							tableModel.addRow(data);
						}
						jt.setModel(tableModel);					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//Btn click �̺�Ʈ
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
	
		recommendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from DB2022_searchGYM WHERE ���� IN (SELECT ���� FROM db2022_ȸ�� USE INDEX (ȸ����ȣ�ε���) WHERE ȸ����ȣ=?)";
				try {
					//�Ҽ��ｺ�� Ʈ���̳� �����ֱ�
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("��õ �ｺ���� ã�� ���߽��ϴ�."));
						btnGroup.add(jpErr);
					}
					else {
						while(rset.next()) {
							String gym = rset.getString(1);
							String location = rset.getString(2);
							String price1 = rset.getString(3);
							String price10 = rset.getString(4);
							String price20 = rset.getString(5);
							String promotion = rset.getString(6);
							
							String[] data = {gym,location,price1,price10,price20,promotion};
							
							tableModel.addRow(data);
						}
							jt.setModel(tableModel);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		
		enrollBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int row = jt.getSelectedRow();
				String GYMname = (String) jt.getValueAt(row, 0);
				
				try { //��������Ƚ���� 0���� Ȯ��
					int check[] = M_totalLeft.M_totalLeft(conn, ID);
					if(check[1]==0) { //��������Ƚ�� == 0
						
						//������ �ｺ�� �̸����� ��ȣ,��üȸ���� ã��
						str = "SELECT �ｺ���ȣ,��üȸ���� FROM DB2022_�ｺ�� WHERE �̸�=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, GYMname);
						rset = pstmt.executeQuery();
						
						String GYMid = null; //������ �ｺ�� ��ȣ
						int GYMnumM=0; //������ �ｺ�� ��üȸ����
						
						rset.next();
						GYMid = rset.getString(1); 
						GYMnumM = rset.getInt(2);
						
						//���� ��ϵ� �ｺ�� Ȯ�� �� ������ ȸ�� ������ ����
						str = "SELECT G.�ｺ���ȣ, G.��üȸ���� FROM db2022_ȸ�� as M, db2022_�ｺ�� as G WHERE M.�Ҽ��ｺ��=G.�ｺ���ȣ and M.ȸ����ȣ = ?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, ID);
						rset = pstmt.executeQuery();
						
						try { //update
							conn.setAutoCommit(false); //transaction ����

							if(rset.isBeforeFirst()) { //�Ҽӵ� �ｺ���� ������ ���� �ｺ�� ��ü ȸ���� -1
								//�Ҽӵ� �ｺ�� ����
								String GYMidNow = null;
								int GYMnumMNow=0;
								
								rset.next();
								GYMidNow = rset.getString(1); //�Ҽ� �ｺ�� ��ȣ
								GYMnumMNow = rset.getInt(2); //�Ҽ� �ｺ�� ��üȸ����
	
								//���� �ｺ�� ��ü ȸ���� -1
								str = "UPDATE DB2022_�ｺ�� SET ��üȸ����=? WHERE �ｺ���ȣ=?";
								pstmt = conn.prepareStatement(str);
								pstmt.setInt(1, GYMnumMNow-1);
								pstmt.setString(2, GYMidNow);
								pstmt.executeUpdate();
							} 
							
							//�Ҽӵ� �ｺ���� ������ �ٷ� ���
							//�ｺ�� ����ϱ� : ȸ�� �Ҽ��ｺ�� update
							str = "UPDATE DB2022_ȸ�� SET �Ҽ��ｺ��=? WHERE ȸ����ȣ=?";
							pstmt = conn.prepareStatement(str);
							pstmt.setString(1, GYMid);
							pstmt.setString(2, ID);
							pstmt.executeUpdate();
							
							//�ｺ�� ����ϱ� : �ｺ�� ��ü ȸ�� �� +1
							str = "UPDATE DB2022_�ｺ�� SET ��üȸ����=? WHERE �ｺ���ȣ=?";
							pstmt = conn.prepareStatement(str);
							pstmt.setInt(1, GYMnumM+1);
							pstmt.setString(2, GYMid);
							pstmt.executeUpdate();
							
							infoText.setText(GYMname+"�� ȸ������ ��ϵǾ����ϴ�.");
							infoText.setForeground(new Color(5,0,153));
							btnGroup.revalidate();
							btnGroup.repaint();	
							
							conn.commit();
							conn.setAutoCommit(true); //transaction ����
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							infoText.setText("�ｺ�� ���/���濡 �����߽��ϴ�. �ٽ� �õ����ּ���.");
							infoText.setForeground(new Color(153,0,5));
							btnGroup.revalidate();
							btnGroup.repaint();
							e2.printStackTrace();

							System.out.println("Roll Back ����");
							
							try {
								if (conn != null)
								conn.rollback(); // ���� ������� �ʾ��� �� rollback();
							} catch (SQLException se2) {
								se2.printStackTrace();
							}
						}
						
					} else {
						//textfield����
						infoText.setText("���� ����Ƚ���� ���Ƽ� �ｺ���� ������ �� �����ϴ�.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	
	}	
}