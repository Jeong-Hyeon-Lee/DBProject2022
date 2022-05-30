package DB2022TEAM03.GEUNJU;

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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class M_recommendGYM extends JFrame {
	public M_recommendGYM(Connection conn, String ID) throws SQLException {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("�ｺ�� ��õ");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//Table 
		String columnNames[] = {"�ｺ��","����","1ȸ����","10ȸ����","20ȸ����","��Ÿ���θ��"}; //headers
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		JTable jt = new JTable(tableModel);
		
		//query for table
		String str = "select �̸�,����,1ȸ����,10ȸ����,20ȸ����,��Ÿ���θ�Ǽ��� from db2022_�ｺ�� natural join db2022_���� WHERE ���� IN (SELECT ���� FROM db2022_ȸ�� WHERE ȸ����ȣ=?)";
		PreparedStatement pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		ResultSet rset = pstmt.executeQuery();
		
		//btn
		JPanel btnGroup = new JPanel();
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
				String price1 = rset.getString(3);
				String price10 = rset.getString(4);
				String price20 = rset.getString(5);
				String promotion = rset.getString(6);
				
				String[] data = {gym,location,price1,price10,price20,promotion};
				
				tableModel.addRow(data);
			}
			jt = new JTable(tableModel);
			
			//��ũ��&column���� ���� JScrollPane ����
			JScrollPane scrollpane=new JScrollPane(jt);
			scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
			btnGroup.add(scrollpane);

			//jt.setPreferredScrollableViewportSize(new Dimension(500, 100));
            //jt.setFillsViewportHeight(true);
		}
		
		//btn
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,700,250);
		
		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
		
		//Btn click �̺�Ʈ
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_GScreen(conn,ID);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
		
		//talbe Ŭ�� ��, �ｺ�� ����ϱ� �ｺ���� ����Ͻðڽ��ϱ�? -> ��ϿϷ� �� �ٷ� �Ѿ
	
	}	
}
