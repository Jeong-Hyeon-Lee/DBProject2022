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
	static String columnNames[]= {"헬스장","지역","1회가격","10회가격","20회가격","기타프로모션"};
	static Statement stmt; 
	
	public M_searchGYM(Connection conn, String ID) throws SQLException {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("헬스장 찾기");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//search - 헬스장 지역으로 찾기
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		
		JPanel i1 = new JPanel();
		JLabel inputDesc = new JLabel("검색어 : ");
		i1.add(inputDesc);
		input.add(i1);
		
		JPanel i2 = new JPanel();
		JTextField inputText = new JTextField(25);
		i2.add(inputText);
		input.add(i2);
		
		JPanel i3 = new JPanel();
		JButton searchGYMBtn = new JButton("지역 검색"); //btn클릭시 원하는 정보만 조회하도록
		i3.add(searchGYMBtn);
		input.add(i3);
		
		JPanel i4 = new JPanel();
		JButton searchGYMBtn2 = new JButton("이름 검색"); //btn클릭시 원하는 정보만 조회하도록
		i4.add(searchGYMBtn2);
		input.add(i4);
	
		//Table
		JPanel table = new JPanel();
		table.setLayout(new GridLayout(1,1));
		tableModel = new DefaultTableModel(columnNames,0);
		jt = new JTable(tableModel);
		
		//query for table
		stmt = conn.createStatement();
		str = "select * from searchGYM";
		rset = stmt.executeQuery(str);
		
		//for err & undo 
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
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
				int price1 = rset.getInt(3);
				int price10 = rset.getInt(4);
				int price20 = rset.getInt(5);
				String promotion = rset.getString(6);
				
				String[] data = {gym,location,String.valueOf(price1),String.valueOf(price10),String.valueOf(price20),promotion};
				
				tableModel.addRow(data);
			}
			jt = new JTable(tableModel);
			
			//스크롤&column명을 위해 JScrollPane 적용
			JScrollPane scrollpane=new JScrollPane(jt);
			scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
		
			table.add(scrollpane);
		}
		
		//안내문구
		JPanel info = new JPanel();
		infoText = new JLabel("헬스장 등록을 원하시면 원하는 헬스장을 클릭한 뒤, 틍록하기 버튼을 눌러주세요.");
		info.add(infoText);
		btnGroup.add(info);
		
		//undo
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		//recommend
		JPanel recommend = new JPanel();
		JButton recommendBtn = new JButton("추천받기");
		recommend.add(recommendBtn);
		jp0.add(recommend);
		
		//enroll
		JPanel enroll = new JPanel();
		JButton enrollBtn = new JButton("등록하기");
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
		
		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
		
		searchGYMBtn.addActionListener(new ActionListener() { //지역으로 검색
			@Override //btn클릭시 원하는 정보만 조회하도록
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();

				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격 WHERE 지역 like ?";
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("헬스장정보를 불러오는데 실패했습니다.")); //입력한 지역에서 헬스장을 찾지 못했습니다.
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
		
		searchGYMBtn2.addActionListener(new ActionListener() { //이름으로 검색
			@Override //btn클릭시 원하는 정보만 조회하도록
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();

				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격 WHERE 이름 like ?";
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
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
						jt.setModel(tableModel);					
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
				new M_MainScreen(conn,ID);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
	
		recommendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select 이름,지역,1회가격,10회가격,20회가격,기타프로모션설명 from db2022_헬스장 natural join db2022_가격 WHERE 지역 IN (SELECT 지역 FROM db2022_회원 WHERE 회원번호=?)";
				try {
					//소속헬스장 트레이너 보여주기
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("추천 헬스장을 찾지 못했습니다."));
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
				
				try { //남은수업횟수가 0인지 확인
					int check[] = M_totalLeft.M_totalLeft(conn, ID);
					if(check[1]==0) { //남은수업횟수 == 0
						//헬스장 이름으로 번호찾기
						String str = "SELECT 헬스장번호 FROM DB2022_헬스장 WHERE 이름=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, GYMname);
						rset = pstmt.executeQuery();
						String GYMid = null;
						
						rset.next();
						GYMid = rset.getString(1);
						
						//헬스장 등록하기
						str = "UPDATE DB2022_회원 SET 소속헬스장=? WHERE 회원번호=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, GYMid);
						pstmt.setString(2, ID);
						pstmt.executeUpdate();
						
						infoText.setText(GYMname+"에 회원으로 등록되었습니다.");
						infoText.setForeground(new Color(5,0,153));
						btnGroup.revalidate();
						btnGroup.repaint();
						
					} else {
						//textfield띄우기
						infoText.setText("아직 수업횟수가 남아서 헬스장을 변경할 수 없습니다.");
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
