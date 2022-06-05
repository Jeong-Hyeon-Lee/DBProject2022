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
		setTitle("헬스장 통합 관리 프로그램");
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
		str = "select * from DB2022_searchTrainer";
		rset = stmt.executeQuery(str);
		
		//for info & btn
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//안내문구
		JPanel info = new JPanel();
		infoText = new JLabel("트레이너 등록을 원하시면 원하는 트레이너를 클릭한 뒤, 틍록하기 버튼을 눌러주세요. (소속헬스장의 트레이너만 등록 가능)");
		info.add(infoText);
		btnGroup.add(info);
		
		//table data
		try {
			if(!rset.isBeforeFirst()) {
				infoText.setText("등록된 트레이너 정보가 없습니다.");
				infoText.setForeground(new Color(153,0,5));
				btnGroup.revalidate();
				btnGroup.repaint();
			}
			else {
				while(rset.next()) {
					String Gname = rset.getString(1);
					String Tname = rset.getString(2);
					String location = rset.getString(3);
					String member = rset.getString(4);
					
					if(!Tname.equals("탈퇴")) {
						String[] data = {Gname,Tname,location,member};
						tableModel.addRow(data);
					} 	
				}
				jt = new JTable(tableModel);
				jt.setRowSelectionInterval(0,0); 
				
				//스크롤&column명을 위해 JScrollPane 적용
				JScrollPane scrollpane=new JScrollPane(jt);
				scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
			
				table.add(scrollpane);
			}
		} catch (SQLException e) {
			infoText.setText("트레이너 정보를 불러오는데 실패했습니다.");
			infoText.setForeground(new Color(153,0,5));
			btnGroup.revalidate();
			btnGroup.repaint();
		}
		
		
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
				str = "select * from DB2022_searchTrainer where 헬스장이름 like ?"; 
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("등록된 헬스장 중 해당 이름을 가진 헬스장이 없습니다.");
						infoText.setForeground(new Color(135,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							if(!Tname.equals("탈퇴")) {
								String[] data = {Gname,Tname,location,member};
								tableModel.addRow(data);
							}
						}
						jt.setModel(tableModel);					
						jt.setRowSelectionInterval(0,0); 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("트레이너를 불러오는데 실패했습니다.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
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
				str = "select * from DB2022_searchTrainer where 트레이너이름 like ?"; 
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("등록된 트레이너 중 해당 이름을 가진 트레이너가 없습니다.");
						infoText.setForeground(new Color(135,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							if(!Tname.equals("탈퇴")) {
								String[] data = {Gname,Tname,location,member};
								tableModel.addRow(data);
							}
						}
						jt.setModel(tableModel);	
						jt.setRowSelectionInterval(0,0); 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("트레이너를 불러오는데 실패했습니다.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
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
				str = "select * from DB2022_searchTrainer where 헬스장번호 IN (SELECT 소속헬스장 FROM db2022_회원 USE INDEX (회원번호인덱스) WHERE 회원번호=?)";
				try {
					//소속헬스장 트레이너 보여주기
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();

					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("헬스장에 소속된 트레이너가 없습니다.");
						infoText.setForeground(new Color(135,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
					}
					else {
						while(rset.next()) {
							String Gname = rset.getString(1);
							String Tname = rset.getString(2);
							String location = rset.getString(3);
							String member = rset.getString(4);
							
							if(!Tname.equals("탈퇴")) {
								String[] data = {Gname,Tname,location,member};
								tableModel.addRow(data);
							}
						}
						jt.setModel(tableModel);					
						jt.setRowSelectionInterval(0,0); 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("트레이너를 불러오는데 실패했습니다.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					e1.printStackTrace();
				}
			}
		});
		
		enrollBtn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) { 
				int row = jt.getSelectedRow();
				if(row==-1) {
					infoText.setText("원하는 트레이너를 클릭한 뒤, 등록하기 버튼을 눌러주세요.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;
				}
				
				String Tname = (String) jt.getValueAt(row, 1); //선택한 트레이너 이름
				
				String Gname = (String) jt.getValueAt(row, 0); //선택한 트레이너의 헬스장 이름
				
				try {
					//현재 소속헬스장이 있는지 확인
					str = "select G.이름 from db2022_회원 as M, db2022_헬스장 as G where M.소속헬스장=G.헬스장번호 and M.회원번호=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1,ID);
					rset=pstmt.executeQuery();	
					
					if(!rset.isBeforeFirst()) { //헬스장이 없다면 
						infoText.setText("우선 헬스장을 등록해주세요.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
						return;
					} else { //헬스장이 있다면
						rset.next();
						String nowGymName = rset.getString(1); //소속된 헬스장 이름
												
						//남은 횟수가 0인지 확인
						int check[] = M_totalLeft.M_totalLeft(conn, ID);
						
						if(check[1]==0) { //남은 횟수가 0이라면
							//소속헬스장과 선택한 트레이너 소속 헬스장이 같은지 확인 (이름으로 확인)
							if(nowGymName.equals(Gname)) { //둘이 같다면 등록 가능!
								str = "select 담당트레이너 from db2022_회원 use index(회원번호인덱스) where 회원번호=?";
								pstmt = conn.prepareStatement(str);
								pstmt.setString(1, ID);
								rset = pstmt.executeQuery();	
								
								//use just rset.next() because any successful query that has data, once executed, will produce a ResultSet with isBeforeFirst() returning true.
								rset.next();
								String nowT = rset.getString(1);
								
								try {
									//update
									conn.setAutoCommit(false); //transaction 시작

									if(nowT!=null) { //현재 담당트레이너가 있다면 기존 담당트레이너 담당회원수 -1									
										//현재 담당트레이너 번호로 담당회원수찾기
										str = "SELECT 담당회원수 FROM DB2022_트레이너 use index(강사번호) WHERE 강사번호=?";
										pstmt = conn.prepareStatement(str);
										pstmt.setString(1, nowT);
										rset = pstmt.executeQuery();
										int nowTnum = 0; //기존 담당트레이너 담당회원수
										rset.next();
										nowTnum = rset.getInt(1);
										
										//등록해제 : 기존 담당트레이너 담당회원수 -1
										str = "UPDATE DB2022_트레이너 SET 담당회원수=? WHERE 강사번호=?";
										pstmt = conn.prepareStatement(str);
										pstmt.setInt(1, nowTnum-1);
										pstmt.setString(2, nowT);
										pstmt.executeUpdate();								
									} 
									//현재 담당트레이너가 없다면 바로 enroll								
									//선택한 트레이너 이름(Tname) 으로 트레이너 번호 찾기
									str = "SELECT 강사번호, 담당회원수 FROM DB2022_트레이너 WHERE 이름=?";
									pstmt = conn.prepareStatement(str);
					                pstmt.setString(1, Tname);
					                rset = pstmt.executeQuery();
					                rset.next();
					                String selectT = rset.getString(1); //선택한 트레이너 번호
					                int selectTnum = rset.getInt(2); //선택한 트레이너의 담당회원 수 
									
									//등록 : 회원 담당트레이너 등록
									str = "UPDATE DB2022_회원 SET 담당트레이너=? WHERE 회원번호=?";
									pstmt = conn.prepareStatement(str);
									pstmt.setString(1, selectT);
									pstmt.setString(2, ID);
									pstmt.executeUpdate();
									
									//등록 : 트레이너 담당회원수 +1
									str = "UPDATE DB2022_트레이너 SET 담당회원수=? WHERE 강사번호=?";
									pstmt = conn.prepareStatement(str);
									pstmt.setInt(1, selectTnum+1);
									pstmt.setString(2, selectT);
									pstmt.executeUpdate();
									
									infoText.setText("담당트레이너("+Tname+")가 등록되었습니다.");
									infoText.setForeground(new Color(5,0,135));
									btnGroup.revalidate();
									btnGroup.repaint();
									
									conn.commit();
									conn.setAutoCommit(true); //transaction 종료
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									infoText.setText("트레이너 등록/변경에 실패했습니다. 다시 시도해주세요.");
									infoText.setForeground(new Color(153,0,5));
									btnGroup.revalidate();
									btnGroup.repaint();
									e2.printStackTrace();

									System.out.println("Roll Back 실행");
							
									try {
										if (conn != null)
										conn.rollback(); // 정상 수행되지 않았을 시 rollback();
									} catch (SQLException se2) {
										se2.printStackTrace();
									}
								}
							} else { //다르다면
								infoText.setText("소속헬스장의 트레이너만 등록가능합니다.(소속헬스장:"+nowGymName+")");
								infoText.setForeground(new Color(153,0,5));
								btnGroup.revalidate();
								btnGroup.repaint();
							}
						} else { //남은 횟수가 0이 아니라면 
							//textfield띄우기
							infoText.setText("아직 수업횟수가 남아서 트레이너를 변경할 수 없습니다.");
							infoText.setForeground(new Color(153,0,5));
							btnGroup.revalidate();
							btnGroup.repaint();
							return;
						}
					}	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	
	}	
}
