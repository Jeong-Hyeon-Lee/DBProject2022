package DB2022Team03.Member_manageClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB2022Team03.MemberInfo.M_MainScreen;


public class M_seeFutureClasses extends JFrame {
	
	/* Member Variables */
	// Swing Class Objects
	public static JPanel panel_main, panel_title, panel_table, panel_bottom, panel_msg, panel_buttons;
	public static JLabel label_title, label_msg;
	
	public static JScrollPane scrollpane;
	public static JTable table;
	public static DefaultTableModel tModel;
	static String attributes[]= {"�����ð�","����������Ȳ"};
	
	public static JButton button_undo;
		
    // SQL Queries
	public static PreparedStatement pStmt1 = null;
	public static PreparedStatement pStmt2 = null;
	public static PreparedStatement pStmt3 = null;
	public static ResultSet rs1=null;
	public static String query1 = null;  // (1) Show classes that can be cancelled.
	public static String query2 = null;  // (2) Cancel classes classified as '����Ȯ����'
	public static String query3 = null;  // (3) Cancel classes classified as '����Ϸ�'.

	
	/* Constructor */
	public M_seeFutureClasses(Connection conn, String ID) throws SQLException {
		
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // End program if the window is closed.
		
		/* Main Panel */
		panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout());
		
		/* Title */
		panel_title = new JPanel();
		panel_title.setLayout(new FlowLayout());
		label_title = new JLabel("����� ���� ��ȸ");
		label_title.setForeground(new Color(5,0,153));
		label_title.setFont(new Font("���� ���", Font.BOLD, 25));
		panel_title.add(label_title);
		
		/* Message */
		panel_msg = new JPanel();
		panel_msg.setLayout(new FlowLayout());
		label_msg = new JLabel("���� ���� ���̰ų� ���� �Ϸ�� ���� ����Դϴ�.");
		panel_msg.add(label_msg);
		
		/* Table of Classes */
		// Swing Objects
		panel_table = new JPanel();
		panel_table.setLayout(new FlowLayout());
		//panel_table.setBounds(100, 200, 500, 600);
		tModel = new DefaultTableModel(attributes,0);
		table = new JTable(tModel);
        table.getColumnModel().getColumn(0).setMaxWidth(350);  // table column size
        table.getColumnModel().getColumn(1).setMaxWidth(150);
        table.getTableHeader().setReorderingAllowed(false); // Fix the location of columns
        table.getTableHeader().setResizingAllowed(false); // Fix the size of columns
        
		scrollpane=new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(500,200));
		scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
		panel_table.add(scrollpane);
		
		// SQL Query
		try {
			query1 = "SELECT �����ð�, ����������Ȳ " + "FROM DB2022_���� " + "USE INDEX(ȸ����ȣ�ε���) " + "WHERE ȸ����ȣ = ? AND ����������Ȳ IN ('����Ȯ����', '����Ϸ�')";
			pStmt1 = conn.prepareStatement(query1);
			pStmt1.setString(1, ID);  // 'ȸ����ȣ'
			rs1 = pStmt1.executeQuery();
			
			if(!rs1.isBeforeFirst()) {
				label_msg.setText("���� ����� ������ �����ϴ�.");
			}
			else {
				while(rs1.next()) {
					//LocalDateTime classDatetime = rs1.getTimestamp(1).toLocalDateTime();
					String classDatetime = rs1.getTimestamp(1).toString();
					classDatetime = classDatetime.substring(0, 19);
					System.out.println(classDatetime);
					String classState = rs1.getString(2);
					
					String[] column = {classDatetime, classState};
					tModel.addRow(column);  // Add the column into the table.
					//System.out.println("�����ð�: " + classDatetime + " ����������Ȳ: " + classState);
				}
				table.setModel(tModel);
			}
		}catch(SQLException sqle1) {
			System.out.println("SQLException_1: " + sqle1);
			//sqle1.printStackTrace();
		}

		/* Buttons */
		panel_buttons = new JPanel();
		panel_buttons.setLayout(new FlowLayout());
		
		button_undo = new JButton("�ڷΰ���");
		panel_buttons.add(button_undo);
		
		/* Center Panel that includes panel_msg and panel_buttons */
		panel_bottom = new JPanel();
		panel_bottom.setLayout(new GridLayout(2, 1, 0, 20));
		panel_bottom.add(panel_msg);
		panel_bottom.add(panel_buttons);

		/* Put all the sub-panels to the main-panel */
		panel_main.add(panel_title, BorderLayout.NORTH);
		panel_main.add(panel_table, BorderLayout.CENTER);
		panel_main.add(panel_bottom, BorderLayout.SOUTH);

		/* Window Settings */
		add(panel_main);
		setBounds(200,200,700,400);
		//setResizable(false); // Fix the window size.
		setVisible(true);
		
		
		
		/* ******************************
		 * Action Listeners
		 ********************************/
		
		/* Button Click */
		// (1) �ڷΰ���: Go back to the main screen
		button_undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new M_manageClass(conn,ID);  
				dispose(); // End the current JFrame.
			}
		});
		
	}
	
}