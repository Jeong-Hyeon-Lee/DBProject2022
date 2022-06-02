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
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		//North-title
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("회원정보확인");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//Table
		String columnNames[] = {"이름","헬스장","지역","전체결제회차","남은수업회차","트레이너","현재회원권"}; //headers
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		JTable jt = new JTable(tableModel);
		
		//query for table
		String str = "SELECT M.이름,G.이름 as 헬스장, M.지역, M.전체횟수, M.남은횟수,T.이름 as 트레이너, M.현재회원권 FROM db2022_회원 as M, db2022_트레이너 as T, db2022_헬스장 as G WHERE M.담당트레이너=T.강사번호 and M.소속헬스장=G.헬스장번호 and M.회원번호 = ?";
		PreparedStatement pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		ResultSet rset = pstmt.executeQuery();
		
		//btn
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//table data -> fail when GYM, Trainer is null.
		if(!rset.isBeforeFirst()) { 
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			jpErr.add(new JLabel("헬스장과 트레이너 등록 후 사용가능한 메뉴입니다."));
			btnGroup.add(jpErr);
		}
		else {
			rset.next();
			
			String name = rset.getString(1);
			String gym = rset.getString(2);
			if(rset.wasNull()) gym="미등록";
			String location = rset.getString(3);
			String total = rset.getString(4);
			String left = rset.getString(5);
			String trainer = rset.getString(6);
			if(rset.wasNull()) trainer="미등록";
			String membership = rset.getString(7);
			
			String[] data = {name,gym,location,total,left,trainer,membership};
			
			tableModel.addRow(data);
			
			jt = new JTable(tableModel);
			
			//for scroll & columnname
			JScrollPane scrollpane=new JScrollPane(jt);
			scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
			btnGroup.add(scrollpane);
		}
		
		//btn
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);;
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,600,200);
		
		setResizable(false); 

		setVisible(true);
		
		//Btn click 
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); 

			}
		});
	
	}	
}
