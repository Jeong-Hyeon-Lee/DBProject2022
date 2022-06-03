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

public class M_searchTrainer extends JFrame {
	public static JTable jt;
	public static DefaultTableModel tableModel;
	static PreparedStatement pstmt = null;
	static ResultSet rset = null;
	static String str = null;
	static JLabel infoText;
	static JPanel btnGroup;
	static String columnNames[]= {"헬스장","트레이너","지역","담당회원수"}; //columnName
	static Statement stmt; 
	
	public M_searchTrainer(Connection conn, String ID) throws SQLException {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("트레이너 찾기");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//search
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
		JButton searchTBtn = new JButton("헬스장 이름으로 검색");
		i3.add(searchTBtn);
		input.add(i3);
		
		JPanel i4 = new JPanel();
		JButton searchTBtn2 = new JButton("트레이너 이름으로 검색"); 
		i4.add(searchTBtn2);
		input.add(i4);

		//Table
		JPanel table = new JPanel();
		table.setLayout(new GridLayout(1,1));
		tableModel = new DefaultTableModel(columnNames,0);
		jt = new JTable(tableModel);
		
		//query for table
		stmt = conn.createStatement();
		str = "select * from searchTrainer";
		rset = stmt.executeQuery(str);
		
		//for err & undo btn
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//table data
		if(!rset.isBeforeFirst()) {
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			jpErr.add(new JLabel("트레이너정보를 불러오는데 실패했습니다."));
			btnGroup.add(jpErr);
		}
		else {
			while(rset.next()) {
				String Gname = rset.getString(1);
				String Tname = rset.getString(2);
				String location = rset.getString(3);
				String member = rset.getString(4);
				
				String[] data = {Gname,Tname,location,member};
				
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
		infoText = new JLabel("트레이너 등록을 원하시면 원하는 트레이너를 클릭한 뒤, 틍록하기 버튼을 눌러주세요. \n(소속헬스장의 트레이너만 등록 가능합니다.)");
		info.add(infoText);
		btnGroup.add(info);
		
		//undo
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		//show
		JPanel showT = new JPanel();
		JButton showTBtn = new JButton("소속헬스장 트레이너 확인");
		showT.add(showTBtn);
		jp0.add(showT);
		
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
		setBounds(200,200,800,400);
		
		setResizable(false); 

		setVisible(true);
		
		//Btn event
		searchTBtn.addActionListener(new ActionListener() { //헬스장 이름으로 검색
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();
				
				//Table
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from searchTrainer where searchTrainer.헬스장이름 like ?"; //G.이름 or 헬스장이름
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("트레이너정보를 불러오는데 실패했습니다."));
						btnGroup.add(jpErr);
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							String[] data = {Gname,Tname,location,member};
							
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

		searchTBtn2.addActionListener(new ActionListener() { //트레이너 이름으로 검색
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String searchText = inputText.getText();
			
				//Table
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from searchTrainer where searchTrainer.트레이너이름 like ?"; //T.이름 or 트레이너이름
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("트레이너정보를 불러오는데 실패했습니다."));
						btnGroup.add(jpErr);
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							String[] data = {Gname,Tname,location,member};
							
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

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); 

			}
		});
		
		showTBtn.addActionListener(new ActionListener() { //소속헬스장 트레이너 확인
			@Override 
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//Table 
				tableModel.setNumRows(0);
				
				//query for table
				str = "select * from searchTrainer where searchTrainer.헬스장번호 IN (SELECT 소속헬스장 FROM DB2022_회원 WHERE 회원번호=?);";
				try {
					//소속헬스장 트레이너 보여주기
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						JPanel jpErr = new JPanel();
						jpErr.setLayout(new FlowLayout());
						jpErr.add(new JLabel("트레이너정보를 불러오는데 실패했습니다."));
						btnGroup.add(jpErr);
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							String[] data = {Gname,Tname,location,member};
							
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
		
		//지금 트레이너와 헬스장정보 비교 -> 남은 횟수(현재) / 남은횟수->헬스장비교로 바꾸기
		enrollBtn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) { 
				int row = jt.getSelectedRow();
				
				String Tname = (String) jt.getValueAt(row, 1);
				
				//선택한 트레이너의 헬스장 이름
				String Gname = (String) jt.getValueAt(row, 0);

				//선택한 트레이너의 헬스장 번호찾기
				String GYMid_T = null;
				try {
					str = "SELECT 헬스장번호 FROM DB2022_헬스장 WHERE 이름=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, Gname);
					rset = pstmt.executeQuery();
					rset.next();
					GYMid_T = rset.getString(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				//소속헬스장 번호찾기 -> 소속헬스장이 null이라면 등록X되도록 구현
				String GYMid_M = null;
				try {
					str = "SELECT 소속헬스장 FROM DB2022_회원 WHERE 회원번호=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					rset.next();
					GYMid_M = rset.getString(1);
			
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if(GYMid_M==null) {
					infoText.setText("헬스장을 먼저 등록해주세요.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;
				}
				
				//소속헬스장과 트레이너의 헬스장이 같은지 비교
				if(GYMid_M.equals(GYMid_T)) {
					try { //남은수업횟수가 0인지 확인
						int check[] = M_totalLeft.M_totalLeft(conn, ID);
						
						if(check[1]==0) { //남은수업횟수 == 0
							//트레이너 이름으로 번호찾기
							str = "SELECT 강사번호 FROM DB2022_트레이너 WHERE 이름=?";
							pstmt = conn.prepareStatement(str);
							pstmt.setString(1, Tname);
							rset = pstmt.executeQuery();
							String Tid = null;
							
							rset.next();
							Tid = rset.getString(1);	
							System.out.println(Tid);
							
							//담당 트레이너 등록하기
							str = "UPDATE DB2022_회원 SET 담당트레이너=? WHERE 회원번호=?";
							pstmt = conn.prepareStatement(str);
							pstmt.setString(1, Tid);
							pstmt.setString(2, ID);
							pstmt.executeUpdate();
							
							infoText.setText("담당트레이너("+Tname+")가 등록되었습니다.");
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
				} else {
					try {
						//textfield띄우기
						str = "SELECT 이름 FROM DB2022_헬스장 WHERE 헬스장번호=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, GYMid_M);
						rset = pstmt.executeQuery();
						rset.next();
						infoText.setText("소속헬스장의 트레이너만 등록가능합니다.(소속헬스장:"+rset.getString(1)+")");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}			
			}
		});
	
	}	
}
