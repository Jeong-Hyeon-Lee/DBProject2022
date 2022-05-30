package DB2022TEAM03.GEUNJU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
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

	public M_searchGYM(Connection conn, String ID) throws SQLException {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("헬스장 검색");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(3,1));
		
		//search
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		
		JPanel i1 = new JPanel();
		JLabel inputDesc = new JLabel("헬스장 이름 : ");
		i1.add(inputDesc);
		input.add(i1);
		
		JPanel i2 = new JPanel();
		JTextField inputText = new JTextField(25);
		i2.add(inputText);
		input.add(i2);
		
		JPanel i3 = new JPanel();
		JButton searchGYMBtn = new JButton("검색"); //btn클릭시 원하는 정보만 조회하도록
		i3.add(searchGYMBtn);
		input.add(i3);
		btnGroup.add(input);

		//Table 
		String columnNames[] = {"헬스장","지역","1회가격","10회가격","20회가격","기타프로모션"}; //headers
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		JTable jt = new JTable(tableModel);
		
		//query for table
		Statement stmt = conn.createStatement();
		String str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격";
		ResultSet rset = stmt.executeQuery(str);
		
		//table data
		if(!rset.isBeforeFirst()) {
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			jpErr.add(new JLabel("헬스장정보를 불러오는데 실패했습니다."));
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
			
			//스크롤&column명을 위해 JScrollPane 적용
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
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,700,400);
		
		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
		
		searchGYMBtn.addActionListener(new ActionListener() {
			@Override //btn클릭시 원하는 정보만 조회하도록
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();
				
				//Table 
				String columnNames[] = {"헬스장","지역","1회가격","10회가격","20회가격","기타프로모션"}; //headers
				DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
				JTable jt = new JTable(tableModel);
				
				//query for table
				String str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격 WHERE 지역=?";
				PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, searchText);
					ResultSet rset = pstmt.executeQuery();
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("헬스장정보를 불러오는데 실패했습니다."));
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
						
						//스크롤&column명을 위해 JScrollPane 적용
						JScrollPane scrollpane=new JScrollPane(jt);
						scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
						btnGroup.add(scrollpane);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//Btn click 이벤트
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_GScreen(conn,ID);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		
		//talbe 클릭 시, 헬스장 등록하기 헬스장을 등록하시겠습니까? -> 등록완료 로 바로 넘어감
	
	}	
}
