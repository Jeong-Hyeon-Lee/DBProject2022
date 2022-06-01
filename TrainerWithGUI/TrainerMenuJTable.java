package TrainerWithGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class TrainerMenuJTable extends JFrame implements ActionListener{
	JMenu m = new JMenu("Ʈ���̳� �޴�");
	// Ʈ���̳ʿ� �޴� ȭ���� ���ٴ� ���� �α��� �� ���¶�� ������ �����Ѵ�.
	JMenuItem myPage = new JMenuItem("����������"); // ���� ������ ��� �����ִ� ȭ��
	JMenuItem logout = new JMenuItem("�α׾ƿ�"); // �α׾ƿ� �ϴ� ��ư
	JMenuItem salary = new JMenuItem("���� ����"); // �� ���� ���� (ȸ������ �󸶳� ������ +  �� ������ ������)
	JMenuItem update = new JMenuItem("���� ��Ȳ üũ"); // ���� ���� / �Ϸ� ���� ����
	JMenuItem reservation = new JMenuItem("���� ��� Ȯ��"); // ���� ������� ���� ��Ȳ�� Ȯ���ϰ� ���� / ���� ���θ� �����ؾ� �Ѵ�.
	JMenuBar mb = new JMenuBar(); // ��� �޴��� �����ϰ� �ִ� �޴� ��
	
	String[] trainer_info = {"Trainer Number", "Trainer Name", "Trainer Gym", "Member No", "Class Time"}; // Ʈ���̳� ���� (�α����� ������) ������ �����ֱ� ���� ���̺�
	String[] class_info = {"Student Number","Class Time", "Class Status"}; // ���� ���� ������ ���� ���̺� (Ʈ���̳� ȭ���� ���� �κп��� Ʈ���̳��� ��� ������ ���ԵǾ� �ִ�.)
	String[] salary_info = {"Student No", "Salary"};
	
	// Ʈ���̳� ���� ���� ���� ���̺�
	DefaultTableModel trainer_table = new DefaultTableModel(trainer_info,0);
	JTable trainer_jt = new JTable(trainer_table);
	// ���� ���� ���̺�
	DefaultTableModel class_table = new DefaultTableModel(class_info, 0);
	JTable class_jt = new JTable(class_table);
	JScrollPane jsp = new JScrollPane(class_jt);
	// ���� ���� ���̺�
	DefaultTableModel salary_table = new DefaultTableModel(salary_info, 0);
	JTable salary_jt = new JTable(salary_table);
	JPanel p = new JPanel();
	String[] comboName = {"����Ϸ�", "���", "����", "�Ϸ�", "����"}; // ������ �����ϸ� �����ͺ��̽����� ������
	JComboBox combo = new JComboBox(comboName);
	
	
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC ���� ��ü ����
	
	public static Boolean login_success = true; // Ʈ���̳� �޴� ȭ������ �Դٴ� ���� �α��� ������ �Ǿ��ٴ� ���̴�.
	public static String trainer_pk = null;
	public static String trainer_name = null;
	
	public static void main(String[] args) {
		new TrainerMenuJTable(trainer_pk);
	}
	public TrainerMenuJTable(String trainer_id) {
		super("Ʈ���̳� �޴�");
		trainer_pk = trainer_id;
		
		m.add(myPage);
		m.add(logout);
		m.add(salary);
		m.add(update);
		m.add(reservation);
		mb.add(m); // �޴��ٿ� �޴� �߰�
		setJMenuBar(mb); // �����쿡 �޴��� ����
		
		
		p.add(combo);
		add(p, "South");
		tmdb.trainerInfoAll(trainer_table, trainer_id); // ���� ������ ����� ���ؼ� �α����� Ʈ���̳��� ��� ������ �����Ѵ�.
		tmdb.classInfoAll(class_table, trainer_id);
		tmdb.calculateSalary(salary_table, trainer_id);
		
		add(jsp, "Center");
	
		logout.addActionListener(this);
		salary.addActionListener(this);
		update.addActionListener(this);
		reservation.addActionListener(this);
		myPage.addActionListener(this);
		
		setBounds(200, 200, 400, 250);

		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (class_table.getRowCount() > 0) {
			class_jt.setRowSelectionInterval(0, 0); // ù��° ������ ���ؼ� Ŀ�� ���� 
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logout) {
			new TrainerJDialogGUI(this, "�α׾ƿ�");
			dispose();
		}
		else if (e.getSource() == myPage) {
			new TrainerJDialogGUI(this, "���� ������");
		}
		else if (e.getSource() == salary) {
			new TrainerJDialogGUI(this, "���� ����");
		}
		else if( (e.getSource() == reservation) || (e.getSource() == update) ){
			int row = class_jt.getSelectedRow();
			
			System.out.println("������ �� : " + row);
			String student_no = (String) class_jt.getValueAt(row, 0);
			String class_t = (String) class_jt.getValueAt(row, 1);
			String status = (String) class_jt.getValueAt(row, 2);
			String fieldName = combo.getSelectedItem().toString();
			tmdb.changeClassStatus(student_no, class_t, status, trainer_pk, fieldName);
			for (int i = 0;i<class_table.getRowCount();i++) {
				class_table.removeRow(0);
			}
			tmdb.classInfoAll(class_table, trainer_pk);
			TrainerJDialogGUI.MessageBox(this, "���� ��Ȳ ���� �Ǿ����ϴ�.");
		}
	
	}
		
}



