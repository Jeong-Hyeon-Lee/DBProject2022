package TrainerWithGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrainerJDialogGUI extends JDialog implements ActionListener{
	// ����ȣ ���ڸ�(id������� ����), pw, �̸�, �Ҽ� �ｺ�� ��ȣ (�Ҽ� �ｺ���� ���ٸ� �������� ���ϰ� �Ǿ� ����)
	JPanel pw = new JPanel(new GridLayout(6,1));
	JPanel pc = new JPanel(new GridLayout(6,1));
	JPanel ps = new JPanel();
	
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC ���� ��ü ����
	
	TrainerMenuJTable tm;
	public static String trainer_no = null;
	public static String trainer_name = null;
	public static String trainer_gym = null;
	
	public TrainerJDialogGUI(TrainerMenuJTable tm, String index) {
		
		super(tm, "Ʈ���̳� �޴�");
		
		trainer_no = (String) tm.trainer_table.getValueAt(0,0); // trainer_table ��ü���� �α����� Ʈ���̳��� Ʈ���̳� ��ȣ�� �ҷ��´�.
		trainer_name = (String) tm.trainer_table.getValueAt(0, 1); // trainer_table ��ü���� �α����� Ʈ���̳��� Ʈ���̳� �̸��� �ҷ��´�.
		trainer_gym = (String) tm.trainer_table.getValueAt(0, 2); // trainer_table ��ü���� �α����� Ʈ���̳��� �Ҽ� �ｺ�� �̸��� �ҷ��´�.
		int student_num = (int) tm.trainer_table.getValueAt(0, 3); // trainer_table ��ü���� �α����� Ʈ���̳��� �� �л� ���� �ҷ��´�.
		int class_t = (int)tm.trainer_table.getValueAt(0, 4);
		
		
		this.tm = tm;
		if (index.equals("�α׾ƿ�")) {
			MessageBox(this, trainer_name + "Ʈ���̳� ���� �α׾ƿ� �մϴ�.");
			tm.dispose();
			return;
		}
		else if (index.equals("���� ����")) {
			JTable salary_table = tm.salary_jt;
			JScrollPane jsp = new JScrollPane(salary_table);
			add(jsp, "Center");
			setSize(500, 500);
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		else if (index.equals("���� ������")) {
			JLabel label_trainerid = new JLabel("Ʈ���̳� ��ȣ : " + trainer_no);
			JLabel label_name = new JLabel("Ʈ���̳� �̸� : " + trainer_name);
			JLabel label_gym = new JLabel("�Ҽ� �ｺ�� ��ȣ : " + trainer_gym);
			JLabel label_studentN = new JLabel("��� ȸ�� �� : " + Integer.toString(student_num));
			JLabel label_classT = new JLabel("���� �ð� : " + Integer.toString(class_t));
			
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
		if (btnLabel.equals("�α׾ƿ�")) {
			MessageBox(this, "�α׾ƿ� �մϴ�.");
			tm.dispose();
			System.exit(0);
		}
		
	}
	public static void MessageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component)obj, message);
	}
}
