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

public class M_enrollMembership extends JFrame {
	public static JTable jt;
	public static JTable jt2;
	public static DefaultTableModel tableModel;
	public static DefaultTableModel tableModel2;
	static PreparedStatement pstmt = null;
	static ResultSet rset = null;
	static String str = null;
	static JLabel infoText;
	static JPanel btnGroup;
	static String columnNames[]= {"이름","전체결제회차","완료수업회차","남은수업회차","현재회원권"};
	static String columnNames2[] ={"1회권","10회권","20회권","기타프로모션"};
	static Statement stmt; 
	
	public M_enrollMembership(Connection conn, String ID) throws SQLException {
		setTitle("헬스장 PT 예약 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임 윈도우를 닫으면 프로그램 종료
		
		//상단 - 회원 MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("회원권 등록 및 변경");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//search - 헬스장 지역으로 찾기
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		
		JPanel i1 = new JPanel();
		JLabel inputDesc = new JLabel("원하는 회원권을 숫자로 입력해주세요.: ");
		i1.add(inputDesc);
		input.add(i1);
		
		JPanel i2 = new JPanel();
		JTextField inputText = new JTextField(25);
		i2.add(inputText);
		input.add(i2);
		
		JPanel i3 = new JPanel();
		JButton enrollBtn = new JButton("등록"); 
		i3.add(enrollBtn);
		input.add(i3);

		//for err & info & undo 
		btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(3,1));
		
		//Table1 : 전체횟수, 남은횟수, 현재회원권
		JPanel table1 = new JPanel();
		table1.setLayout(new GridLayout(1,1));
		tableModel = new DefaultTableModel(columnNames,0);
		jt = new JTable(tableModel);
		
		//query for table
		str = "select 이름, 전체횟수, 남은횟수, 현재회원권 from db2022_회원 use index (회원번호인덱스) where 회원번호 = ?";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		rset.next();
		String name = rset.getString(1);
		String total = rset.getString(2);
		String left = rset.getString(3);
		String membership = rset.getString(4);
		String finish = String.valueOf(Integer.parseInt(total)-Integer.parseInt(left));
		String[] data1 = {name,total,finish,left,membership};
				
		tableModel.addRow(data1);
		jt = new JTable(tableModel);
		
		JScrollPane scrollpane=new JScrollPane(jt);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//padding
	
		table1.add(scrollpane);
			
		//Table2 : 헬스장 가격표
		JPanel table2 = new JPanel();
		table2.setLayout(new GridLayout(1,1));
		tableModel2 = new DefaultTableModel(columnNames2,0);
		jt2 = new JTable(tableModel2);
		
		//query for table 
		str = "select 1회가격, 10회가격, 20회가격, 기타프로모션설명 from db2022_가격 where 헬스장번호 in (select 소속헬스장 from db2022_회원 use index (회원번호인덱스) where 회원번호=?) ";
		pstmt = conn.prepareStatement(str);
		pstmt.setString(1, ID);
		rset = pstmt.executeQuery();
		
		if(!rset.isBeforeFirst()) {
			JPanel jpErr = new JPanel();
			jpErr.setLayout(new FlowLayout());
			JLabel errText = new JLabel("헬스장 가격정보를 불러올 수 없습니다. 먼저 헬스장을 등록해주세요.");
			errText.setForeground(new Color(153,0,5));
			jpErr.add(errText);
			btnGroup.add(jpErr);
		} else {
			rset.next();
			int price1 = rset.getInt(1);
			int price10 = rset.getInt(2);
			int price20 = rset.getInt(3);
			String promotion = rset.getString(4);
			String[] data2 = {String.valueOf(price1), String.valueOf(price10),String.valueOf(price20),promotion};
			
			tableModel2.addRow(data2);
			jt2.setModel(tableModel2);

			JScrollPane scrollpane2=new JScrollPane(jt2);
			scrollpane2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
			table2.add(scrollpane2);
		}
		
		//안내문구
		JPanel info = new JPanel();
		infoText = new JLabel("회원권 등록 및 변경은 남은수업회차가 0일 때만 가능합니다.");
		info.add(infoText);
		btnGroup.add(info);
		
		//undo
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("뒤로가기");
		Menu9.add(undo);
		jp0.add(Menu9);
		
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		//center를 그리드로 3개
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(3,1));
		center.add(input);
		center.add(table1);
		center.add(table2);
		
		add(M_main,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
		add(btnGroup,BorderLayout.SOUTH);
		setBounds(200,200,800,400);
		
		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
				
		//Btn click 이벤트
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen(conn,ID);
				dispose(); // 현재의 frame을 종료시키는 메서드.

			}
		});
		
		 enrollBtn.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int input = Integer.parseInt(inputText.getText());
				/*
				if(input!=1&&input!=10&&input!=20) {
					infoText.setText("1회권, 10회권, 20회권 중 하나를 선택해주세요.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
				}
				*/
				//소속헬스장, 담당트레이너 번호찾기 -> null이라면 등록X되도록 구현
				String GYMid_M = null;
				String Tid_M = null;
				try {
					str = "SELECT 소속헬스장, 담당트레이너 FROM db2022_회원 USE INDEX (회원번호인덱스) WHERE 회원번호=?";
					pstmt = conn.prepareStatement(str);
					pstmt.setString(1, ID);
					rset = pstmt.executeQuery();
					
					rset.next();
					GYMid_M = rset.getString(1);
					Tid_M = rset.getString(2);
			
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
				} else if (Tid_M==null) {
					infoText.setText("트레이너를 먼저 등록해주세요.");
					infoText.setForeground(new Color(153,0,5));
					btnGroup.revalidate();
					btnGroup.repaint();
					return;					
				}
	
				
				try { //남은수업횟수가 0인지 확인
					int check[] = M_totalLeft.M_totalLeft(conn, ID);
					if(check[1]==0) { //남은수업횟수 == 0
						//Update
						str = "UPDATE DB2022_회원 SET 현재회원권=?,전체횟수=?,남은횟수=? WHERE 회원번호=?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, input+"회권");
						pstmt.setInt(2, input+check[0]);
						pstmt.setInt(3, input);
						pstmt.setString(4, ID);
						pstmt.executeUpdate();
						
						infoText.setText("회원권을 "+input+"회권으로 변경했습니다.");
						infoText.setForeground(new Color(5,0,153));
						btnGroup.revalidate();
						btnGroup.repaint();
						
						//table
						tableModel.setNumRows(0);
						//query for table
						str = "select 이름, 전체횟수, 남은횟수, 현재회원권 from db2022_회원 use index(회원번호인덱스) where 회원번호 = ?";
						pstmt = conn.prepareStatement(str);
						pstmt.setString(1, ID);
						rset = pstmt.executeQuery();
				
						rset.next();
						String name = rset.getString(1);
						String total = rset.getString(2);
						String left = rset.getString(3);
						String membership = rset.getString(4);
						String finish = String.valueOf(Integer.parseInt(total)-Integer.parseInt(left));
						String[] data1 = {name,total,finish,left,membership};
								
						tableModel.addRow(data1);
						jt.setModel(tableModel);
						
					} else {
						//안내문구 빨간색으로 표시
						infoText.setText("회원권 등록 및 변경은 남은수업회차가 0일 때만 가능합니다.");
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
