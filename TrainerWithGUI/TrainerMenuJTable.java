package TrainerWithGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class TrainerMenuJTable extends JFrame implements ActionListener{
	JMenu m = new JMenu("트레이너 메뉴");
	// 트레이너용 메뉴 화면이 떴다는 것은 로그인 한 상태라는 가정을 포함한다.
	JMenuItem myPage = new JMenuItem("마이페이지"); // 개인 정보를 모두 보여주는 화면
	JMenuItem logout = new JMenuItem("로그아웃"); // 로그아웃 하는 버튼
	JMenuItem salary = new JMenuItem("수입 계산기"); // 총 수입 계산기 (회원별로 얼마나 버는지 +  총 수입을 보여줌)
	JMenuItem update = new JMenuItem("수업 현황 체크"); // 수업 불참 / 완료 여부 변경
	JMenuItem reservation = new JMenuItem("예약 대기 확인"); // 수락 대기중인 예약 현황을 확인하고 수락 / 거절 여부를 결정해야 한다.
	JMenuBar mb = new JMenuBar(); // 모든 메뉴를 포함하고 있는 메뉴 바
	
	String[] trainer_info = {"Trainer Number", "Trainer Name", "Trainer Gym", "Member No", "Class Time"}; // 트레이너 정보 (로그인한 본인의) 정보를 보여주기 위한 테이블
	String[] class_info = {"Student Number","Class Time", "Class Status"}; // 수업 정보 저장을 위한 테이블 (트레이너 화면의 메인 부분에는 트레이너의 모든 수업이 포함되어 있다.)
	String[] salary_info = {"Student No", "Salary"};
	
	// 트레이너 개인 정보 저장 테이블
	DefaultTableModel trainer_table = new DefaultTableModel(trainer_info,0);
	JTable trainer_jt = new JTable(trainer_table);
	// 수업 정보 테이블
	DefaultTableModel class_table = new DefaultTableModel(class_info, 0);
	JTable class_jt = new JTable(class_table);
	JScrollPane jsp = new JScrollPane(class_jt);
	// 수입 정보 테이블
	DefaultTableModel salary_table = new DefaultTableModel(salary_info, 0);
	JTable salary_jt = new JTable(salary_table);
	JPanel p = new JPanel();
	String[] comboName = {"예약완료", "취소", "불참", "완료", "거절"}; // 거절을 선택하면 데이터베이스에서 지워짐
	JComboBox combo = new JComboBox(comboName);
	
	
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC 연동 객체 생성
	
	public static Boolean login_success = true; // 트레이너 메뉴 화면으로 왔다는 것은 로그인 승인이 되었다는 뜻이다.
	public static String trainer_pk = null;
	public static String trainer_name = null;
	
	public static void main(String[] args) {
		new TrainerMenuJTable(trainer_pk);
	}
	public TrainerMenuJTable(String trainer_id) {
		super("트레이너 메뉴");
		trainer_pk = trainer_id;
		
		m.add(myPage);
		m.add(logout);
		m.add(salary);
		m.add(update);
		m.add(reservation);
		mb.add(m); // 메뉴바에 메뉴 추가
		setJMenuBar(mb); // 윈도우에 메뉴바 세팅
		
		
		p.add(combo);
		add(p, "South");
		tmdb.trainerInfoAll(trainer_table, trainer_id); // 마이 페이지 기능을 위해서 로그인한 트레이너의 모든 정보를 저장한다.
		tmdb.classInfoAll(class_table, trainer_id);
		tmdb.calculateSalary(salary_table, trainer_id);
		
		add(jsp, "Center");
	
		logout.addActionListener(this);
		salary.addActionListener(this);
		update.addActionListener(this);
		reservation.addActionListener(this);
		myPage.addActionListener(this);
		
		setBounds(200, 200, 400, 250);

		setResizable(false); // 화면 크기 고정하는 작업

		setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (class_table.getRowCount() > 0) {
			class_jt.setRowSelectionInterval(0, 0); // 첫번째 수업에 대해서 커서 설정 
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logout) {
			new TrainerJDialogGUI(this, "로그아웃");
			dispose();
		}
		else if (e.getSource() == myPage) {
			new TrainerJDialogGUI(this, "마이 페이지");
		}
		else if (e.getSource() == salary) {
			new TrainerJDialogGUI(this, "수입 계산기");
		}
		else if( (e.getSource() == reservation) || (e.getSource() == update) ){
			int row = class_jt.getSelectedRow();
			
			System.out.println("선택한 행 : " + row);
			String student_no = (String) class_jt.getValueAt(row, 0);
			String class_t = (String) class_jt.getValueAt(row, 1);
			String status = (String) class_jt.getValueAt(row, 2);
			String fieldName = combo.getSelectedItem().toString();
			tmdb.changeClassStatus(student_no, class_t, status, trainer_pk, fieldName);
			for (int i = 0;i<class_table.getRowCount();i++) {
				class_table.removeRow(0);
			}
			tmdb.classInfoAll(class_table, trainer_pk);
			TrainerJDialogGUI.MessageBox(this, "예약 현황 변경 되었습니다.");
		}
	
	}
		
}



