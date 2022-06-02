package DB2022Team03.TrainerWithGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrainerJDialogGUI extends JDialog implements ActionListener{
	// 폰번호 뒷자리(id대신으로 생각), pw, 이름, 소속 헬스장 번호 (소속 헬스장이 없다면 가입조차 못하게 되어 있음)
	JPanel pw = new JPanel(new GridLayout(6,1));
	JPanel pc = new JPanel(new GridLayout(6,1));
	JPanel ps = new JPanel();
	
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC 연동 객체 생성
	
	TrainerMenuJTable tm;
	public static String trainer_no = null;
	public static String trainer_name = null;
	public static String trainer_gym = null;
	
	public TrainerJDialogGUI(TrainerMenuJTable tm, String index) {
		
		super(tm, "트레이너 메뉴");
		
		trainer_no = (String) tm.trainer_table.getValueAt(0,0); // trainer_table 객체에서 로그인한 트레이너의 트레이너 번호를 불러온다.
		trainer_name = (String) tm.trainer_table.getValueAt(0, 1); // trainer_table 객체에서 로그인한 트레이너의 트레이너 이름을 불러온다.
		trainer_gym = (String) tm.trainer_table.getValueAt(0, 2); // trainer_table 객체에서 로그인한 트레이너의 소속 헬스장 이름을 불러온다.
		int student_num = (int) tm.trainer_table.getValueAt(0, 3); // trainer_table 객체에서 로그인한 트레이너의 총 학생 수를 불러온다.
		int class_t = (int)tm.trainer_table.getValueAt(0, 4);
		
		
		this.tm = tm;
		if (index.equals("로그아웃")) {
			MessageBox(this, trainer_name + "트레이너 님이 로그아웃 합니다.");
			tm.dispose();
			return;
		}
		else if (index.equals("수입 계산기")) {
			JTable salary_table = tm.salary_jt;
			JScrollPane jsp = new JScrollPane(salary_table);
			add(jsp, "Center");
			setSize(500, 500);
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		else if (index.equals("마이 페이지")) {
			JLabel label_trainerid = new JLabel("트레이너 번호 : " + trainer_no);
			JLabel label_name = new JLabel("트레이너 이름 : " + trainer_name);
			JLabel label_gym = new JLabel("소속 헬스장 번호 : " + trainer_gym);
			JLabel label_studentN = new JLabel("담당 회원 수 : " + Integer.toString(student_num));
			JLabel label_classT = new JLabel("수업 시간 : " + Integer.toString(class_t));
			
			pw.add(label_trainerid);
			pw.add(label_name);
			pw.add(label_gym);
			pw.add(label_studentN);
			pw.add(label_classT);
			
			add(pw, "West");
			setBounds(200, 200, 400, 250);
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnLabel = e.getActionCommand();
		if (btnLabel.equals("로그아웃")) {
			MessageBox(this, "로그아웃 합니다.");
			tm.dispose();
			System.exit(0);
		}
		
	}
	public static void MessageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component)obj, message);
	}
}
