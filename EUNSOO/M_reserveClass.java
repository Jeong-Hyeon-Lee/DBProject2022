package DB2022TEAM03.EUNSOO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DB2022TEAM03.GEUNJU.M_MainScreen;


public class M_reserveClass extends JFrame {
	
	/* Member Variables */
	// Swing Class Objects
	public static JPanel panel_main, panel_title, panel_table, panel_comboBoxes, panel_center, panel_bottom, panel_msg, panel_buttons;
	public static JLabel label_title, label_msg;
	public static JLabel label_year, label_month, label_day, label_hour, label_min;
	
	public static JScrollPane scrollpane;
	public static JTable table;
	public static DefaultTableModel tModel;
	public static String attributes[]= {"수업시간","수업진행현황"};
	
	public static JComboBox<String> comboBox_year, comboBox_month, comboBox_day, comboBox_hour, comboBox_min;
	public static JButton button_undo, button_reserve;
		
    // SQL Queries
	public static PreparedStatement pStmt1 = null;
	public static PreparedStatement pStmt2 = null;
	public static PreparedStatement pStmt3 = null;
	public static PreparedStatement pStmt4 = null;
	public static ResultSet rs1=null;
	public static ResultSet rs2=null;
	public static ResultSet rs3=null;
	public static String query1 = null;  // (1) Show future classes.
	public static String query2 = null;  // (2) Search current dateTime.
	public static String query3 = null;  // (3) Search the trainer ID of the current member.
	public static String query4 = null;  // (3) Reserve the selected class.


	
	/* Constructor */
	public M_reserveClass(Connection conn, String ID) throws SQLException {
		
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // End program if the window is closed.
		
		/* Main Panel */
		panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout());
		
		/* Title */
		panel_title = new JPanel();
		panel_title.setLayout(new FlowLayout());
		label_title = new JLabel("수업 예약");
		label_title.setForeground(new Color(5,0,153));
		label_title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		panel_title.add(label_title);
		
		/* Message */
		panel_msg = new JPanel();
		panel_msg.setLayout(new FlowLayout());
		label_msg = new JLabel("수업 취소를 원하시면 취소할 수업을 클릭한 뒤, 취소하기 버튼을 눌러주세요.");
		panel_msg.add(label_msg);
		
		
		/* DateTime Combo Boxes */
		panel_comboBoxes = new JPanel();
		panel_comboBoxes.setLayout(new FlowLayout());
		// (1) Year
		comboBox_year = new JComboBox<String>();
		comboBox_year.addItem("2022");
		comboBox_year.addItem("2023");
		// (2) Month
		comboBox_month = new JComboBox<String>();
		for(int i=1; i<=9; i++) {
			comboBox_month.addItem("0" + Integer.toString(i));
		}
		for(int i=10; i<=12; i++) {
			comboBox_month.addItem(Integer.toString(i));
		}
		// (3) Day
		comboBox_day = new JComboBox<String>();
		for(int i=1; i<=9; i++) {
			comboBox_day.addItem("0" + Integer.toString(i));
		}
		for(int i=10; i<=31; i++) {
			comboBox_day.addItem(Integer.toString(i));
		}
		// (4) Hour
		comboBox_hour = new JComboBox<String>();
		for(int i=6; i<=9; i++) {
			comboBox_hour.addItem("0" + Integer.toString(i));  // 06~09
		}
		for(int i=10; i<=23; i++) {
			comboBox_hour.addItem(Integer.toString(i));  // 10~23
		}
		// (5) Minute
		comboBox_min = new JComboBox<String>();
		comboBox_min.addItem("00");
		comboBox_min.addItem("30");
		
		// Labels for comboBoxes
		label_year = new JLabel("년");
		label_month = new JLabel("월");
		label_day = new JLabel("일");
		label_hour = new JLabel("시");
		label_min = new JLabel("분");
		
		panel_comboBoxes.add(comboBox_year);
		panel_comboBoxes.add(label_year);

		panel_comboBoxes.add(comboBox_month);
		panel_comboBoxes.add(label_month);

		panel_comboBoxes.add(comboBox_day);
		panel_comboBoxes.add(label_day);

		panel_comboBoxes.add(comboBox_hour);
		panel_comboBoxes.add(label_hour);

		panel_comboBoxes.add(comboBox_min);
		panel_comboBoxes.add(label_min);
			
		
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
			query1 = "SELECT 수업시간, 수업진행현황 " + "FROM DB2022_수업 " + "WHERE 회원번호 = ? AND 수업진행현황 IN ('예약확인중', '예약완료')";
			pStmt1 = conn.prepareStatement(query1);
			pStmt1.setString(1, ID);  // '회원번호'
			rs1 = pStmt1.executeQuery();
			
			if(!rs1.isBeforeFirst()) {
				label_msg.setText("예약한 수업이 없습니다.");
			}
			else {
				while(rs1.next()) {
					//LocalDateTime classDatetime = rs1.getTimestamp(1).toLocalDateTime();
					String classDatetime = rs1.getTimestamp(1).toString();
					classDatetime = classDatetime.substring(0, 19);
					//System.out.println(classDatetime);
					String classState = rs1.getString(2);
					
					String[] column = {classDatetime, classState};
					tModel.addRow(column);  // Add the column into the table.
					//System.out.println("수업시간: " + classDatetime + " 수업진행현황: " + classState);
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
		
		button_undo = new JButton("뒤로가기");
		panel_buttons.add(button_undo);

		button_reserve = new JButton("예약하기");
		panel_buttons.add(button_reserve);		
		
		/* Center Panel */
		panel_center = new JPanel();
		panel_center.setLayout(new BorderLayout());
		panel_center.add(panel_table, BorderLayout.CENTER);
		panel_center.add(panel_comboBoxes, BorderLayout.SOUTH);
		
		
		/* Bottom Panel that includes panel_msg and panel_buttons */
		panel_bottom = new JPanel();
		panel_bottom.setLayout(new GridLayout(2, 1));
		panel_bottom.add(panel_msg);
		panel_bottom.add(panel_buttons);

		/* Put all the sub-panels to the main-panel */
		panel_main.add(panel_title, BorderLayout.NORTH);
		panel_main.add(panel_center, BorderLayout.CENTER);
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
		
		// (2) 예약하기: Cancel reservation of the selected class.
		button_reserve.addActionListener(new ActionListener() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  // Parses a String value to a DateTime value.
			
			public void actionPerformed(ActionEvent e) {				
				String year = comboBox_year.getSelectedItem().toString();
				String month = comboBox_month.getSelectedItem().toString();
				String day = comboBox_day.getSelectedItem().toString();
				String hour = comboBox_hour.getSelectedItem().toString();
				String min = comboBox_min.getSelectedItem().toString();				
				
				String dateTime = year + "-" + month + "-" + day + " " + hour + ":" + min + ":00";
				System.out.println(dateTime);
						
				LocalDateTime currentDateTime = null;
				LocalDateTime classDateTime = LocalDateTime.parse(dateTime, formatter);
				String trainerId = null;  // Trainer ID of the current member
				
				try {
					query2 = "SELECT NOW()";
					query3 = "SELECT DISTINCT 강사번호 FROM DB2022_수업 WHERE 회원번호 = ?";
					query4 = "INSERT IGNORE INTO DB2022_수업 " + "VALUES(?, ?, ?, ?)";  // Ignore insertion if duplicated.

					pStmt2 = conn.prepareStatement(query2);
					rs2 = pStmt2.executeQuery();
					if(rs2.next()) currentDateTime = rs2.getTimestamp(1).toLocalDateTime();

					pStmt3 = conn.prepareStatement(query3);
					pStmt3.setString(1, ID);
					rs3 = pStmt3.executeQuery();
					if(rs3.next()) trainerId = rs3.getString(1);
					
					if(classDateTime.isAfter(currentDateTime)) {  // Check the reservation time (only available after now).
						pStmt4 = conn.prepareStatement(query4);
						pStmt4.setString(1, ID);
						pStmt4.setString(2, trainerId);
						pStmt4.setTimestamp(3, Timestamp.valueOf(dateTime));  // '수업시간'
						pStmt4.setString(4, "예약확인중"); 
									
						// Update DB.
						int row = pStmt4.executeUpdate();
				
						// Update the JTable.
						if(row > 0) {
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
							}
							table.setModel(tModel);
							System.out.println("**정상적으로 예약 처리되었습니다.**");
						}
						else {
							JOptionPane.showMessageDialog(null, "이미 존재하는 수업입니다.");
							System.out.println("**이미 존재하는 수업입니다.**");
						}
					}
					else {  // If not reservable: 1) If class dateTime is before now, 2) If the same class is already reserved
						JOptionPane.showMessageDialog(null, "예약 가능한 시간이 아닙니다.");
						System.out.println("**예약 가능한 수업시간이 아닙니다.**");
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

