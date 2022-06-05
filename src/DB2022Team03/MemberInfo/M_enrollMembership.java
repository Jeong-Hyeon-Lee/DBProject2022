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

public class M_enrollMembership extends JFrame {
	public static JTable jt;
	public static JTable jt2;
	public static DefaultTableModel tableModel;
	public static DefaultTableModel tableModel2;
	static PreparedStatement pstmt = null;
	static ResultSet rset = null;
	static String str = null;
	static JLabel infoText;
	static JPanel btnGroup;
	static String columnNames[]= {"�̸�","��ü����ȸ��","�Ϸ����ȸ��","��������ȸ��","����ȸ����"};
	static String columnNames2[] ={"1ȸ��","10ȸ��","20ȸ��","��Ÿ���θ��"};
	static Statement stmt; 
	
	public M_enrollMembership(Connection conn, String ID) throws SQLException {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("ȸ���� ��� �� ����");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//search - �ｺ�� �������� ã��
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		
		JPanel i1 = new JPanel();
		JLabel inputDesc = new JLabel("���ϴ� ȸ������ ���ڷ� �Է����ּ���.: ");
		i1.add(inputDesc);
		input.add(i1);
		
		JPanel i2 = new JPanel();
		JTextField inputText = new JTextField(25);
		i2.add(inputText);
		input.add(i2);
		
		JPanel i3 = new JPanel();
		JButton enrollBtn = new JButton("���"); 
		i3.add(enrollBtn);
		input.add(i3);

		//for err & info & undo 
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(3,1));
		
		//Table1 : ��üȽ��, ����Ƚ��, ����ȸ����
		JPanel table1 = new JPanel();
		table1.setLayout(new GridLayout(1,1));
		tableModel = new DefaultTableModel(columnNames,0);
		jt = new JTable(tableModel);
		
		//query for table
		str = "select �̸�, ��üȽ��, ����Ƚ��, ����ȸ���� from db2022_ȸ�� use index (ȸ����ȣ�ε���) where ȸ����ȣ = ?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		rset.next();
		String name = rset.getString(1);
		String total = rset.getString(2);
		String left = rset.getString(3);
		String membership = rset.getString(4);
		String finish = String.valueOf(Integer.parseInt(total)-Integer.parseInt(left));
		String[] data1 = {name,total,finish,left,membership};
				
		tableModel.addRow(data1);
		jt = new JTable(tableModel);
		
		JScrollPane scrollpane=new JScrollPane(jt);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
	
		table1.add(scrollpane);
			
		//Table2 : �ｺ�� ����ǥ
		JPanel table2 = new JPanel();
		table2.setLayout(new GridLayout(1,1));
		tableModel2 = new DefaultTableModel(columnNames2,0);
		jt2 = new JTable(tableModel2);
		
		//query for table 
		str = "select 1ȸ����, 10ȸ����, 20ȸ����, ��Ÿ���θ�Ǽ��� from db2022_���� where �ｺ���ȣ in (select �Ҽ��ｺ�� from db2022_ȸ�� use index (ȸ����ȣ�ε���) where ȸ����ȣ=?) ";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		if(!rset.isBeforeFirst()) {
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			JLabel errText = new JLabel("�ｺ�� ���������� �ҷ��� �� �����ϴ�. ���� �ｺ���� ������ּ���.");
			errText.setForeground(new Color(153,0,5));
			jpErr.add(errText);
			btnGroup.add(jpErr);
		} else {
			rset.next();
			int price1 = rset.getInt(1);
			int price10 = rset.getInt(2);
			int price20 = rset.getInt(3);
			String promotion = rset.getString(4);
			String[] data2 = {String.valueOf(price1), String.valueOf(price10),String.valueOf(price20),promotion};
			
			tableModel2.addRow(data2);
			jt2.setModel(tableModel2);

			JScrollPane scrollpane2=new JScrollPane(jt2);
			scrollpane2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
			table2.add(scrollpane2);
		}
		
		//�ȳ�����
		JPanel info = new JPanel();
		infoText = new JLabel("ȸ���� ��� �� ������ ��������ȸ���� 0�� ���� �����մϴ�.");
		info.add(infoText);
		btnGroup.add(info);
		
		//undo
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		//center�� �׸���� 3��
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(3,1));
		center.add(input);
		center.add(table1);
		center.add(table2);
		
		add(M_main,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
		add(btnGroup,BorderLayout.SOUTH);
		setBounds(200,200,800,400);
		
		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
				
		//Btn click �̺�Ʈ
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); // ������ frame�� �����Ű�� �޼���.
			}
		});
		
		 enrollBtn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int input = Integer.parseInt(inputText.getText());
				
				if(input!=1&&input!=10&&input!=20) { //�� �� �ƴ� ��.. 
					infoText.setText("1ȸ��, 10ȸ��, 20ȸ�� �� �ϳ��� �������ּ���.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;
				}
				
				//�Ҽ��ｺ��, ���Ʈ���̳� ��ȣã�� -> null�̶�� ���X�ǵ��� ����
				String GYMid_M = null;
				String Tid_M = null;
				try {
					str = "SELECT �Ҽ��ｺ��, ���Ʈ���̳� FROM db2022_ȸ�� USE INDEX (ȸ����ȣ�ε���) WHERE ȸ����ȣ=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					rset.next();
					GYMid_M = rset.getString(1);
					Tid_M = rset.getString(2);
			
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if(GYMid_M==null) {
					infoText.setText("�ｺ���� ���� ������ּ���.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;
				} else if (Tid_M==null) {
					infoText.setText("Ʈ���̳ʸ� ���� ������ּ���.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;					
				}
				
				//Ʈ���̳� �ｺ���̶� �Ҽ��ｺ���� �ٸ��� �� �ٲ�
				try{
					str = "SELECT �ｺ���ȣ FROM DB2022_Ʈ���̳� WHERE �����ȣ=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, Tid_M);
					rset = pstmt.executeQuery();
					
					rset.next();
					String Tgym = rset.getString(1);
					
					if(!Tgym.equals(GYMid_M)) {
						infoText.setText("�Ҽ��ｺ��� Ʈ���̳��� �ｺ���� ��ġ���� �ʽ��ϴ�. Ʈ���̳ʸ� �ٽ� ������ּ���.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
						return;	
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
	
				
				try { //��������Ƚ���� 0���� Ȯ��
					int check[] = M_totalLeft.M_totalLeft(conn, ID);
					if(check[1]==0) { //��������Ƚ�� == 0
						
						try{ //update
						
						conn.setAutoCommit(false); //transaction ����
	
						str = "UPDATE DB2022_ȸ�� SET ����ȸ����=?,��üȽ��=?,����Ƚ��=? WHERE ȸ����ȣ=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, input+"ȸ��");
						pstmt.setInt(2, input+check[0]);
						pstmt.setInt(3, input);
						pstmt.setString(4, ID);
						pstmt.executeUpdate();
						
						infoText.setText("ȸ������ "+input+"ȸ������ �����߽��ϴ�.");
						infoText.setForeground(new Color(5,0,153));
						btnGroup.revalidate();
						btnGroup.repaint();
						
						//table
						tableModel.setNumRows(0);
						//query for table
						str = "select �̸�, ��üȽ��, ����Ƚ��, ����ȸ���� from db2022_ȸ�� use index(ȸ����ȣ�ε���) where ȸ����ȣ = ?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, ID);
						rset = pstmt.executeQuery();
				
						rset.next();
						String name = rset.getString(1);
						String total = rset.getString(2);
						String left = rset.getString(3);
						String membership = rset.getString(4);
						String finish = String.valueOf(Integer.parseInt(total)-Integer.parseInt(left));
						String[] data1 = {name,total,finish,left,membership};
								
						tableModel.addRow(data1);
						jt.setModel(tableModel);
						
						infoText.setText("ȸ������ ����߽��ϴ�.");
						infoText.setForeground(new Color(5,0,153));
						btnGroup.revalidate();
						btnGroup.repaint();	
						
						conn.commit();
						conn.setAutoCommit(true); //transaction ����
						
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							infoText.setText("ȸ���� ���/���濡 �����߽��ϴ�. �ٽ� �õ����ּ���.");
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
						//�ȳ����� ���������� ǥ��
						infoText.setText("ȸ���� ��� �� ������ ��������ȸ���� 0�� ���� �����մϴ�.");
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
