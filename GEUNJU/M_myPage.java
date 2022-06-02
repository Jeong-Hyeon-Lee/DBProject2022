package DB2022Team03.GEUNJU;

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

public class M_myPage extends JFrame {
	public M_myPage(Connection conn, String ID) throws SQLException {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("ȸ������Ȯ��");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//Table
		String columnNames[] = {"�̸�","�ｺ��","����","��ü����ȸ��","��������ȸ��","Ʈ���̳�","����ȸ����"}; //headers
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		JTable jt = new JTable(tableModel);
		
		//query for table
		String str = "SELECT M.�̸�,G.�̸� as �ｺ��, M.����, M.��üȽ��, M.����Ƚ��,T.�̸� as Ʈ���̳�, M.����ȸ���� FROM db2022_ȸ�� as M, db2022_Ʈ���̳� as T, db2022_�ｺ�� as G WHERE M.���Ʈ���̳�=T.�����ȣ and M.�Ҽ��ｺ��=G.�ｺ���ȣ and M.ȸ����ȣ = ?";
		PreparedStatement pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		ResultSet rset = pstmt.executeQuery();
		
		//btn
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//table data -> �ｺ��,Ʈ���̳� null�̸� ȸ������ �ҷ����µ� ������ (query�� null���� ���� ����)
		
		if(!rset.isBeforeFirst()) { //Ŀ���� ù��° �� �տ� �ִ��� Ȯ��. false�� ����
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			jpErr.add(new JLabel("�ｺ��� Ʈ���̳� ��� �� ��밡���� �޴��Դϴ�.")); //ȸ�������� �ҷ����µ� �����߽��ϴ�.
			btnGroup.add(jpErr);
		}
		else {
			rset.next();
			
			String name = rset.getString(1);
			String gym = rset.getString(2);
			if(rset.wasNull()) gym="�̵��";
			String location = rset.getString(3);
			String total = rset.getString(4);
			String left = rset.getString(5);
			String trainer = rset.getString(6);
			if(rset.wasNull()) trainer="�̵��";
			String membership = rset.getString(7);
			
			String[] data = {name,gym,location,total,left,trainer,membership};
			
			tableModel.addRow(data);
			
			jt = new JTable(tableModel);
			
			//��ũ��&column���� ���� JScrollPane ����
			JScrollPane scrollpane=new JScrollPane(jt);
			scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
			btnGroup.add(scrollpane);
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
		
		add(M_main,BorderLayout.NORTH);;
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,600,200);
		
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
	
	}	
}
