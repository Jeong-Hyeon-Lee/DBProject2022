package DB2022Team03.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.*;

import DB2022Team03.TrainerWithGUI.TrainerMenuJTable;
import DB2022Team03.Gym.G_selectMenu;
import DB2022Team03.MemberInfo.M_MainScreen;

public class DeleteScreen extends JFrame {

	String owner_name = null;

	public DeleteScreen(Connection conn, String userType, String ID) {
		setTitle("탈퇴");

		// title
		JPanel titlePanel = new JPanel();
		JLabel delete = new JLabel(userType + " 탈퇴");
		delete.setForeground(new Color(5, 0, 153));
		delete.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		titlePanel.add(delete);

		// check 문구
		JPanel checkPanel = new JPanel(new FlowLayout());
		JLabel check = new JLabel(" ", JLabel.CENTER);
		check.setText(ID + "님, 정말 탈퇴하시겠습니까?");
		checkPanel.add(check);

		JLabel check2 = new JLabel(" ", JLabel.CENTER);
		check2.setText("탈퇴 시 관련된 모든 정보가 삭제됩니다.");
		checkPanel.add(check2);

		// 버튼
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JPanel deletePanel = new JPanel(new FlowLayout());
		JButton deleteButton = new JButton("탈퇴");
		deletePanel.add(deleteButton);

		JPanel backPanel = new JPanel(new FlowLayout());
		JButton backButton = new JButton("취소");
		backPanel.add(backButton);

		buttonPanel.add(deletePanel);
		buttonPanel.add(backPanel);

		// Panel에 배치
		JPanel Panel = new JPanel();
		Panel.setLayout(new FlowLayout());
		Panel.add(buttonPanel);

		setLayout(new BorderLayout());

		add(titlePanel, BorderLayout.NORTH);
		add(checkPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setBounds(200, 200, 400, 250);

		setResizable(false); // 화면 크기 고정하는 작업

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// 탈퇴 버튼 눌렀을 때 이벤트처리
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				boolean deleteSuccess = true;

				// =================================================
				// ===================== 회원 =======================
				if (userType.equals("회원")) { // 회원삭제시, 회원 테이블, 수업 테이블에서 삭제 이뤄져야함
					try {
						// transaction 시작
						conn.setAutoCommit(false);

						// STEP0. 필요한 값들 찾아서 저장해두기
						String member_gym = null; // 로그인한 회원의 소속 헬스장번호
						String member_trainer = null; // 로그인한 회원의 담당 트레이너번호

						String loginquery = " SELECT * "
								+ " FROM DB2022_회원 USE INDEX (회원번호인덱스)"
								+ " WHERE (회원번호=?) ";

						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();

						while (rs.next()) {
							member_gym = rs.getString("소속헬스장");
							member_trainer = rs.getString("담당트레이너");
						}

						// STEP1. 수업 테이블에서 해당 회원의 회원번호를 000000으로 변경
						// 수업이 회원을 참조함. 회원 테이블에 이미 탈퇴처리를 위해 000000인 tuple이 있음.
						String deleteQuery1 = " UPDATE DB2022_수업 "
								+ " SET 회원번호 = ? "
								+ " WHERE 회원번호 = ? ";

						PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
						pst1.setString(1, "000000");
						pst1.setString(2, ID);
						pst1.executeUpdate();

						// STEP2. 회원 테이블에서 해당 회원 삭제
						String deleteQuery2 = " DELETE FROM DB2022_회원 "
								+ " WHERE 회원번호 = ? ";

						PreparedStatement pst2 = conn.prepareStatement(deleteQuery2);
						pst2.setString(1, ID);
						pst2.executeUpdate();

						// STEP3. 헬스장에서 전체회원수 변경
						String deleteQuery3 = " UPDATE DB2022_헬스장 "
								+ " SET 전체회원수 = 전체회원수 - 1 "
								+ " WHERE 헬스장번호 = ? ";

						PreparedStatement pst3 = conn.prepareStatement(deleteQuery3);
						pst3.setString(1, member_gym);
						pst3.executeUpdate();

						// STEP4. 담당트레이너의 담당 회원수 변경
						String deleteQuery4 = " UPDATE DB2022_트레이너 "
								+ " SET 담당회원수 = 담당회원수 - 1 "
								+ " WHERE 강사번호 = ? ";

						PreparedStatement pst4 = conn.prepareStatement(deleteQuery4);
						pst4.setString(1, member_trainer);
						pst4.executeUpdate();

						// STEP5. 수업에서, 회원번호 트레이너번호가 둘다 000000인 경우 해당 수업 삭제
						String deleteQuery5 = " DELETE FROM DB2022_수업 "
								+ " WHERE 회원번호 = ? and 강사번호 = ?";

						PreparedStatement pst5 = conn.prepareStatement(deleteQuery5);
						pst5.setString(1, "000000");
						pst5.setString(2, "000000");
						pst5.executeUpdate();

						conn.commit(); // transaction 끝
						conn.setAutoCommit(true);

					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();

						System.out.println("Roll Back 실행");

						try {
							if (conn != null)
								conn.rollback(); // 정상 수행되지 않았을 시 rollback();
						} catch (SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== 트레이너 =======================
				else if (userType.equals("트레이너")) {
					try {
						// transaction 시작
						conn.setAutoCommit(false);

						// STEP0. 필요한 값들 찾아서 저장해두기
						String trainer_gym = null; // 로그인한 트레이너의 소속 헬스장번호

						String loginquery = " SELECT * "
								+ " FROM DB2022_트레이너 USE INDEX (강사번호인덱스)"
								+ " WHERE(강사번호=?) ";

						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();

						while (rs.next()) {
							trainer_gym = rs.getString("헬스장번호");
						}

						// STEP1. 담당회원의 담당트레이너를 000000로
						// 회원이 트레이너를 참조하고 있으므로, 회원쪽을 먼저 000000로 변경해줘야 삭제 가능.
						String deleteQuery1 = " UPDATE DB2022_회원"
								+ " SET 담당트레이너 = ?"
								+ " WHERE(담당트레이너 = ?)";

						PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
						pst1.setString(1, "000000");
						pst1.setString(2, ID);
						pst1.executeUpdate();

						// STEP2. 트레이너 테이블에서 해당 트레이너 삭제
						String deleteQuery2 = " DELETE FROM DB2022_트레이너"
								+ " WHERE(강사번호=?)";

						PreparedStatement pst2 = conn.prepareStatement(deleteQuery2);
						pst2.setString(1, ID);
						pst2.executeUpdate();

						// STEP3. 헬스장에서 전체트레이너수 변경
						String deleteQuery3 = " UPDATE DB2022_헬스장"
								+ " SET 전체트레이너수 = 전체트레이너수 - 1"
								+ " WHERE(헬스장번호=?)";

						PreparedStatement pst3 = conn.prepareStatement(deleteQuery3);
						pst3.setString(1, trainer_gym);
						pst3.executeUpdate();

						// STEP4. 해당 트레이너의 수업에서 강사번호를 000000으로 변경
						// 수업이 트레이너를 참조함. 트레이너 테이블에 이미 탈퇴처리를 위해 000000인 tuple이 있음.
						String deleteQuery4 = " UPDATE DB2022_수업 "
								+ " SET 강사번호 = ? "
								+ " WHERE 강사번호 = ? ";

						PreparedStatement pst4 = conn.prepareStatement(deleteQuery4);
						pst4.setString(1, "000000");
						pst4.setString(2, ID);
						pst4.executeUpdate();

						// STEP5. 수업에서, 회원번호 트레이너번호가 둘다 000000인 경우 해당 수업 삭제
						String deleteQuery5 = " DELETE FROM DB2022_수업 "
								+ " WHERE 회원번호 = ? and 강사번호 = ?";

						PreparedStatement pst5 = conn.prepareStatement(deleteQuery5);
						pst5.setString(1, "000000");
						pst5.setString(2, "000000");
						pst5.executeUpdate();

						conn.commit(); // transaction 끝
						conn.setAutoCommit(true);

					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();

						System.out.println("Roll Back 실행");

						try {
							if (conn != null)
								conn.rollback(); // 정상 수행되지 않았을 시 rollback();
						} catch (SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				// =================================================
				// ===================== 관장 =======================
				else if (userType.equals("관장")) {
					try {
						// transaction 시작
						conn.setAutoCommit(false);

						// STEP0. 해당 헬스장에 소속한 트레이너, 회원이 0명일 때만 탈퇴를 허락함
						int trainerNum = -1;
						int memberNum = -1;

						// 소속트레이너수, 소속회원수 찾기, 이따가 다시 관장메뉴 불러올 때 필요한 owner_name도 찾아두기
						String loginquery = " SELECT *"
								+ " FROM DB2022_헬스장"
								+ " WHERE (헬스장번호=?) ";

						PreparedStatement pst = conn.prepareStatement(loginquery);
						pst.setString(1, ID);
						ResultSet rs = pst.executeQuery();

						while (rs.next()) {
							trainerNum = rs.getInt("전체트레이너수");
							memberNum = rs.getInt("전체회원수");
							owner_name = rs.getString("이름");
						}

						// 소속 트레이너, 소속 회원 수가 0명일 때만 탈퇴 허용
						if (trainerNum == 0 && memberNum == 0) {
							// STEP1. 가격정보 삭제 >> ON DELETE CASCADE 되어있음.
							// STEP2. 수업정보 삭제 >> 이미 회원이나 트레이너 삭제될 때 삭제됨.
							// STEP3. 회원정보 삭제 >> 이미 0명이라 삭제할 거 없음
							// STEP4. 트레이너정보 삭제 >> 이미 0명이라 삭제할 거 없음
							// STEP5. 헬스장 정보 삭제
							String deleteQuery1 = " DELETE FROM DB2022_헬스장 "
									+ " WHERE 헬스장번호 = ? ";

							PreparedStatement pst1 = conn.prepareStatement(deleteQuery1);
							pst1.setString(1, ID);
							pst1.executeUpdate();

							conn.commit(); // transaction 끝
							conn.setAutoCommit(true);
						} else {
							deleteSuccess = false;
							JOptionPane.showMessageDialog(null, "해당 헬스장에 속한 트레이너와 회원이 모두 탈퇴했을 때만 탈퇴가능합니다."
									+ "\n"
									+ "현재 전체 트레이너 수: " + trainerNum + "\n"
									+ "현재 전체 회원 수: " + memberNum + "\n"
									+ "이므로 탈퇴가 불가능합니다.");
						}

					} catch (SQLException se) {
						deleteSuccess = false;
						se.printStackTrace();

						System.out.println("Roll Back 실행");

						try {
							if (conn != null)
								conn.rollback(); // 정상 수행되지 않았을 시 rollback();
						} catch (SQLException se2) {
							se2.printStackTrace();
						}
					}
				}

				if (deleteSuccess == true) {
					// 탈퇴성공시 다시 start화면으로
					JOptionPane.showMessageDialog(null, "탈퇴처리 되었습니다.");
					new StartScreen(conn);
					dispose(); // 현재의 frame을 종료시키는 메서드.
				}
			}
		});

		// 취소 버튼을 클릭했을 때 이벤트 처리
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				// 다시 각자 메뉴 페이지로 돌아가기
				if (userType.equals("회원")) {
					new M_MainScreen(conn, ID);
				} else if (userType.equals("트레이너")) {
					new TrainerMenuJTable(ID);
				} else if (userType.equals("관장")) {
					try {
						new G_selectMenu(ID, owner_name);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				dispose();

			}
		});

	}
}