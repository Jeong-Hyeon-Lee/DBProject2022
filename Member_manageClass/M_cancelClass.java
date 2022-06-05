/*
* 수정사항:
* 1) '예약완료'인 수업을 취소하면 남은횟수+1
* 2) '예약확인중'인 수업을 취소하면 남은횟수는 변화X
*/
package DB2022Team03.Member_manageClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DB2022Team03.MemberInfo.M_MainScreen;


public class M_cancelClass extends JFrame {
		
	/* Member Variables */
	// Swing Class Objects
	public static JPanel panel_main, panel_title, panel_table, panel_bottom, panel_msg, panel_buttons;
	public static JLabel label_title, label_msg;
	
	public static JScrollPane scrollpane;
	public static JTable table;
	public static DefaultTableModel tModel;
	static String attributes[]= {"수업시간","수업진행현황"};
	
	public static JButton button_undo, button_cancel;
		
    // SQL Queries
	public static PreparedStatement pStmt1 = null;
	public static PreparedStatement pStmt2 = null;
	public static PreparedStatement pStmt3 = null;
	public static ResultSet rs1=null;
	public static String query1 = null;  // (1) Show classes that can be cancelled.
	public static String query2 = null;  // (2) Cancel classes classified as '예약확인중'
	public static String query3 = null;  // (3) Cancel classes classified as '예약완료'.

	
	/* Constructor */
	public M_cancelClass(Connection conn, String ID) throws SQLException {
		
		setTitle("헬스장 통합 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // End program if the window is closed.
		
		/* Main Panel */
		panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout());
		
		/* Title */
		panel_title = new JPanel();
		panel_title.setLayout(new FlowLayout());
		label_title = new JLabel("수업 취소");
		label_title.setForeground(new Color(5,0,153));
		label_title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		panel_title.add(label_title);
		
		/* Message */
		panel_msg = new JPanel();
		panel_msg.setLayout(new FlowLayout());
		label_msg = new JLabel("수업 취소를 원하시면 취소할 수업을 클릭한 뒤, 취소하기 버튼을 눌러주세요.");
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
			query1 = "SELECT 수업시간, 수업진행현황 " + "FROM DB2022_수업 " + "USE INDEX(회원번호인덱스) " + "WHERE 회원번호 = ? AND 수업진행현황 IN ('예약확인중', '예약완료') AND 수업시간 > DATE_ADD(NOW(), INTERVAL 5 HOUR)";
			pStmt1 = conn.prepareStatement(query1);
			pStmt1.setString(1, ID);  // '회원번호'
			rs1 = pStmt1.executeQuery();
			
			if(!rs1.isBeforeFirst()) {
				label_msg.setText("취소 가능한 수업이 없습니다.");
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
					//System.out.println("수업시간: " + classDatetime + " 수업진행현황: " + classState);
				}
				table.setModel(tModel);
				table.setRowSelectionInterval(0,0); 
			}
		}catch(SQLException sqle1) {
			System.out.println("SQLException_1: " + sqle1);
			//sqle1.printStackTrace();
		}

		/* Buttons */
		panel_buttons = new JPanel();
		panel_buttons.setLayout(new FlowLayout());
		
		button_undo = new JButton("뒤로가기");
		panel_buttons.add(button_undo);

		button_cancel = new JButton("취소하기");
		panel_buttons.add(button_cancel);		
		
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
		// (1) 뒤로가기: Go back to the main screen
		button_undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new M_manageClass(conn,ID);  
				dispose(); // End the current JFrame.
			}
		});
		
		// (2) 취소하기: Cancel reservation of the selected class.
		button_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();  // selected column(class)
				if(row==-1) {

					return;
				}
				String dateTime = (String) table.getValueAt(row, 0);  // dateTime of the selected class
				System.out.println(dateTime);
								
				try {
					query2 = "DELETE FROM DB2022_수업 " + "WHERE 회원번호 = ? AND 수업시간  = ? AND 수업진행현황 = '예약확인중' AND 수업시간 > DATE_ADD(NOW(), INTERVAL 5 HOUR)";
					query3 = "UPDATE DB2022_수업 " + "SET 수업진행현황 = '취소' " + "WHERE 회원번호 = ? AND 수업시간 = ? AND 수업진행현황 = '예약완료' AND 수업시간 > DATE_ADD(NOW(), INTERVAL 5 HOUR)";

					pStmt2 = conn.prepareStatement(query2);
					pStmt3 = conn.prepareStatement(query3);
					
					pStmt2.setString(1, ID);
					pStmt2.setTimestamp(2, Timestamp.valueOf(dateTime));  // '수업시간'
					
					pStmt3.setString(1, ID);  
					pStmt3.setTimestamp(2, Timestamp.valueOf(dateTime));  // '수업시간'
					
					/* Update DB. */
					int row2 = pStmt2.executeUpdate(); 
					int row3 = pStmt3.executeUpdate();
					
					// 예약된 수업이 '예약완료'인 경우, 남은 수업횟수 += 1 ('예약확인중'은 변화 x)
					if(row3 > 0) {
						String query = "UPDATE DB2022_회원 SET 남은횟수 = 남은횟수 + ? WHERE 회원번호 = ?";
						PreparedStatement pStmt = conn.prepareStatement(query);
						pStmt.setInt(1, 1);  // '남은횟수'
						pStmt.setString(2, ID);  // '회원번호'
						pStmt.executeUpdate();
					}
				
					// Update the JTable.
					tModel.setNumRows(0);  // Erase all the columns.
					pStmt1 = conn.prepareStatement(query1);  // Execute query1.
					pStmt1.setString(1, ID);  // '회원번호'
					rs1 = pStmt1.executeQuery();
					while(rs1.next()) {
						String classDatetime = rs1.getTimestamp(1).toString();
						classDatetime = classDatetime.substring(0, 19);
						String classState = rs1.getString(2);
						String[] column = {classDatetime.toString(), classState};
						tModel.addRow(column);  // Add the column into the table.	
						//System.out.println("수업시간: " + classDatetime + " 수업진행현황: " + classState);
						table.setModel(tModel);
						table.setRowSelectionInterval(0,0); 
					}
										
				} catch (SQLException sqle2) {
					// TODO Auto-generated catch block
					System.out.println("SQLException_2: " + sqle2);
					sqle2.printStackTrace();
				}
			}
		});			
		
	}
}
