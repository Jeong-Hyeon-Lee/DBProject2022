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

public class M_searchTrainer extends JFrame {
	public static JTable jt;
	public static DefaultTableModel tableModel;
	static PreparedStatement pstmt = null;
	static ResultSet rset = null;
	static String str = null;
	static JLabel infoText;
	static JPanel btnGroup;
	static String columnNames[]= {"�ｺ��","Ʈ���̳�","����","���ȸ����"}; //columnName
	static Statement stmt; 
	
	public M_searchTrainer(Connection conn, String ID) throws SQLException {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("Ʈ���̳� ã��");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//search
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
		JButton searchTBtn = new JButton("�ｺ�� �̸����� �˻�");
		i3.add(searchTBtn);
		input.add(i3);
		
		JPanel i4 = new JPanel();
		JButton searchTBtn2 = new JButton("Ʈ���̳� �̸����� �˻�"); 
		i4.add(searchTBtn2);
		input.add(i4);

		//Table
		JPanel table = new JPanel();
		table.setLayout(new GridLayout(1,1));
		tableModel = new DefaultTableModel(columnNames,0);
		jt = new JTable(tableModel);
		
		//query for table
		stmt = conn.createStatement();
		str = "select * from DB2022_searchTrainer";
		rset = stmt.executeQuery(str);
		
		//for info & btn
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//�ȳ�����
		JPanel info = new JPanel();
		infoText = new JLabel("Ʈ���̳� ����� ���Ͻø� ���ϴ� Ʈ���̳ʸ� Ŭ���� ��, �v���ϱ� ��ư�� �����ּ���. (�Ҽ��ｺ���� Ʈ���̳ʸ� ��� ����)");
		info.add(infoText);
		btnGroup.add(info);
		
		//table data
		try {
			if(!rset.isBeforeFirst()) {
				infoText.setText("��ϵ� Ʈ���̳� ������ �����ϴ�.");
				infoText.setForeground(new Color(153,0,5));
				btnGroup.revalidate();
				btnGroup.repaint();
			}
			else {
				while(rset.next()) {
					String Gname = rset.getString(1);
					String Tname = rset.getString(2);
					String location = rset.getString(3);
					String member = rset.getString(4);
					
					if(!Tname.equals("Ż��")) {
						String[] data = {Gname,Tname,location,member};
						tableModel.addRow(data);
					} 	
				}
				jt = new JTable(tableModel);
				
				//��ũ��&column���� ���� JScrollPane ����
				JScrollPane scrollpane=new JScrollPane(jt);
				scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
			
				table.add(scrollpane);
			}
		} catch (SQLException e) {
			infoText.setText("Ʈ���̳� ������ �ҷ����µ� �����߽��ϴ�.");
			infoText.setForeground(new Color(153,0,5));
			btnGroup.revalidate();
			btnGroup.repaint();
		}
		
		
		//undo
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		//show
		JPanel showT = new JPanel();
		JButton showTBtn = new JButton("�Ҽ��ｺ�� Ʈ���̳� Ȯ��");
		showT.add(showTBtn);
		jp0.add(showT);
		
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
		setBounds(200,200,800,400);
		
		setResizable(false); 

		setVisible(true);
		
		//Btn event
		searchTBtn.addActionListener(new ActionListener() { //�ｺ�� �̸����� �˻�
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();
				
				//Table
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from DB2022_searchTrainer where �ｺ���̸� like ?"; 
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("��ϵ� �ｺ�� �� �ش� �̸��� ���� �ｺ���� �����ϴ�.");
						infoText.setForeground(new Color(135,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							if(!Tname.equals("Ż��")) {
								String[] data = {Gname,Tname,location,member};
								tableModel.addRow(data);
							}
						}
						jt.setModel(tableModel);					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("Ʈ���̳ʸ� �ҷ����µ� �����߽��ϴ�.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					e1.printStackTrace();
				}
			}
		});

		searchTBtn2.addActionListener(new ActionListener() { //Ʈ���̳� �̸����� �˻�
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();
			
				//Table
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from DB2022_searchTrainer where Ʈ���̳��̸� like ?"; 
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("��ϵ� Ʈ���̳� �� �ش� �̸��� ���� Ʈ���̳ʰ� �����ϴ�.");
						infoText.setForeground(new Color(135,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							if(!Tname.equals("Ż��")) {
								String[] data = {Gname,Tname,location,member};
								tableModel.addRow(data);
							}
						}
						jt.setModel(tableModel);					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("Ʈ���̳ʸ� �ҷ����µ� �����߽��ϴ�.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					e1.printStackTrace();
				}
			}
		});

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); 

			}
		});
		
		showTBtn.addActionListener(new ActionListener() { //�Ҽ��ｺ�� Ʈ���̳� Ȯ��
			@Override 
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from DB2022_searchTrainer where �ｺ���ȣ IN (SELECT �Ҽ��ｺ�� FROM db2022_ȸ�� USE INDEX (ȸ����ȣ�ε���) WHERE ȸ����ȣ=?)";
				try {
					//�Ҽ��ｺ�� Ʈ���̳� �����ֱ�
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();

					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("�ｺ�忡 �Ҽӵ� Ʈ���̳ʰ� �����ϴ�.");
						infoText.setForeground(new Color(135,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							if(!Tname.equals("Ż��")) {
								String[] data = {Gname,Tname,location,member};
								tableModel.addRow(data);
							}
						}
						jt.setModel(tableModel);					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("Ʈ���̳ʸ� �ҷ����µ� �����߽��ϴ�.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					e1.printStackTrace();
				}
			}
		});
		
		enrollBtn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) { 
				int row = jt.getSelectedRow();
				
				String Tname = (String) jt.getValueAt(row, 1); //������ Ʈ���̳� �̸�
				
				String Gname = (String) jt.getValueAt(row, 0); //������ Ʈ���̳��� �ｺ�� �̸�
				
				try {
					//���� �Ҽ��ｺ���� �ִ��� Ȯ��
					str = "select G.�̸� from db2022_ȸ�� as M, db2022_�ｺ�� as G where M.�Ҽ��ｺ��=G.�ｺ���ȣ and M.ȸ����ȣ=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1,ID);
					rset=pstmt.executeQuery();	
					
					if(!rset.isBeforeFirst()) { //�ｺ���� ���ٸ� 
						infoText.setText("�켱 �ｺ���� ������ּ���.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
						return;
					} else { //�ｺ���� �ִٸ�
						rset.next();
						String nowGymName = rset.getString(1); //�Ҽӵ� �ｺ�� �̸�
												
						//���� Ƚ���� 0���� Ȯ��
						int check[] = M_totalLeft.M_totalLeft(conn, ID);
						
						if(check[1]==0) { //���� Ƚ���� 0�̶��
							//�Ҽ��ｺ��� ������ Ʈ���̳� �Ҽ� �ｺ���� ������ Ȯ�� (�̸����� Ȯ��)
							if(nowGymName.equals(Gname)) { //���� ���ٸ� ��� ����!
								str = "select ���Ʈ���̳� from db2022_ȸ�� use index(ȸ����ȣ�ε���) where ȸ����ȣ=?";
								pstmt = conn.prepareStatement(str);
								pstmt.setString(1, ID);
								rset = pstmt.executeQuery();	
								
								//use just rset.next() because any successful query that has data, once executed, will produce a ResultSet with isBeforeFirst() returning true.
								rset.next();
								String nowT = rset.getString(1);
								
								try {
									//update
									conn.setAutoCommit(false); //transaction ����

									if(nowT!=null) { //���� ���Ʈ���̳ʰ� �ִٸ� ���� ���Ʈ���̳� ���ȸ���� -1									
										//���� ���Ʈ���̳� ��ȣ�� ���ȸ����ã��
										str = "SELECT ���ȸ���� FROM DB2022_Ʈ���̳� use index(�����ȣ) WHERE �����ȣ=?";
										pstmt = conn.prepareStatement(str);
										pstmt.setString(1, nowT);
										rset = pstmt.executeQuery();
										int nowTnum = 0; //���� ���Ʈ���̳� ���ȸ����
										rset.next();
										nowTnum = rset.getInt(1);
										
										//������� : ���� ���Ʈ���̳� ���ȸ���� -1
										str = "UPDATE DB2022_Ʈ���̳� SET ���ȸ����=? WHERE �����ȣ=?";
										pstmt = conn.prepareStatement(str);
										pstmt.setInt(1, nowTnum-1);
										pstmt.setString(2, nowT);
										pstmt.executeUpdate();								
									} 
									//���� ���Ʈ���̳ʰ� ���ٸ� �ٷ� enroll								
									//������ Ʈ���̳� �̸�(Tname) ���� Ʈ���̳� ��ȣ ã��
									str = "SELECT �����ȣ, ���ȸ���� FROM DB2022_Ʈ���̳� WHERE �̸�=?";
									pstmt = conn.prepareStatement(str);
					                pstmt.setString(1, Tname);
					                rset = pstmt.executeQuery();
					                rset.next();
					                String selectT = rset.getString(1); //������ Ʈ���̳� ��ȣ
					                int selectTnum = rset.getInt(2); //������ Ʈ���̳��� ���ȸ�� �� 
									
									//��� : ȸ�� ���Ʈ���̳� ���
									str = "UPDATE DB2022_ȸ�� SET ���Ʈ���̳�=? WHERE ȸ����ȣ=?";
									pstmt = conn.prepareStatement(str);
									pstmt.setString(1, selectT);
									pstmt.setString(2, ID);
									pstmt.executeUpdate();
									
									//��� : Ʈ���̳� ���ȸ���� +1
									str = "UPDATE DB2022_Ʈ���̳� SET ���ȸ����=? WHERE �����ȣ=?";
									pstmt = conn.prepareStatement(str);
									pstmt.setInt(1, selectTnum+1);
									pstmt.setString(2, selectT);
									pstmt.executeUpdate();
									
									infoText.setText("���Ʈ���̳�("+Tname+")�� ��ϵǾ����ϴ�.");
									infoText.setForeground(new Color(5,0,135));
									btnGroup.revalidate();
									btnGroup.repaint();
									
									conn.commit();
									conn.setAutoCommit(true); //transaction ����
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									infoText.setText("Ʈ���̳� ���/���濡 �����߽��ϴ�. �ٽ� �õ����ּ���.");
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
							} else { //�ٸ��ٸ�
								infoText.setText("�Ҽ��ｺ���� Ʈ���̳ʸ� ��ϰ����մϴ�.(�Ҽ��ｺ��:"+nowGymName+")");
								infoText.setForeground(new Color(153,0,5));
								btnGroup.revalidate();
								btnGroup.repaint();
							}
						} else { //���� Ƚ���� 0�� �ƴ϶�� 
							//textfield����
							infoText.setText("���� ����Ƚ���� ���Ƽ� Ʈ���̳ʸ� ������ �� �����ϴ�.");
							infoText.setForeground(new Color(153,0,5));
							btnGroup.revalidate();
							btnGroup.repaint();
							return;
						}
					}	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	
	}	
}