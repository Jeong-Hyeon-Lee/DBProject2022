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
		setTitle("헬스장 통합 관리 프로그램");
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
		str = "select * from DB2022_searchGYM";
		rset = stmt.executeQuery(str);
		
		//for info & undo btn 
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//안내문구
		JPanel info = new JPanel();
		infoText = new JLabel("헬스장 등록을 원하시면 원하는 헬스장을 클릭한 뒤, 틍록하기 버튼을 눌러주세요.");
		info.add(infoText);
		btnGroup.add(info);
		
		//table data
		try {
			if(!rset.isBeforeFirst()) {
				infoText.setText("등록된 헬스장 정보가 없습니다.");
				infoText.setForeground(new Color(153,0,5));
				btnGroup.revalidate();
				btnGroup.repaint();
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
				jt.setRowSelectionInterval(0,0); 
				
				//스크롤&column명을 위해 JScrollPane 적용
				JScrollPane scrollpane=new JScrollPane(jt);
				scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
			
				table.add(scrollpane);
			}
		} catch (SQLException e) {
			infoText.setText("헬스장 정보를 불러오는데 실패했습니다.");
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
				str = "select * from DB2022_searchGYM WHERE 지역 like ?";
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("해당 지역에 등록된 헬스장이 없습니다.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
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
						jt.setRowSelectionInterval(0,0); 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("헬스장 정보를 불러오는데 실패했습니다.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
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
				str = "select * from DB2022_searchGYM WHERE 이름 like ?";
				try {
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, "%"+searchText+"%");
					rset = pstmt.executeQuery();
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("등록된 헬스장 중 해당 이름을 가진 헬스장이 없습니다.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
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
						jt.setRowSelectionInterval(0,0); 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("헬스장 정보를 불러오는데 실패했습니다.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
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
				str = "select * from DB2022_searchGYM WHERE 지역 IN (SELECT 지역 FROM db2022_회원 USE INDEX (회원번호인덱스) WHERE 회원번호=?)";
				try {
					//등록된 회원 지역과 같은 지역에 있는 헬스장 추천
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					//table data
					if(!rset.isBeforeFirst()) {
						infoText.setText("추천 헬스장을 찾지 못했습니다. 회원 지역 근처에 등록된 헬스장이 없습니다.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
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
							jt.setRowSelectionInterval(0,0); 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoText.setText("헬스장 정보를 불러오는데 실패했습니다.");
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
					infoText.setText("원하는 헬스장을 클릭한 뒤, 등록하기 버튼을 눌러주세요.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;
				}
				
				String GYMname = (String) jt.getValueAt(row, 0);
				
				try { //남은수업횟수가 0인지 확인
					int check[] = M_totalLeft.M_totalLeft(conn, ID);
					
					if(check[1]!=0 ) { //남은수업횟수 0이 아닐 때 변경X
						//안내문구 빨간색으로 표시
						infoText.setText("헬스장 등록 및 변경은 남은수업회차가 0일 때만 가능합니다.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
						return;
		            }
					
					//남은수업횟수가 0이면
		            //예약된 수업 개수가 0인지 확인
		            str = "SELECT COUNT(*) FROM DB2022_수업 USE INDEX(회원번호인덱스) WHERE 회원번호 = ? AND 수업진행현황 IN ('예약확인중', '예약완료')";
		            pstmt = conn.prepareStatement(str);
		            pstmt.setString(1, ID);
		            rset = pstmt.executeQuery();
		            rset.next();
		            int reservedClass = rset.getInt(1);
					
		            if(reservedClass==0) { //예약된 수업 개수==0
						//선택한 헬스장 이름으로 번호,전체회원수 찾기
						str = "SELECT 헬스장번호,전체회원수 FROM DB2022_헬스장 WHERE 이름=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, GYMname);
						rset = pstmt.executeQuery();
						
						String GYMid = null; //선택한 헬스장 번호
						int GYMnumM=0; //선택한 헬스장 전체회원수
						
						rset.next();
						GYMid = rset.getString(1); 
						GYMnumM = rset.getInt(2);
						
						//현재 등록된 헬스장 확인 후 있으면 회원 수에서 삭제
						str = "SELECT G.헬스장번호, G.전체회원수 FROM db2022_회원 as M, db2022_헬스장 as G WHERE M.소속헬스장=G.헬스장번호 and M.회원번호 = ?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, ID);
						rset = pstmt.executeQuery();
						
						try { //update
							conn.setAutoCommit(false); //transaction 시작

							if(rset.isBeforeFirst()) { //소속된 헬스장이 있으면 기존 헬스장 전체 회원수 -1
								//소속된 헬스장 정보
								String GYMidNow = null;
								int GYMnumMNow=0;
								
								rset.next();
								GYMidNow = rset.getString(1); //소속 헬스장 번호
								GYMnumMNow = rset.getInt(2); //소속 헬스장 전체회원수
	
								//기존 헬스장 전체 회원수 -1
								str = "UPDATE DB2022_헬스장 SET 전체회원수=? WHERE 헬스장번호=?";
								pstmt = conn.prepareStatement(str);
								pstmt.setInt(1, GYMnumMNow-1);
								pstmt.setString(2, GYMidNow);
								pstmt.executeUpdate();
							} 
							
							//소속된 헬스장이 없으면 바로 등록
							//헬스장 등록하기 : 회원 소속헬스장 update
							str = "UPDATE DB2022_회원 SET 소속헬스장=? WHERE 회원번호=?";
							pstmt = conn.prepareStatement(str);
							pstmt.setString(1, GYMid);
							pstmt.setString(2, ID);
							pstmt.executeUpdate();
							
							//헬스장 등록하기 : 헬스장 전체 회원 수 +1
							str = "UPDATE DB2022_헬스장 SET 전체회원수=? WHERE 헬스장번호=?";
							pstmt = conn.prepareStatement(str);
							pstmt.setInt(1, GYMnumM+1);
							pstmt.setString(2, GYMid);
							pstmt.executeUpdate();
							
							infoText.setText(GYMname+"에 회원으로 등록되었습니다.");
							infoText.setForeground(new Color(5,0,153));
							btnGroup.revalidate();
							btnGroup.repaint();	
							
							conn.commit();
							conn.setAutoCommit(true); //transaction 종료
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							infoText.setText("헬스장 등록/변경에 실패했습니다. 다시 시도해주세요.");
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
						
					} else { //예약된 수업이 남아있다면
						infoText.setText("예약확인중이거나 예약완료된 수업이 남아있어 헬스장을 변경할 수 없습니다.");
						infoText.setForeground(new Color(153,0,5));
						btnGroup.revalidate();
						btnGroup.repaint();
						return;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	
	}	
}
